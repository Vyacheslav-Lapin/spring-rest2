package ru.ibs.trainings.spring.advanced.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.NonFinal;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.ibs.trainings.spring.advanced.impl.beans.FlightConfig;
import ru.ibs.trainings.spring.advanced.impl.dao.CountryRepository;
import ru.ibs.trainings.spring.advanced.impl.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.impl.exceptions.PassengerNotFoundException;
import ru.ibs.trainings.spring.advanced.impl.mappers.CollectionMapper;
import ru.ibs.trainings.spring.advanced.impl.mappers.CountryMapper;
import ru.ibs.trainings.spring.advanced.impl.mappers.PassengerMapper;
import ru.ibs.trainings.spring.advanced.impl.model.Passenger;
import ru.ibs.trainings.spring.dto.CountryDto;
import ru.ibs.trainings.spring.dto.FlightDto;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(FlightConfig.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtensionMethod(suppressBaseMethods = false,
    value = {
        CollectionMapper.class,
        CountryMapper.class,
    })
class RestApplicationTest {

    MockMvc mvc;
    FlightDto flight;
    Map<String, CountryDto> countriesMap;

    @SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
    @MockitoBean @NonFinal PassengerRepository passengerRepository;

    @SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
    @MockitoBean @NonFinal CountryRepository countryRepository;

    @Test
    void testGetAllCountries() throws Exception {
        when(countryRepository.findAll())
            .thenReturn(new ArrayList<>(
                countriesMap.values()
                            .transformList(CountryMapper::toCountryEntity)
            ));

        mvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPassengers() throws Exception {
        when(passengerRepository.findAll())
            .thenReturn(new ArrayList<>(flight.passengers()
                                              .transformList(PassengerMapper::toPassengerEntity)));

        mvc.perform(get("/passengers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(20)));

        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    void testPassengerNotFound() {
        Throwable throwable = assertThrows(ServletException.class, () ->
            mvc.perform(get("/passengers/30"))
               .andExpect(status().isNotFound()));
        assertEquals(PassengerNotFoundException.class, throwable.getCause().getClass());
    }

    @Test
    @Disabled
    void testPostPassenger() throws Exception {

        val passenger = new Passenger("Peter Michelsen")
            .setCountry(countriesMap.get("US").toCountryEntity());

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        val resultActions = mvc.perform(post("/passengers")
                                                      .content(new ObjectMapper().writeValueAsString(passenger))
                                                      .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                               .andExpect(status().isCreated());

//        val response = resultActions.andReturn()
//                                    .getResponse()
//                                    .getContentAsString();

        resultActions
            .andExpect(jsonPath("$.name", is("Peter Michelsen")))
            .andExpect(jsonPath("$.country.codeName", is("US")))
            .andExpect(jsonPath("$.country.name", is("USA")))
            .andExpect(jsonPath("$.registered", is(Boolean.FALSE)));

        verify(passengerRepository, times(1)).save(passenger);

    }

    @Test
    void testPatchPassenger() throws Exception {
        Passenger passenger = new Passenger("Sophia Graham");
        passenger.setCountry(countriesMap.get("UK").toCountryEntity());
        passenger.setRegistered(false);
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(passenger)).thenReturn(passenger);
        String updates =
                "{\"name\":\"Sophia Jones\", \"country\":\"AU\", \"isRegistered\":\"true\"}";

        mvc.perform(patch("/passengers/1")
                .content(updates)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(passengerRepository, times(1)).findById(1L);
        verify(passengerRepository, times(1)).save(passenger);
    }

//    PassengerControllerClient client;
//
//    @Test
//    @DisplayName(" works correctly")
//    void worksCorrectlyTest() {
//         given
//        val passenger = client.findPassenger(14325L);
//         when
//        assertThat(passenger).isNotNull()
//                              then
//                             .extracting(Passenger::getCountry)
//                             .extracting(Country::getCodeName)
//                             .isEqualTo("NK");
//    }
}
