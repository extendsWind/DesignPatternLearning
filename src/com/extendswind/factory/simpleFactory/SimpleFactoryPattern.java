package com.extendswind.factory.simpleFactory;

/**

 简单工厂模式做的：

 - 将new产品和初始化过程放入到工厂类中

 主要优点在于降低客户端的逻辑复杂程度，并更容易实现产品的相似的初始化。

 仍存在的缺点：

 - 每次添加新的类都需要修改工厂类
 - 生产的产品类较多时初始化过程复杂且逻辑复杂
 - 某些类可能存在类似的初始化过程

 适用的情况：

 - 需要生产的产品较少
 - 客户端不关心产品创建过程的初始化，只想要得到产品对象并使用

 */

// Logger 为产品类的基类
abstract class Logger {
   public void writeLog(){
       System.out.println("writeLog by Logger");
   }

   // 可添加公共实现

}

class FileLogger extends Logger {

    @Override
    public void writeLog(){
        System.out.println("writeLog by the FileLogger");
    }
}

class DataBaseLogger extends Logger {

    @Override
    public void writeLog(){
        System.out.println("writeLog by the DataBaseLogger");
    }
}


// simple factory
class SimpleFact {

    public static Logger produceLogger(String loggerType){
        Logger logger;

        if (loggerType == "database"){
            // 此处一般会添加相应的初始化（连接数据库等）
            logger = new FileLogger();
        }
        else if(loggerType == "file"){
            // 此处一般会添加相应的初始化 （创建日志文件等）
            logger = new DataBaseLogger();
        }
        else
            logger = null;

        return logger;
    }

    // 解决添加新类需要修改源码的问题，直接通过字符串创建，也免去了大量的嵌套
    // 如果存在不同的初始化逻辑会出现麻烦
    public static Logger produceLogger2(String loggerName) {
        Logger logger = null;

        try {
            Class c= Class.forName(loggerName);
            logger = (Logger)c.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        return logger;
    }

}

// -----------------  默认上面的代码会打包，需要尽可能降低修改 ----



public class SimpleFactoryPattern {

    public static void main(String []argvs){

        // 此处的输入一般采用配置文件的形式
        Logger currentLogger = SimpleFact.produceLogger("file");
        currentLogger.writeLog();
        SimpleFact.produceLogger2("com.extendswind.factory.simpleFactory.FileLogger").writeLog();
    }

}

