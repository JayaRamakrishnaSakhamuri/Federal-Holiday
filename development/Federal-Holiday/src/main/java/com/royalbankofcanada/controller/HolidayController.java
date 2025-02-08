package com.royalbankofcanada.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.royalbankofcanada.entity.Country;
import com.royalbankofcanada.entity.Holiday;
import com.royalbankofcanada.service.HolidayService;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
	
	private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    public ResponseEntity<Holiday> addHoliday(@RequestBody Holiday holiday) {
        return ResponseEntity.ok(holidayService.addHoliday(holiday));
    }

    @GetMapping("/{country}")
    public ResponseEntity<List<Holiday>> getHolidays(@PathVariable Country country) {
        return ResponseEntity.ok(holidayService.getHolidays(country));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Holiday> updateHoliday(@PathVariable Long id, @RequestBody Holiday holiday) {
        return ResponseEntity.ok(holidayService.updateHoliday(id, holiday));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        holidayService.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully");
    }

}
