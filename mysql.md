### 数据库操作：
- 显示所有数据库：
```sql
show databases;
```
- 创建数据库：
```sql
create database database_name;
```
- 切换数据库：
```sql
use database_name;
```
- 查看创建好的数据库的定义：
```sql
show create database database_name\G;
```
- 删除数据库：执行删除数据库，该数据库中的数据一同被删除
```sql
drop database database_name;
```
### 表的操作
- 主键约束：
```sql
create table S(
	SID int primary key,
	SName char(25),
	SSex char(1),
	SProId int,
	SAge int
);
```
- 多字段联合主键：
```sql
create table S(
	SID int,
	SName char(25),
	SSex char(1),
	SProId int,
	SAge int,
	PRIMARY KEY(SID,SName)
);
```
- 外键约束：
```sql
create table department(
	id int(11) primary key,
	name varchar(22) not null,
	location varchar(50)
);
create table employee(
	id int(11) primary key,
	name varchar(25) unique,
	deptId int(11),
	salary FLOAT,
	CONSTRAINT fk_emp_dept FOREIGN KEY(deptId) REFERENCES department(id)
);
```

- 查看表结构：
```sql
 	desc table_name
```
- 查看表的详细结构：
```sql
	show create table table_name\G;
```
 ### 问题
 - mysql主从复制原理及流程
 - primary key 和 UNIQUE区别