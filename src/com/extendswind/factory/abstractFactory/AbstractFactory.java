package com.extendswind.factory.abstractFactory;

// simple factoryMethod 的问题:
// 产品数量过多，又有明显的分类时，使用多个工厂生产自己分类的产品

// 如 有多种主题，每种主题包含的Button和text的逻辑略有不同，使用每个工厂生产的

// 当产品的数量过多时难以分类，难以共用相同的特征
// 如不同的厂商生产的产品有自己的生产需求时

// 解决方案： 使用多个工厂创建，由抽象工厂处理共性特征

// 感觉上抽象工厂和工厂模式的处理思想虽然都是通过工厂类对产品初始化，但抽象工厂模式更像对大量的产品类进行分类组织的方式


// ---- product
class ButtonUI{
    public void print() { System.out.println("ButtonUI");}
}
class ButtonUI_theme1 extends ButtonUI{
    @Override
    public void print() { System.out.println("ButtonUI_theme1");}
}
class ButtonUI_theme2 extends ButtonUI{
    @Override
    public void print() { System.out.println("ButtonUI_theme2");}
}

class TextUI{
    public void print() { System.out.println("TextUI");}
}
class TextUI_theme1 extends TextUI{
    @Override
    public void print() { System.out.println("TextUI_theme1");}
}
class TextUI_theme2 extends TextUI{
    @Override
    public void print() { System.out.println("TextUI_theme2");}
}


// ----- factory
abstract class AbstractThemeFactory{
    public void printCommon(){
        System.out.println("common factory method");
    }
    abstract public ButtonUI createButton();
    abstract public TextUI createText();
}
class Theme1Factory extends AbstractThemeFactory{
    @Override
    public ButtonUI createButton(){ return new ButtonUI_theme1(); }

    @Override
    public TextUI createText() {
        return new TextUI_theme1();
    }
}
class Theme2Factory extends AbstractThemeFactory{
    @Override
    public ButtonUI createButton() {
        return new ButtonUI_theme2();
    }
    @Override
    public TextUI createText() {
        return new TextUI_theme2();
    }
}




// ------------ Test
public class AbstractFactory {

    public static void main(String args[]){
        AbstractThemeFactory factory = new Theme1Factory();
        ButtonUI buttonUI = factory.createButton();
        TextUI textUI = factory.createText();
    }
}
