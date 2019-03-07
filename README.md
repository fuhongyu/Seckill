# Seckill 项目描述
本项目实现电商系统中秒杀功能的简单实现

## 技术描述
Java语言、Spring+SpringMVC+Mybatis框架、RESTful编程规范、redis缓存服务器、MySQL数据库

## 页面展示
1、秒杀列表：
![秒杀列表](https://github.com/fuhongyu/Seckill/blob/master/img-folder/seckill-list.png)
2、开始秒杀：
![开始秒杀](https://github.com/fuhongyu/Seckill/blob/master/img-folder/time-to-seckill.png)
3、重复秒杀
![重复秒杀](https://github.com/fuhongyu/Seckill/blob/master/img-folder/repeat-seckill.png)
4、秒杀成功
![秒杀成功](https://github.com/fuhongyu/Seckill/blob/master/img-folder/success-seckill.png)
5、等待秒杀
![等待秒杀](https://github.com/fuhongyu/Seckill/blob/master/img-folder/wait-to-seckill.png)

## 高并发优化点：
1、使用redis缓存服务器，缓存秒杀商品，减少对数据库的访问压力。

2、使用存储过程替代sql语句，减少sql语句执行的网络传输时间，提高的数据库执行的QPS。
