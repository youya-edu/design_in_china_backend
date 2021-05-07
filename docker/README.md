## 如何启动？

确保你的文件夹的构造如下：
**design_in_china_backend**和**design_in_china_frontend**是在同一层级。

```
.
├── design_in_china
│   ├── design_in_china_backend
├───└── design_in_china_frontend
```

```shell
$ cd design_in_china
# 一定是这个命令，顺序不能变
$ docker-compose -f design_in_china_backend/docker/docker-compose.yml -f design_in_china_frontend/docker/docker-compose.yml up -d
```

### Play with docker

![play with docker](images/check-in-docker-gui.gif)

## Troubleshooting

### docker-compose up 无法启动 docker

```
Creating dic-mysql    ... error

Creating dic-frontend ... done
Only one usage of each socket address (protocol/network address/port) is normally permitted.

ERROR: for db  Cannot start service db: Ports are not available: listen tcp 0.0.0.0:3306: bind: Only one usage of each socket address (protocol/network address/port) is normally permitted.
ERROR: Encountered errors while bringing up the project.
```

启动时如果遇到了上述的问题，应该是你本地安装的 MySQL 服务没有关闭。
请停止你本地的 MySQL 服务再重新尝试。

### 访问 mysql 失败

```shell
$ mysql -u dic -p
Enter password: ***
ERROR 2026 (HY000): SSL connection error: unknown error number
```

查看你的 MySQL 版本。  
参考：<https://www.digitalocean.com/community/questions/error-2026-hy000-ssl-connection-error-unknown-error-number>

### git commit 失败

```
> Timeout waiting to lock file hash cache (/home/zyd/git-project/design_in_china/design_in_china_backend/.gradle/6.8.3/fileHashes). It is currently in use by another Gradle instance.
              Owner PID: 45
              Our PID: 9731
              Owner Operation:
              Our operation:
              Lock file: /home/zyd/git-project/design_in_china/design_in_china_backend/.gradle/6.8.3/fileHashes/fileHashes.lock
```

如果遇到上述问题，是因为 gradle 已经在 docker 中使用。  
但是 git commit 的 hook 也会使用 gradle，gradle 默认只能启动一个 instance，  
所以需要先停止 backend 的 docker 然后再提交代码。

## 如何在 IntelliJ 中 debug 代码？

参考资料：

- <https://qiita.com/Tomoyuki_Mikami/items/92f63e4e2b2241959f1e>
- <https://www.jetbrains.com/help/idea/run-and-debug-a-spring-boot-application-using-docker-compose.html#c8207df5>

![在IntelliJ中debug](images/debug-in-idea.gif)

## 最后

参考：<https://stackoverflow.com/questions/63503350/general-error-during-semantic-analysis-unsupported-class-file-major-version-57>

因为 gradle 的版本问题，暂时还不支持 Java16，所以暂时还是使用 Java11。
将来考虑换成最新版本的 Java。（不想重蹈 COMPANY 的 Java1.6 的覆辙）
