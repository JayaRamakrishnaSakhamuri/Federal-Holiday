package com.royalbankofcanada.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.royalbankofcanada.entity.Country;
import com.royalbankofcanada.entity.Holiday;


@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long>{
	
	List<Holiday> findByCountry(Country country);

}
