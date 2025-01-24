package ru.ibs.trainings.spring.advanced.controllers.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.ibs.trainings.spring.advanced.controllers.api.CountryController;

@FeignClient(
    name = "CountryController",
    url = "https://kjhsfg.ru",
    path = "/api/v1"
)
public interface CountryControllerClient extends CountryController {
}
