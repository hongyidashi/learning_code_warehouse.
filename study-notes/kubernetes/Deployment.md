# Deployment

## 简介
Deployment 是Kubernetes v1.2引入的概念，引入的目的是为了更好地解决Pod的编排问题。为此，Deployment 在内部使用了 Replica Set 来实现目的，无论从 Deployment 的作用与目的，它的 YAML 定义，还是从它的具体命令行操作来看，我们都可以把它看作RC的一次升级，两者相似度超过90%。

Deployment 相对于RC的一个最大升级是我们随时知道当前Pod“部署”的进度。实际上由于一个Pod的创建、调度、绑定节点及在目标Node上启动对应的容器这一完整过程需要一定的时间，所以我们期待系统启动N个Pod副本的目标状态，实际上是一个连续变化的“部署过程”导致的最终状态。

## Deployment的典型使用场景
- 创建一个Deployment对象来生成对应的Replica Set并完成Pod副本的创建过程。
- 检查Deployment的状态来看部署动作是否完成（Pod副本的数量是否达到预期的值）。
- 更新Deployment以创建新的Pod（比如镜像升级）。
- 如果当前Deployment不稳定，则回滚到一个早先的Deployment版本。
- 暂停Deployment以便于一次性修改多个PodTemplateSpec的配置项，之后再恢复Deployment，进行新的发布。
- 扩展Deployment以应对高负载。
- 查看Deployment的状态，以此作为发布是否成功的指标。
- 清理不再需要的旧版本ReplicaSets。

## 示例
创建一个名为tomcat-deployment.yaml的Deployment描述文件
```yaml
# 1.6版本之前 apiVsersion：extensions/v1beta1
# 1.6版本到1.9版本之间：apps/v1beta1
# 1.9版本之后:apps/v1
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
spec:
  replicas: 1
  selector:
    matchLabels:
      tier: frontend
    matchExpressions:
      - {key: tier, operator: In, values: [frontend]}
  template:
    metadata:
      labels:
        app: app-demo
        tier: frontend
    spec:
      containers:
      - name: tomcat-demo
        image: tomcat
        imagePullPolicy: IfNotPresent
        ports:
        - containerPort: 8080
```

## 常见命令
```shell script
# 查看Deployment的信息
kubectl get deployments

# 查看对应的Replica Set
kubectl get rs
```

## 最后
Pod的管理对象，除了RC和Deployment，还包括ReplicaSet、DaemonSet、StatefulSet、Job等