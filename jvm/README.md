# JVM

#### 介绍
学习过程中的代码

### JVM体系概览
    ![测试](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/JVM%E4%BD%93%E7%B3%BB%E6%A6%82%E8%A7%88.png "在这里输入图片标题")
  ![JVM体系概览](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/JVM%E4%BD%93%E7%B3%BB%E6%A6%82%E8%A7%88.png)
  
#### 类装载器ClassLoader
  负责加在class文件，class文件在文件开头有特定的文件标示，将class文件字节码内容加载到内存中，并将这些内容转换成方法区中的运行时数据结构并且ClassLoader只负责class文件的加载，至于它是否可以运行，则由Execution Engine决定。
  ![类装载器ClassLoader](https://gitee.com/a1031749665/learning_code_warehouse/blob/master/jvm/image/ClassLoader.png)
  
  ### 加载器种类
  1. 启动类加载器（Bootstrap）C++；
  2. 扩展类加载器（Extension）Java；
  3. 应用程序类加载器（AppClassLoader），也叫系统类加载器，加载当前应用的classpath的所有类；
  4. 用户自定义加载器，Java.lang.ClassLoader的子类，用户可以定制类的加载方式。
  
  ### 双亲委派机制
  当一个类收到了类加载器请求，它首先不会尝试直接去加载这个类，而是把这个请求委派给父类去完成，每一个层次加载器都是如此，因此所有的加载请求都应该传送到启动类加载器中，只有当父类加载器反馈自己无法完成这个请求的时候（在它的加载路径下没有找到所需加载的Class），子类加载器才会尝试直接去加载。
  采用双亲委派的一个好处是比如加载位于rt.jar包中的类java.lang.Object，不管是哪个加载器加载这个类，最终都是委托给顶层的启动类加载器进行加载，这样就保证了使用不同的类加载器最终得到的都是同样的一个object对象。
  **简而言之：加载一个类的时候会先让父类加载器去加载，当父加载器未能加载时才会轮到子加载器，这样提高了Java的安全性。**