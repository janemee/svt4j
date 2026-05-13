---
name: dst-design-guide
description: DST 项目设计指南。在进行项目开发和设计时，必须阅读并遵循本指南中的规范和约定。
---

# DST 项目设计指南 (DST Design Guide)

## Overview

本指南汇总了 DST 项目中的设计规范与约定。在进行需求分析、系统设计和编码实现时，**必须**阅读并遵循本指南，确保设计方案与项目现有规范保持一致。

---

## 1. 字典管理规范 (Dictionary Management)

### 背景

DST 通过**字典表 (`sys_dict`)** 统一管理枚举值，为前端提供下拉选框的选项和枚举值的翻译功能。

### 识别字典字段

#### 后端识别方式

如果 Java 实体/VO 的字段上标注了 `@Translation(dictType = "")` 注解，则该字段是一个**字典值字段**，`dictType` 的值即为对应的字典类型。

```java
// 示例：字段 orderType 使用字典类型 "order_type"
@Translation(dictType = "order_type")
private String orderType;
```

#### 前端识别方式

如果前端通过 `dict/getSimpleDictByType` 接口获取选项值，则该字段是一个**字典项**。

### 设计时的强制检查

在项目设计中，当遇到以下场景时，**必须**使用 `AskUserQuestion` 向用户确认是否使用字典或是否需要修改字典内容：

- 字段语义为**类型、类别、方式、状态**等枚举性质的字段
- 前端交互形式为**下拉选框 (Select)**、**单选 (Radio)**、**多选 (Checkbox)** 等选项类控件
- 新增或修改的字段值域为有限的、可枚举的值集合

### 确认内容

向用户确认时，应包含以下要点：

1. **是否使用字典**：该字段是否通过 `sys_dict` 管理，还是使用代码中的枚举类
2. **字典类型 (type)**：如果使用字典，对应的 `type` 值是什么（需检查是否已有相同类型）
3. **字典项内容**：需要哪些字典项（`value` 和 `name` 的对应关系）
4. **是否需要新增/修改字典项**：是否需要在现有字典类型中增加新选项或调整已有选项

### 提供SQL脚本

**如果需要新增或编辑字典时，必须提供对应的SQL脚本**

### 字典表结构

```sql
CREATE TABLE sys_dict
(
    id            INT AUTO_INCREMENT COMMENT '主键ID' PRIMARY KEY,
    p_id          INT       DEFAULT 0                 NULL COMMENT '父级ID',
    type          VARCHAR(100)                        NOT NULL COMMENT '类型',
    value         VARCHAR(600)                        NOT NULL COMMENT '数据值',
    name          VARCHAR(100)                        NOT NULL COMMENT '字典名称',
    description   VARCHAR(100)                        NULL COMMENT '描述',
    sort          INT                                 NULL COMMENT '排序（升序）',
    state         INT(1)                              NULL COMMENT '状态 0正常 1禁用',
    creator       BIGINT                              NULL COMMENT '创建人id',
    created_time  BIGINT                              NULL COMMENT '创建时间',
    modifier      BIGINT                              NULL COMMENT '修改人id',
    modifier_time BIGINT                              NULL COMMENT '更新时间',
    bak_desc      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'baktime'
) COMMENT '字典表';
```

### 字典使用的关键字段说明

| 字段    | 说明                                       |
| ------- | ------------------------------------------ |
| `type`  | 字典类型标识，同一类型下包含多个字典项     |
| `value` | 字典项的值，存储在业务表中的实际值         |
| `name`  | 字典项的显示名称，前端展示用               |
| `p_id`  | 父级 ID，用于构建树形字典结构（0 表示顶级）|
| `sort`  | 排序字段，控制前端下拉选项的显示顺序       |
| `state` | 状态：0 正常，1 禁用                       |

---

## 附录：指南扩展

本指南将持续更新。如有新的设计规范或约定，请按照以下格式添加新章节：

```
## N. 规范名称 (English Name)

### 背景
### 识别方式
### 设计时的强制检查
### 具体规范内容
```
