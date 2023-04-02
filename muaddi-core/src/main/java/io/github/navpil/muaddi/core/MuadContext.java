package io.github.navpil.muaddi.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MuadContext {

    private Set<Class<?>> classes = new HashSet<>();
    private Map<Class<?>, Object> instances = new HashMap<>();

    private boolean initialized = false;

    public <T> MuadContext register(Class<T> clazz) {
        classes.add(clazz);
        return this;
    }

    public MuadContext initialize() {
        Map<Class, Constructor> allConstructors = new HashMap<>();

        for (Class<?> clazz : classes) {
            allConstructors.put(clazz, findConstructor(clazz));
        }

        for (Class<?> clazz : allConstructors.keySet()) {
            createInstance(clazz, allConstructors);
        }

        initialized = true;
        return this;
    }

    private Constructor findConstructor(Class clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> injectedConstructor = null;
        Constructor<?> defaultConstructor = null;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getDeclaredAnnotation(MuadInject.class) != null) {
                injectedConstructor = constructor;
                break;
            } else if (constructor.getParameterCount() == 0) {
                defaultConstructor = constructor;
            }
        }
        if (injectedConstructor != null) {
            return injectedConstructor;
        } else if (defaultConstructor != null) {
            return defaultConstructor;
        } else {
            throw new RuntimeException("Cannot find correct constructor for " + clazz);
        }
    }

    private <T> T createInstance(Class clazz, Map<Class, Constructor> allConstructors) {
        if (!instances.containsKey(clazz)) {
            Constructor<?> constructor = allConstructors.get(clazz);
            if (constructor.getParameterCount() == 0) {
                instances.put(clazz, instantiate(constructor));
            } else {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                Object[] parameters = new Object[constructor.getParameterCount()];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameters[i] = createInstance(parameterTypes[i], allConstructors);
                }
                instances.put(clazz, instantiate(constructor, parameters));
            }
        }
        return (T)instances.get(clazz);
    }

    private static Object instantiate(Constructor<?> constructor, Object[] parameters){
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object instantiate(Constructor<?> constructor)  {
        try {
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T get(Class<T> clazz) {
        if (!initialized) {
            throw new ContextNotInitializedException();
        }
        return (T)instances.get(clazz);
    }

    public static class ContextNotInitializedException extends RuntimeException {
    }

}
