CREATE TABLE proto
(
    `id` VARCHAR(32) NOT NULL
        PRIMARY KEY COMMENT '迭代ID',
    `name` VARCHAR(128) NOT NULL COMMENT '迭代名称',
    `project_id` VARCHAR(32) NOT NULL COMMENT '项目ID',
    `status` VARCHAR(32) DEFAULT 'TO_DEVELOP' NOT NULL COMMENT '迭代状态，TO_DEVELOP-待开发，DEVELOPING-已开发，DEVELOPED-已开发',
    `created_by_user_id` VARCHAR(32) NOT NULL DEFAULT '1'  COMMENT '创建者用户ID',
    `updated_by_user_id` VARCHAR(32) NOT NULL DEFAULT '1' COMMENT '更新者用户ID',
    `created_time` datetime(6) NOT NULL COMMENT '创建时间',
    `updated_time` datetime(6) NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT '迭代表';