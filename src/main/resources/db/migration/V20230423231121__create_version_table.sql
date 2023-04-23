CREATE TABLE version
(
    `id` VARCHAR(32) NOT NULL
        PRIMARY KEY COMMENT '版本ID',
    `number` INTEGER NOT NULL COMMENT '版本号',
    `log` TEXT NOT NULL COMMENT '更新日志',
    `proto_id` VARCHAR(32) NOT NULL COMMENT '迭代ID',
    `file_id` VARCHAR(32) NOT NULL COMMENT '文件ID',
    `created_by_user_id` VARCHAR(32) NOT NULL DEFAULT '1'  COMMENT '创建者用户ID',
    `updated_by_user_id` VARCHAR(32) NOT NULL DEFAULT '1' COMMENT '更新者用户ID',
    `created_time` datetime(6) NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6) NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '版本表';