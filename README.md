# imooc_miaosha
imooc秒杀项目学习笔记

# 目录结构
- sql/   
    存放sql脚本
- jmeter/  
    存放jmeter测试文件


# 已完成章节
## 第1章-课程介绍及项目框架搭建
## 第2章-实现用户登录以及分布式session功能
学习到了不使用已集成的session插件, 通过cookie完成分布式session功能. 将cookie存放到redis中.
## 第3章-秒杀功能开发及管理后台
学习到了基础的商品下单流程, 查库存,查是否重复下单,减库存,创建订单等过程
## 第4章-秒杀压测-Jmeter压力测试
学习使用jmeter做压力测试, 学习jmeter变量定义, redis压力测试工具
## 第5章-页面级高并发秒杀优化（Redis缓存+静态化分离）
页面缓存,URL缓存 