package ru.ibs.trainings.spring.stub;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.val;
import ru.ibs.trainings.spring.api.CountryController;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.stub.common.CsvUtils;

import java.util.List;

@RequiredArgsConstructor
@Accessors(fluent = true)
public class CountryClientStub implements CountryController {

  @Getter(lazy = true, value = AccessLevel.PRIVATE)
  List<CountryDto> countries = readCountries();

  @SneakyThrows
  private static List<CountryDto> readCountries() {
    @Cleanup val readCountries = CsvUtils.readFile("/countries_information.csv",
                                                            new TypeReference<CountryDto>() {});
    return readCountries.readAll().stream()
                        .toList();
  }

  @Override
  public List<CountryDto> findAll() {
    return countries();
  }
}
