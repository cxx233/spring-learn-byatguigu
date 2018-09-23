package com.cxx.aop.prelude.handle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Calculatord的日志代理类 -- 第一种写法
 */
public class CalculatorLoggingHandler implements InvocationHandler {

    private Object target; // 目标

    public CalculatorLoggingHandler(Object target) {
        super();
        this.target = target;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("日志输出： The method " + method.getName() + "() begins with " + Arrays.toString(args));
        // method.invoke(target,args); method.invoke一定是目标实例，并且是这些方法
        Object result = method.invoke(target,args);
        System.out.println("日志输出： The method " + method.getName() + "() ends with" + result);
        return result;
    }

    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),new CalculatorLoggingHandler(target));
    }

}
