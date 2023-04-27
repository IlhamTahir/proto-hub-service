ALTER TABLE `proto`
    ADD COLUMN `last_version_id` VARCHAR(32) NULL COMMENT '最近版本ID' AFTER `status`;