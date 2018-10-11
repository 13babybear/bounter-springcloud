# bounter-springcloud
基于 Spring Cloud 搭建的开发框架原型

模块说明：
- bounter-app-simon（微服务调用方）
- bounter-app-susan（微服务被调用方）
- bounter-common（公共工具）
- bounter-config-repo（配置中心仓库）
- bounter-config（配置中心）
- bounter-discovery（服务注册与发现）
- bounter-gateway（微服务网关）
- bounter-oauth2（OAuth2认证中心）

已实现的功能：
   - 配置中心（Spring Cloud Config）
   - 微服务网关（Zuul）
   - OAuth2认证中心（Spring Security OAuth2）
   - 服务注册与发现（Eureka）
   - 声明式REST调用（Feign）
   - 负载均衡（Ribbon）
   - 断路器（Hystrix）
   - 微服务跟踪（Sleuth + Zipkin）
   - 部署到Docker（dockerfile-maven-plugin）
   - 日志聚合（GrayLog + Logspout）
   
   
即将实现的功能：
  - 微服务监控（Spring Cloud Turbine）
  - 微服务控制台
  - 分布式事务
   
