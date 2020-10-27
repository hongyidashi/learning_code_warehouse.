# StatefulSet

## 有状态服务
在Kubernetes系统中，Pod的管理对象RC、Deployment、DaemonSet和Job都是面向无状态的服务。但现实中有很多服务是有状态的，特别是一些复杂的中间件集群，例如MySQL集群、MongoDB集群、Kafka集群、Zookeeper集群等，这些应用集群有以下一些共同点：
- 每个节点都有固定的身份ID，通过这个ID，集群中的成员可以相互发现并且通信。
- 集群的规模是比较固定的，集群规模不能随意变动。
- 集群里的每个节点都是有状态的，通常会持久化数据到永久存储中。
- 如果磁盘损坏，则集群里的某个节点无法正常运行，集群功能受损。

## 原有管理对象存在的问题
如果用 RC/Deployment 控制Pod副本数的方式来实现上述有状态的集群，则我们会发现第一点是无法满足的，因为Pod的名字是随机产生的，Pod的IP地址也是在运行期才确定且可能有变动的，我们事先无法为每个Pod确定唯一不变的ID，为了能够在其他节点上恢复某个失败的节点，这种集群中的Pod需要挂接某种共享存储

## 引入StatefulSet
Kubernetes从v1.4版本开始引入了 PetSet 这个新的资源对象，并且在v1.5版本时更名为 StatefulSet，StatefulSet 从本质上来说，可以看作 Deployment/RC 的一个特殊变种，它有如下一些特性：
- StatefulSet 里的每个Pod都有稳定、唯一的网络标识，可以用来发现集群内的其他成员。假设 StatefulSet 的名字叫kafka，那么第一个Pod叫 kafak-0，第二个Pod叫 kafak-1，以此类推。
- StatefulSet 控制的Pod副本的启停顺序是受控的，操作第n个Pod时，前n-1个Pod已经时运行且准备好的状态。
- StatefulSet 里的Pod采用稳定的持久化存储卷，通过 PV/PVC 来实现，删除Pod时默认不会删除与 StatefulSet 相关的存储卷（为了保证数据的安全）

StatefulSet 除了要与PV卷捆绑使用以存储Pod的状态数据，还要与 Headless Service 配合使用，即在每个 StatefulSet 的定义中要声明它属于哪个 Headless Service。Headless Service与 普通Service的关键区别在于，它没有Cluster IP，如果解析 Headless Service 的DNS域名，则返回的是该 Service 对应的全部Pod的Endpoint列表。StatefulSet 在 Headless Service 的基础上又为 StatefulSet 控制的每个Pod实例创建了一个DNS域名，这个域名的格式为：  
`$(podname).$(headless service name)`  
比如一个3节点的Kafka的 StatefulSet 集群，对应的 Headless Service 的名字为kafka，StatefulSet 的名字为kafka，则StatefulSet里面的3个Pod的DNS名称分别为kafka-0.kafka、kafka-1.kafka、kafka-3.kafka，这些DNS名称可以直接在集群的配置文件中固定下来
