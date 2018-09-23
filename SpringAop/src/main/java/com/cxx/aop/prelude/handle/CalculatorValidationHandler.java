package com.cxx.aop.prelude.handle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Calculator的验证代理器 -- 第一种写法
 */
public class CalculatorValidationHandler implements InvocationHandler{

    private Object target;

    public CalculatorValidationHandler(Object target) {
        this.target = target;
    }

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
        // 验证操作
        for (Object arg: args) {
            validate((Integer) arg);
        }
        Object result = method.invoke(target,args);
        System.out.println("方法调用 - 结束： The method " + method.getName());
        return result;
    }

    public static Object createProxy(Object target) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new CalculatorValidationHandler(target));
    }

    private void validate(Integer arg) {
        if (arg<0) throw new IllegalArgumentException("Positive numbers only") ;
    }
}
