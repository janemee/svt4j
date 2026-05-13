---
name: feign-assistant
description: 查询spring boot项目中的Feign接口定义,或者按项目规范新增Feign接口定义
---

## 我可以做什么

- 使用yapi mcp服务查询相关apiId的信息
- 查询spring boot项目中的Feign接口的方法
- 按项目规范新增Feign接口

## 什么时候使用我

- 在开发spring boot项目时，需要通过feign调api时使用

## 工作流

## 工作流

1. 接口信息检索
- 如果识别到api path，直接搜索方法定义，搜索不到时，不新增接口定义，并提示需要提供apiId。
- 如果未识别到api path，则识别apiId，并使用 `yapi_get_interface_info` 工具，查询api信息。


2. 本地 Feign 匹配 (查询阶段)
- 在项目中执行代码搜索（使用 `grep` 或内置搜索工具）：
    - 搜索包含该 `path` 字符串的 Java 文件。
    - **匹配准则**：
        - 类上必须带有 `@FeignClient` 注解。
        - 方法上带有 `@RequestMapping`、`@GetMapping` 或 `@PostMapping` 等注解，且其 `value` 或 `path` 属性与 YApi 的 `path` 一致。（注意`@feignClient`中的path可以已经包含了一部分`path`）
- **返回结果**：
    - 如果找到，返回该方法的**全限定名称**（例如：`com.example.client.UserClient#getUserInfo`）。

### 3. Feign 代码生成 (缺失处理)
如果没有在项目中找到匹配的方法，请执行以下步骤：

- **定位目标类**：寻找与接口所属模块最相关的 `@FeignClient` 接口文件。
- **生成方法定义**：
    - **注解**：使用对应的 Spring MVC 注解。
    - **服务名**：api信息中的service_name即为FeignClient注解中的name。
    - **入参**：
        - 路径参数使用 `@PathVariable`。
        - 查询参数使用 `@RequestParam`。
        - 请求体使用 `@RequestBody`。
    - **返回值**：根据 `res_body` 结构生成对应的DTO。
- **规范说明**：
    - 使用项目中现有的统一response结构，如`Response<T>`或`Results<T>` 包装类规范。
    - 确保导入必要的包（如 `org.springframework.cloud.openfeign.FeignClient`）。

## 注意事项
- 在执行写操作前，务必先展示生成的代码片段并请求用户确认。
- 如果路径中包含动态参数（如 `/user/{id}`），搜索时需注意占位符匹配。
- 优先搜索 `**/infrastructure/acl/**` 目录下的接口定义。
