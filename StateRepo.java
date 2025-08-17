package prayagya.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import prayagya.entities.StateEntity;

public interface StateRepo extends JpaRepository<StateEntity, Integer>  {
	
	public List<StateEntity> findByCountryId(Integer countryId);

}
