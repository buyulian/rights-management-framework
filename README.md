# 一款轻量级的java web 权限管理框架 
* 在 Spring+SpringMVC+Mybatias+Maven工程上搭建
* 不需要巨大的学习代价
* 不需繁重的配置
* 实现了简单易用的权限管理功能

## 主要用法
* AuthenticationFilter过滤器主要负责对url进行全局的过滤。
* Authentication工具类提供了登录、退出、注册、md5加密、随机生成盐等一些必要的方法封装。
* Authentication工具类也用于对于一些全局配置权限所处理不了的特殊情况进行微型手术式的权限管理

