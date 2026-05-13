# SVT4J - 抖管家小视频管理工具服务端

基于 Spring Boot + H+ 后台框架开发的小视频管理工具服务端项目。

## 项目简介

本项目是一个针对抖音、快手等短视频平台的管理工具，提供设备管理、任务管理、直播热门监控等功能。

## 技术栈

- **框架**: Spring Boot 1.5.11.RELEASE
- **JDK**: 1.8
- **构建工具**: Maven
- **ORM**: MyBatis + TK.MyBatis
- **权限框架**: Apache Shiro
- **缓存**: Redis
- **消息队列**: RabbitMQ + MQTT
- **文件存储**: 阿里云 OSS
- **数据库**: MySQL
- **API文档**: Swagger 2

## 项目结构

```
svt4j/
├── admin/              # 后台管理模块（可独立运行）
├── apis/               # API接口模块（可独立运行）
├── common/             # 公共模块（基础依赖）
├── core/               # 核心业务模块
├── nettySocket/        # Netty Socket通信模块（可独立运行）
├── _doc/               # 文档和数据库脚本
├── lib/                # 第三方jar包
└── pom.xml             # 父工程POM
```

## 模块说明

### 依赖关系图

```
admin (后台管理)
    └── depends on core
apis (API接口)
    └── depends on core
core (核心业务)
    └── depends on common
nettySocket (Socket服务)
    └── independent module
common (公共基础)
    └── no dependencies
```

### 模块详情

#### 1. common 模块 (公共基础)

位置: `common/`

**功能**: 提供项目通用的工具类、配置、基类等基础组件

**主要目录结构**:
```
common/src/main/java/com/huimi/common/
├── annotation/          # 自定义注解
├── baseMapper/          # MyBatis基础Mapper
├── config/              # 配置类
│   └── xss/            # XSS防护配置
├── encode/              # 编解码工具
├── entity/              # 通用实体类
│   ├── authResult/     # 认证结果
│   ├── dtgrid/         # 表格组件
│   ├── export/         # 导出相关
│   └── extSysResult/   # 外部系统结果
├── enums/               # 枚举定义
├── google/              # Google相关工具
├── interceptor/         # 拦截器
├── mask/                # 数据脱敏
├── mybatis/             # MyBatis配置
├── page/                # 分页组件
├── tools/               # 工具类集合
│   └── excel/          # Excel工具
└── utils/               # 工具类
    ├── OSSClientUtils  # 阿里云OSS上传工具（配置在这里）
    ├── QRCodeUtil      # 二维码生成工具
    └── ...
```

**重要文件**:
- `OSSClientUtils.java`: 阿里云OSS上传工具类，OSS配置硬编码在此文件中

#### 2. core 模块 (核心业务)

位置: `core/`

**功能**: 包含核心业务逻辑、数据模型、数据访问层、服务层

**主要目录结构**:
```
core/src/main/java/com/huimi/core/
├── common/              # 通用组件
├── config/              # 配置类
│   └── mqtt2/          # MQTT配置
├── constant/            # 常量定义
│   ├── ConfigConsts    # 配置常量（注意：OSS相关常量未被使用）
│   └── ConfigNID       # 配置NID常量
├── entity/              # 实体类
├── exception/           # 异常定义
├── mapper/              # MyBatis Mapper接口
│   ├── bizApkHistory/  # APK历史
│   ├── bs/             # 业务基础
│   ├── cms/            # CMS内容管理
│   ├── Comment/        # 评论
│   ├── equipment/      # 设备
│   ├── liveHotSubTask/ # 热门子任务
│   ├── system/         # 系统管理
│   ├── task/           # 任务
│   └── tpl/            # 模板
├── model/               # 模型类
├── po/                  # 持久化对象
└── service/             # 业务服务层
    ├── apkHistory/     # APK历史服务
    ├── base/           # 基础服务
    ├── bs/             # 业务服务
    ├── cache/          # 缓存服务（Redis）
    ├── cms/            # CMS服务
    ├── comment/        # 评论服务
    ├── equipment/      # 设备服务
    ├── msg/            # 消息服务
    └── task/           # 任务服务
```

**Redis配置**: 图片服务器地址等配置存储在Redis中，通过 `ConfigNID.IMAGE_SEVER_URL` 获取

#### 3. admin 模块 (后台管理)

位置: `admin/`

**功能**: 提供后台管理系统的Web界面和API

**启动类**: `com.huimi.admin.AdminApplication`

**主要目录结构**:
```
admin/src/main/java/com/huimi/admin/
├── config/              # 配置类
│   ├── ShiroConfig     # Shiro权限配置
│   ├── MybatisConfig   # MyBatis配置
│   └── mqtt/           # MQTT相关配置
├── controller/          # 控制器层
│   ├── BaseController  # 基础控制器
│   ├── index/          # 首页
│   ├── login/          # 登录
│   ├── upload/         # 文件上传（包括OSS上传）
│   ├── permission/     # 权限管理（用户、角色、菜单）
│   ├── equipment/      # 设备管理
│   ├── tikTok/         # 抖音相关
│   │   ├── task/       # 任务管理
│   │   └── liveHotSubTask/ # 热门子任务
│   ├── kuaiShou/       # 快手相关
│   ├── comment/        # 评论管理
│   ├── setting/        # 系统设置
│   ├── template/       # 模板管理
│   └── ...
├── exception/           # 异常处理
├── quartz/              # 定时任务
├── tags/                # 自定义标签
├── ueditor/             # 富文本编辑器集成
└── utils/               # 工具类

admin/src/main/resources/
├── config/              # 配置文件
│   ├── application.yml         # 主配置
│   ├── application-test.yml    # 测试环境配置
│   └── application-prod.yml    # 生产环境配置
├── webapp/              # Web资源
│   └── template/       # FreeMarker模板
└── js/ueditor1.4.3.3/  # UEditor富文本编辑器
```

**上传接口**:
- `/file/doUploadPic`: 上传图片并生成二维码
- `/file/upload`: 普通文件上传
- `/file/imgFile`: 图片文件上传
- `/file/multi_upload`: 商品图片批量上传
- `/file/tree_multi_upload`: 树形商品图片批量上传

#### 4. apis 模块 (API接口)

位置: `apis/`

**功能**: 提供移动端/前端的API接口

**启动类**: `com.huimi.apis.ApisApplication`

**主要目录结构**:
```
apis/src/main/java/com/huimi/apis/
├── config/              # 配置类
│   └── aop/            # AOP切面
└── controller/          # 控制器层
    ├── common/         # 通用接口
    │   └── UploadController  # 文件上传接口（OSS）
    ├── equipment/      # 设备接口
    ├── pay/            # 支付接口
    ├── task/           # 任务接口
    ├── test/           # 测试接口
    └── v1/             # v1版本接口
```

**上传接口**:
- `/api/web/upload/doUploadH5`: H5上传（Base64）
- `/api/web/upload/doUploadIOS`: iOS上传（文件流）

#### 5. nettySocket 模块 (Socket通信)

位置: `nettySocket/`

**功能**: 基于Netty的Socket通信服务

**主要目录结构**:
```
nettySocket/src/main/java/com/huimi/nettySocket/
├── config/              # 配置类
├── context/             # 上下文管理
├── controller/          # 控制器
├── redis/               # Redis相关
├── server/              # Netty服务器
└── service/             # 服务层
```

#### 6. _doc 目录 (文档)

位置: `_doc/`

**内容**:
```
_doc/
├── small_video_tool.sql    # 数据库初始化脚本
├── hz_tz_zsj_prod.sql      # 生产数据库脚本
├── update.sql              # 升级脚本
├── settings_localhost.xml  # 本地配置
├── test.py                 # 测试脚本
└── easyCodeSettings.jar    # EasyCode插件配置
```

## OSS上传链路说明

### 涉及文件

1. **配置位置**: `common/src/main/java/com/huimi/common/utils/OSSClientUtils.java`
   - 硬编码的OSS配置（ENDPOINT、ACCESSKEYID、ACCESSKEYSECRET、BUCKETNAME等）

2. **管理后台上传**: `admin/src/main/java/com/huimi/admin/controller/upload/UploadJsonController.java`
   - 调用 `OSSClientUtils.uploadFile()` 上传文件到OSS
   - 从Redis获取 `IMAGE_SEVER_URL` 拼接返回URL

3. **API上传**: `apis/src/main/java/com/huimi/apis/controller/common/UploadController.java`
   - 调用 `OSSClientUtils.uploadFile()` 上传文件到OSS
   - 从Redis获取 `IMAGE_SEVER_URL` 拼接返回URL

4. **配置常量**: `core/src/main/java/com/huimi/core/constant/ConfigConsts.java`
   - 定义了OSS相关常量，但**当前未被使用**

### 替换OSS配置步骤

1. 修改 `common/src/main/java/com/huimi/common/utils/OSSClientUtils.java` 中的配置
2. 更新Redis中的 `IMAGE_SEVER_URL` 配置（通过后台管理系统或直接操作Redis）
3. 重新编译部署相关模块

## 快速开始

### 环境要求

- JDK 1.8+
- Maven 3.x
- MySQL 5.7+
- Redis
- RabbitMQ (可选)

### 安装步骤

1. **初始化数据库**
   ```bash
   # 执行 _doc/small_video_tool.sql
   ```

2. **配置应用**
   - 各模块的配置文件位于 `src/main/resources/config/`
   - 修改数据库、Redis、MQTT等配置

3. **编译项目**
   ```bash
   mvn clean install
   ```

4. **运行模块**
   ```bash
   # 启动后台管理
   cd admin && mvn spring-boot:run
   
   # 启动API服务
   cd apis && mvn spring-boot:run
   
   # 启动Socket服务
   cd nettySocket && mvn spring-boot:run
   ```

### 访问地址

- 后台管理系统: `http://localhost:8080/` (端口以实际配置为准)
- API文档(Swagger): `http://localhost:端口/swagger-ui.html`

## 主要功能

### 1. 权限管理
- 管理员用户管理
- 角色管理
- 菜单权限管理

### 2. 设备管理
- 设备分组管理
- 设备信息管理

### 3. 任务管理
- 抖音任务管理
- 快手任务管理
- 热门监控子任务

### 4. 内容管理
- 评论模板管理
- 通知模板管理
- 富文本编辑

### 5. 文件管理
- 图片上传（阿里云OSS）
- 二维码生成
- 批量文件上传

## 开发说明

### 新增模块

1. 创建模块目录
2. 在父pom.xml的 `<modules>` 中添加
3. 配置模块pom.xml，继承父POM
4. 按需依赖其他模块（通常依赖core或common）

### 代码规范

- 使用Lombok简化代码
- 使用TK.MyBatis简化数据访问
- Service层放在core模块
- Controller按功能分包
- 配置文件区分test/prod环境

## 版本历史

- **v1.0.0**: 初始版本

## 许可证

Copyright © 2023
