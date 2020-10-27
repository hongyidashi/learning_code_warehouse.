# Label（标签）

## 简介
Label 是Kubernetes系统中另外一个核心概念。一个Label是一个`key=value`的键值对，其中 key 与 vauel 由用户自己指定。Label 可以附加到各种资源对象上，例如 Node、Pod、Service、RC 等，一个资源对象可以定义任意数量的 Label，同一个 Label 也可以被添加到任意数量的资源对象上去，Label 通常在资源对象定义时确定，也可以在对象创建后动态添加或者删除

一些常用等label示例如下：  
- 版本标签："release" : "stable" , "release" : "canary"...
- 环境标签："environment" : "dev" , "environment" : "production"
- 架构标签："tier" : "frontend" , "tier" : "backend" , "tier" : "middleware"
- 分区标签："partition" : "customerA" , "partition" : "customerB"...
- 质量管控标签："track" : "daily" , "track" : "weekly"

给某个资源对象定义一个Label，随后可以通过 `Label Selector（标签选择器）`查询和筛选拥有某些 Label 的资源对象，Kubernetes 通过这种方式实现了类似 SQL 的简单又通用的对象查询机制。

## 示例
1. 在Pod中定义label
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myweb
  labels:
      app: myweb
```

2. 管理对象RC和Service在spec中定义Selector与Pod进行关联：
```yaml
apiVersion: v1
kind: ReplicationController
metadata:
  name: myweb
spec:
  replicas: 1
  selector:
    app: myweb
  template:
  ...略...

---

apiVersion: v1
kind: Service
metadata:
  name: myweb
spec:
  selector:
    app: myweb
  ports:
  - port: 8080
```

3. 新出现的管理对象如Deploment、ReplicaSet、DaemonSet和Job则可以在Selector中使用基于集合的筛选条件定义：
```yaml
selector:
  # matchLabels用于定义一组Label，与直接写在Selector中作用相同
  matchLabels:
    app: myweb
  # matchExpression用于定义一组基于集合的筛选条件，可用的条件运算符包括：In、NotIn、Exists和DoesNotExist
  matchExpressions:
    - {key: tier, operator: In, values: [frontend]}
    - {key: environment, operator: NorIn, values: [dev]}
```
如果同时设置了`matchLabels`和`matchExpression`，则两组条件为“AND”关系，即所有条件需要满足才能完成`Selector`的筛选

## Label Selector在 Kubernetes 的使用场景
- kube-controller 进程通过资源对象RC上定义都 Label Selector 来筛选要监控的Pod副本的数量，从而实现Pod副本的数量始终符合预期设定的全自动控制流程。
- kube-proxy 进程通过 Service 的 Label Selector 来选择对应的Pod，自动建立起每个Service到对应Pod的请求转发路由表，从而实现Service的智能负载均衡机制。
- 通过对某些Node定义特定的 Label，并且在Pod定义文件中使用 NodeSelector 这种标签调度策略，kube-scheduler 进程可以实现Pod“定向调度”的特性。