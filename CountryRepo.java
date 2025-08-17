package prayagya.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import prayagya.entities.CountryEntity;

public interface CountryRepo extends JpaRepository<CountryEntity, Integer> {
	

}
