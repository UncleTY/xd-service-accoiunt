create table t_menu (
`id` bigint primary key,
`name` varchar(128),
`path` varchar(128),
`icon` varchar(128),
`description` varchar(128),
`pid` bigint,
`page_path`varchar(128)
);
INSERT INTO `t_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`) VALUES (1, '主页', '/home', 'el-icon-house', '1', NULL, 'Home');
INSERT INTO `t_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`) VALUES (2, '系统管理', NULL, 'el-icon-s-grid', '2', NULL, NULL);
INSERT INTO `t_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`) VALUES (3, '用户管理', '/user', 'el-icon-user-solid', '3', 2, 'User');
INSERT INTO `t_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`) VALUES (4, '角色管理', '/role', 'el-icon-s-custom', '4', 2, 'Role');
INSERT INTO `t_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`) VALUES (5, '菜单管理', '/menu', 'el-icon-menu', '5', 2, 'Menu');
INSERT INTO `t_menu` (`id`, `name`, `path`, `icon`, `description`, `pid`, `page_path`) VALUES (6, '文件管理', '/file', 'el-icon-document', '6', 2, 'File');
