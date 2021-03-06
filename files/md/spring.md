### 随记知识点（书中或自己理解）
  - 服务对象和Repository分离，而不是将应用程序和数据访问耦合到一起，导致僵化设计，实现松耦合主要通过接口或Spring的Bean两种方式  
  - 纯粹的JPA方式远胜于基于模板的JPA（JpaTemplate）
  - spring data 依赖配置： https://github.com/spring-projects/spring-data-examples/tree/master/bom  
  - JPA里的repository就是传统的DAO，UserRepository 就是传统的UserDao，MyBatis里用Mapper，都是一个意思
  JPA里Repository只有接口，没有实现类
  
  - @NoRepositoryBean:
  一般用作父类的repository，有这个注解，spring不会去实例化该repository。  
  - springboot的Classpath目录：src/main/resources  
  - springbootinaction中50页，配置文件application.properties或application.yml的优先级，“当前目录的“/config”子目录”的当前  
  目录指的是src同一级别的目录，另外yml配置文件优先级高于properites文件  
  - @ConfigurationProperties注解是从properties配置文件中获取信息，但是该属性要有setter方法
  - springboot开发中 spring-boot-starter-thymeleaf依赖spring-boot-starter-web，所以在依赖中如果有thymeleaf就不用依赖web
### JdbcTemplate了解下

### SpringBoot相关
        spring.datasource.driverClassName=com.mysql.jdbc.Driver
        spring.datasource.url=jdbc:mysql://localhost:3306/test
        spring.datasource.username=root
        spring.datasource.password=root123
        以上为配置数据源
        通常无需指定JDBC驱动，springboot会帮助识别驱动，但是如果自动识别出现问题，就显示加上
        spring.datasource.drive-class-name=com.mysql.jdbc.Driver
        
        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jackson.serialization.indent_output=true
        (以上引自 java开发颠覆者springboot P266)
        
        
      springboot 搭建eureka 服务，pox.xml如下，如果没有加dependencyManagement，就不能引入相关jar
     <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.4.7.RELEASE</version>
    </parent>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Camden.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>
    </dependencies>
    
    
    spring开发时如果要用H2数据库，除了把H2加入到maven依赖之外，还要在application.properties里增加H2的配置,增加的目的是可以通过  
    localhost：8080/h2-console访问内存数据库
    spring.datasource.url=jdbc:h2:mem:myproject，配置h2数据库的连接地址
    spring.datasource.driver-class-name=org.h2.Driver，配置JDBC Driver
    spring.datasource.username=root，配置数据库用户名
    spring.datasource.password=123456，配置数据库密码
    
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    
    之后就可以通过浏览器访问H2数据库，访问：localhost：8080/h2-console,url\username\password都填入application.properties  
    的配置内容
    
    springboot项目如果想要启动时执行resources下的data.sql，可以配置如下：
```
spring:
   datasource:
     platform: mysql
     schema: classpath:schema.sql
     data: classpath:data.sql
```

### idea springboot 热部署
        1.添加依赖
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
         2.添加插件
         <build>
                <plugins>
                    <plugin>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-maven-plugin</artifactId>
                        <configuration>
                            <fork>true</fork>
                        </configuration>
                    </plugin>
                </plugins>
        </build>
        3. idea setting
        file->Settings->Build,Execution,Deplment->Compiler , 选择Build project automatically 点击OK按钮
        4. 组合键：Shift+ALT+Ctrl+/ ，选择“Registry”，回车，找到“complier.automake.allow.when.app.running” 
        
### 较新版(1.4.0之后的版本)的Spring Boot取消了@SpringApplicationConfiguration这个注解
        用@SpringBootTest就可以了
        @SpringBootTest
        @RunWith(SpringRunner.class)

### WebSecurityConfigurerAdapter
  springboot里的请求授权通常是扩展（extends）WebSecurityConfigurerAdapter，然后重写几个方法来完成

### maven
  maven工程的pom.xml文件的packaging标签有三个选项：pom、jar、war。pom表示该模块是个父模块，是被其他子模块继承的模块  
  maven子模块的依赖继承父模块的


### Jpa
  spring jpa mysql的配置方式  
  #Specify the DBMS  
  spring.jpa.database = MYSQL  
  #Show or not log for each sql query  
  spring.jpa.show-sql = true  
  #Hibernate ddl auto (create, create-drop, update)  
  spring.jpa.hibernate.ddl-auto = update  
  spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy  
  #stripped before adding them to the entity manager)  
  spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect  

  OneToMany注解一般要联合@JoinColumn注解使用，作用是外键约束的字段名称，  
  @JoinColumn(name = “order_id”)中的order_id为many一方的外键字段  
  如下例子有Order和OrderRecord两个entity，在spring启动时会自动创建两个表
```java
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private Long id;

    @OneToMany
    @JoinColumn(name = "order_id")
    private Set<OrderRecord> orderRecords;
}
@Entity
public class OrderRecord {
    @Id
    private Long id;
    private String name;
}
```
  使用show create table order_record\G; 查看表的详细信息
```sql  
  Create Table: CREATE TABLE `order_record` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq7tqcnthko0xae7xntn93n2ft` (`order_id`),
  CONSTRAINT `FKq7tqcnthko0xae7xntn93n2ft` FOREIGN KEY (`order_id`) REFERENCES `
orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```
  可见order_id是作为order_record的一个字段，并且该字段外键关联Order的id，而order表中，并没有orderRecords字段
  
  repository: 目前为止直接继承JpaRepository接口，提供的18个方法，已足够需求
  
### redis
  windows版本的下载链接：https://github.com/MicrosoftArchive/redis/releases  
  redis启动远程连接配置：要修改redis.windows.conf的配置  
  1. 注释掉bind 127.0.0.1  
  2. 将默认的protected-mode yes改为 protected-mode no  
  3. 启动服务器要带上配置文件： redis-server.exe redis.windows.conf  
  
  - 练习在一个普通的springboot+jpa应用中使用redis做缓存，用JavaEETest中的test22-jpa的基础上增加
  1. 增加依赖：
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-redis</artifactId>
        </dependency>
```        
        
   2. 配置：
```   
     spring.redis.host=localhost
     #Redis服务器连接端口
     spring.redis.port=6379
     #Redis服务器连接密码（默认为空）
     spring.redis.password=
```     
   3. 增加PersonSerice，注入PersonRepository,同时通过@CachePut、@CacheEvict、@Cacheable来进行缓存处理， 在controller中，注入PersonService  
     restful方法中调用PersonService
   4. Person 要implements Serializable
   5. Application类要加@EnableCaching注解
    
### SpringMvc
  源码分析：https://www.cnblogs.com/fangjian0423/p/springMVC-directory-summary.html
  SpringMVC工作原理之一：DispatcherServlet: https://www.cnblogs.com/tengyunhao/p/7518481.html
  
### 源码分析
  ClassPathXmlApplicationContext 
   spring的源码不是一时半会就就能看懂的，要不停的积累，经常看，把重要的类记下，没事看看，要能说清楚重要的类的功能  
   BeanFactory  
   ApplicationContext  
   WebApplicationContext  
   DefaultListableBeanFactory  
   XmlBeanFactory  
   XmlBeanDefinationReader  
   注册并解析BeanDefination　　
  
### DefaultListableBeanFactory
  源码分析：https://www.cnblogs.com/davidwang456/p/4187012.html，DefaultListableBeanFactory的类结构图在前面的源码分析里  
  DefaultListableBeanFactory 是整个spring ioc的始祖，研究透它的前生今世对我们理解spring ioc的概念有着重要的作用。  
  XmlBeanFactory对DefaultListableBeanFactory进行了扩展，只多了XmlBeanDefinitionReader类型的reader属性  
  
### springboot工程结构
  
```
src
├─main
│  ├─java
│  │  └─com
│  │      └─example
│  │          └─demo
│  │                  DemoApplication.java
│  │
│  └─resources
│      │  application.properties
│      │
│      ├─static
│      └─templates
└─test
    └─java
        └─com
            └─example
                └─demo
                        DemoApplicationTests.java
```
  
  
