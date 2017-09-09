package process;

import model.ServiceHelper;
import annotations.Init;
import annotations.Service;
import service.LazyService;
import service.SimpleService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationProcessor {
    private static Map<String, ServiceHelper> services = new HashMap<>();

    public static void main(String[] args){
        loadService("service.LazyService");
        loadService("service.SimpleService");
        loadService("java.lang.String");
        System.out.println("****************************************");
        LazyService lazyService = (LazyService) getServiceByName("veryLazyService");
        SimpleService simpleService = (SimpleService) getServiceByName("superPuperService");
    }

    private static void loadService(String className){
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(Service.class)) {
                Object serviceObj = clazz.newInstance();
                invokeInitMethod(clazz, serviceObj);
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }

    private static void invokeInitMethod(Class<?> service, Object serviceObj) {
        ServiceHelper serviceHelper;
        if (service.getAnnotation(Service.class).lazyLoad()) {
            serviceHelper = new ServiceHelper(serviceObj, true, service);
        } else {
            serviceHelper = new ServiceHelper(serviceObj, false, service);
            for (Method method : service.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Init.class)) {
                    try {
                        method.invoke(serviceObj);
                    } catch (Exception e) {
                        Init ann = method.getAnnotation(Init.class);
                        if (ann.suppressException()) {
                            System.err.println(e.getMessage());
                        } else {
                            throw new RuntimeException();
                        }
                    }
                }
            }
        }
        services.put(service.getAnnotation(Service.class).name(), serviceHelper);
    }

    public static Object getServiceByName(String name) {
        ServiceHelper service = services.get(name);
        if (service.isLazy()) {
            new Thread(() -> {
                for (Method method : service.getClazz().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(Init.class)) {
                        try {
                            method.invoke(service.getService());
                        } catch (Exception e) {
                            Init ann = method.getAnnotation(Init.class);
                            if (ann.suppressException()) {
                                System.err.println(e.getMessage());
                            } else {
                                throw new RuntimeException();
                            }
                        }
                    }
                }
            }).start();
        }
        return service.getService();
    }
}
