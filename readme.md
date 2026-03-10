# DaaS Boot - 基础开发框架

## 简介

DaaS Boot 是基于 Spring Boot 3.5 的基础开发框架，提供微服务开发所需的核心组件和工具类。

## 模块结构

```
daas-boot/
├── daas-dependencies/              # 依赖版本管理
├── daas-core/                      # 核心工具模块
├── daas-data/                      # 数据访问模块
│   ├── daas-data-core/             # 数据核心 - 基础实体类
│   ├── daas-data-mybatis-plus/     # MyBatis-Plus 增强
│   └── daas-data-hudi/             # Apache Hudi 大数据支持
├── daas-web/                       # Web 层模块
│   ├── daas-web-core/              # Web 核心 - 异常处理、工具类
│   ├── daas-web-rest/              # REST 控制器基类
│   └── daas-web-audit/             # 审计日志
├── daas-security/                  # 安全模块
│   ├── daas-security-core/         # 安全核心 - 用户实体、工具类
│   ├── daas-security-authentication/ # 认证组件
│   └── daas-security-authorization/  # 授权组件
├── daas-scheduler/                 # 调度模块
│   ├── daas-scheduler-common/      # 调度通用接口
│   └── daas-scheduler-powerjob/    # PowerJob 集成
├── daas-dataformat/                # 数据格式化
├── daas-exchange/                  # 数据交换 - API 日志
├── daas-id/                        # 分布式 ID 生成
├── daas-oss/                       # 对象存储
│   └── daas-oss-minio/             # MinIO 集成
└── daas-ops-metric/                # 运维监控指标
```

## 技术栈

| 组件 | 版本 |
|------|------|
| Spring Boot | 3.5.11 |
| Spring Framework | 6.2.16 |
| Spring Security | 6.5.8 |
| MyBatis-Plus | 3.5.12 |
| MySQL Connector | 9.2.0 |
| Redisson | 3.45.0 |
| Hutool | 5.8.37 |
| FastJSON2 | 2.0.53 |
| Guava | 33.0.0-jre |

## 核心功能

### 1. 基础实体类

**BaseEntity** - 基础实体
```java
@Data
public abstract class BaseEntity extends AbstractEntity {
    private String id;              // 主键
    private String createBy;        // 创建人
    private LocalDateTime createTime; // 创建时间
    private String updateBy;        // 更新人
    private LocalDateTime updateTime; // 更新时间
    private Integer updateCount;    // 更新次数
    private String deleteFlag;      // 删除标识
}
```

**BaseTreeEntity** - 树形实体
```java
@Data
public abstract class BaseTreeEntity<T> extends BaseEntity {
    private String parentId;        // 父节点ID
    private List<T> children;       // 子节点列表
}
```

### 2. REST 控制器基类

**BaseCRUDController** - 标准 CRUD 控制器
```java
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseCRUDController<User, UserVO, UserQuery> {
    // 自动拥有: 增删改查、分页查询、批量删除等功能
}
```

**BaseTreeCRUDController** - 树形 CRUD 控制器
```java
@RestController
@RequestMapping("/api/dept")
public class DeptController extends BaseTreeCRUDController<Dept, DeptVO, DeptQuery> {
    // 自动拥有: 树形查询、CRUD 等功能
}
```

### 3. 统一响应格式

```java
// 成功响应
RestResult.success("操作成功", data);

// 失败响应
RestResult.failure("操作失败");

// 分页响应
RestResult.page(list, total);
```

### 4. 全局异常处理

框架自动处理以下异常：
- 业务异常 (BizException)
- 参数校验异常 (MethodArgumentNotValidException)
- 权限异常 (AccessDeniedException)
- 认证异常 (AuthenticationException)

### 5. 审计日志

使用注解记录操作日志：
```java
@OperationLog(type = OperationType.CREATE, desc = "新增用户", businessId = "#result.data")
public RestResult<String> add(@RequestBody User user) {
    // ...
}
```

### 6. OAuth2 认证授权

提供完整的 OAuth2 授权服务器支持：
- 密码模式
- 客户端凭证模式
- 授权码模式
- 刷新令牌

## 快速开始

### 编译安装

```bash
mvn clean install -DskipTests
```

### 引用依赖

在项目中添加父 POM：

```xml
<parent>
    <groupId>cn.healthcaredaas</groupId>
    <artifactId>daas-boot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</parent>
```

或按需引入模块：

```xml
<dependency>
    <groupId>cn.healthcaredaas</groupId>
    <artifactId>daas-web-rest</artifactId>
</dependency>
```

## 模块依赖关系

```
daas-web-rest
    └── daas-web-core
            └── daas-core

daas-security-authorization
    └── daas-security-core
            └── daas-web-core

daas-data-mybatis-plus
    └── daas-data-core
            └── daas-core
```

## 开发规范

### 实体类定义

```java
@TableName("sys_user")
@Data
public class User extends BaseEntity {
    @TableField("username")
    private String username;

    @TableField("password")
    @JsonIgnore
    private String password;
}
```

### 服务层定义

```java
public interface UserService extends IBaseService<User> {
    User findByUsername(String username);
}

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, User>
        implements UserService {
    // ...
}
```

### 控制器定义

```java
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseCRUDController<User, UserVO, UserQuery> {

    @Override
    public RestResult<String> add(@RequestBody @Validated UserVO user) {
        // 自定义新增逻辑
        return super.add(user);
    }
}
```

## 版本历史

### v0.0.1-SNAPSHOT (2026-03-03)

#### 版本升级
- Spring Boot 升级到 3.5.11
- Spring Framework 升级到 6.2.16
- Spring Security 升级到 6.5.8
- MySQL Connector 升级到 9.2.0
- Redisson 升级到 3.45.0
- Hutool 升级到 5.8.37
- FastJSON2 升级到 2.0.53

#### 功能变更
- 移除多租户功能模块 (daas-tenant-*)
- 合并 web-protect 到 web-core
- 模块数量从 31 个优化到 22 个

## 许可证

Apache 2.0