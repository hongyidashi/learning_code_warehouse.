# PersistentVolumes

## 概述
管理`存储`和管理`计算`有着明显的不同。PersistentVolume 给用户和管理员提供了一套API，抽象出存储是如何提供和消耗的细节。介绍两种新的API资源：`PersistentVolume`（简称PV）`和PersistentVolumeClaim`（简称PVC）。

- PersistentVolume（持久卷，简称PV）是集群内，由管理员提供的网络存储的一部分。就像集群中的节点一样，PV也是集群中的一种资源。它也像 Volume一样，是一种 volume插件，但是它的生命周期却是和使用它的Pod相互独立的。PV这个API对象，捕获了诸如NFS、ISCSI、或其他云存储系统的实现细节。
- PersistentVolumeClaim（持久卷声明，简称PVC）是用户的一种存储请求。它和Pod类似，Pod消耗Node资源，而PVC消耗PV资源。Pod能够请求特定的资源（如CPU和内存）。PVC能够请求指定的大小和访问的模式（可以被映射为一次读写或者多次只读）。

PVC 允许用户消耗抽象的存储资源，用户也经常需要各种属性（如性能）的 PV。集群管理员需要提供各种各样、不同大小、不同访问模式的PV，而不用向用户暴露这些 volume 如何实现的细节。因为这种需求，就催生出一种 StorageClass 资源。

`StorageClass` 提供了一种方式，使得管理员能够描述他提供的存储的等级。集群管理员可以将不同的等级映射到不同的服务等级、不同的后端策略。

## pv和pvc的区别
PersistentVolume（持久卷）和PersistentVolumeClaim（持久卷申请）是k8s提供的两种API资源，用于抽象存储细节。管理员关注于如何通过pv提供存储功能而无需关注用户如何使用，同样的用户只需要挂载pvc到容器中而不需要关注存储卷采用何种技术实现。  
可以通过 Provision -> Claim 的方式，来对存储资源进行控制。

## volume和claim的生命周期
PV是集群中的资源，PVC是对这些资源的请求，同时也是这些资源的“提取证”。PV和PVC的交互遵循以下生命周期：
1. 供给  
有两种PV提供的方式：静态和动态。
- 静态：集群管理员创建多个PV，它们携带着真实存储的详细信息，这些存储对于集群用户是可用的。它们存在于 Kubernetes API中，并可用于存储使用。
- 动态：当管理员创建的静态PV都不匹配用户的PVC时，集群可能会尝试专门地供给volume给PVC。这种供给基于 StorageClass：PVC必须请求这样一个等级，而管理员必须已经创建和配置过这样一个等级，以备发生这种动态供给的情况。请求等级配置为“”的PVC，有效地禁用了它自身的动态供给功能。

2. 绑定  
用户创建一个PVC（或者之前就已经就为动态供给创建了），指定要求存储的大小和访问模式。master中有一个控制回路用于监控新的PVC，查找匹配的PV（如果有），并把PVC和PV绑定在一起。如果一个PV曾经动态供给到了一个新的PVC，那么这个回路会一直绑定这个PV和PVC。另外，用户总是至少能得到它们所要求的存储，但是volume可能超过它们的请求。一旦绑定了，PVC绑定就是专属的，无论它们的绑定模式是什么。

如果没找到匹配的PV，那么PVC会无限期得处于unbound未绑定状态，一旦PV可用了，PVC就会又变成绑定状态。比如，如果一个供给了很多50G的PV集群，不会匹配要求100G的PVC。直到100G的PV添加到该集群时，PVC才会被绑定。

3. 使用  
Pod使用PVC就像使用volume一样。集群检查PVC，查找绑定的PV，并映射PV给Pod。对于支持多种访问模式的PV，用户可以指定想用的模式。一旦用户拥有了一个PVC，并且PVC被绑定，那么只要用户还需要，PV就一直属于这个用户。用户调度Pod，通过在Pod的volume块中包含PVC来访问PV。
  
4. 释放  
当用户使用PV完毕后，他们可以通过API来删除PVC对象。当PVC被删除后，对应的PV就被认为是已经是“released”了，但还不能再给另外一个PVC使用。前一个PVC的属于还存在于该PV中，必须根据策略来处理掉。

5. 回收  
PV的回收策略告诉集群，在PV被释放之后集群应该如何处理该PV。当前，PV可以被Retained（保留）、 Recycled（再利用）或者Deleted（删除）。保留允许手动地再次声明资源。对于支持删除操作的PV卷，删除操作会从Kubernetes中移除PV对象，还有对应的外部存储（如AWS EBS，GCE PD，Azure Disk，或者Cinder volume）。动态供给的卷总是会被删除。

## Recycled（再利用）
如果PV卷支持再利用，再利用会在PV卷上执行一个基础的擦除操作（rm -rf /thevolume/*），使得它可以再次被其他PVC声明利用。

管理员可以通过 Kubernetes controller manager 的命令行工具，来配置自定义的再利用Pod模板。自定义的再利用Pod模板必须包含PV卷的详细内容，如下示例：
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: pv-recycler
  namespace: default
spec:
  restartPolicy: Never
  volumes:
  - name: vol
    hostPath:
      path: /any/path/it/will/be/replaced
  containers:
  - name: pv-recycler
    image: "gcr.io/google_containers/busybox"
    command: ["/bin/sh", "-c", "test -e /scrub && rm -rf /scrub/..?* /scrub/.[!.]* /scrub/*  && test -z \"$(ls -A /scrub)\" || exit 1"]
    volumeMounts:
    - name: vol
      mountPath: /scrub
```
如上，在 `volumes` 部分的指定路径，应该被替换为PV卷需要再利用的路径。

