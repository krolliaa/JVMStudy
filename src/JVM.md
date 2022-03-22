# `JVM`

## 1. `JVM`与`JAVA`体系结构

### 1.1 `JVM`简单介绍

- 运行着的线上系统突然卡死，系统无法访问，甚至直接报内存无法访问
- 想解决线上`JVM GC`问题，但却无从下手
- 新项目上线，对各种`JVM`参数设置一脸茫然，直接默认吧后面直接`GG`
- 每次卖你是之前都要背理论性的东西，但是却没有在实际项目中调优，没有生产经验

通过加硬盘、自行车的原理、汽车的原理类比了`JVM`，大部分程序员在上层应用上耗费了大量的精力而没有精力去学习、弄懂底层的`JVM`，就像电脑坏了换电脑，汽车坏了换汽车，自行车坏了换自行车等，大部分没想去弄底层的东西。

如果说`JAVA API`是数学公式的话，那么`JVM`就是这些公式的推导过程。

![](https://img-blog.csdnimg.cn/e1228afa33b546e18ce709fdcf71d7e4.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

计算机系统体系离我们越来越远，底层的东西被我们关注的越来越少，但实际上计算机并不认识高级语言。

![](https://img-blog.csdnimg.cn/7621979a555a42fdb859f8c246258f79.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

- 架构师每天都在思考什么？ ---> 应该如何让我的系统更快？如何避免系统出现瓶颈？【`JVM`调优】

- `JAVA`相比于`C++`有内存动态分配+垃圾自动回收，但是如果技术水平高、管理得好的话，用`C++`会觉得很舒服，可以更加高效地优化。 ---> 《深入理解`JAVA`虚拟机》---> `Write once, run anywhere`

- `JVM`：跨语言的凭平台 || 三大难题：`CPU`、编译器、操作系统 || 《自己动手写`Java`虚拟机》 ---> `Go`语言

- 所谓虚拟机`Virtual Machine`就是一台虚拟的计算机。它是一款软件，用来执行一系列虚拟计算机指令。大体上，虚拟机可以分为系统虚拟机和程序虚拟机。大名鼎鼎的`Visual Box、VMware`就属于系统虚拟机，它们完全是对物理计算机的仿真，提供了一个可运行完整操作系统的软件平台。

- 程序虚拟机的典型代表就是`JVM`，它专门为执行单个计算机程序而设计，在`JVM`中执行的指令我们称为`Java`字节码指令。

- 无论是系统虚拟机还是程序虚拟机，在上面运行的软件都被限制于虚拟机提供的资源中。`JAVA`技术的核心就是`JVM`，只要遵循字节码编译的规则就可以在`JVM`上运行。`JVM`是二进制字节码的运行环境。

- 特点：一次编译到处运行、自动内存管理、自动回收功能

- 位置：`JVM`是运行在操作系统之上的，它与硬件没有直接的交互

  ![](https://img-blog.csdnimg.cn/5d96dbd8c8b140e9b581b313ebdc0a25.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

### 1.2 `JVM`整体结构

【类装载器子系统、运行时数据区、执行引擎】

- `HotSpot VM`是目前市面上高性能虚拟机的代表作之一
- 它采用解释器与即时编译器并存的架构
- 在今天，`Java`程序的运行性能早已脱胎换骨，已经达到可以和`C/C++`一较高下的地步

`Class Files`字节码文件加载到类装载器子系统，生成到运行时数据区`(Runtime Data Area)`【方法去和堆数据是多线程共享的，栈、本地方法栈、程序计数器都是不共享的】【非常重要的一部分】 ---> 执行引擎【解释器、垃圾回收器】，解释运行【用到解释器，只用解释器的话，如果遇到反复执行的热点代码效率差一些，我们希望将这些热点代码提前的编译出来，需要使用到后端`JIT`编译器】 ，操作系统只能执行机器指令，若要我们的`Java`文件可以转换成机器指令，就需要使用到执行引擎。

![](https://img-blog.csdnimg.cn/ce274aee4602480f8cb46bfb43307162.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

### 1.3 `Java`代码执行流程

`Java`程序 ---> 字节码文件 ---> `JVM`

1. `Java(xxx.java)`源码 ---生成---> 编译【语法分析 ---> 词法分析 ---> 语法/抽象词法树 ---> 语义分析 ---> 注解抽象语法树 ---> 字节码生成器】---生成---> 字节码`(xxx.class)`【对于任意一个编译环节失败了都无法生成字节码文件】
2. 然后生成到`Java`虚拟机，通过类加载器、字节码校验器、执行引擎生成计算机可以识别的机器指令包括翻译字节码（解释执行）以及编译执行（`JIT`后端编译器）
3. 最终落实到操作系统，由操作系统去完成

### 1.4 `JVM`架构模型

`Java`编译器输入的指令流基本上是一种<font color="red">**栈的指令集架构**</font>，另外一种指令集架构则是基于**<font color="red">寄存器的指令集架构</font>**

具体来说，这两种架构之间的区别：

1. **<font color="red">基于栈的指令集架构</font>：**【跨平台、指令集小、指令集多、便于设计和实现】

   > 设计和实现更简单，适用于资源受限的系统
   >
   > 避开了寄存器的分配难题：使用零地址指令方式分配
   >
   > 指令流中的指令大部分是零地址指令，其执行过程依赖于操作栈。指令集更小，编译器容易实现
   >
   > 不需要硬件支持，可移植性更好，更加实现跨平台【非常重要的一个特点】

2. **<font color="red">基于寄存器的指令集架构</font>**：

   > 典型的应用是`x86`的二进制指令集：比如传统的`PC`以及`Android`的`Davlik`虚拟机
   >
   > 指令集完全依赖硬件，可以执行差
   >
   > 性能有效和执行更高效，指令需要`CPU`，高度依赖耦合度高，但是速度非常快
   >
   > 花费更少的指令去完成一项操作，栈指令集架构是零地址指令，但是寄存器指令集架构是双字节对齐的这种方式
   >
   > 在大部分情况下，基于寄存器架构的指令集往往都以一地址指令、二地址指令和三地址指令为主，而基于栈指令集架构的指令集却是以零地址指令为主

   `javap -v StackStructureTest.java`

   ![](https://img-blog.csdnimg.cn/ec2680940d49474193639dacfa1d4950.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

上图可以看到栈指令集寄存器的处理过程，由于编译一次到处运行，跨平台性的这种设计，`Java`的指令都是根据栈指令集寄存器来设计的。不同平台的`CPU`架构不同，所以不能设计为基于寄存器的。

优点是跨平台，指令集小，编译器容易实现，缺点是性能不像寄存器指令集架构那样直接跟`CPU`打交道，高速缓存使得性能高效，实现同样的功能栈指令集架构需要更多的指令。

总结来说使用栈指令集架构的最主要的原因就是：设计和实现较方便、跨平台，其它特点有指令集小、指令多、执行性能比寄存器指令集架构差

### 1.5 `JVM`生命周期

`JVM`生命周期可以简单地分为：启动、执行、结束

- 虚拟机的启动

  `JVM`通过引导类加载器创建一个初始类来完成启动。这个类是由虚拟机的具体实现指定的。

  程序执行某个方法，比如这里的`main`方法：

  1. 首先要将类加载到内容中，这是一个自定义类，这个类通过系统类加载器加载，比如这里的`JVMStartTest`

  2. 该类中父类是`Object`类，`Object`类需要被引导类加载器加载，除了父类`Object`类，程序的运行还需要许多类的加载，所有依赖的类都加载之后，`JVM`才完成了启动，最早加载的类称为初始类。`Object`类并不是初始类，只是拿出来举个例子。

     ```java
     public class JVMStartTest {
         public static void main(String[] args) {
             System.out.println("123");
         }
     }
     ```

  系统类加载器 ------> 扩展类加载器 ------> 引导类加载器

- 虚拟机的执行

  一个`Java`程序的执行实际上是一个`JVM`进程在执行，可尝试运行一个程序睡眠一阵子使用`jps`查看当前运行的进程，然后等程序结束再运行一次`jps`会发现程序结束进程也就随之结束了。

  也就是说，`Java`程序的运行其实就是`JVM`进程在运行，当前者终止时，`JVM`程序也就随之停止了。

  ```java
  public class JVMExecuteTest {
      public static void main(String[] args) throws InterruptedException {
          int i = 2 + 3;
          Thread.sleep(10000);
      }
  }
  ```

  ![](https://img-blog.csdnimg.cn/c1ceba4bf13245ee8551475209673c8f.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

- 虚拟机的结束：`JVM`结束有多种原因

  1. 正常情况，程序执行完毕后退出
  2. 自定义情况，程序调用了`RunTime`或者`System.exit()`方法等`Java`安全管理中允许的操作从而退出
  3. 错误情况：程序运行过程中遇到异常从而终止运行导致退出
  4. 崩溃情况：操作系统、硬件原因导致`JVM`退出

### 1.6 `JVM`发展历程

按照出现顺序排序：

1. `Sun Classic VM`

   在`JDK 1.0`发布，在`JDK 1.4`淘汰。

   世界上第一款商用`Java`虚拟机，只提供了解释器没有`JIT`后端编译器，执行的效率比较低下，需要逐行进行解释，即使是重复性的代码比如循环，也需要逐行解释，但是也行得通只是效率很低。`JIT`编译器可以处理热点代码，可以缓存热点代码编译后的机器指令也就是说可以大大提高效率。该款虚拟机想用`JIT`即时编译器就无法使用解释器，而且只能用外挂。

   - 为什么不能只用解释器？解释器是逐行解释代码，即使是重复性的代码也是如此，效率非常低下
   - 为什么不能只用`JIT`即时编译器？编译器虽然通过缓存热点代码，先生成了机器指令，但是如果只用它的话，那么就需要花费大量的时间去缓存、等待，暂停的时间有点长。所以在开始，人们对`Java`的第一印象就是慢，就是因为这一点，一直到现在都有很多人认为`Java`比`C/C++`慢，但是因为越来越多人加入了`Java`生态，现在`Java`的速度其实不亚于`C/C++`的速度

   **<font color="red">【所以混合起来用是最好的，就好比与步行和公交车，到达一个目的地，如果单纯步行那很慢，如果单纯用公交车，那每一个站点都要等一下，走了一段又等一阵子，人多就等很久。所以混合起来，步行然后搭公交然后等的时候就下来走（这个解释就很强）】</font>**

2. `Exact VM`

   - 为了解决`Sun Classic VM`的问题该问题就是解释器和`JIT`即时编译器无法混合工作，`JDK1.2`的时候`SUN`公司提供了此虚拟机

   - `Exact Memory Management：`准确式内存管理

     > 也可以叫做`Non-Conservative/Accurate Memory Management`
     >
     > 该款虚拟机也可以知道内存中某个位置的数据具体是什么类型

   - 具备现代高性能虚拟机的雏形：热点探测、即时编译器`JIT`和解释器混合工作模式

   - 只在`Solaris`平台短暂使用，其它平台是还是`Sun Classic VM`，非常短暂，刚出来不久就被`HotSpot`取代了【注：`Solaris`是一个由`Sun`公司开发的操作系统，`Sun`后来被`Oracle`收购】

3. `HotSpot VM`【商用三大虚拟机之一】【默认虚拟机】

   - 最初由`Longview Technologies`小公司设计然后被`Sun`公司收购，后面`Sun`又被`Oracle`收购所以现在属于`Oracle`

   - `JDK1.3`的时候，`HotSpot VM`成了默认的虚拟机，此前`1.2`的版本都是`Sun Classic VM`，一直到现在的`JVM`还是`HotSpot VM`，占有绝对的市场地位，是到目前默认的虚拟机。从服务器、桌面到移动端、嵌入式都有应用。方法区的概念只有`HotSpot`才有。`HotSpot`指的就是它的热点代码探测技术：

     > 通过计数器找到最具有编译价值的代码，触发即时编译或栈上替换
     >
     > 通过编译器与解释器协同工作，在最优化的程序响应时间与最佳执行性能中取得平衡

     即时编译器和解释器混合工作在程序最佳响应时间【解释器】和最佳性能执行【即时编译器`JIT`】也就是时间和效率中达到平衡。如何需要热点代码？通过计时器找到，然后触发即时编译器`JIT`。
     
   - 从`HotSpot VM`开始才有了方法区的概念

4. `JRockit`【商用三大虚拟机之一】

   - 由`BEA`开发但是现在该公司已经被`Oracle`收购了，专注于服务器端应用，这个跟`HotSpot`是不一样的，因为是服务器端，所以可以不关心缓存时间/暂停时间，所以没有解释器，只有即时编译器，所以它是世界上最快的`JVM`，`JRockit`提供毫秒级或微秒级`JVM`的响应时间，适合财务、军事指挥、电信网络的需要。


   - `Oracle`说要整合`HotSpot`和`JRockit`，以`HotSpot`为基础，移植`JRockit`的优秀特性。

5. `IBM J9`【商用三大虚拟机之一】

   - 全称`IBM Technology for Java Virtual Machine`，简称`IT4J`，内部代号：`J9`

   - 市场定位与`HotSpot`接近，服务器端、桌面应用、嵌入式等多用途`VM`广泛用于`IBM`的各种`Java`产品。自己号称是世界上最快的Java虚拟机。但其实只是在自家产品上运行相当快，这就好比`iOS`和`Android`两个系统一样，`iOS`很快但只是在其自家产品上，如果在`Android`手机的硬件上装`iOS`系统，反倒更慢了。

6. `KVM和CDC/CLDC HotSpot`
   - `Oracle`在`Java ME`产品线上的两款虚拟机为：`CDC/CLDC HotSpot Implementation VM`，`Java ME`是为机顶盒、移动电话和`PDA`之类嵌入式消费电子设备提供的Java语言平台
   - `KVM`是`CLDC-HI`早期产品，现在手机市场基本就是被`iOS`和`Android`霸占，所处位置相当尴尬，主要面向的就是老人机、智能控制器、传感器、经济欠发达地区的功能手机

7. `Azul VM`

   - 与特定硬件平台绑定、软硬件配合的专用虚拟机 ---> 高性能`JVM`中的战斗机

   - 由`Azul Systems`公司在`HotSpot`基础上进行大量改造，运行于`Azul Systems`公司转悠硬件`Vega`系统上的`JVM`

   - 每个`Azul VM`都可以管理至少数十个处理器和数百`GB`内存的硬件资源【好猛】，并提供在巨大内存范围内实现可控的`GC`时间的垃圾收集器，专有硬件优化的线程调度等优秀特性

     注：`Vega`一款用于实时视觉模拟、虚拟现实和普通视觉应用的工业软件

   - `2010`年，`Azul Systems`公司开始从硬件转向软件，发布了自己的`Zing JVM`，可以在通用`X86`平台上提供接近于`Vega`系统的特型

   - `Azul Systems`是一个神奇的公司

8. `Liquid VM`

   - 高性能`JVM`中的战斗机，由`BEA`公司开发并且运行在自己的`HyperVisor`自家的虚拟机监视器，因为自身实现了操作系统的必要功能包括线程调度、文件系统、网络支持等所以不需要操作系统的支持
   - 其实`Liquid VM`就是`JRockit VE`，随着`JRockit VM`的停止开发，`Liquid VM`也停止了

9. `Apache Harmony`

   - 目前没有大规模使用该虚拟机的商用案例，它的`Java`类库吸纳进了`Android SDK`

10. `Microsoft JVM`

    - 目前`Windows`装的`JVM`都是`HotSpot`，该款虚拟机被`Sun`公司告了然后微软在`WindowsXP SP3`上抹掉了这个`VM`

11. `TaobaoJVM`

    - 已在天猫淘宝上线，严重依赖`Intel`处理器
    - 阿里：国内使用`Java`最强大的公司。基于`OpenJDK`开发了定制版`Alibabajdk`简称`AJDK`，是整个阿里`Java`体系的基石
    - `TaobaoJVM`开创了一种新的技术：`off-heap`，也就是`GCIH Gabage Collection Invisible Heap`，即将声明周期较长的`Java`对象从`Heap`转移到了`Heap`外，并且`GC`不能管理`GCIH`内部的`Java`对象，以此达到降低`GC`的回收频率和提升`GC`的回收效率
    - `GCIH`中的对象还能在多个`Java`虚拟机进程中实现共享

12. `Dalvik VM`

    - 谷歌开发应用于`Android`发展迅猛

13. `Graal VM`【未来虚拟机】

    - 还是`Oracle`	---> `Oracle Labs`
    - `Run Programs Faster Anywhere`，跨语言全栈虚拟机，可以作为任何语言的运行平台使用
    - 如果说`HotSpot`有一天真的被取代，`Graal VM`的希望最大

## 2. 类加载器子系统

整个`JVM`的体系结构如图所示：【做一个`JVM`必不可少的就是类加载器子系统和执行引擎】

字节码文件经过类加载器子系统`Class Loader SubSystem`，加载【系统类加载器、扩展类加载器、引导类加载器】然后通过`Linking`的解析、准备、验证最后初始化，然就转到`Runtime Data Areas`运行时数据区再到执行引擎。

![](https://img-blog.csdnimg.cn/747d05b5481c4db386239c3077e05181.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

### 2.1 类加载器子系统的作用

> 类加载器子系统负责从文件系统/网络中加载字节码文件，字节码文件在开头有特定的文件标识
>
> 类加载器只负责`Class`文件的加载，至于可不可以运行要看执行引擎`Execution Engine`决定
>
> 加载的类信息存放于某一块称为方法区的内存空间。除了类信息外，方法区中还存放运行时常量池信息包括字符串字面量和数字常量【这部分常量信息是`Class`文件中常量池部分的内存映射】【`javap -v xxx.java` ---> `Constant Pool`】

充当的角色就是将字节码文件转化为最终元数据。说白了作用就是加载字节码文件。

1. `XXX.class`字节码文件存在于本地硬盘上，可以理解为设计师在纸上的模板，而最终这个模板在执行的时候需要加载到`JVM`中，根据这个字节码文件实例化出一个又一个的实例。
2. 字节码文件加载到`JVM`中被称为`DNA`元数据模板，放在方法区
3. 最终转变为元数据，这个加载的过程需要一个运输工具就是`Class Loader SubSystem`

类加载器子系统加载过程：`加载Loading ---> 链接Linking（验证Verification - 准备Preparation - 解析 Resolution） ---> 初始化Initialization`

![](https://img-blog.csdnimg.cn/89ee0661af6a4e8696976e5bb9eb1dd7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

### 2.2 类加载器子系统的加载过程

1. 加载`Loading`：

   > 1. 通过一个类的全限定名获取定义此类的二进制字节流【简单点就是从硬盘中读取字节码文件】
   > 2. 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构【简单点就是把数据放到方法区】
   > 3. 在内存中生成一个`java.lang.Class`对象，作为方法区这个类的各种数据的入口

   加载字节码文件的方式：

   - 从本地系统中直接加载
   - 通过网络获取`Web Applet`
   - 从压缩包中获取`jar、war`
   - 动态代理等运行时计算获取
   - 由其他文件生成 ---> `JSP`应用
   - 从专有数据库中提取字节码文件
   - 从加密文件中获取，典型的防止`Class`文件反编译的保护措施

2. 链接`Linking`：

   > 1. 验证`Verify`：确保字节码文件的字节流包含的信息符合当前虚拟机的要求，保证被加载类的正确性，不会危害虚拟机的自身安全。主要包括四种验证：文件格式验证、元数据验证、字节码验证、符号引用验证
   >
   >    之前我们说字节码文件在开头有特定的文件标识，可以通过十六进制查看专用软件查看，可以发现都是以`CAFEBABE`打头
   >
   >    ![](https://img-blog.csdnimg.cn/c8f0c54e40d74b09bed5b42ca1921cf7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)
   >
   > 2. 准备`Prepare`：为类变量分配内存并设置该类变量的默认初始值即零值。这里不包含用`final`修饰的`static`，因为`final`在编译的时候就会分配了，准备阶段会显式的初始化，也就是说`static final`修饰的会直接显示不会给一个默认值即零值。
   >
   >    准备阶段不会为实例变量分配初始化，因为这时候还没有对象呢。类变量会分配在方法区中，而实例变量会随着对象一起分配到`Java Heap`中。【初始化赋值阶段，不给实例变量初始化并且排除`static final`变量直接显式分配】
   >
   > 3. 解析`Resolve`：将常量池内的符号引用转换为直接引用的过程。事实上解析操作往往会伴随着`JVM`执行完初始化之后再执行。符号引用就是一组符号来描述所引用的目标。采用直接引用，直接指向目标的指针、相对偏移量或一个间接定位到目标的句柄。
   >
   >    接续动作主要针对类或接口、字段、类方法、接口方法、方法类型等。对应常量池中的`CONSTANT_CLASS_INFO`、`CONSTANT_FIELDREF_INFO`、`CONSTANT_METHODREF_INFO`等。
   >
   >    其实就是引导类加载器加载其它如`Object`类等类构成直接引用

3. 初始化`Initialization`：

   > - 初始化阶段就是执行<font color="red">**类构造器方法**</font>`<clinit>()`的过程【不是我们写的那个构造器不需要我们定义的，在`Bytecoder Viewer`有构造器可以用`jclasslib`插件看到】
   >
   > - 此方法不需定义，是`javac`编译器自动收集类中的所有类变量的赋值动作和静态代码块中的语句合并而来，如果没有在链接`Linking`中的准备`Prepare`阶段中赋值以及没有在静态代码块中赋值就没有`<clinit>()`
   >
   > - 构造器方法中指令按语句在源文件中出现的顺序执行
   >
   > - `<clinit>()`不同于类的构造器。（关联：构造器是虚拟机视角下的`<init>()`）
   >
   > - 若该类具有父类，`JVM`会保证子类的`<clinit>()`执行前，父类的`<clinit>()`已经执行完毕，子类的执行一定是晚于父类的执行
   >
   > - 虚拟机必须保证一个类的`<clinit>()`方法在多线程下被同步加锁