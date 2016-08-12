--程序数据库数据初始化脚本
-- 2016-03-15

--注意：初始数据结果集中由于直接查询数据，因此数据内容直接作为列名，如果数据内容重复，需要指定别名

-- 新建默认管理员账号（666, 123456），前台用户账号（888, 123456）
MERGE INTO PUBLIC.USERS t
  USING ( VALUES
            ('666', '超级管理员', 'e10adc3949ba59abbe56e057f20f883e', '2016-03-15 15:14:21', 1, 2),
            ('777', '后台运维', 'e10adc3949ba59abbe56e057f20f883e', '2016-03-15 15:14:21', 1, 0),
            ('888', '手机用户1', 'e10adc3949ba59abbe56e057f20f883e', '2016-03-15 15:14:21', 1, 1))
  AS vals(c1,c2,c3,c4,c5,C6) ON t.MOBILE = vals.c1
  WHEN NOT MATCHED THEN
    INSERT (MOBILE,NAME,PASSWORD,CREATETIME,STATUS,ROLE) VALUES (vals.c1,vals.c2,vals.c3,vals.c4,vals.c5,vals.c6);