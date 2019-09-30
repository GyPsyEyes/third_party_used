CREATE TABLE `t_user` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) NOT NULL COMMENT '用户状态',
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户表';

CREATE TABLE `t_role` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '角色名称',
  `code` varchar(100) NOT NULL COMMENT '角色代码',
  `status` tinyint(4) NOT NULL COMMENT '角色状态',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '角色表';

CREATE TABLE `t_permission` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '权限名称',
  `code` varchar(100) NOT NULL COMMENT '权限代码',
  `status` tinyint(4) NOT NULL COMMENT '权限状态',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '权限表';

CREATE TABLE `t_user_role` (
  `id` varchar(100) NOT NULL,
  `user_id` varchar(100) NOT NULL COMMENT '用户ID',
  `role_id` varchar(100) NOT NULL COMMENT '角色ID',
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户角色表';

CREATE TABLE `t_role_permission` (
  `id` varchar(100) NOT NULL,
  `role_id` varchar(100) NOT NULL COMMENT '角色ID',
  `permission_id` varchar(100) NOT NULL COMMENT '权限ID',
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '角色权限表';

CREATE TABLE `t_tenant` (
  `id` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL COMMENT '租户名称',
  `status` tinyint(4) NOT NULL COMMENT '租户状态',
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '租户表';

CREATE TABLE `t_user_tenant` (
  `id` varchar(100) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `tenant_id` varchar(100) NOT NULL,
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户租户表';

CREATE TABLE `t_role_tenant` (
  `id` varchar(100) NOT NULL,
  `role_id` varchar(100) NOT NULL,
  `tenant_id` varchar(100) NOT NULL,
  `create_user_id` varchar(100) DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_user_id` varchar(100) DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '角色租户表';