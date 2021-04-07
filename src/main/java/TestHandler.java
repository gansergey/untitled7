import annotation.AfterSuite;
import annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestHandler {

    public static void start(Class<?> testClass) {
        Method[] declaredMethods = testClass.getDeclaredMethods();
        Method beforeMethod = getSuitMethod(BeforeSuite.class, declaredMethods);
        Method afterMethod = getSuitMethod(AfterSuite.class, declaredMethods);

        runTests(declaredMethods, beforeMethod, afterMethod);
    }

    private static Method getSuitMethod(Class<? extends Annotation> suitAnnotationClass, Method[] declaredMethods) {
        Method suitMethod = null;

        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(suitAnnotationClass)) {
                if (suitMethod != null) {
                    throw new RuntimeException(suitAnnotationClass.getName() + " more than one");
                } else {
                    suitMethod = declaredMethod;
                }
            }
        }
        return suitMethod;
    }

    private static void runTests(Method[] declaredMethods, Method beforeMethod, Method afterMethod) {
        invokeMethod(beforeMethod);
        callTestMethods(declaredMethods);
        invokeMethod(afterMethod);
    }

    private static void callTestMethods(Method[] declaredMethods) {
        List<Method> filterMethods = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Test.class)) {
                filterMethods.add(declaredMethod);
            }
        }
        filterMethods.sort((m1, m2) -> Integer.compare(m2.getAnnotation(Test.class).priority(), m1.getAnnotation(Test.class).priority()));
        for (Method in : filterMethods) {
            invokeMethod(in);
        }
    }

    private static void invokeMethod(Method method) {
        try {
            method.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
