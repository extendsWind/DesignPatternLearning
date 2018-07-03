package com.extendswind.prototype;

/**
 * 首先画重点，原型模式是一种 **创建对象** 的模式。通过复制已经初始化好的对象以避免对对象进行某些复杂和耗时的初始化过程。可能存在多个被复制的对象，创建自不同的类或同一个 类的不同初始化过程，用户需要动态决定复制哪一个对象。
 *
 * 主要实现思想：
 *
 * - 对象的复制只需要在每个类中实现一个clone函数即可
 * - 使用工厂模式相关思想获取具体的clone对象*
 */

/**
 * a simple realization
 * 通过调用clone函数可以得到新的相同的对象
 * 一般java语言的实现用下面的效率更高，因为直接从内存中进行的复制而没有通过重新构造对象
 */
class SimpleClone{
    private String attr;
    public SimpleClone clone(){
        SimpleClone clone = new SimpleClone();
        clone.attr = this.attr;
        return clone;
    }
}

/**
 * java版本的clone实现
 * java的所有类都继承于Object，Object类中定义了native实现的clone函数，但需要实现Cloneable接口才能调用。
 * 注意，java Object的clone函数为 **浅拷贝**，只会复制对象地址而不创建新的对象。
 *
 * 此处吐槽，很多博客和书上的示例实质上就下面这一段，然后加个实例里n个成员变量和函数凑数，实现个Cloneable接口加个clone函数就算完了....就加个clone函数用得着扯上面向对象和设计模式？？
 */
class Prototype implements Cloneable{
    int attr;
    public Prototype clone(){
        Prototype prototype = null;
        try {
            prototype = (Prototype)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return prototype;
    }
}

class ConcretePrototype1 extends Prototype {
    int attr;
    public Prototype clone(){
        Prototype prototype = (ConcretePrototype1)super.clone();
        return prototype;
    }
}
class ConcretePrototype2 extends Prototype {
    int attr;
    public Prototype clone(){
        Prototype prototype = (ConcretePrototype2)super.clone();
        return prototype;
    }
}

/**
 * 使用简单工厂模式的实现
 */
class PrototypeFactory{
    public static Prototype[]prototypes;
    public PrototypeFactory(){
        prototypes = new Prototype[3];
        prototypes[0] = new ConcretePrototype1();
        prototypes[1] = new ConcretePrototype2();
        prototypes[1] = new ConcretePrototype2();// 可以对同一个类进行两种不同的初始化
        // 此处省略初始化操作....
    }
    public static Prototype getProduct(int id){
        return prototypes[id].clone();
    }
}

// for test
public class PrototypePattern {
    public static void main(String []argvs){
        Prototype prototype1 = PrototypeFactory.getProduct(0);
        Prototype prototype2 = PrototypeFactory.getProduct(2);
    }
}



