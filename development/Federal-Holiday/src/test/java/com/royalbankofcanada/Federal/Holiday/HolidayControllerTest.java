package com.royalbankofcanada.Federal.Holiday;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.royalbankofcanada.controller.HolidayController;
import com.royalbankofcanada.entity.Country;
import com.royalbankofcanada.entity.Holiday;
import com.royalbankofcanada.service.HolidayService;

public class HolidayControllerTest {

	private MockMvc mockMvc;

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private HolidayController holidayController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(holidayController).build();
    }

    @Test
    public void testAddHoliday() throws Exception {
        // Arrange
        Holiday holiday = new Holiday(null, "Labor Day", Country.USA, "2025-09-01");
        when(holidayService.addHoliday(any(Holiday.class))).thenReturn(holiday);

        // Act & Assert
        mockMvc.perform(post("/api/holidays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(holiday)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Labor Day"));
    }

    @Test
    public void testGetHolidays() throws Exception {
        // Arrange
        Holiday holiday = new Holiday(1L, "Canada Day", Country.CANADA, "2025-07-01");
        when(holidayService.getHolidays(Country.CANADA)).thenReturn(List.of(holiday));

        // Act & Assert
        mockMvc.perform(get("/api/holidays/CANADA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Canada Day"));
    }

    @Test
    public void testUpdateHoliday() throws Exception {
        // Arrange
        Holiday updatedHoliday = new Holiday(1L, "Updated Holiday", Country.USA, "2025-12-31");
        when(holidayService.updateHoliday(eq(1L), any(Holiday.class))).thenReturn(updatedHoliday);

        // Act & Assert
        mockMvc.perform(put("/api/holidays/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedHoliday)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Holiday"));
    }
}
