package ru.ibs.trainings.spring.client;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = ClientConfiguration.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CountryClientTest {
  CountryClient countryClient;

  @Test
  @Disabled("Need for impl application started")
  @DisplayName("Country client works correctly")
  void countryClientWorksCorrectlyTest() {
    // given
    val countryDtos = countryClient.findAll();

    // when
    assertThat(countryDtos).isNotNull()
        // then
        .hasSize(3);
  }
}
