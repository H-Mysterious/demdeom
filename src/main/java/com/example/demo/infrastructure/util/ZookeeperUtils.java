package com.example.demo.infrastructure.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Luyouming
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZookeeperUtils {

    /**
     * 执行Zookeeper四字命令：stat
     *
     * @param ip   节点IP
     * @param port 节点端口
     * @return 节点状态信息
     */
    public static String stat(String ip, Integer port) throws IOException {
        String ret = exec(ip, port, ClusterConstants.CMD_STAT);
        String[] pairs = ret.split("\n");
        String mode = "";
        for (String pair : pairs) {
            if (pair.toUpperCase().startsWith("MODE")) {
                mode = pair.split(":")[1].trim();
                break;
            }
        }
        return StringUtils.trim(mode);
    }

    /**
     * 执行Zookeeper四字命令：ruok
     *
     * @param ip   节点IP
     * @param port 节点端口
     * @return 节点运行状态
     */
    public static String ruok(String ip, Integer port) throws IOException {
        return exec(ip, port, ClusterConstants.CMD_RUOK);
    }

    /**
     * 通过连接执行Zookeeper四字命令
     *
     * @param ip   节点IP
     * @param port 节点端口
     * @param cmd  命令
     * @exception IOException 连接异常
     * @return 执行结果
     */
    private static String exec(String ip, Integer port, String cmd) throws IOException {
        StringBuilder ret = new StringBuilder();
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(ip, port));
            try (InputStream in = socket.getInputStream();
                 OutputStream out = socket.getOutputStream()) {
                out.write(cmd.getBytes());
                byte[] buf = new byte[256];
                int i;
                while ((i = in.read(buf)) != -1) {
                    ret.append(new String(buf, 0, i));
                }
            }
            return ret.toString();
        }
    }
}