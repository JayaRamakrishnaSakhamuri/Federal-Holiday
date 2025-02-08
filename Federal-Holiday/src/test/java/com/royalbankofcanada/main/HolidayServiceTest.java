package com.royalbankofcanada.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class HolidayServiceTest {
	@Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayService holidayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddHoliday() {
        // Arrange
        Holiday holiday = new Holiday(null, "Christmas", Country.USA, "2025-12-25");
        when(holidayRepository.save(any(Holiday.class))).thenReturn(holiday);

        // Act
        Holiday savedHoliday = holidayService.addHoliday(holiday);

        // Assert
        assertNotNull(savedHoliday);
        assertEquals("Christmas", savedHoliday.getName());
    }

    @Test
    public void testGetHolidays() {
        // Arrange
        Holiday holiday = new Holiday(null, "Canada Day", Country.CANADA, "2025-07-01");
        when(holidayRepository.findByCountry(Country.CANADA)).thenReturn(List.of(holiday));

        // Act
        List<Holiday> holidays = holidayService.getHolidays(Country.CANADA);

        // Assert
        assertEquals(1, holidays.size());
        assertEquals("Canada Day", holidays.get(0).getName());
    }

    @Test
    public void testUpdateHoliday() {
        // Arrange
        Holiday existingHoliday = new Holiday(1L, "Thanksgiving", Country.USA, "2025-11-27");
        Holiday updatedDetails = new Holiday(1L, "Updated Thanksgiving", Country.USA, "2025-11-28");

        when(holidayRepository.findById(1L)).thenReturn(Optional.of(existingHoliday));
        when(holidayRepository.save(any(Holiday.class))).thenReturn(updatedDetails);

        // Act
        Holiday updatedHoliday = holidayService.updateHoliday(1L, updatedDetails);

        // Assert
        assertEquals("Updated Thanksgiving", updatedHoliday.getName());
        assertEquals("2025-11-28", updatedHoliday.getDate());
    }

    @Test
    public void testUpdateHoliday_NotFound() {
        // Arrange
        when(holidayRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> holidayService.updateHoliday(1L, new Holiday()));
    }

}
