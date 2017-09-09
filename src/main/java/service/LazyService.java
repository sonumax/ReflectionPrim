package service;

import annotations.Init;
import annotations.Service;

@Service(name = "veryLazyService", lazyLoad = true)
public class LazyService {

    @Init(suppressException = true)
    public void lazyInit() throws Exception {
        System.out.println("LazyService - lazyInit");
    }
}
