package ru.ibs.trainings.spring.advanced.controllers.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.ibs.trainings.spring.advanced.controllers.api.PassengerController;

@FeignClient(
    name = "PassengerController",
    url = "${address:https://jker.ru}",
    path = "/api/v1"
)
public interface PassengerControllerClient extends PassengerController {
}
