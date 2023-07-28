CREATE TABLE stage
(
    `id` VARCHAR(32) NOT NULL
        PRIMARY KEY COMMENT '阶段ID',
    `title` VARCHAR(128) NOT NULL COMMENT '阶段名称',
    `code` VARCHAR(32) NOT NULL COMMENT '更新编码',
    `sort` INTEGER NOT NULL DEFAULT 0 COMMENT '排序',
    `created_by_user_id` VARCHAR(32) NOT NULL DEFAULT '1'  COMMENT '创建者用户ID',
    `updated_by_user_id` VARCHAR(32) NOT NULL DEFAULT '1' COMMENT '更新者用户ID',
    `created_time` datetime(6) NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6) NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '阶段表';
ALTER TABLE `version`
    ADD COLUMN `stage_id` VARCHAR(32) NULL COMMENT '阶段ID' AFTER `log`;