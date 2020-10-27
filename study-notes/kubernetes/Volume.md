# Volume(存储卷)

## 概述
`Volume` 是Pod中能够被多个容器访问的共享目录。Kubernetes 的 Volume 概念、用途和目的与 Docker 的 Volume 比较类似，但两者不能等价。首先，Kubernetes 中的 Volume 定义在Pod上，然后被一个Pod里的多个容器挂载到具体的文件目录下；其次，Kubernetes 中的 Volume 中的数据也不会丢失。最后，Kubernetes 支持多种类型的Volume，例如Gluster、Ceph等先进的分布式文件系统。

## 使用
Volume 的使用也比较简单，在大多数情况下，我们先在Pod上声明一个 Volume，然后在容器里引用该 Volume 并 `Mount` 到容器里的某个目录上。举例来说，我们要給 Tomcat Pod 增加一个名字为 datavol 的 Volume，并且 Mount 到容器的 /mydata-data 目录上，则只要对Pod的定义文件做如下修正即可：
```yaml
template:
  metadata:
    labels:
      app: app-demo
      tier: frontend
  spec:
    volumes:
    - name: datavol
      emptyDir: {}
    containers:
    - name: tomcat-demo
      image: tomcat
      volumeMounts:
       - mountPath: /mydata-data
         name: datavol
      imagePullPolicy: IfNotPersent
```

## Volume类型
1. emptyDir  
一个 emptyDir Volume 是在Pod分配到Node时创建的，它的初始内容为空，并且无须指定宿主机上对应的目录文件，因为这是 Kubernetes 自动分配的一个目录，当Pod从Node上移除时，emptyDir 中的数据也会被永久删除。empty的一些用途如下：
- 临时空间，例如用于某些应用程序运行时所需的临时目录，且无须永久保留。
- 长时间任务的中间过程CheckPoint的临时保存目录。
- 一个容器需要从另一个容器中获取数据的目录（多容器共享目录）。

目前，用户无法控制 emptyDi r使用的介质种类。如果 kubelet 的配置是使用硬盘，那么所有 emptyDir 都将创建在该硬盘上。Pod在将来可以设置 emptyDir 是位于硬盘、固态硬盘上还是基于内存的tmpfs上，上面的例子便采用了 emptyDir 类的 Volume。

缺省情况下，EmptyDir 是使用主机磁盘进行存储的

2. hostPath  
hostPath为在Pod上挂载宿主机上的文件或目录，它通常可以用于以下几方面：
- 容器应用程序生成的日志文件需要永久保持时，可以使用宿主机的高速文件系统进行存储。
- 需要访问宿主机上Docker引擎内部数据结构的容器应用时，可以通过定义 hostPath 为宿主机 /var/lib/docker 目录，使容器内部应用可以直接访问Docker的文件系统。

在使用这种类型的Volume时，需要注意以下几点：
- 在不同的Node上具有相同配置的Pod可能会因为宿主机上的目录和文件不同而导致对Volume上目录和文件的访问结构不一致。
- 如果使用了资源配额管理，则Kubernetes无法将hostPath在宿主机上使用的资源纳入管理。

在下面对例子中使用宿主机的/data目录定义了一个hostPath类型的Volume：
```yaml
volumes:
- name: "persistent-storage"
  hostPath:
    path: "/data"
```

3. 其他类型的Volume  
- iscsi：使用iSCSI存储设备上的目录挂载到Pod中
- flocker：使用Flocker来管理存储卷
- glusterfs：使用开源GlusterFS网络文件系统的目录挂载到Pod中。
- rbd：使用Ceph块设备共享存储（Rados Block Device）挂载到Pod中。
- gitRepo：通过挂载一个空目录，并从GIT库clone一个git repository以供Pod使用。
- secret：一个secret volume用于为Pod提供加密的信息，你可以将定义在Kubernetes中的secret直接挂载的volume总是不会持久化的。
