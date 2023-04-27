ALTER TABLE `proto`
ADD COLUMN `last_version_number` INTEGER NULL COMMENT '最近版本' AFTER `status`,
ADD COLUMN `last_version_updated_time` datetime(6)  NULL COMMENT '最近版本更新时间' AFTER `status`,
ADD COLUMN `last_version_log` TEXT NULL COMMENT '最近版本更新日志' AFTER `status`,
ADD COLUMN `baseline_version_number` INTEGER NULL COMMENT '基线版本' AFTER `status`;