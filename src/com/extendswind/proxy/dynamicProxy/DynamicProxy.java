package com.extendswind.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 动态代理利用java的反射机制动态得到一个类。
 *
 * 静态代理要求代理类与被代理类实现相同的接口，而动态代理不需要针对具体的类，而是动态的对传入的类进行处理。
 *
 * 因此动态代理的主要优势为：动态代理不针对具体的类，因此，当较多的接口需要相同的调度逻辑时，只通过一个动态代理类即可实现，而不需静态代理需要较多的实现类。
 */


interface Logger {
    void writeLog();
}

class Logger1 implements Logger{
    @Override
    public void writeLog(){
        System.out.println("writeLog by Logger1");
    }
}

class DynamicProxyTest implements InvocationHandler
{
    private Logger logger;
    DynamicProxyTest(Logger logger){
        this.logger = logger;
    }

    @Override
    public Object invoke(Object object, Method method, Object[] args)
            throws Throwable
    {
        //　　在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before logger");
        System.out.println("Method:" + method);

        //    当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(logger, args);

        //　　在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("after logger");
        return null;
    }
}

public class DynamicProxy {

    public static void main(String []argvs){
        Logger logger = new Logger1();
        InvocationHandler handler = new DynamicProxyTest(logger);

        // 此处在运行时由JVM动态生成了一个新的对象
        Logger proxyLogger = (Logger)Proxy.newProxyInstance(handler.getClass().getClassLoader(), logger
                .getClass().getInterfaces(), handler);
        proxyLogger.writeLog();
    }


    // 一种直接使用handler对象的方法
    private static void directProxy(){
        Logger logger = new Logger1();
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before logger");
                method.invoke(logger, args);
                return null;
            }
        };
        // 此处略奇怪，handler被传入了两次，可能有其他用处
        // 此处在运行时由JVM动态生成了一个新的对象
        Logger proxyLogger = (Logger)Proxy.newProxyInstance(handler.getClass().getClassLoader(), logger
                .getClass().getInterfaces(), handler);
        // 通过代理调用函数后，会跳到invoke函数中，method.invoke()为实际调用的位置
        proxyLogger.writeLog();
    }

}
