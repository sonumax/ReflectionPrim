package service;

import annotations.Init;
import annotations.Service;

@Service(name = "superPuperService")
public class SimpleService {

    @Init
    private void initService() {
        System.out.println("SimpleService - initService");
    }

    private void notInitService() {
        System.out.println("SimpleService - notInitService");
    }
}
