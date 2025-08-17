package prayagya.service;

import java.util.Map;

import prayagya.dto.LoginFormDTO;
import prayagya.dto.RegisterFormDTO;
import prayagya.dto.ResetpwdFormDTO;
import prayagya.dto.UserDTO;

public interface UserService {
	
	public Map<Integer, String> getCountries();
	
	public Map<Integer, String> getStates(Integer countryId);
	
	public Map<Integer, String> getCitis(Integer stateId);
	
	public boolean duplicatEmailCheck(String email);
	
	public boolean saveUser(RegisterFormDTO registerFormDTO);
	
	public UserDTO login(LoginFormDTO loginFormDTO);
	
	public boolean resetPwd(ResetpwdFormDTO resetpwdFormDTO);

}
