package prayagya.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import prayagya.entities.CityEntity;


public interface CityRepo extends JpaRepository<CityEntity, Integer>  {
	
	public List<CityEntity> findByStateId(Integer stateId);
	

}
