# 后端开发

## 后端结构

### package 结构

---

后端目前 package 的结构如下，

```
├── domain1
│   ├── resource
│   ├── model
│   ├── service
│   ├── repository
│   ├── database
│   ├── cache
│   ├── external
│   ├── servicestub
│   └── exception
│
├── domain2...
│
├── domain3...
│
├── security
│
└── util
```

每个域都有类似的结构，可以单独成为一个应用（为了将来的微服务化）。

### resource

---

资源，REST 风格当中的概念。

功能上类似于 controller，负责对请求的分发。

resource 只可以引用，

- model
- service
- 公用类

### model

---

业务域对象。核心对象。

### service

---

业务逻辑。

负责返回结果给 resource。

当需要域对象时，从 repository 取得。

service 只可以引用，

- model
- service
- repository
- 公用类

### repository

---

负责返回域对象给 service。这一层抽象避免了业务逻辑 service 直接与数据获取类耦合，

repository 可以用各种策略来管理从

- database
- cache
- external api
- servicestub

取得的数据。

比如说，repository 可以先尝试从 cache 获取的，若没有则从 database 或 external api 获得，
返回给 service 后，存入 cache。

由上，repository 只可以引用，

- model
- repository
- database
- cache
- external api
- servicestub
- 公用类

### database

---

从数据库获取数据。

database 只可以引用，

- model
- 公用类

### cache

---

缓存域对象。

cache 只可以引用，

- model
- 公用类

### external api

---

通过调用其他 Web Service 的 api 来获取数据。

external 只可以引用，

- model
- 公用类

### servicestub

---

相当于 mock 数据，一般用于在还没有其他 3 种可用的情况下，提供假的数据以供快速开发。

servicestub 只可以引用，

- model
- 公用类

---

### security

---

公用类，控制对资源的访问。

### util

---

公用工具类

### 构成图

---

![构成图](./design/structure.svg)
