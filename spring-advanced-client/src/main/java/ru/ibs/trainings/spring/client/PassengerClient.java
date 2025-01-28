package ru.ibs.trainings.spring.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.ibs.trainings.spring.api.PassengerController;

@FeignClient(name = "passenger-client",
             url = "${client.url:http://localhost:8081}",
             path = "${client.contextPath:/api}")
public interface PassengerClient extends PassengerController {
}
