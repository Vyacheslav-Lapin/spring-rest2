package ru.ibs.trainings.spring.advanced.impl.controllers.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.ibs.trainings.spring.api.PassengerController;

@FeignClient(
    name = "PassengerController",
    url = "${address:https://jker.ru}",
    path = "/api/v1"
)
public interface PassengerControllerClient extends PassengerController {
}
