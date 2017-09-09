package annotations;

public @interface Service {

    String name();

    boolean lazyLoad() default false;
}
