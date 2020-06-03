# 面试题

## 学习笔记
  &emsp;&emsp;此笔记为面试题案例

## JUC相关
## CAS
1. CAS是什么
比较并交换 *CompareAndSwap*，它是一条CPU并发原语，它的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的。
2. 原理
Unsafe类是CAS的核心类，由于Java方法无法直接访问底层系统，需要通过本地方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据；Unsafe类存在于sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，因此Java中CAS操作的执行依赖于Unsafe类的方法。  
**注意：Unsafe类中的所有方法都是native修饰的，也就是说Unsafe类中的方法都直接调用操作系统底层资源执行相应任务。**
3. CAS缺点
   1. 循环时间长，开销很大；
   2. 只能保证一个共享变量的原子操作；
   3. 引出ABA问题。

## 线程池
1. 为什么要用线程池？
- 降低资源消耗。
- 提高响应速度。
- 提高线程的可管理性。
2. 实现Runnable接口和Callable接口的区别？  
**Runnable 接口**不会返回结果或抛出检查异常，但是**Callable 接口**可以
3. 执行execute()方法和submit()方法的区别是什么呢？
- execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；
- submit()方法用于提交需要返回值的任务。线程池会返回一个 Future 类型的对象，
通过这个 Future 对象可以判断任务是否执行成功，并且可以通过 Future 的 get()方法来获取返回值，
get()方法会阻塞当前线程直到任务完成，而使用 get（long timeout，TimeUnit unit）方法则会阻塞当前线程一段时间后立即返回，
这时候有可能任务没有执行完。
4. 如何创建线程池？  
**方法一**：通过Executor框架的工具类Executors来实现
> 提供了三种实现方式：
> - FixedThreadPool ： 该方法返回一个固定线程数量的线程池
> - SingleThreadExecutor： 方法返回一个只有一个线程的线程池
> - CachedThreadPool： 该方法返回一个可根据实际情况调整线程数量的线程池

线程池不允许使用 `Executors` 去创建，而是通过 `ThreadPoolExecutor` 的方式  
> 使用`Executors`创建线程池的弊端:
> - FixedThreadPool 和 SingleThreadExecutor ： 允许请求的队列长度为 Integer.MAX_VALUE ，可能堆积大量的请求，从而导致OOM。
> - CachedThreadPool 和 ScheduledThreadPool ： 允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致OOM。  

**方法二**：通过`ThreadPoolExecutor`构造方法实现  


