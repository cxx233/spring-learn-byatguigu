package com.cxx.aop.prelude;

import com.cxx.aop.prelude.handle.CalculatorLoggingHandler;
import com.cxx.aop.prelude.handle.CalculatorLoggingHandlerSecond;
import com.cxx.aop.prelude.handle.CalculatorValidationHandler;
import com.cxx.aop.prelude.handle.CalculatorValidationHandlerSecondMethod;

public class Main {

    public static void main(String[] args) {
        // 第一种方法的实现调用： 先写日志，再写验证
        ArithmeticCalculator arithmeticCalculatorImpl = new ArithmeticCalculatorImpl();

/*        ArithmeticCalculator arithmeticCalculator =
                (ArithmeticCalculator) CalculatorValidationHandler.createProxy(CalculatorLoggingHandler.createProxy(arithmeticCalculatorImpl));
        System.out.println(arithmeticCalculator.mul(5,6));
        System.out.println(arithmeticCalculator.add(-12,13));*/

        // 第二种方法的实现调用：
        ArithmeticCalculator arithmeticCalculator = new ArithmeticCalculatorImpl();
//        ArithmeticCalculator arithmeticCalculatorProxy = new CalculatorValidationHandlerSecondMethod(new CalculatorLoggingHandlerSecond(arithmeticCalculator).getLogginProxy()).getValidationProxy();
        ArithmeticCalculator arithmeticCalculatorProxy = new CalculatorLoggingHandlerSecond(new CalculatorValidationHandlerSecondMethod(arithmeticCalculator).getValidationProxy()).getLogginProxy();
        System.out.println(arithmeticCalculatorProxy.add(5,6));
        System.out.println(arithmeticCalculatorProxy.add(-12,13));


    }
}
