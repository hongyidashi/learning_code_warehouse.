# Docker

## 目录
+ [常用命令](#常用命令)
+ [Docker镜像](#Docker镜像)
+ [容器数据卷](#容器数据卷)
+ [DockerFile](#DockerFile)
+ [Docker网络](#Docker网络)
+ [遇到的问题及解决方案](#遇到的问题及解决方案)

## <span id="常用命令">常用命令</span>
```shell script
# 进入容器
docker exec -it 容器id /bin/bash

# 查看镜像的元数据
docker inspect 容器id
```

## <span id="Docker镜像">Docker镜像</span>
docker的镜像实际上由一层一层的文件系统组成，这种从层级的文件系统UnionFS  
所有的docker镜像都起始于一个基础镜像层，当进行修改或增加新的内容时，就会在当前镜像层之上，创建新的镜像层  
Docker镜像都是只读的，当容器启动时，一个新的可写层被加载镜像的顶部！  

### commit镜像

```shell script
# 提交容器成为一个新的副本
docker commit 
# 命令和git原理类似
docker commit -m="提交的描述信息" -a="作者" 容器id 目标镜像名:[TAG]
```

## <span id="容器数据卷">容器数据卷</span>
容器之间可以有一个数据共享的技术！docker容器中产生的数据，同步到本地  
这就是数据卷技术，目录的挂载，将我们容器内的目录，挂载到Linux上面  
总结：容器的持久化和同步操作！容器间也是可以数据共享的  

### 使用数据卷
```shell script
docker run -it -v 主机目录:容器内目录 镜像id
```

### 匿名和具名挂载
```shell script
# 匿名挂载
-v 容器内路径 (不写主机的路径)
```
**所有的docker容器内的卷，没有指定目录的情况下都是在： /var/lib/docker/volumes/xxxx/_data**

通过具名挂载，可以方便的找到我们的一个卷，大多数情况下载使用的 **具名挂载**
```shell script
-v 容器内路径				#匿名挂载
-v 卷名:容器内路径	        #具名挂载
-v /宿主机路径:容器内路径 	#指定路径挂载
```

## <span id="DockerFile">DockerFile</span>
DockerFile就是用来构建docker镜像的构建文件  

### 构建步骤
- 编写一个dockerfile文件
- docker build 构建一个镜像
- docker run 运行镜像
- docker push 发布镜像（DockerHub\阿里云镜像仓库）

### DockerFile构建过程
基础知识：  
- 每个保留关键字（指令）都必须是大写字母
- 执行从上到下顺序执行
- \#表示注释
- 每个指令都是创建提交一个新的镜像层，并提交！

基础概念：
- DokcerFile: 构建文件，定义了一切的步骤，源代码
- DockerImages: 通过DockerFile构建生成的镜像，最终发布和运行的产品，原来是jar,war
- Docker容器： 容器就是镜像运行起来提供服务的

### DockerFile的指令
```shell script
FROM			# 基础镜像，就是一切从这里构建 centos
MAINTAINER		# 镜像是谁写的，姓名+邮箱
RUN				# 镜像构建的时候需要运行的命令
ADD				# 步骤，tomcat镜像，这个tomcat压缩包就是添加的内容
WORKDIR			# 镜像的工作目录
VOLUME			# 挂载的目录位置
EXPOSE			# 暴露端口配置  -p
CMD				# 指定这个容器启动的时候要运行的命令,只有最后一个会生效，可被替代
ENTRYPOINT		# 指定这个容器启动的时候要运行的命令,可以追加命令
ONBUILD			# 当构建一个被继承DockerFil,这个时候就会触发onbuild的指令
COPY			# 类似ADD命令，将我们文件拷贝到镜像中
ENV				# 构建的时候设置环境变量！ 比如说设置内存大小，mysql的密码
```

### 实战测试：创建一个自定义centos镜像
1. DockerFile编写 `mydockerfile-centos`
```docker
FROM centos
MAINTAINER 泓一大shi<1031749665@qq.com>

ENV MYPATH /usr/local
WORKDIR $MYPATH

RUN yum -y install vim
RUN yum -y install net-tools

EXPOSE 80

CMD echo $MYPATH
CMD echo "----------------end------------"
CMD /bin/bash
```

2. 构建镜像
`docker build -f mydockerfile-centos -t mycentos:1.0 .`  
最后面拿个 **.** 不能少

执行结果
>Successfully built 418805324d62  
>Successfully tagged mycentos:1.0

3. 运行镜像
`docker run -it 418805324d62`  
进去后会发现在`usr/local`目录，并且支持`ifconfig`和`vim`指令

## <span id="Docker网络">Docker网络</span>
### 网络模型
每启动一个docker容器，docker就会给容器分配一个ip,我们只要安装了Docker,就会有一个网卡docker0
桥接模式，使用的技术是 veth-pair 技术，
> veth-pair 就是一对的虚拟设备接口，它是成对出现的。一端连着协议栈，一端彼此相连着  
> veth-pair 充当一个侨联，连接各种虚拟网络设备

网络连接模型图：
![网络连接模型图](/study-notes/images/Docker-network.png)  
所有的容器不指定网络的情况下，都是由Docker0来路由的，docker会给每个容器分配一个可用 IP  
**只要容器删除，对应的网桥一对也就删除了！**  

### 自定义网络
查看所有docker网络
```
docker network ls

NETWORK ID          NAME                DRIVER              SCOPE
e7f3aa246f2f        bridge              bridge              local
7c4bb074e5de        host                host                local
3dd303b46bea        none                null                local
```

**网络模式**  
- bridge 桥接：在docker上搭桥（默认，自己创建也是用bridge模式）
- none : 不配置网络
- host : 主机，和宿主机共享网络
- container : 容器内网络连通（用的少，局限性很大）

**实战测试**  
1. 创建一个自己的网络
```shell script
# --driver bridge
# --subnet 192.168.0.0/16    支持范围： 192.168.0.2~192.168.255.255
# --gateway 192.168.0.1
docker network create --driver bridge --subnet 192.168.0.0/16 --gateway 192.168.0.1 mynet
docker network inspect mynet  # 查看自己定义的网络
```

2. 使用自定义网络启动Tomcat
```shell script
docker run -d -P --name tomcat-net-01 --net mynet tomcat
docker run -d -P --name tomcat-net-02 --net mynet tomcat
```

3. 查看自定义网络  
这时查看自定义网络就会发现`Containers`里多了两个Tomcat

4. 尝试自定义网络是否支持通过容器名来访问
```shell script
docker exec -it tomcat-net-01 ping tomcat-net-02
PING tomcat-net-02 (192.168.0.3) 56(84) bytes of data.
64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=1 ttl=64 time=0.085 ms
64 bytes from tomcat-net-02.mynet (192.168.0.3): icmp_seq=2 ttl=64 time=0.127 ms
```
结果应该是可以的

5. 总结
- 我们自定义网络Docker都已经帮我们维护好了对应的关系，推荐我们平时这样使用网络
- 好处：保证不同的集群使用不同的网络，保证集群是安全和健康的。因为都在不同的网段！！

### 网络连通
目标：使用docker0网络下的 tomcat-docker0 Ping 通 mynet 网络下的 tomcat-net-01

直接 Ping 肯定是不通的，因为是属于不同的网段下的两个容器  
![不同网段示意图](/study-notes/images/docker-net-no-ping.png)

**注意：这里说的 要打通 还不是两个网络直接打通（Docker0打通mynet）,而是 容器与网卡打通（tomcat-01 与 mynet 打通）！！！**

**开始操作**
```shell script
# 打通  docker network connect 网络名 容器名
docker network connect mynet tomcat-docker0
# 打通之后查看一下网络，会发现mynet里有tomcat-docker0
docker network inspect mynet
# 测试一下是否能Ping通，结果肯定是通的 :)
docker exec -it tomcat-docker0 ping tomcat-net-01
```

## <span id="遇到的问题及解决方案">遇到的问题及解决方案</span>
1. Cannot connect to the Docker daemon at unix:///var/run/docker.sock. Is the docker daemon running?  
解决：
```shell script
systemctl daemon-reload
systemctl restart docker.service
```

## <span id="安装Docker">安装Docker</span>
1. 安装
```shell script
1、卸载旧的版本
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
# 2、需要的安装包    
sudo yum install -y yum-utils

# 3、设置镜像的仓库
sudo yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo # 默认是从国外的！
    
    
sudo yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo  # 使用阿里云的，十分快
   
# 更新yum软件包索引
yum makecache fast

# 4、安装docker引擎 docker-ce 社区版    ee企业版
sudo yum install docker-ce docker-ce-cli containerd.io

# 5、启动docker
sudo systemctl start docker

```

2. 阿里云镜像加速，自己去阿里云上找