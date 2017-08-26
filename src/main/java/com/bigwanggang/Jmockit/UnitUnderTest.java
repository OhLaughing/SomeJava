package com.bigwanggang.Jmockit;

public class UnitUnderTest {
    private final DependencyAbc abc = new DependencyAbc();

    public void doSomething() {
        int n = abc.intReturningMethod();

        for (int i = 0; i < n; i++) {
            String s;

            try {
                s = abc.stringReturningMethod();
                System.out.println(s);
            } catch (SomeCheckedException e) {
                // 处理异常
            }

            // 这里可以处理其他逻辑
        }
    }
}