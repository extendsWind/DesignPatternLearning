package com.extendswind.decoration;


/**
 * 用于给一个类添加新功能
 *
 * 主要思想：被包装类作为类成员被调用，为了使包装类能和被包装类一样的使用，两者实现相同的接口。
 *
 * 通过构造函数传入被包装类的模式，能够自由组合包装，如下面的调用
 *
 * 缺点和注意：包装的自由组合的灵活性可能导致测试的困难，注意组合可能带来的bug。
 *
 */

interface Logger {
    public void writeLog();
}

class BaseLogger implements Logger {
    public void writeLog(){
        System.out.println("writeLog");
    }
}

class DecorationLogger implements Logger{
    private Logger logger;
    DecorationLogger(Logger logger){
        this.logger = logger;
    }
    @Override
    public void writeLog(){
        logger.writeLog();
        System.out.println("Decoration");
    }
}

class DecorationLogger2 implements Logger{
    private Logger logger;
    DecorationLogger2(Logger logger){
        this.logger = logger;
    }
    @Override
    public void writeLog(){
        logger.writeLog();
        System.out.println("Decoration2");
    }
}


public class Decoration {
    public static void main(String []argvs){
        Logger logger = new DecorationLogger2(new DecorationLogger(new BaseLogger()));
        logger.writeLog();

        Logger logger1 = new DecorationLogger(new DecorationLogger2(new BaseLogger()));
        logger1.writeLog();

        Logger logger2 = new DecorationLogger(new BaseLogger());
        logger2.writeLog();
    }
}
