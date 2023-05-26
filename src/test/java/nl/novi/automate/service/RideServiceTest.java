package nl.novi.automate.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RideServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addRide() {
    }

//    @Test
//    void getRideById() {
//    }
@Test
void getRideByIdSuccess() throws Exception {
    Long rideId = 1L;

    given(rideService.getRideById(rideId)).willReturn(rideDto1);

    this.mockMvc.perform(MockMvcRequestBuilders.get("/rides/" + rideId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
            .andExpect(MockMvcResultMatchers.jsonPath("$.pickUpLocation").value("Amsterdam"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.destination").value("Utrecht"));

    verify(rideService, times(1)).getRideById(rideId);
}

    @Test
    void getRideByIdRecordNotFoundException() throws Exception {
        Long rideId = 1L;

        given(rideService.getRideById(rideId)).willThrow(new RecordNotFoundException("Geen rit gevonden"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/rides/" + rideId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Geen rit gevonden"));

        verify(rideService, times(1)).getRideById(rideId);
    }


    @Test
    void calculateTotalRitPrice() {
    }

    @Test
    void addUserToRide() {
    }

    @Test
    void getRidesByCriteria() {
    }

    @Test
    void transferRideListToDtoList() {
    }

    @Test
    void deleteRide() {
    }

    @Test
    void updateRide() {
    }

    @Test
    void removeUserFromRide() {
    }
}