package com.royalbankofcanada.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.royalbankofcanada.entity.Country;
import com.royalbankofcanada.entity.Holiday;
import com.royalbankofcanada.repository.HolidayRepository;

@Service
public class HolidayService {
	
	@Autowired
	private final HolidayRepository holidayRepository;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    public Holiday addHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    public List<Holiday> getHolidays(Country country) {
        return holidayRepository.findByCountry(country);
    }

    public Holiday updateHoliday(Long id, Holiday holidayDetails) {
        return holidayRepository.findById(id).map(holiday -> {
        	holiday.setName(holidayDetails.getName());
            holiday.setDate(holidayDetails.getDate());
            return holidayRepository.save(holiday);
        }).orElseThrow(() -> new RuntimeException("Holiday not found"));
    }

    public void uploadFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            List<Holiday> holidays = reader.lines().skip(1) // Skip header
                .map(line -> {
                    String[] data = line.split(",");
                    return new Holiday(null, data[0], Country.valueOf(data[1].toUpperCase()), data[2]);
                }).collect(Collectors.toList());
            holidayRepository.saveAll(holidays);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process file", e);
        }
    }

}
