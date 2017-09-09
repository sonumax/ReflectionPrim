package service;

import annotations.Init;
import annotations.Service;

@Service(name = "varyLazyService", lazyLoad = true)
public class LazyService {

    @Init
    public void lazyInit() throws Exception {
        System.out.println("LazyService - lazyInit");
    }
}
