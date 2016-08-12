--程序数据库数据初始化脚本
-- 2016-03-15

--注意：初始数据结果集中由于直接查询数据，因此数据内容直接作为列名，如果数据内容重复，需要指定别名

-- 新建默认管理员账号（666, 123456），前台用户账号（888, 123456）
INSERT INTO PUBLIC.USERS 
    (MOBILE, NAME, PASSWORD, CREATETIME, STATUS, ROLE)
SELECT * FROM (
SELECT '666' as c1, '超级管理员' as c2, 'e10adc3949ba59abbe56e057f20f883e' as c3, '2016-03-15 15:14:21' as c4, 1 as c5, 2 as c6 union
SELECT '777' as c1, '后台运维' as c2, 'e10adc3949ba59abbe56e057f20f883e' as c3, '2016-03-15 15:14:21' as c4, 1 as c5, 0 as c6 union
SELECT '888' as c1, '手机用户1' as c2, 'e10adc3949ba59abbe56e057f20f883e' as c3, '2016-03-15 15:14:21' as c4, 1 as c5, 1 as c6
) WHERE NOT EXISTS(SELECT * FROM PUBLIC.USERS);