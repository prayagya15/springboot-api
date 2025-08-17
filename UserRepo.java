package prayagya.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import prayagya.entities.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Integer>  {
	
	public UserEntity findByEmail(String email);
	
	public UserEntity findByEmailAndPwd(String email,String pwd);
	
	//55 min

}
