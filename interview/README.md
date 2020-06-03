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

**hreadPoolExecutor 饱和策略**  
饱和策略定义:  
如果当前同时运行的线程数量达到最大线程数量并且队列也已经被放满了任时，`ThreadPoolTaskExecutor` 定义一些策略:
- ThreadPoolExecutor.AbortPolicy：抛出 RejectedExecutionException来拒绝新任务的处理。
- ThreadPoolExecutor.CallerRunsPolicy：调用执行自己的线程运行任务。但是这种策略会降低对于新任务提交速度，影响程序的整体性能。另外，这个策略喜欢增加队列容量。如果您的应用程序可以承受此延迟并且你不能任务丢弃任何一个任务请求的话，你可以选择这个策略。
- ThreadPoolExecutor.DiscardPolicy： 不处理新任务，直接丢弃掉。
- ThreadPoolExecutor.DiscardOldestPolicy： 此策略将丢弃最早的未处理的任务请求。

## Atomic 原子类
1. 介绍一下Atomic 原子类  
Atomic 是指一个操作是不可中断的。即使是在多个线程一起执行的时候，一个操作一旦开始，就不会被其他线程干扰。
2. 简单介绍一下 AtomicInteger 类的原理
AtomicInteger 类主要利用 CAS (compare and swap) + volatile 和 native 方法来保证原子操作，
从而避免 synchronized 的高开销，执行效率大为提升。  
CAS的原理是拿期望的值和原本的一个值作比较，如果相同则更新成新的值。UnSafe 类的 objectFieldOffset() 方法是一个本地方法，
这个方法是用来拿到“原来的值”的内存地址，返回值是 valueOffset。另外 value 是一个volatile变量，在内存中可见，
因此 JVM 可以保证任何时刻任何线程总能拿到该变量的最新值。
3. 请你说一下自己对于AQS原理的理解    
**AQS 原理**  
如果一个资源目前空闲，那么将当前请求资源的线程设为有效工作线程，并将资源锁定，如果有其他线程来请求，
就需要有一套线程等待、唤醒和锁分配的机制，这个机制是用CLH队列锁实现的。  
CLH是一个虚拟的双向队列，仅靠节点间的关联关系维护。  
![AQS原理图](https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=441063706,2187734245&fm=15&gp=0.jpg)

> AQS核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态。
>如果被请求的共享资源被占用，那么就需要一套线程阻塞等待以及被唤醒时锁分配的机制，这个机制AQS是用CLH队列锁实现的，
>即将暂时获取不到锁的线程加入到队列中。  
>CLH(Craig,Landin,and Hagersten)队列是一个虚拟的双向队列（虚拟的双向队列即不存在队列实例，仅存在结点之间的关联关系）。
>AQS是将每条请求共享资源的线程封装成一个CLH锁队列的一个结点（Node）来实现锁的分配。