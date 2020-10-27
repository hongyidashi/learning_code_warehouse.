# Replication Controller

## 简介
Replication Controller（简称RC）是 Kubernetes 系统中的核心概念之一，简单来说，它其实是定义了一个期望的场景，即声明某种Pod的副本数量在任意时刻都符合某个预期值，所以RC的定义包括如下几个部分：
- Pod期待的副本数（replicas）。
- 用于筛选目标Pod的 Label Selector。
- 当Pod的副本数量小于预期数量时，用于创建新Pod的Pod模版（template）

## RC示例
下面是一个完整的RC定义的例子，即确保拥有`tier=frontend`标签的这个Pod（运行Tomcat容器）在整个 Kubernetes 集群中始终只有一个副本：
```yaml
apiVersion: v1
kind: ReplicationController
metadata:
  name: frontend
spec:
  replicas: 1
  selector:
    tier: frontend
template:
  metadata:
    labels:
      app: app-demo
      tier: frontend
  spec:
    containers:
     - name: tomcat-demo
       image: tomcat
       ports: 
       imagePullPolicy: IfNotPresent
       env:
       - name: GET_HOSTS_FROM
         value: dns
       ports:
      - containerPort: 80
```

当我们定义了一个RC并提交到 Kubernetes 集群中以后，Master节点上的 Controller Manager 组件就得到通知，定期巡检系统中当前存活的目标Pod，并确保目标Pod实例的数量刚好等于此RC的期望值，如果有过多的Pod副本在运行，系统就会停掉一些Pod，否则系统就会再自动创建一些Pod。可以说，通过RC，Kubernetes 实现了用户应用集群的高可用性，并且大大减少了系统管理员在传统IT环境中需要完成的许多手工运维工作（如主机监控脚本、应用监控脚本、故障恢复脚本等）。

在运行时，我们可以通过修改RC的副本数量，来实现Pod的动态缩放（Scaling）功能，还可以通过执行kubectl scale命令来一键完成：
```shell script
kubectl scale rc redis-slave --replicas=3
```

需要注意的是，删除RC并不会影响通过该RC已创建好的Pod。为了删除所有Pod，可以设置 replicas 的值为0，然后更新该RC。另外，kubectl 提供了stop和delete命令来一次性删除RC和RC控制的全部Pod

## Replica Set
由于 Replication Controller 与 Kubernetes 代码中的模块 Replication Controller 同名，同时这个词也无法准确表达它的本意，所以在 Kubernetes v1.2 时，它就升级成了另外一个新的概念--Replica Set，官方解释为“下一代的RC”，它与RC当前存在的唯一区别是：Replica Set 支持基于集合的 Label selector（Set-based selector），而RC只支持基于等式的 Label Selector（equality-based selector），这使得Replica Set的功能更强，下面是等价于之前RC例子的Replica Set的定义（省去了Pod模版部分的内容）：
```yaml
apiVersion: extensions/v1beta1
kind: ReplicaSet
metadata：
  name: frontend
spec:
  selector:
    matchLabels:
      tier: frontend
    matchExpression:
      - {key: tier, operator: In, values: [frontend]}
  template:
  .......
```

我们很少单独使用Replica Set，它主要被 Deployment 这个更高层的资源对象所使用，从而形成一整套Pod创建、删除、更新的编排机制。当我们使用Deployment时，无须关心它是如何创建和维护Replica Set的，这一切都是自动发生的。

## 滚动升级
当我们的应用升级时，通常会通过Build一个新的Docker镜像，并用新的镜像版本来替代旧的版本的方式达到目的，在系统升级的过程中，我们希望是平滑的方式，比如当前系统中10个对应的旧版本的Pod，最佳的方式是旧版本的Pod每次停止一个，同时创建一个新版本的Pod，在整个升级过程中，此消彼长，而运行中的Pod数量始终是10个，几分钟以后，当所有的Pod都已经是最新版本时，升级过程完成。通过RC的机制，Kubernetes很容易就实现了这种高级实用的特性，被称为“滚动升级”（Rolling Update）

## 总结
最后我们总结一下关于RC（Replica Set）的一些特性与作用：
- 在大多数情况下，我们通过定义一个RC实现Pod的创建过程及副本数量的自动控制。
- RC里包括完整的Pod定义模版。
- RC通过Label Selector机制实现对Pod副本的自动控制。
- 通过改变RC里的Pod副本数量，可以实现Pod的扩容或缩容功能。
- 通过改变RC里的Pod模版中的镜像版本，可以实现Pod的滚动升级功能。
