ALTER TABLE `version`
    ADD COLUMN `demo_path` TEXT NOT NULL COMMENT '原型解压文件位置' AFTER `file_id`;