# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

RuoYi-Vue v3.9.2（若依管理系统）— 基于 Spring Boot 3.5 + JDK 17 的前后端分离 Java 快速开发框架（springboot3
分支）。本仓库仅含后端；前端在独立的 RuoYi-Vue2/Vue3 仓库。认证采用 JWT + Spring Security + Redis。

## 常用命令

```bash
# 完整构建（在仓库根目录）
mvn clean package

# 跳过测试构建（本项目当前没有测试，两种等效）
mvn clean package -DskipTests

# 启动应用（主类 com.ruoyi.RuoYiApplication，位于 ruoyi-admin）
mvn -pl ruoyi-admin spring-boot:run
# 或打包后运行
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

- 本仓库没有单元测试（无 `src/test`），`mvn test` 不执行任何内容。
- Maven 仓库使用阿里云镜像（见根 pom）。

## 运行前提

1. MySQL：执行 `sql/ry_sys.sql` 和 `sql/quartz.sql`，数据库名 `ry-vue`（见 `application-druid.yml`）。
2. Redis：`localhost:6379`（见 `application.yml`），登录态、验证码、字典缓存、防重提交等全部依赖 Redis，不可用则启动失败。
3. 应用端口 8080；API 文档在 `/swagger-ui.html`。

## 模块结构与依赖方向

Maven 多模块，依赖严格单向：

```
ruoyi-admin     启动模块 & Controller 层（web.controller.system/monitor/tool/common）
  ↓
ruoyi-framework 框架核心：Security 配置、AOP 切面、动态数据源、线程池、Redis/MyBatis/Druid 配置
  ↓
ruoyi-system    业务模块：domain/mapper/service（用户、角色、菜单、部门、字典、日志等）
  ↓
ruoyi-common    通用层：核心类、注解、工具、常量、异常

ruoyi-quartz     定时任务（依赖 common，由 admin 引入）
ruoyi-generator  代码生成器（依赖 common，由 admin 引入；Velocity 模板在 src/main/resources/vm）
```

- 新增业务代码通常放 `ruoyi-system`（或新建模块），Controller 放 `ruoyi-admin`，不要反向依赖。
- Mapper XML 位于各模块 `src/main/resources/mapper/**/*Mapper.xml`，由 `application.yml` 的 `mapperLocations` 统一扫描。

## 关键架构机制

### 请求与响应约定

- 统一返回 `AjaxResult`（单条）/`TableDataInfo`（分页列表），分页用 `startPage()`（PageHelper，只对紧跟的第一条查询生效）。
- Controller 基类 `BaseController`（ruoyi-common `core.controller`）封装了分页、当前登录用户获取等。

### 认证与权限

- JWT filter (`JwtAuthenticationTokenFilter`) → Token 有效期 30 分钟，存储在 Redis（`token` 配置段）。
- 权限注解：`@PreAuthorize("@ss.hasPermi('system:user:list')")` — `@ss` 指向 `PermissionService`；按钮级权限走 `hasPermi`
  ，角色走 `hasRole`。
- 允许匿名访问的接口加 `@Anonymous` 注解。

### AOP 注解（切面在 ruoyi-framework `aspectj`，注解定义在 ruoyi-common `annotation`）

- `@Log` — 操作日志记录（异步，存 sys_oper_log）。
- `@DataScope(deptAlias=..., userAlias=...)` — 数据权限，按角色 dataScope 动态拼接 SQL 到实体 `params.dataScope`，Mapper
  XML 中需写 `${params.dataScope}`。
- `@DataSource(DataSourceType.XXX)` — 主从数据源切换（master/slave），基于动态数据源；slave 默认未配置。
- `@RateLimiter`、`@RepeatSubmit` — 限流与防重复提交（Redis 实现）。
- `@Excel` — 导出 Excel 字段映射。

### 代码生成器（ruoyi-generator）

- 读取数据库表元数据（gen_table/gen_table_column），用 Velocity 模板（`vm/java`、`vm/vue`、`vm/xml`、`vm/sql`）生成完整 CRUD
  前后端代码。修改生成逻辑需改模板和 `GenTableServiceImpl`。

## 其他

- 国际化资源在 `ruoyi-admin/src/main/resources/i18n/`；异常消息通过 `MessageUtils` 取值。
- XSS 过滤、防盗链（referer）、文件上传路径（`ruoyi.profile`）等开关在 `application.yml`。
