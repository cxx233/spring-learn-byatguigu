package com.cxx.aop.prelude.handle;

import com.cxx.aop.prelude.ArithmeticCalculator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Calculatord的日志代理类 -- 第二种写法
 */
public class CalculatorLoggingHandlerSecond {

    // 要代理的对象
    private ArithmeticCalculator target;

    public CalculatorLoggingHandlerSecond(ArithmeticCalculator target) {
        this.target = target;
    }

    public ArithmeticCalculator getLogginProxy() {
        ArithmeticCalculator proxy = null;

        // 代理对象是由那个类加载器负责加载
        ClassLoader loader = target.getClass().getClassLoader();

        // 代理对象的类型，即其中有哪些方法
        Class[] interfaces = new Class[]{ArithmeticCalculator.class};

        // 当调用代理对象其中的方法时，执行的代码
        InvocationHandler h = new InvocationHandler() {
            /**
             *
             * @param proxy 正在返回的那个代理对象，一般情况下，在invoke 方法中都不适用该对象
             * @param method 正在被调用的方法
             * @param args 调用方法时，传入的参数
             * @return
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println("对象为：" + proxy.toString());
                System.out.println("日志输出： The method " + method.getName() + "() begins with " + Arrays.toString(args));
                Object result = method.invoke(target,args);
                System.out.println("日志输出： The method " + method.getName() + "() ends with " + result);
                return result;
            }
        };

        proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader,interfaces,h);
        return proxy;
    }

}
