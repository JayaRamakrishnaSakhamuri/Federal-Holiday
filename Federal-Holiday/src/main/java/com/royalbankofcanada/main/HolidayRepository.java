package com.royalbankofcanada.main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>{
	
	List<Holiday> findByCountry(Country country);

}
