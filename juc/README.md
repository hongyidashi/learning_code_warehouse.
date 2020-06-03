# JUC

## 学习笔记
 &emsp;&emsp;主要内容为：锁，线程，线程池等

#### 1、Lock
  1. Lock是个接口ReentrantLock；
  2. ReentrantLock是可重用、非公平、递归锁;
  3. 使用非常简单，只需定义好锁 `Lock lock = new ReentrantLock();`，然后在需要的地方加上`lock.lock();`，
  并在使用后`lock.unlock();`（建议放在finally中，防止发生异常无法释放锁）即可;
  4. synchronized和lock的区别
     1. 首先synchronized是java内置关键字，在jvm层面，Lock是个java类;
     2. synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁;
     3. synchronized会自动释放锁(a线程执行完同步代码会释放锁；b线程执行过程中发生异常会释放锁)，Lock需在finally中手工释放锁（unlock()方法释放锁），否则容易造成线程死锁;
     4. Lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题。

#### 2、ArrayList的线程安全问题
  1. ArrayList是线程不安全的；
  2. 解决方法一般有3种：
   - 使用Collections工具类将集合变为线程安全
   - 使用线程安全的集合Vector，实现是在操作方法上加了synchronized关键字
   - 使用JUC下的线程安全集合：CopyOnWriteArrayList
  3. 以上解决思路也适用于Map和Set

#### 3、synchronized锁的作用范围
  1. 加在普通方法：普通方法锁的是该实例对象，同一个对象，一旦有线程进入了该类中带有synchronized的方法，
   其他所有带有synchronized的方法都会被锁住，简而言之就是所有带synchronized方法（包括方法里面带有synchronized）都受到影响；
  2. 加在静态方法：静态方法锁的是类里static修饰的方法，只有带static的方法会被影响，static属性
  不受影响字节码里只有一个static块，锁的是这个static块。
  
#### 4、生产者和消费者间的通信
  1. 多线程判断是否唤醒要用while，不能用if，否则会出现虚假唤醒；
  2. Lock的等待、唤醒和synchronized相似。

#### 5、Condition的使用
  1. 可以视为一个锁配有多把钥匙；
  2. 创建：`Condition c = lock.newCondition();`；
  3. 通过`c.signal();`唤醒持有c的线程。

#### 6、Callable<T>和FutureTask
  1. Callable<T>是另一种创建线程的方式，于实现Runnable的主要区别在于：
   - Callable有返回值，Runnable没有；
   - Callable会抛出异常，Runnable没有。
  2. FutureTask创建：
   - `FutureTask<Integer> futureTask = new FutureTask(Callable<V> callable);`
   - `FutureTask<Integer> futureTask = new FutureTask(Runnable runnable, V result);`
  3. FutureTask是非阻塞的，如果线程里面在处理一个耗时的业务，会同时执行后面的代码，在futureTask.get()前不会阻塞。

#### 7、CountDownLatch
  1. CountDownLatch相当于一个倒计时的计数器，只有在计数值为0的时候才会执行之后的代码。
  2. 创建：`CountDownLatch countDownLatch = new CountDownLatch(int count);`
  3. 计数-1：`countDownLatch.countDown();`
  4. 作用：实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
  5. 缺点：CountDownLatch是一次性的，计数器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当CountDownLatch使用完毕后，它不能再次被使用。

#### 8、CyclicBarrier
  1. 相当于加法计数器，当计数达到预定值时，就会执行barrierAction。它要做的事情是，
  让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
  2. 创建`CyclicBarrier cyclicBarrier = new CyclicBarrier(int parties, Runnable barrierAction);`
  3. 计数+1：`countDownLatch.countDown();`

#### 9、Semaphore
  1. 用于并发控制和资源互斥，只有获得信号的线程才能被执行；
  2. 创建：`Semaphore semaphore = new Semaphore(int permits);`
  3. 资源数-1：`semaphore.acquire();`，资源数+1：`semaphore.release();`

#### 10、ReadWriteLock
  1. 读写锁，在有读操作的时候，排斥其他的读和写操作；没有写的时候可以共享读；
  2. 创建：`ReadWriteLock readWriteLock = new ReentrantReadWriteLock();`
   - 指定为读锁：`readWriteLock.readLock().lock();`
   - 指定为写锁：`readWriteLock.writeLock().lock();`
   
#### 11、BlockingQueue
  常见阻塞队列：
   - ArrayBlockingQueue：由数组结构组成的有界阻塞队列；
   - LinkedBlockingQueue：链表结构组成的有界（但默认大小值为`Integer.MAX_VALUE`）阻塞队列；
   - PriorityBlockingQueue：支持优先级排序的无界阻塞队列；
   - DelayQueue：使用优先级队列实现的延迟无界阻塞队列；
   - SynchronousQueue：不存储元素的阻塞队列，也即单个元素的队列；
   - LinkedTransferQueue： 由链表组成的无界阻塞队列；
   - LinkedBlockingDeque：由链表组成的双向阻塞队列。

#### 12、线程池
  1. 常见线程池：
   - FixedThreadPool：执行长期任务性能好，创建一个线程池，一池有N个固定线程，有固定的线程数的线程池；
   - CachedThreadPool：可扩容线程池；
  2. 创建线程池的7个参数：  
  `ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,
                             long keepAliveTime,
                             TimeUnit unit,
                             BlockingQueue<Runnable> workQueue,
                             ThreadFactory threadFactory,
                             RejectedExecutionHandler handler)`
   - corePoolSize：线程池中的常驻核心线程数；
   - maximumPoolSize：线程池中能容纳同时执行的最大线程数，此值必须大于等于1；
   - keepAliveTime：多余的空闲线程的存活时间，当线程池中的线程数量超过corePoolSize时，当空闲时间达到keepAliveTime时，
   多余的线程会被销毁直到只剩下corePoolSize个线程为止；
   - unit：keepAliveTime的单位；
   - workQueue： 任务队列，被提交但尚未执行的队列；
   - threadFactory：表示生成线程池中工作线程的线程工厂，用于创建线程，一般默认的即可；
   - handler：拒绝策略：表示队列满了，并且工作线 程大于或等于线程池的最大线程数（maximumPoolSize）时
   如何拒绝请求执行的Runnable的策略。
  3. 线程池工作流程：
   - 在创建线程池后开始等待请求；
   - 当调用execute()方法添加一个请求任务时，线程池会做出如下判断：
     1. 如果正在运行的线程数小于corePoolSize，会立刻运行；
     2. 如果正在运行的线程数量大于或等于corePoolSize，将会放入队列；
     3. 如果这个队列满了且运行线程数量小于maximumPoolSize，那么还是要创建非核心线程立即运行这个任务；
     4. 如果队列满了且运行的线程数大于或等于maximumPoolSize，那么线程池会启动饱和拒绝策略执行。
   - 当一个线程完成任务时，将会从队列中取出任务执行；
   - 当一个线程无事可做超过一定时间（keepAliveTime），线程池会判断：
    1. 如果当前运行的线程数大于corePoolSize，这个线程就会被停掉；
    2. 所以线程池的线程完成所有任务后，它最终会收缩到corePoolSize大小。
  4. 四种拒绝策略
   - AbortPolicy：直接抛出异常
   - CallerRunsPolicy：该策略既不会抛弃任务也不会抛出异常，而是将某些任务回退到调用者
   - DiscardPolicy：丢弃无法执行的任务，不予任何处理也不抛出异常
   - DiscardOldestPolicy：丢弃等待时间最长的任务
  5. 线程池有几种默认实现
     1. newSingleThreadExecutor：单线程的线程池
     2. newFixedThreadPool：创建固定大小的线程池
     3. newCachedThreadPool：可缓存的线程池
     4. newScheduledThreadPool：大小无限的线程池
   
#### 13、CAS
  1. CAS是什么  
  &emsp;&emsp;比较并交换 *CompareAndSwap*，它是一条CPU并发原语，它的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的。
  2. 原理  
   - Unsafe类是CAS的核心类，由于Java方法无法直接访问底层系统，需要通过本地方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据；Unsafe类存在于sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，因此Java中CAS操作的执行依赖于Unsafe类的方法。  
   - **注意：Unsafe类中的所有方法都是native修饰的，也就是说Unsafe类中的方法都直接调用操作系统底层资源执行相应任务。**
  3. CAS缺点
   - 循环时间长，开销很大；
   - 只能保证一个共享变量的原子操作；
   - 引出ABA问题。

#### 14、自旋锁
  1. 自旋锁是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁；
  2. 优点是减少线程上下文切换的消耗，缺点是循环会消耗CPU。