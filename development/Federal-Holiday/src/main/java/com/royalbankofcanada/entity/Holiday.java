package com.royalbankofcanada.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Holiday{
	
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
    
private String name;
    
@Enumerated(EnumType.STRING)
private Country country;
    
private String date; // Format: YYYY-MM-DD


public Holiday(Long id, String name, Country country, String date) {
	super();
	this.id = id;
	this.name = name;
	this.country = country;
	this.date = date;
}

public Holiday() {
	super();
	// TODO Auto-generated constructor stub
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Country getCountry() {
	return country;
}

public void setCountry(Country country) {
	this.country = country;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}

}
