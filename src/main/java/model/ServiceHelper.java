package model;

public class ServiceHelper {

    private Object service;
    private boolean lazy;
    private Class<?> clazz;

    public ServiceHelper(Object service, boolean lazy, Class<?> clazz) {
        this.service = service;
        this.lazy = lazy;
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getService() {
        return service;
    }

    public boolean isLazy() {
        return lazy;
    }

}
