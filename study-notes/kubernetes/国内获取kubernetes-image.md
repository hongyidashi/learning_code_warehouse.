# 国内获取kubernetes镜像

无需科学上网即可加载镜像

## 版本选择
可以基于以下方式逐个确认各个组件版本号：

- kubernetes版本可以在docker的有关信息中找，如在桌面版中，点击docker图标下拉菜单里的About Docker Desktop，弹出的界面里就有k8s的版本号。
- etcd版本在 https://github.com/kubernetes/kubernetes/blob/master/cluster/images/etcd/Makefile 里找版本号（需要选择对应的k8s版本的tag，注意版本号后要加-0）。
- pause版本在 https://github.com/kubernetes/kubernetes/blob/master/build/pause/Makefile 里找（需要选择对应的k8s版本的tag）。
- coredns版本在 https://github.com/coredns/deployment/blob/master/kubernetes/CoreDNS-k8s_version.md 里找。

## 开始
1. 新建`images`镜像文件，内容如下：
```shell script
k8s.gcr.io/kube-proxy=registry.cn-hangzhou.aliyuncs.com/google_containers/kube-proxy=v1.19.3
k8s.gcr.io/kube-controller-manager=registry.cn-hangzhou.aliyuncs.com/google_containers/kube-controller-manager=v1.19.3
k8s.gcr.io/kube-scheduler=registry.cn-hangzhou.aliyuncs.com/google_containers/kube-scheduler=v1.19.3
k8s.gcr.io/kube-apiserver=registry.cn-hangzhou.aliyuncs.com/google_containers/kube-apiserver=v1.19.3
k8s.gcr.io/coredns=registry.cn-hangzhou.aliyuncs.com/google_containers/coredns=1.7.0
k8s.gcr.io/pause=registry.cn-hangzhou.aliyuncs.com/google_containers/pause=3.3
k8s.gcr.io/etcd=registry.cn-hangzhou.aliyuncs.com/google_containers/etcd=3.4.9-0
quay.io/coreos/flannel=registry.cn-hangzhou.aliyuncs.com/google_containers/flannel=v0.13.0
kubernetesui/dashboard=registry.cn-hangzhou.aliyuncs.com/google_containers/dashboard=v2.0.4
kubernetesui/metrics-scraper=registry.cn-hangzhou.aliyuncs.com/google_containers/metrics-scraper=v1.0.4
```

2. 新建一个`load_images.sh`文件，内容如下：
```shell script
#!/bin/bash
file="images"
if [ -f "$file" ]
then
  echo "$file found."

  while IFS='=' read -r key value version
  do
    docker pull ${value}:${version}
    docker tag ${value}:${version} ${key}:${version}
    docker rmi ${value}:${version}
  done < "$file"

else
  echo "$file not found."
fi
```

> 注意`load_images.sh` 和 `images` 要在同一个目录下

3. 执行`load_images.sh`即可