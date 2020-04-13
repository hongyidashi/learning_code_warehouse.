# JVM

## 学习笔记
  &emsp;&emsp;此笔记为面试题案例

### CAS
  #### CAS是什么
  &emsp;&emsp;比较并交换 *CompareAndSwap*，它是一条CPU并发原语，它的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的。
  #### 原理
  &emsp;&emsp;Unsafe类是CAS的核心类，由于Java方法无法直接访问底层系统，需要通过本地方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据；Unsafe类存在于sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，因此Java中CAS操作的执行依赖于Unsafe类的方法。  
  &emsp;&emsp;**注意：Unsafe类中的所有方法都是native修饰的，也就是说Unsafe类中的方法都直接调用操作系统底层资源执行相应任务。**

  #### CAS缺点
  &emsp;&emsp;1. 循环时间长，开销很大；
  &emsp;&emsp;2. 只能保证一个共享变量的原子操作；
  &emsp;&emsp;3. 引出ABA问题。