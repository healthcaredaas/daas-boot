# daas-boot - 基础框架

## 项目概述

daas-boot 是 DataSphere 项目的基础框架，提供通用组件、工具类和基础功能支持。

## 模块结构

```
daas-boot/
├── daas-core/              # 核心模块
│   ├── daas-core-common/   # 公共工具类
│   └── daas-core-web/      # Web 组件
│
├── daas-data/              # 数据模块
│   ├── daas-data-core/     # 数据核心
│   └── daas-data-mybatis/  # MyBatis 增强
│
└── daas-security/          # 安全模块
    ├── daas-security-core/ # 安全核心
    └── daas-security-jwt/  # JWT 认证
```

## 核心模块

### daas-core-common

公共工具类和基础组件：

- 日期工具：DateFormatterConstant, DateUtil
- 字符串工具：StringUtil
- 集合工具：CollectionUtil
- 异常定义：BusinessException
- 常量定义：通用常量

### daas-core-web

Web 相关组件：

- 统一响应：RestResult
- 控制器基类：BaseController
- 全局异常处理：GlobalExceptionHandler
- 跨域配置：CorsConfig

### daas-data-core

数据相关核心组件：

- 实体基类：BaseEntity, BaseTreeEntity
- 分页封装：PageResult
- 数据填充：MetaObjectHandler

### daas-security

安全认证模块：

- JWT 生成和验证
- 权限注解支持
- 用户认证信息获取

## 关键类说明

### BaseEntity

所有实体类的基类，包含基础审计字段：

```java
@Data
public abstract class BaseEntity extends AbstractEntity {
    @TableId(value = "id")
    private String id;

    @TableField(value = "create_by")
    private String createBy;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_count", update = "%s+1")
    private Integer updateCount;

    @TableField(value = "update_by")
    private String updateBy;

    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "delete_flag", fill = FieldFill.INSERT)
    private String deleteFlag;

    @TableField(value = "delete_time")
    private LocalDateTime deleteTime;
}
```

### RestResult

统一响应封装：

```java
@Data
public class RestResult<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> RestResult<T> success(T data) {
        RestResult<T> result = new RestResult<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> RestResult<T> error(String message) {
        RestResult<T> result = new RestResult<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
}
```

### BaseController

控制器基类，提供统一响应方法：

```java
public class BaseController {
    protected <T> RestResult<T> success(T data) {
        return RestResult.success(data);
    }

    protected <T> RestResult<T> error(String message) {
        return RestResult.error(message);
    }

    protected <T> RestResult<PageResult<T>> success(IPage<T> page) {
        // 分页结果封装
    }
}
```

## 使用方式

### 在其他项目中引用

```xml
<dependency>
    <groupId>cn.healthcaredaas.data.cloud</groupId>
    <artifactId>daas-core-common</artifactId>
    <version>${project.version}</version>
</dependency>

<dependency>
    <groupId>cn.healthcaredaas.data.cloud</groupId>
    <artifactId>daas-data-core</artifactId>
    <version>${project.version}</version>
</dependency>
```

### 实体类继承 BaseEntity

```java
@Data
@TableName("sys_user")
public class User extends BaseEntity {
    private String username;
    private String realName;
    private String email;
}
```

### 控制器继承 BaseController

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends BaseController {

    @GetMapping
    public RestResult<PageResult<User>> page() {
        IPage<User> page = userService.page();
        return success(page);
    }
}
```

## 构建命令

```bash
# 编译
mvn clean compile

# 打包
mvn clean package -DskipTests

# 安装到本地仓库
mvn clean install -DskipTests
```

## 注意事项

1. 修改 daas-boot 后，需要先 `mvn install` 安装到本地仓库
2. 其他项目引用 daas-boot 时，版本号要一致
3. BaseEntity 的审计字段会自动填充，无需手动设置