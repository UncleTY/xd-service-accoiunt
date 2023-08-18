create table t_user (
`id` bigint primary key,
`username` varchar(128),
`password` varchar(128),
`nickname` varchar(128),
`email` varchar(128),
`phone` varchar(128),
`address` varchar(128),
`create_time` timestamp,
`avatar_url` varchar(256),
`role` varchar(32)
);
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (2, 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', 'admin@qq.com', '18888888', '上海', '2022-08-31 18:05:55', 'http://localhost:8081/file/571f96608c984f2890eb645c7b47149c.jpg', 'ROLE_ADMIN');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (3, '曾爱佳', '', '小曾', 'zeng@qq.com', '18888666619', '福建', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (4, '蔡燕姿', '', '小蔡', 'cai@qqq.com', '16666888805', '福建', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (5, '吴晓冰', '', '小饼', 'wu@qq.com', '18866113325', '福建', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (6, '小白', '', 'nano', 'bai@qq.com', '19974564233', '福建', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (7, '张三', '', '失败的man', 'san@qq.com', '11048498545', '浙江', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (8, '菜宝坤', '', 'kun', 'kun@qq.com', '16668456123', '浙江', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (9, '吴诗琪', '', '小吴', 'wu@qq.com', '18986798640', '福建', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (10, '吴婷婷', '', '婷婷', 'wu123@qq.com', '16867645778', '湖南', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (11, '沈泽霖', '', '小霖', 'shen@qq.com', '18464546878', '江苏', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (12, '张诗煜', '', '小张', 'zhang@qq.com', '16687684654', '北京', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (13, '陈志文', '', '小陈', 'chen@qq.com', '16878345465', '上海', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (14, '孙悟空', '', '齐天大圣', 'hou@qq.com', '18888546499', '花果山', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (15, '猪八戒', '', '天蓬元帅', 'zhu@qq.com', '19992826334', '高老庄', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (16, '周星驰', '', '星仔', 'zhou@qq.com', '18886465666', '香港', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (17, '王五', '', '小王', 'wang@qq.com', '16686464648', '陕西', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (18, '韩涵', '', '小韩', 'han@qq.com', '15544662887', '浙江', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (19, '林雅南', '', '小林', 'lin@qq.com', '14556632587', '湖南', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (20, '夏志豪', '', '夏冰雹', 'xia@qq.com', '14798658666', '广东', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (21, '黄文隆', '', 'bcubu', 'huang@qq.com', '16489746456', '江苏', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (22, '孝欣娜', '', '爱神的箭', 'xiao@qq.com', '18676878668', '湖北', '2022-08-31 18:07:47', '', 'ROLE_USER');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (23, ' 李梦莹', '', 'natity', 'lisdsd@qq.com', '16548464384', '福建', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (24, '黄晓慧', '', 'prettyG', 'geallke@qq.com', '17985554853', '河北', '2022-08-31 18:07:47', '', 'ROLE_VIP');
INSERT INTO `t_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `address`, `create_time`, `avatar_url`, `role`) VALUES (25, '陈欣昕', '', '越努力越幸运', 'lucky@qq.com', '18798745468', '北京', '2022-08-31 18:07:47', '', 'ROLE_VIP');
