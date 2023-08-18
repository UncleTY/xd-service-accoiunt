create table t_role (
`id` bigint primary key,
`name` varchar(128),
`description` varchar(128),
`flag` varchar(32)
);
INSERT INTO `t_role` (`id`, `name`, `description`, `flag`) VALUES (1, '管理员', '管理员', 'ROLE_ADMIN');
INSERT INTO `t_role` (`id`, `name`, `description`, `flag`) VALUES (3, '普通用户', '普通用户', 'ROLE_USER');
INSERT INTO `t_role` (`id`, `name`, `description`, `flag`) VALUES (4, '会员用户', '会员用户', 'ROLE_VIP');
