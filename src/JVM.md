# `JVM`

## 1. `JVM`与`JAVA`体系结构

## 1.1 `JVM`简单介绍

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
