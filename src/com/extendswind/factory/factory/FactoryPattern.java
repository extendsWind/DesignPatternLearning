package com.extendswind.factory.factory;

import sun.rmi.runtime.Log;

/**

 正常的工厂模式 (FactoryPattern Method Pattern, 简称 FactoryPattern Pattern)

 简单工厂模式的主要麻烦在于对较多的产品难以管理，工厂模式针对此问题，将每个产品对应一个工厂。

 感觉只是这么用就没什么意思了，实质上工厂变成了对产品类的进一步封装，重点在于提供产品的生产（new）和初始化功能，产品类自身其实可以实现，产品类自己实现一个getLogger然后new自身不是更好？


 */

abstract class Logger {
   public void writeLog(){
       System.out.println("writeLog by Logger");
   }
   // 可添加公共实现
}

abstract class SuperFactory{

    public abstract Logger produceLogger();
}


class FileLogger extends Logger {

    @Override
    public void writeLog(){
        System.out.println("writeLog by the FileLogger");
    }
}

class FileLoggerFactory extends SuperFactory{
    @Override
    public Logger produceLogger(){
        // 初始化忽略
        return new FileLogger();
    }
}


class DataBaseLogger extends Logger {

    @Override
    public void writeLog(){
        System.out.println("writeLog by the DataBaseLogger");
    }
}
class DataBaseLoggerFactory extends SuperFactory{
    @Override
    public Logger produceLogger(){
        // 初始化忽略
        return new DataBaseLogger();
    }
}




// -----------------  默认上面的代码会打包，需要尽可能降低修改 ----



public class FactoryPattern {
     public static SuperFactory getFactory(String factoryName) {
        SuperFactory factory = null;

        try {
            Class c= Class.forName(factoryName);
            factory = (SuperFactory)c.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return factory;
    }

    public static void main(String []argvs){
        String testFactory = "com.extendswind.factory.factory.FileLoggerFactory"; //一般从配置文件读取
        SuperFactory factory = getFactory(testFactory);
        Logger logger = factory.produceLogger();
        logger.writeLog();
    }

}
