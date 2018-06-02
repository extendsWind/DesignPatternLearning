package com.extendswind.factory.abstractFactory;

// simple factory 的问题:
// 当产品的数量过多时难以分类，难以共用相同的特征
// 如不同的厂商生产的产品有自己的生产需求时

// 解决方案： 使用多个工厂创建，由抽象工厂处理共性特征


// ----------- Bicycle 为向后提供的product类
class Bicycle {
   public void print(){
       System.out.println("writeLog by Bicycle");
   }
}

class Atx660 extends Bicycle {
    @Override
    public void print(){
        System.out.println("writeLog by the Atx660");
    }
}
class Atx770 extends Bicycle {
    @Override
    public void print(){
        System.out.println("writeLog by the Atx770");
    }
}

class Phoenix28 extends Bicycle {
    @Override
    public void print(){
        System.out.println("writeLog by the Phoenix28");
    }
}


// ----------- FactoryPattern
abstract class AbstractBicycleFactory{
    public void printCommon(){
        System.out.println("common property: bicycle");
    }

    abstract public void printBrand();
    abstract public Bicycle produceBicycle(int bikeType);
}

class GiantFactory extends AbstractBicycleFactory{

    public void printBrand(){
        System.out.println("Giant");
    }

    public Bicycle produceBicycle(int bikeType){
        printBrand();
        if(bikeType == 660)
            return new Atx660();
        else if(bikeType == 770)
            return new Atx770();
        else
            return null;
    }

}
class PhoenixFactory extends AbstractBicycleFactory{

    public void printBrand(){
        System.out.println("Phoenix");
    }

    public Bicycle produceBicycle(int bikeType){
         if(bikeType == 28)
             return new Phoenix28();
         else
             return null;
    }

    @Override
    public void printCommon() {
        super.printCommon();
    }
}


// ------------ Test
public class AbstractFactoryTest {

    public static void main(String args[]){
        AbstractBicycleFactory abf = new GiantFactory();
        Bicycle bicycle = abf.produceBicycle(660);
        bicycle.print();
    }
}
