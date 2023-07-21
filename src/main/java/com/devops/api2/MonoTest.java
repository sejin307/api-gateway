package com.devops.api2;

import reactor.core.publisher.Mono;


public class MonoTest {
    public static void main(String[] args) {
        Mono.just("gateway")
                .doOnNext(response -> System.out.println("response: " + response))
                .subscribe();
    }


}
