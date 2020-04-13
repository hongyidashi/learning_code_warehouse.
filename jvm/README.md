# JVM

## 学习笔记
  &emsp;&emsp;此笔记为JVM基础入门
  &emsp;&emsp;主要内容为：JVM基础概念，GC

## JVM体系概览
  &emsp;&emsp;[JVM体系概览](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/JVM%E4%BD%93%E7%B3%BB%E6%A6%82%E8%A7%88.png)
  
### 类装载器ClassLoader
  &emsp;&emsp;负责加在class文件，class文件在文件开头有特定的文件标示，将class文件字节码内容加载到内存中，并将这些内容转换成方法区中的运行时数据结构并且ClassLoader只负责class文件的加载，至于它是否可以运行，则由Execution Engine决定。  
  &emsp;&emsp;[类装载器ClassLoader](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/ClassLoader.png)
  
  #### 加载器种类
  1. 启动类加载器（Bootstrap）C++；
  2. 扩展类加载器（Extension）Java；
  3. 应用程序类加载器（AppClassLoader），也叫系统类加载器，加载当前应用的classpath的所有类；
  4. 用户自定义加载器，Java.lang.ClassLoader的子类，用户可以定制类的加载方式。
  
  #### 双亲委派机制
  &emsp;&emsp;当一个类收到了类加载器请求，它首先不会尝试直接去加载这个类，而是把这个请求委派给父类去完成，每一个层次加载器都是如此，因此所有的加载请求都应该传送到启动类加载器中，只有当父类加载器反馈自己无法完成这个请求的时候（在它的加载路径下没有找到所需加载的Class），子类加载器才会尝试直接去加载。  
  &emsp;&emsp;采用双亲委派的一个好处是比如加载位于rt.jar包中的类java.lang.Object，不管是哪个加载器加载这个类，最终都是委托给顶层的启动类加载器进行加载，这样就保证了使用不同的类加载器最终得到的都是同样的一个object对象。  
  &emsp;&emsp;**简而言之：加载一个类的时候会先让父类加载器去加载，当父加载器未能加载时才会轮到子加载器，这样提高了Java的安全性。**

### PC寄存器
  &emsp;&emsp;1. 每个线程都有一个程序计数器，是线程私有的，就是一个指针，指向方法区中的方法字节码（用来存储指向下一条指令的地址，也即将要执行的指令代码），由执行引擎读取下一条指令，是一个非常小的内存空间，几乎可以忽略不计；  
  &emsp;&emsp;2. 这块内存区域很小，它是当前线程所执行的字节码行号指示器，字节码解释器通过改变这个计数器的值来选取下一条需要执行的字节码指令；  
  &emsp;&emsp;3. 如果执行的是一个native方法，那这个计数器是空的；  
  &emsp;&emsp;4. 用以完成分支、循环、跳转、异常处理、线程恢复等基础功能；不会发生内存溢出（OOM）错误。  
  &emsp;&emsp;**简而言之：是一个指针，表明了执行完一个方法后下一个要执行的方法。**
  
### Method Area方法区
  1. 供各线程共享的运行时内存区。它存储了每一个类的结构信息，例如运行时常量池（Runtiome Constant Pool）、字段和方法数据、构造函数和普通方法的字节码内容；
  2. 上面讲的是规范，在不同的虚拟机里头实现是不一样的，最典型的就是永久代（PermGen space）和元空间（Metaspace）；
  3. 实例变量存在堆内存中，和方法区无关。

### Stack栈
  &emsp;&emsp;栈也叫栈内存，主管Java程序的运行，是在现场创建时创建，它的生命周期是跟随线程的生命周期，线程结束栈内存也就释放，对于栈来说不存在垃圾回收问题，只要线程已结束该栈就over，生命周期和线程一致，是线程私有的。  
  &emsp;&emsp;**栈存储的东西：8种基本类型的变量+对象单独引用变量+实例方法。**

  #### 栈帧存储的数据：
   - 本地变量（Local Variables）：输入参数和输出参数以及方法内的变量；
   - 栈操作（Operand Stack）：记录出栈、入栈的操作；
   - 栈帧数据（Frame Data）：包括类文件、方法等。

### Heap堆
  &emsp;&emsp;一个JVM实例只存在一个堆内存，堆内存的大小是可以调节的。类加载读取了类文件后，需要把类、方法、常变量放到堆内存中，保存所有引用类型的真实信息，以方便执行器执行。
  &emsp;&emsp;**堆内存逻辑上分为三部分：新生+养老+永久**
  
  #### 新生区（简单版）
  &emsp;&emsp;1. 新生区是类的诞生、成长、消亡的区域，一个类在这里产生、应用，最后被垃圾回收器回收，结束生命；  
  &emsp;&emsp;2. 新生区又分为两部分：伊甸区（Eden space）和幸存者区（Survivor pace），所有的类都是在伊甸区被new出来的。幸存区有两个：0区和1区；  
  &emsp;&emsp;3. 3个区的比例为8：1：1。 
   
  如果出现OOM异常，说明Java的堆内存不够，原因有二：  
  &emsp;&emsp;1. Java虚拟机的堆内存设置不够，可通过参数-Xms、-Xmx来调整；  
  &emsp;&emsp;2. 代码中创建了大量大对象，并长时间不能被垃圾收集器收集（存在被引用）。  

  #### MinorGC(YGC)过程
  事件| Eden区资源数<br>（占用空间80%） | from区资源数<br>（占用空间10%）  | to区资源数<br>（占用空间10%） 
  :---------:|:---------:|:---------:|:---------:
  达到触发条件|100<br>（设100达到触发条件）|0|0
  第一次触发GC|0<br>（全部清除）|2<br>（将幸存的资源复制到这来）|0
  第一次GC完成|0|2|0
  达到触发条件|100（设100达到触发条件）|2|0
  将Eden区中的幸存资源和from区<br>中的幸存资源复制到to区|100|2|0+2+2
  第二次触发GC|0<br>（全部清除）|2-2<br>（全部清除）|4
  to区和from区互换（逻辑互换）|0|4|0
  第二次GC完成|0|4|0
  &emsp;&emsp;解释：  
  - 每被复制一次，年龄都会+1，如果对象已经达到老年的标准，就会被复制到老年区（默认是15）；
  - 每次清理，都是Eden和from一起清空的；
  - YGC（MinorGC）过程：复制，清空，互换。
  
### JVM调优（入门）
  #### JVM参数简介
  - -Xms：设置初始分配大小，默认为物理内存的1/64；
  - -Xmx：最大分配内存，默认为物理内存的1/4；
  - -XX:+PrintGCDetails：输出详细的GC处理日志。
  &emsp;&emsp;**修改VM参数（IDEA中）：-Xmx1024m -Xms1024m -XX:+PrintGCDetails**  
  &emsp;&emsp;**生产环境中，-Xms和-Xmx要保持一致，防止内存忽高忽低，造成卡顿等问题。**
  
  #### GC参数解读
  &emsp;&emsp;[GC参数](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/GC参数解读.png) &emsp;&emsp;
  &emsp;&emsp;[GC参数解读公式](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/GC参数公式.png)
  
### GC垃圾回收
  #### 总体概述
  &emsp;&emsp;[GC垃圾回收总体概述](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/GC概述.png)  
  &emsp;&emsp;JVM在进行GC时，并非每次都对上面三个内存区一起回收的，大部分时候回收的都是新生代，因此GC按照回收的区域又分了两种类型，一种是普通GC（minor GC），一种是全局GC（major GC or Full GC）。
  
  #### Minor GC和Full GC的区别
   - 普通GC（minor GC）：只针对新生代区域的GC，指发生在新生代的垃圾收集动作，因为大多数Java对象存活率都不高，所以minor GC非常频繁，一般回收速度也比较快；
   - 全局GC（major GC or Full GC）：指发生在老年代的垃圾收集动作，出现了major GC，经常会伴随至少一次的minor GC（但并不是绝对的）。major GC的速度一般比minor GC慢上10倍以上；Major GC慢是因为扫描的空间大。
  
  #### 四大回收算法
   1. [引用计数法(了解即可，基本不用)](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/引用计数法.png)
   2. 复制算法：就是上面提到的GC，主要缺点是消耗空间；
   3. 标记清除算法：算法分成标记和清除两个阶段，先标记出要回收的对象，然后统一回收这些对象；缺点是有内存碎片；
   4. 标记压缩算法：比标记清除多了一步整理的步骤；缺点，效率不高消耗时间。
  
  ####小总结
   1. 内存效率：复制算法>标记清除算法>标记整理算法(此处的效率只是简单的对比时间复杂度，实际情况不一定如此)
   2. 内存整齐度：复制算法=标记整理算法>标记清除算法
   3. 内存利用率：标记整理算法=标记清除算法>复制算法