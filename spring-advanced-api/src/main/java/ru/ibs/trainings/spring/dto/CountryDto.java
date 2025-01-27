package ru.ibs.trainings.spring.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
@JsonPropertyOrder({"name", "codeName"})
public record CountryDto(
    String codeName,
    String name) {
}
