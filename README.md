# SVT4J - 抖管家小视频管理工具服务端

基于 Spring Boot 的多平台小视频营销管理系统，支持抖音、快手、火山等平台的任务管理和设备控制。

## 技术栈

| 组件 | 版本 |
|------|------|
| Spring Boot | 2.7.18 |
| Java | 1.8 |
| MyBatis | (with tk.mybatis 2.0.4) |
| MySQL | (mysql-connector-java) |
| Redis | (spring-boot-starter-data-redis) |
| Netty | 4.1.17.Final |
| Lombok | 1.18.30 |
| Swagger | 2.8.0 |
| FastJSON | 1.2.47 |
| OkHttp3 | 3.6.0 |
| Apache POI | 3.17 |
| Hutool | 5.0.5 |
| Shiro | 1.4.0 |

## 项目结构

项目采用多模块 Maven 架构，包含 5 个主要模块：

```
svt4j (root, pom packaging)
├── common (基础工具模块)
│   └── core (核心业务模块，依赖 common)
│       ├── admin (管理后台服务，依赖 core)
│       ├── apis (API 接口服务，依赖 core)
│       └── nettySocket (WebSocket 实时通讯服务，依赖 core)
```

### 模块说明

| 模块 | 端口 | 说明 |
|------|------|------|
| **admin** | 9080 | 管理后台服务 - 任务配置、设备管理、权限管理 |
| **apis** | 9060 | API 接口服务 - 提供给前端/设备端的 REST API |
| **nettySocket** | 9050 | WebSocket 实时通讯服务 - 实时消息推送 |
| **core** | - | 核心业务模块 - 业务逻辑和数据访问 |
| **common** | - | 公共模块 - 通用工具和基础设施 |

### 架构层次

```
┌─────────────────────────────────────────┐
│   前端 (H5 / APP / 管理后台)            │
└────────────────┬────────────────────────┘
                 │
         ┌───────┴───────┐
         │               │
    ┌────▼────┐   ┌─────▼─────┐   ┌──────────────┐
    │  admin  │   │   apis    │   │  nettySocket │
    │ (9080)  │   │   (9060)  │   │    (9050)    │
    └────┬────┘   └─────┬─────┘   └───────┬──────┘
         │              │                 │
         └──────────────┼─────────────────┘
                        │
                  ┌─────▼─────┐
                  │   core    │
                  │  (业务层) │
                  └─────┬─────┘
                        │
                  ┌─────▼─────┐
                  │  common   │
                  │ (工具层)  │
                  └───────────┘
                        │
         ┌──────────────┼──────────────┐
         │              │              │
    ┌────▼────┐   ┌────▼─────┐   ┌────▼────┐
    │  MySQL  │   │  Redis   │   │  MQTT   │
    └─────────┘   └──────────┘   └─────────┘
```

## 核心功能

### 1. 多平台小视频任务管理
- 抖音任务管理
- 快手任务管理
- 火山任务管理
- 直播热门子任务

### 2. 设备管理
- 设备信息管理
- 设备分组管理
- 设备状态监控
- MQTT 设备通讯

### 3. 实时通讯
- 基于 Netty 的 WebSocket
- Redis 消息监听
- 实时消息推送

### 4. 权限管理
- 基于 Shiro 的 RBAC
- 管理员管理
- 角色管理
- 菜单权限

### 5. 支付集成
- 支付宝支付
- 微信支付
- YY 支付

### 6. 内容管理
- Banner 广告管理
- 公告管理
- 评论模板管理
- 协议管理

### 7. 其他功能
- 短信服务
- 文件上传 (阿里云 OSS)
- 二维码生成
- 数据导出

## 数据库实体

| 模块 | 实体类 | 描述 |
|------|--------|------|
| **system** | `Admin` | 管理员 |
| | `Role` | 角色 |
| | `Menu` | 菜单 |
| | `Conf` | 配置 |
| | `Dict` | 字典 |
| | `NoticeTemplate` | 通知模板 |
| **equipment** | `Equipment` | 设备 |
| | `EquipmentGroup` | 设备组 |
| **task** | `Task` | 任务 |
| | `TaskDetails` | 任务详情 |
| **cms** | `Banner` | Banner 广告 |
| | `Announcement` | 公告 |
| **comment** | `CommentTemplate` | 评论模板 |
| **user** | `Users` | 用户 |
| **other** | `BizApkHistory` | APK 历史 |
| | `Protocol` | 协议 |

## 快速开始

### 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 3.0+
- MQTT Broker (可选)

### 配置说明

各模块使用多环境配置文件：
- `application.yml` - 主配置
- `application-dev.yml` - 开发环境
- `application-test.yml` - 测试环境
- `application-prod.yml` - 生产环境

### 构建运行

```bash
# 编译项目
mvn clean install -DskipTests

# 运行各模块
cd admin && mvn spring-boot:run
cd apis && mvn spring-boot:run
cd nettySocket && mvn spring-boot:run
```

## 升级记录

### 2026-05-14
- Spring Boot: 1.5.11.RELEASE → **2.7.18**
- Lombok: 1.16.14 → **1.18.30**

## License

本项目仅供学习和研究使用。
