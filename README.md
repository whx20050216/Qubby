# Qubby - 在线讨论平台

基于Spring Boot 3 + Thymeleaf + H2构建的实时讨论系统

## 项目概述

Qubby是一个功能完整的在线讨论平台，采用Spring Boot重构传统Servlet架构，实现用户认证、发帖讨论和实时回复功能。项目严格遵循IOC控制反转和MVC分层设计原则。

## 技术栈

- Spring Boot 3.5.9（核心框架，内置Tomcat）
- Spring Data JPA 3.5.9（数据持久化）
- Thymeleaf 3.1.3（模板引擎）
- H2 Database 2.3.232（内存数据库）
- Lombok 1.18.42（简化代码）
- JDK 21

## 核心功能

### 用户认证
- 用户注册（用户名唯一校验）
- 图形验证码登录防刷
- Session会话管理

### 讨论交互
- 发起新讨论（标题+内容）
- 讨论列表（按时间倒序，自动刷新）
- 实时回复（嵌套展示，局部刷新）

### 技术特性
- **IOC实现**：全程`@Autowired`注入，无硬编码实例化
- **MVC分层**：Controller→Service→Repository架构清晰
- **内存数据库**：H2自动建表，应用重启数据清空
- **实时交互**：AJAX局部刷新，无页面闪烁

## 项目结构

```
src/main/java/com/adplatform/qubby
├── QubbyApplication.java          # 启动类
├── controller/                    # 请求处理层
│   ├── LoginController            # 登录/验证码
│   ├── RegisterController         # 用户注册
│   ├── DiscussionController       # 讨论管理（含片段接口）
│   ├── ReplyController            # 回复管理
│   ├── CaptchaController          # 验证码生成
│   ├── HomeController             # 首页重定向
│   └── LogoutController           # 退出登录
├── service/                       # 业务逻辑层
│   ├── UserService                # 用户业务
│   └── DiscussionService          # 讨论业务
├── repository/                    # 数据访问层
│   ├── UserRepository
│   ├── DiscussionRepository
│   └── ReplyRepository
├── model/                         # 实体类
│   ├── User.java
│   ├── Discussion.java
│   └── Reply.java
└── util/
    └── CaptchaUtil.java           # 验证码工具

src/main/resources
├── application.properties         # 核心配置
└── templates/                     # Thymeleaf模板
    ├── login.html
    ├── register.html
    ├── discussionList.html        # 列表+自动刷新
    ├── discussionDetail.html      # 详情+自动刷新
    └── newDiscussion.html
```

## 关键实现

### IOC控制反转
```java
@Service
public class UserService {
    @Autowired  // Spring自动注入
    private UserRepository userRepository;
}
```

### MVC分层架构
```
浏览器请求 → Controller → Service → Repository → H2数据库
        ↑                                       ↓
        ←────────────── Thymeleaf渲染 ←──────────────
```

### 实时局部刷新
```javascript
// 讨论列表每10秒局部刷新
setInterval(() => {
  fetch('/discussion/list-fragment')
    .then(r => r.text())
    .then(html => {
      document.getElementById('listContainer').innerHTML = html;
    });
}, 10000);
```

## 快速开始

### 本地运行
1. Maven打包：`mvn clean package -DskipTests`
2. 运行JAR：`java -jar target/qubby.jar`
3. 访问：`http://localhost:8080/login`

### IDE运行
直接运行`QubbyApplication.java`，访问`http://localhost:8080/login`

## 服务器部署

```bash
# 1. 上传qubby.jar到服务器
mkdir -p /opt/qubby/apps

# 2. 检查JDK 21
java -version

# 3. 后台运行
cd /opt/qubby/apps
nohup java -jar qubby.jar > /dev/null 2>&1 &

# 4. 验证状态
ps aux | grep qubby
ss -tlnp | grep 8080  # 或配置的端口

# 5. 浏览器访问
http://服务器IP:8080/login
```

## 配置说明

### application.properties
```properties
# 应用端口
server.port=8080

# H2内存数据库
spring.datasource.url=jdbc:h2:mem:discussiondb
spring.jpa.hibernate.ddl-auto=update

# H2控制台（调试用）
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Thymeleaf
spring.thymeleaf.cache=false
```

### 端口冲突处理
如果8080被占用，修改为8081并重启：
```properties
server.port=8081
```

## 验收要点

✅ **IOC**：全程依赖注入  
✅ **MVC**：三层架构清晰  
✅ **功能**：注册/登录/发帖/回复完整实现  
✅ **体验**：局部刷新无闪烁  
✅ **部署**：独立JAR运行  
✅ **数据**：内存模式符合要求

## 已知限制
- H2内存数据库重启后数据清空（符合作业要求）
- 验证码图片依赖系统字体，某些Linux环境可能显示异常

## 许可证
MIT
