

# 实现需求

- 实现多个日志记录器logger(文件logger，数据库logger等)
- 使用logger类时，通过配置文件确定logger的类型，并创建logger对象
- 不同的logger类有不同的初始化过程

## 可扩展需求

- 添加新的logger类能够不修改源码

# 一般实现  （FactoryProblem.java）

- logger 基类实现通用的日志记录功能，子类实现各自的特有功能
- 使用时根据配置文件中的类型，new相应的子类

类的实现：

```java
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
```

使用时:

``` java
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
```


# 从一般实现到工厂模式

## 根据字符串生成对象会产生大量的判断

使用时每个logger的初始化过程导致使用的类逻辑复杂

一般的解决方案：将判断过程挪到工厂类中，简化客户端的调用和逻辑 （简单工厂模式）

实质上主要提高了代码的可读性和逻辑，主要为逻辑上的优点。

## 添加新的对象需要修改源码  

利用java的反射机制，直接通过字符串得到对象

``` java
public static Logger produceLogger2(String loggerName) {
     Logger logger = null;
     Class c= Class.forName(loggerName);
     logger = (Logger)c.newInstance();
     // 省略 try catch  ......
     return logger;
 }

```

此时直接编译新类，将类名添加到配置文件即可，实现了添加新类不修改源码

## 不同logger的初始化需要各自不同的设置

前面的反射使客户端对各个具体的logger派生类只能自己负责初始化。当初始化过程复杂时，放在另一个类（工厂类）中会让逻辑更为清晰。（工厂模式）

感觉此处如果初始化较为简单，构建工厂类的行为有点多余。

对每个logger构建一个工厂类，使用工厂类进行一定的逻辑操作创建对象，使用户能够直接通过工厂类获取对象。

添加新的产品时添加logger和对应的工厂类。

# 工厂模式具体实现

## 简单工厂模式

## 工厂模式（工厂方法模式）

## 抽象工厂模式

# 个人看法

new具体对象的主要问题不只是使用不同的类，而是不同类有不同的初始化流程需要处理。

个人认为工厂模式的主要作用为：

- 处理多个产品的选择逻辑
- 处理产品的初始化逻辑和其它流程
- 对产品分类创建

工厂模式不应该被过分整体套用，而应对于具体解决的问题选择其中的处理方式。如对于随便几个参数就能初始化的情况，工厂类起到的作用并不大。

主要的启示：

- 在有多个产品或以后可能有多个产品扩展的情况下（而且数量不会过多、初始化逻辑不复杂），使用简单工厂模式将产品的选择逻辑放在工厂类以简化使用代码。
- 在产品类有较为复杂的初始化和其他逻辑时，使用工厂模式构建工厂类包装简化使用。
- 在产品类较多且有明显的分类时，使用抽象工厂模式对每个分类的产品构建一个工厂类。

## 吐槽

某些书上讲的设计模式的问题有的就是在创造问题。

如很多提到简单工厂模式的缺点在于不适合管理多个产品，添加新产品需要修改源码。前者只在于产品类需要复杂的初始化逻辑，后者和工厂模式一样使用反射就能解决了。

## 网上其他的看法

使用new对类实例化可能破坏类的可扩展性，由于new跟随的是具体的对象，很可能会被修改，因此给其他人使用的类要尽可能少用new。感觉工厂模式虽然对加入新的产品能够降低修改，但反射同样可以解决此问题。

