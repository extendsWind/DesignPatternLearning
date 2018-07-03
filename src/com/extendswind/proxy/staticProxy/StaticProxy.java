package com.extendswind.proxy.staticProxy;

/**
 * 静态代理的思想：通过一个类包装另外一个类，将被包装类作为类成员调用对应的函数。包装类与被包装类实现同一接口，使得使用时的代码一致。
 *
 * 代理类和被代理类实现同样的接口
 * 代理类调用Logger中的函数并添加新的功能
 *
 * 静态代理与装饰者模式的主要区别：
 *
 * 1. 原则上的区别，代理为了控制对某个函数前后的操作，而装饰着模式是为了添加某一操作（其实目标没差太远）
 * 2. 实现上的区别，代理模式的类一般和被代理类的操作一致，因此构造函数一般不传入类对象，使用时的不同如下：
 *
 * Logger logger = new Proxy(); // 代理模式 （为了让Proxy的行为像Logger）
 * Logger logger = new DecorateLogger(new Logger()); // 装饰者模式，还可以有更多层
 *
 *
 * ## 个人吐槽
 *
 * 很多博客里再提高一点深度的说法：静态代理在编译时已经确定代理的具体对象，装饰模式是在运行动态的构造。（听起来有道理，其实就是要不要在构造函数中传入对象的问题）
 *
 * 如果需要对一个类的众多派生类做代理，按照标准的说法岂不是对每一个派生类都需要写一个静态代理类？？ 感觉上如果不要求代理类和被代理类在构建对象时一致（或者也给被代理类一个构造函数传入），从构造函数传入被代理类能让代理类更加灵活的处理实现接口的各种类。因此，此处还是建议根据具体情况活用；当然，更建议直接用动态代理。
 *
 *
 */

interface Logger {
    void writeLog();
}

class LoggerSubject implements Logger{
    @Override
    public void writeLog(){
        System.out.println("writeLog by LoggerSubject");
    }
}

class Proxy implements Logger{
    Logger logger;

    // 与装饰者模式的主要区别位置
    // 代理模式一般要求和原来的类行为一致，因此构造函数不传入对象
    Proxy(){
        this.logger = new LoggerSubject();
    }

    @Override
    public void writeLog(){
        System.out.println("logger write before");
        logger.writeLog();
        System.out.println("logger write after");
    }
}

public class StaticProxy {
    private static void write(Logger logger){
        logger.writeLog();
    }
    public static void main(String []argvs){
        Logger logger = new Proxy();
        // 还可能出现下面的嵌套
        //Logger logger = new Logger3(new Proxy(new LoggerSubject()));
        write(logger);
    }
}
