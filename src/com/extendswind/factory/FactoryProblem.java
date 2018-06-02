package com.extendswind.factory;

/**

实现需求：
 - 实现多个loggor类(文件loggor，数据库loggor等）
 - 使用loggor类时，通过配置文件确定logger的类型，并创建logger对象

一般实现：


当前实现的缺点:

1. 客户端在判断时逻辑较为复杂
2. 当增加新的实现时（添加一个新的自行车类），需要对new的位置修改
3. 产品类较多时结构不清晰
4.

 三种工厂模式其实并非递进关系，

工厂模式的主要改进：

 简单工厂模式：使用一个工厂类负责所有产品的new过程（创建和初始化），将客户端根据输入参数判断具体产品的复杂逻辑转移到工厂类中，仍有添加新的类时需要修改工厂类的缺点。

 工厂模式： 对每个产品使用一个工厂类包装，主要优点为封装了，添加新的产品不需要修改源码
- 使用工厂类决定生产的具体产品和初始化过程 (简单工厂模式[]）
 - 通过工厂类的进一步封装（初始化细节），使产品更容易使用。
- 抽象工厂模式使用多个工厂生产产品，
 将添加新的实现带来的修改转变为对工厂类的修改

 应用背景：  TODO
 - 保证
 - 有较多的产品类需要管理

注意点：

使用new对类实例化可能破坏类的可扩展性，由于new跟随的是具体的对象，很可能会被修改
给其他人使用的类要尽可能少用new

 */

// Logger 为向后提供的类
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


// -----------------  默认上面的代码会打包，需要尽可能降低修改 ----



public class FactoryProblem{

    private static Logger produceLogger(String loggerType){
        Logger logger;

        if (loggerType.equals("database")){
            // 此处一般会添加相应的初始化
            logger = new FileLogger();
        }
        else if(loggerType.equals("file")){
            // 此处一般会添加相应的初始化
            logger = new DataBaseLogger();
        }
        else
            logger = null;

        return logger;
    }

    // 更高级一点的解决方案，通过反射避免大量的if else
    // 如果每个类有不同的初始化参数通过配置文件由每个类自行处理
    //
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

    public static void main(String []argvs){
        Logger currentLogger = produceLogger("file");
        currentLogger.writeLog();
    }

}


