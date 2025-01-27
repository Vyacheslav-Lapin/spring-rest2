package ru.ibs.trainings.spring.advanced.impl.controllers.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.ibs.trainings.spring.api.CountryController;

@FeignClient(
    name = "CountryController",
    url = "https://kjhsfg.ru",
    path = "/api/v1"
)
public interface CountryControllerClient extends CountryController {
}
