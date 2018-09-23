package com.cxx.aop.prelude.handle;

import com.cxx.aop.prelude.ArithmeticCalculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 数据验证第二种方法--也是代理模式
 * 没有实现InvocationHandler接口
 */
public class CalculatorValidationHandlerSecondMethod {
    private ArithmeticCalculator target;

    public CalculatorValidationHandlerSecondMethod(ArithmeticCalculator target) {
        this.target = target;
    }

    public ArithmeticCalculator getValidationProxy() {
        ArithmeticCalculator proxy = null;
        // 代理对象是由那个类加载器负责加载
        ClassLoader loader = target.getClass().getClassLoader();
        // 代理对象的类型，即其中有哪些方法
        Class[] interfaces = new Class[]{ArithmeticCalculator.class};
        // 当调用代理对象其中的方法时，执行的代码
        InvocationHandler h = new InvocationHandler() {
            /**
             *
             * @param proxy 正在返回的代理的对象，一边拿情况下，在invoke 方法中都不是用该对象。因为proxy也要进行生成后才可以被调用，所以每次调用对要回到这里，所以会生成死循环
             * @param method 正在被调用的方法
             * @param args 调用方法时，传入的参数
             * @return
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("方法调用 - 开始： The method " + method.getName());
                for (Object o : args) {
                    validate((Integer) o);
                }
                Object result = method.invoke(target,args);
                System.out.println("方法调用 - 结束： The method " + method.getName());
                return result;
            }
        };

        proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader,interfaces,h);
        return proxy;
    }

    private void validate(Integer arg) {
        if (arg<0) throw new IllegalArgumentException("Positive numbers only") ;
    }

}
