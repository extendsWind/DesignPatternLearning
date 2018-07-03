package com.extendswind.proxy;

/*

 目标：对于一个已经写好的类，在不修改其源码的情况下添加一些其它操作
 以logger为例，写入日志前后可能需要其它的操作

 下面是最朴素的实现，使用另外的类封装Logger
 破坏了 Logger 的可扩展性，如之前有函数 writer( Logger logger); 不能对Logger2使用

 可以有两种选择：

 1. 直接继承原来的类覆盖writeLog()函数
 2. 将writeLog()放入接口中，由代理类和原类同时实现（静态代理）；

 设计模式提倡优先使用组合代替继承，通常不建议在继承中修改父类方法。
 如当需要添加多个功能进行灵活组合时，会出现Logger4继承Logger3继承Logger2继承Logger，而某些情况只使用Logger4和Logger2的修改时，又需要构建其他的类。

*/


class Logger {
    void writeLog(){
        System.out.println();
    }
}

class Logger2 {
    private Logger logger = new Logger();

    public void writeLog(){
        System.out.println("logger write before");
        logger.writeLog();
        System.out.println("logger write after");
    }
}

public class proxyProblem {

    public static void main(String []argvs){
        Logger2 logger = new Logger2();
        logger.writeLog();
    }

}
