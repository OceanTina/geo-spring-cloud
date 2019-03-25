package com.geo.spring.websocket.geospringwebsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GeoSpringWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeoSpringWebsocketApplication.class, args);
    }

}
