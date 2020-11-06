package com.example.demo.infrastructure.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 集群管理使用的常量
 *
 * @author Luyouming
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClusterConstants {

    public static final Integer SSH_PORT = 22;

    public static final String SSH_USERNAME = "root";

    public static final String SSH_PASSWORD = "root";

    public static final String CMD_PREFIX = "source /etc/profile; echo ";

    public static final String CMD_POSTFIX = " | nc ";

    public static final String CMD_STAT = "stat";

    public static final String CMD_RUOK = "ruok";
}