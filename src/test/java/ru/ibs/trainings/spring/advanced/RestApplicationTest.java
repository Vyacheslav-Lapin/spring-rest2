package ru.ibs.trainings.spring.advanced;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
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
import ru.ibs.trainings.spring.advanced.beans.FlightBuilder;
import ru.ibs.trainings.spring.advanced.dao.CountryRepository;
import ru.ibs.trainings.spring.advanced.dao.PassengerRepository;
import ru.ibs.trainings.spring.advanced.exceptions.PassengerNotFoundException;
import ru.ibs.trainings.spring.advanced.model.Country;
import ru.ibs.trainings.spring.advanced.model.Flight;
import ru.ibs.trainings.spring.advanced.model.Passenger;

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
@Import(FlightBuilder.class)
class RestApplicationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    Flight flight;

    @Autowired
    Map<String, Country> countriesMap;

    @MockitoBean
    PassengerRepository passengerRepository;

    @MockitoBean
    CountryRepository countryRepository;

    @Test
    void testGetAllCountries() throws Exception {
        when(countryRepository.findAll())
            .thenReturn(new ArrayList<>(countriesMap.values()));

        mvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));

        verify(countryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPassengers() throws Exception {
        when(passengerRepository.findAll())
            .thenReturn(new ArrayList<>(flight.getPassengers()));

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
            .setCountry(countriesMap.get("US"));

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
        passenger.setCountry(countriesMap.get("UK"));
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
