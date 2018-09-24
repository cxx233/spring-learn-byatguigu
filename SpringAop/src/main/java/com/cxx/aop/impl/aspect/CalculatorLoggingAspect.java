package com.cxx.aop.impl.aspect;

import com.cxx.aop.impl.ArithmeticCalculator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 计算器的日志切面
 */
// 把这个类声明为一个切面：需要把该类放入到IOC容器中，再声明为一个切面
@Aspect
@Component
public class CalculatorLoggingAspect {

    // 声明该方法是一个前置通知：在目标方法开始之前执行
    @Before("execution(* com.cxx.aop.impl.ArithmeticCalculator.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        // 获取当前目标对象joinPoint.getTarget();
        Object object = joinPoint.getTarget();
//        ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) joinPoint.getTarget();
//        System.out.println(arithmeticCalculator.div(10,8));

        // 获取方法名joinPoint.getSignature().getName()
        String methodName = joinPoint.getSignature().getName();
        // 获取参数joinPoint.getArgs()
        String methodArgs = Arrays.toString(joinPoint.getArgs());

        System.out.println("日志出输出-对象为："+object.getClass()+" 方法："+methodName+" 参数为："+methodArgs);
    }

    // 后置通知：在目标方法执行后（无论是否发生异常），执行的通知
    // 在后置通知中还不能访问目标执行的结果
    @After("execution(* com.cxx.aop.impl.ArithmeticCalculator.*(..))")
    public void afterLog(JoinPoint joinPoint){
        // 获取方法名joinPoint.getSignature().getName()
        String methodName = joinPoint.getSignature().getName();
        // 获取参数joinPoint.getArgs()
        String methodArgs = Arrays.toString(joinPoint.getArgs());
        System.out.println("方法结束后 -- 日志输出-方法："+methodName+" 结束");
    }

}
