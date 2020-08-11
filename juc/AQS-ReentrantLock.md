# AQS以及ReentrantLock
1. [为啥要有Lock](#为啥要有Lock)
2. [显式锁Lock](#显式锁Lock)
3. [队列同步器AQS](#队列同步器AQS)

## <span id="为啥要有Lock">为啥要有Lock</span>
肯定是因为 synchronized 在某些情况下无法很好的解决某些问题，例如 **死锁**。  
可以发生死锁的情形有4种情况（自行复习），其中有个「不可剥夺条件」是指：  
线程已经获得资源，在未使用完之前，不能被剥夺，只能在使用完时自己释放。  
要想破坏这个条件，就需要**具有申请不到进一步资源就释放已有资源的能力**，
然鹅 synchronized 不具备这样的能力，我们是无法改变它的状态的这是 synchronized 轮子的致命弱点，
这就强有力的给了重造轮子 Lock 的理由。

## <span id="显式锁Lock">显式锁Lock</span>
要解决上面的问题，就必须具备不会阻塞的功能，下面的三个方案都是解决这个问题的好办法（看下面表格描述你就明白三个方案的含义了）

|特性|描述|API|
|:---|:---:|---:|
|能响应中断|如果不能自己释放，那可以响应中断也是很好的。Java多线程中断机制 专门描述了中断过程，目的是通过中断信号来跳出某种状态，比如阻塞|lockInterruptbly()|
|非阻塞式的获取锁|尝试获取，获取不到不会阻塞，直接返回|tryLock()|
|支持超时|给定一个时间限制，如果一段时间内没获取到，不是进入阻塞状态，同样直接返回|tryLock(long time, timeUnit)|

#### Lock 使用范式
```
Lock lock = new ReentrantLock();
lock.lock();
try{
 ...
}finally{
 lock.unlock();
}
```
既然是范式（没事不要挑战更改写法的那种），肯定有其理由：  
**标准1—finally 中释放锁**  
在 finally 中释放锁，目的是保证在获取到锁之后，最终能被释放。  
**标准2—在 try{} 外面获取锁**  
在 try{} 外获取锁主要考虑两个方面：
1. 如果没有获取到锁就抛出异常，最终释放锁肯定是有问题的，因为还未曾拥有锁谈何释放锁呢；
2. 如果在获取锁时抛出了异常，也就是当前线程并未获取到锁，但执行到 finally 代码时，如果恰巧别的线程获取到了锁，
则会被释放掉（无故释放），这就很糟糕了。

#### Lock 是怎样起到锁的作用呢？
从范式上来看：  
`lock.lock()` 获取锁，“等同于” synchronized 的 moniterenter 指令  
`lock.unlock()` 释放锁，“等同于” synchronized 的 moniterexit 指令  

>其实很简单，比如在 ReentrantLock 内部维护了一个 volatile 修饰的变量 state，通过 CAS 来进行读写（最底层还是交给硬件来保证原子性和可见性），
>如果CAS更改成功，即获取到锁，线程进入到 try 代码块继续执行；如果没有更改成功，线程会被「挂起」，不会向下执行。

可 Lock 是一个接口，里面根本没有 state 这个变量的存在，但因为接口定义行为，具体都是需要实现类的；
Lock 接口的实现类基本都是通过「聚合」了一个「队列同步器」的子类完成线程访问控制的。

## <span id="队列同步器AQS">队列同步器AQS</span>
队列同步器（AbstractQueuedSynchronizer），简称同步器或AQS，在锁的实现类中会聚合同步器，然后利同步器实现锁的语义。  
ReentrantLock，ReentrantReadWriteLock，Semaphore(信号量)，CountDownLatch，公平锁，非公平锁，ThreadPoolExecutor 都和 AQS 有直接关系。  

>插一句：为什么要用聚合模式，如何进一步理解锁和同步器的关系  
>1. 锁是面向使用者的：它定义了使用者与锁交互的接口，隐藏了实现细节，可以就像范式那样使用就可，使用简单；
>2. 同步器面向的是锁的实现者：它简化了锁的实现方式，屏蔽了同步状态管理、线程排队、线程等待/唤醒等底层操作。

>该不会还有人不知道什么是聚合模式吧，不会吧不会吧不会吧  
>聚合&组合模式  
>```
>// 聚合模式
>public ClassRoom{
>   public Student student;
>  
>   public ClassRoom(Student student){
>   this.student = student;
>   }
>}
>```
>```
>// 组合模式
>public Student{
>     public Head head;
>     
>     public Student(){
>         head = new Head();
>     }
>}
>```

我们绝大多数都是在使用锁，实现锁之后，其核心就是要使用方便。

### <span id="同步器可重写的方法">同步器可重写的方法</span>
从 AQS 的类名称和修饰上来看，这是一个抽象类，所以从设计模式的角度来看同步器一定是基于「模版模式」来设计的，使用者需要继承同步器，
实现自定义同步器，并重写指定方法，随后将同步器组合在自定义的同步组件中，并调用同步器的模版方法，
而这些模版方法又回调用使用者重写的方法。

同步器提供的可重写方法只有5个，这大大方便了锁的使用者：


| 方法名称     | 方法描述    |
| --------| -----: |
|protected boolean tryAcquire(int arg)|「独占式」获取同步状态|
|protected boolean tryRelease(int arg)|「独占式」释放同步状态|
|protected int tryAcquireShared(int arg)|「共享式」获取公布状态  返回值>0表示成功，反之失败|
|protected boolean tryReleaseShared(int arg)|「共享式」释放同步状态|
|protected boolean isHeldExclusively(int arg)|当前同步器是否在独占模式下被线程使用，一般该方法表示是否被当前线程占用|

上面的5个来自抽象类方法都没有被 abstract 修饰，为啥捏？  
因为自定义的同步组件或者锁不可能既是「独占式」又是「共享式」，为了避免强制重写不相干方法，所以就没有 abstract 来修饰了，
但要抛出异常告知不能直接使用该方法（即默认实现是抛出异常）。

表格方法描述中所说的「同步状态」就是上文提到的有 volatile 修饰的 state，所以在重写上面几个方法时，
还要通过同步器提供的下面三个方法（AQS 提供的）来获取或修改同步状态：

| 方法名称     | 方法描述    |
| --------| -----: |
|getState()|获取当前同步状态|
|setState()|设置同步状态|
|compareAndSetState(int expect, int update)|使用CAS设置同步状态，该方法会保证同步状态设置的原子性|

而独占式和共享式操作 state 变量的区别也就很简单了
独占式：0<--->1  
共享式：0<--->N

所以 ReentrantLock  ReentrantReadWriteLock  Semaphore(信号量)  CountDownLatch 这几个类其实仅仅是在实现以上几个方法上略有差别，
其他的实现都是通过同步器的模版方法来实现的。

>如果之前没有对AQS有简单的了解的话，到这应该已经一脸懵逼了吧。。。