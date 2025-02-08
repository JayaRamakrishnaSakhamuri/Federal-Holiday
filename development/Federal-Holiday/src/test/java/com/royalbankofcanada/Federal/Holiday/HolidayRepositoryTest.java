package com.royalbankofcanada.Federal.Holiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.royalbankofcanada.entity.Country;
import com.royalbankofcanada.entity.Holiday;
import com.royalbankofcanada.repository.HolidayRepository;

@DataJpaTest
public class HolidayRepositoryTest {
	
	@Autowired
    private HolidayRepository holidayRepository;

    @Test
    public void testFindByCountry() {
        // Arrange: Save test data
        Holiday holiday1 = new Holiday(null, "Canada Day", Country.CANADA, "2025-07-01");
        Holiday holiday2 = new Holiday(null, "Thanksgiving", Country.USA, "2025-11-27");

        holidayRepository.save(holiday1);
        holidayRepository.save(holiday2);

        // Act: Find holidays by country
        List<Holiday> canadaHolidays = holidayRepository.findByCountry(Country.CANADA);
        List<Holiday> usaHolidays = holidayRepository.findByCountry(Country.USA);

        // Assert
        assertThat(canadaHolidays).hasSize(1);
        assertThat(usaHolidays).hasSize(1);
    }

}
