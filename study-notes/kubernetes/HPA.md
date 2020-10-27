# 自动扩容之Horizontal Pod Autoscaling（HPA）

我们通过手动执行kubectl scale命令，可以实现Pod扩容。但是，分布式系统要能够根据当前负载的变化情况自动触发水平扩展或缩容的行为，因为这一过程可能是频繁发生的、不可预料的，所以手动控制的方式是不现实的。

因此，在Kubernetes1.1版本中首次发布了这一重量级新特性-----`Horizontal Pod Autoscaler`

Horizontal Pod Autoscaler简称HAP，意思是Pod横向自动扩容，与之前的RC、Deployment一样，也属于一种Kubernetes资源对象。通过追踪分析RC控制的所有目标Pod的负载来自动水平扩容，如果系统负载超过预定值，就开始增加Pod的个数，如果低于某个值，就自动减少Pod的个数。

目前，可以有以下两种方式作为Pod负载的度量指标：
- CPU utilization percentageb
- 应用程序自定义的度量指标，比如服务在每秒内的相应的请求数（TPS或QPS）

`CPU utilization percentage`是一个算术平均值，即目标pod所有副本自身的CPU利用率的平均值。一个Pod自身的CPU利用率是该Pod当前CPU使用量除以它的Pod request的值。比如当我们定义一个Pod的pod request为0.4，而当前pod的cpu使用量为0.2，则使用率为50%。如此可以得出一个平均值，如果某一个时刻CPU utilization percentage超过80%，则意味着当前Pod副本不足以支撑接下来更多的请求，需要进行动态扩容。而当请求高峰时段过去后，Pod CPU利用率又会降下来，此时对应的Pod副本数应该自动减少到一个合理的水平。

CPU utilization percentage计算过程使用到的Pod的CPU使用量通常是1分钟的平均值。

## 条件
HPA通过定期（定期轮询的时间通过–horizontal-pod-autoscaler-sync-period选项来设置，默认的时间为30秒）通过Status.PodSelector来查询pods的状态，获得pod的CPU使用率。然后，通过现有pods的CPU使用率的平均值（计算方式是最近的pod使用量（最近一分钟的平均值，从heapster中获得）除以设定的每个Pod的CPU使用率限额）跟目标使用率进行比较，并且在扩容时，还要遵循预先设定的副本数限制：MinReplicas <= Replicas <= MaxReplicas。

计算扩容后Pod的个数：sum(最近一分钟内某个Pod的CPU使用率的平均值)/CPU使用上限的整数+1

## 流程
> 1、创建HPA资源，设定目标CPU使用率限额，以及最大、最小实例数
> 2、收集一组中（PodSelector）每个Pod最近一分钟内的CPU使用率，并计算平均值
> 3、读取HPA中设定的CPU使用限额
> 4、计算：平均值之和/限额，求出目标调整的实例个数
> 5、目标调整的实例数不能超过1中设定的最大、最小实例数，如果没有超过，则扩容；超过，则扩容至最大的实例个数

