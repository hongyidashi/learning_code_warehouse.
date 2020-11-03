# 搭建kubernetes



## 硬件配置

0.  科学上网 ~~你不能科学上网你玩给🔨~~

1.  CPU核数>=2，官方推荐~~，你要是非得0给核👴也不知道会发生啥~~

2.  可用内存>3G（我说的），可用存储>40G（还是我说的）

3.  机器需要支持虚拟化，VM虚拟机配置虚拟化：

   ![VM配置虚拟化](/study-notes/images/VM配置虚拟化.png)

4.  准备centos7一台

## 开始搭建

### 0. 版本选择

全都是新的就对了

> > ## 时代，要看的比其他人至少早两年



### 1. 系统配置

```shell
# 关闭防火墙
systemctl stop firewalld
systemctl disable firewalld

# 禁用SELinux
setenforce 0
# 编辑文件/etc/selinux/config，将SELINUX修改为disabled
vi /etc/selinux/config
SELINUX=disabled

# 关闭系统Swap
#Kubernetes 1.8开始要求关闭系统的Swap，如果不关闭，默认配置下kubelet将无法启动。方法一,通过kubelet的启动参数–fail-swap-on=false更改这个限制。方法二,关闭系统的Swap
swapoff -a
```



### 2. 准备docker



#### 配置docker

```shell
# 开启iptables filter表的FORWARD链 
# 编辑/lib/systemd/system/docker.service，在ExecStart=..上面加入如下内容：
vi /lib/systemd/system/docker.service
# 内容要放在
# [Service]
# Type=notify
# 的下面
ExecStartPost=/usr/sbin/iptables -I FORWARD -s 0.0.0.0/0 -j ACCEPT

# 配置Cgroup Driver 
# 创建文件/etc/docker/daemon.json，添加如下内容：
vi /etc/docker/daemon.json
{
 "exec-opts": ["native.cgroupdriver=systemd"]
}

# 重启Docker服务
systemctl daemon-reload && systemctl restart docker && systemctl status docker
```



### 3. 安装kubernetes

#### 安装kubeadm、kubectl、kubelet

配置

```shell
# 配置软件源
cat <<EOF > /etc/yum.repos.d/kubernetes.repo
[kubernetes]
name=Kubernetes
baseurl=https://packages.cloud.google.com/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://packages.cloud.google.com/yum/doc/yum-key.gpg https://packages.cloud.google.com/yum/doc/rpm-package-key.gpg
EOF

# 解决路由异常
cat <<EOF > /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sysctl --system

# 调整swappiness参数 
# 修改/etc/sysctl.d/k8s.conf添加一行：
vi /etc/sysctl.d/k8s.conf
vm.swappiness=0
# 使修改生效
sysctl -p /etc/sysctl.d/k8s.conf
```



安装

```shell
# 查看可用版本
yum list --showduplicates | grep 'kubeadm\|kubectl\|kubelet'

# 安装指定版本(后面那个 -y 是我图省事，你可以不加)
yum install kubeadm-1.19.3 kubectl-1.19.3 kubelet-1.19.3 -y
systemctl enable kubelet
systemctl start kubelet
```

等吧



#### 使用kubeadm init初始化集群

```shell
# 初始化master节点
# kubeadm init --kubernetes-version=写你下载的版本 --pod-network-cidr=10.244.0.0/16
kubeadm init --kubernetes-version=v1.19.3 --pod-network-cidr=10.244.0.0/16

# ...漫长的等待...它在拉镜像...好无聊...

# 根据提示配置普通用户使用kubectl访问集群
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config

# 查看集群状态
kubectl get cs
# 你将看到
Warning: v1 ComponentStatus is deprecated in v1.19+
NAME                 STATUS      MESSAGE                                                                                       ERROR
scheduler            Unhealthy   Get "http://127.0.0.1:10251/healthz": dial tcp 127.0.0.1:10251: connect: connection refused   
controller-manager   Unhealthy   Get "http://127.0.0.1:10252/healthz": dial tcp 127.0.0.1:10252: connect: connection refused   
etcd-0               Healthy     {"health":"true"}  

# 好了，瞎子都看得出来创建失败，再见(划掉)
# master初始化完成后，以下两个组件状态显示依然为Unhealthy
# 这时我们可以执行  ls /etc/kubernetes/manifests/ 看看，发现下面有3给yaml文件，这3给yaml文件就是定义这三个组件用的，我们来对他动点手脚
# 我们分别进入 kube-controller-manager.yaml 和 kube-scheduler.yaml ，然后
    - --port=0   ########### 删除或者注释这行 #################
# --port=0：关闭监听 http /metrics 的请求


# 重启kubelet服务，虽然你不重启也行
systemctl restart kubelet服务

# 就会看到  ohhhhhhhhhhhhhhhhhhhhhh
Warning: v1 ComponentStatus is deprecated in v1.19+
NAME                 STATUS    MESSAGE             ERROR
controller-manager   Healthy   ok                  
scheduler            Healthy   ok                  
etcd-0               Healthy   {"health":"true"}  
```



#### 安装Pod Network

```shell
# 安装Flannel
# 你想办法把 https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml 这个东西搞到手。。
# 复制这url在浏览器打开就有了
# 然后
kubectl apply -f kube-flannel.yml

# 查询Pod状态
kubectl get pod --all-namespaces -o wide

# 舒服
NAMESPACE     NAME                              READY   STATUS    RESTARTS   AGE    IP               NODE      NOMINATED NODE   READINESS GATES
kube-system   coredns-f9fd979d6-27dfz           1/1     Running   0          17m    10.244.0.2       k8s-m-n   <none>           <none>
kube-system   coredns-f9fd979d6-jz4dq           1/1     Running   0          17m    10.244.0.3       k8s-m-n   <none>           <none>
kube-system   etcd-k8s-m-n                      1/1     Running   0          17m    192.168.94.136   k8s-m-n   <none>           <none>
kube-system   kube-apiserver-k8s-m-n            1/1     Running   0          17m    192.168.94.136   k8s-m-n   <none>           <none>
kube-system   kube-controller-manager-k8s-m-n   1/1     Running   0          10m    192.168.94.136   k8s-m-n   <none>           <none>
kube-system   kube-flannel-ds-wp9f9             1/1     Running   0          2m4s   192.168.94.136   k8s-m-n   <none>           <none>
kube-system   kube-proxy-pz258                  1/1     Running   0          17m    192.168.94.136   k8s-m-n   <none>           <none>
kube-system   kube-scheduler-k8s-m-n            1/1     Running   0          10m    192.168.94.136   k8s-m-n   <none>           <none>
```



#### Master节点参与工作负载

```shell
# 使用kubeadm初始化的集群，出于安全考虑Pod不会被调度到Master Node上，可使用如下命令使Master节点参与工作负载
# kubectl taint nodes node的NAME node-role.kubernetes.io/master-
kubectl taint nodes k8s-m-n node-role.kubernetes.io/master-

# 如果不知道node NAME可以通过这个看到
kubectl get nodes
```



#### 部署Dashboard插件

```shell
# 同样想办法得到Dashboard插件配置文件，我这里拿的是目前最新的，你可以去https://github.com/kubernetes/kubernetes/tree/master/cluster/addons/dashboard 获取
# 编辑kubernetes-dashboard.yaml文件
kind: Service
apiVersion: v1
metadata:
  labels:
    k8s-app: kubernetes-dashboard
  name: kubernetes-dashboard
  namespace: kubernetes-dashboard
spec:
  type: NodePort ## 还有这个
  ports:
    - port: 443
      targetPort: 8443
      nodePort: 31002 ## 加上这个，端口是自定义的
  selector:
    k8s-app: kubernetes-dashboard

# 安装Dashboard插件
kubectl create -f kubernetes-dashboard.yaml
```



#### 授予Dashboard账户集群管理权限

```shell
# 创建一个kubernetes-dashboard-admin的ServiceAccount并授予集群admin的权限，创建kubernetes-dashboard-admin.rbac.yaml
vi kubernetes-dashboard-admin.rbac.yaml
# 添加如下内容
---
apiVersion: v1
kind: ServiceAccount
metadata:
  labels:
    k8s-app: kubernetes-dashboard
  name: kubernetes-dashboard-admin
  namespace: kube-system
---
apiVersion: rbac.authorization.k8s.io/v1beta1
kind: ClusterRoleBinding
metadata:
  name: kubernetes-dashboard-admin
  labels:
    k8s-app: kubernetes-dashboard
subjects:
  - kind: ServiceAccount
    name: kubernetes-dashboard-admin
    namespace: kube-system
roleRef:
  kind: ClusterRole
  name: cluster-admin
  apiGroup: rbac.authorization.k8s.io

# 正义执行! 不要管那个警告，8重要
kubectl create -f kubernetes-dashboard-admin.rbac.yaml

# 查看kubernete-dashboard-admin的token
kubectl -n kube-system get secret | grep kubernetes-dashboard-admin
# 出现：
kubernetes-dashboard-admin-token-6psd7           kubernetes.io/service-account-token   3      49s

# 获取token，这个token是用于登录Dashboard的，前面忘了说，命令为 kubectl describe -n kube-system secret/上一条命令执行结果的第一个参数，实在不懂的话下面有图
kubectl describe -n kube-system secret/kubernetes-dashboard-admin-token-6psd7
# 于是乎：
Name:         kubernetes-dashboard-admin-token-6psd7
Namespace:    kube-system
Labels:       <none>
Annotations:  kubernetes.io/service-account.name: kubernetes-dashboard-admin
              kubernetes.io/service-account.uid: 2470f9a6-bd47-46bd-81ca-2ab8f4ce603c

Type:  kubernetes.io/service-account-token

Data
====
ca.crt:     1066 bytes
namespace:  11 bytes
token:      eyJhbGciOiJSUzI1NiIsImtpZCI6IlJZNWJ6R3VCTFdNM0I5OU82QkNNZ3NYOWZuUmN4cDQyWDNSeGlkeGVqazgifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlLXN5c3RlbSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZC1hZG1pbi10b2tlbi02cHNkNyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZC1hZG1pbiIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjI0NzBmOWE2LWJkNDctNDZiZC04MWNhLTJhYjhmNGNlNjAzYyIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDprdWJlLXN5c3RlbTprdWJlcm5ldGVzLWRhc2hib2FyZC1hZG1pbiJ9.RlcSI6FBc2QdJkrijpnzM_t2AP642Udamcoz6YFHWV9K08RrX084RIoS9zo3iBL8E7KMm-6mCVYG2YEO57YFd3BjiWugSxlJ5v7Qq9ZWMMqOscm9Y-XgpbmNQdrb1bEwXAEbFw1HGA8ndFfs9r82O5Q4iuo955XzfrvfWzXxOa6KouNRhLZ5csZiyv27qclXY79qvQxUbHCDB4zizbANSm4lcN_ZAduz_q08bMqLZ_pggafKnO4P67cWAKxBFjCiBsWRvR3788mWdwUBlNyN7VNQhT6Yh3NZQ9aUiFDORKn6BxUn5L-6eoh1XZ0WwCIJmqTg8bDG3s5p6uEefgovNQ

# 我们取用最后一个token，你的屏幕上可能会有一些空格，那些是不要的，这个token记得保存下来，是永久有效的，可看截图
```

![token截图](/study-notes/images/toekn截图.png)



#### 访问Dashboard 

打开浏览器输入 https://你的IP:31002，然后

![Dashboard登录](/study-notes/images/Dashboard登陆.png)

里面是英文界面，我记得老一点的版本是有中文界面的，好像是v1.10.1就可以，有空可以试一下

![Dashboard界面](/study-notes/images/Dashboard界面.png)
