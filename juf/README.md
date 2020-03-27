# JUF

## 学习笔记

#### 四大函数型接口
  1. Function<T, R> 函数型接口：对类型为T的对象应用操作，并返回结果，结果是R类型的对象。包含方法：`R apply(T t);`
  2. Predicate<T> 断言型接口：确定类型为T的对象是否满足某约束，并返回boolean值。包含方法：`boolean test(T t);`
  3. Consumer<T> 消费型接口：对类型为T的对象应用操作，无返回值。包含方法：`void accept(T t);`
  4. Supplier<T> 供给型接口：返回类型为T的对象。包含方法：`T get();`
  
#### 分支合并计算
  1. ForkJoinTask<V>思想类似归并：递归计算再合并;
  2. 使用流程大致是：指定分组计算规则，再将任务放入ForkJoinPool线程池中执行。

#### 异步回调
  1. 无返回值：CompletableFuture.runAsync，异步执行，使用消费型接口；
  2. 有返回值：CompletableFuture.supplyAsync，异步供给，使用供给型接口；
  3. 都可使用链式编程，并且可以借助whenComplete来制定类似后置处理逻辑以及发生异常时的处理逻辑，这样发生异常时不会报错.


