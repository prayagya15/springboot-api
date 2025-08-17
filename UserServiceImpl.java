package prayagya.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prayagya.dto.LoginFormDTO;
import prayagya.dto.RegisterFormDTO;
import prayagya.dto.ResetpwdFormDTO;
import prayagya.dto.UserDTO;
import prayagya.entities.CityEntity;
import prayagya.entities.CountryEntity;
import prayagya.entities.StateEntity;
import prayagya.entities.UserEntity;
import prayagya.repos.CityRepo;
import prayagya.repos.CountryRepo;
import prayagya.repos.StateRepo;
import prayagya.repos.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private CountryRepo countryRepo;
	@Autowired
	private StateRepo stateRepo;
	@Autowired
	private CityRepo cityRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailService emailService;
	

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryEntity> countriesList = countryRepo.findAll();
		Map<Integer, String> countryMap= new HashMap<>();
		countriesList.stream().forEach(c->{
			countryMap.put(c.getCuntryId(), c.getCuntryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		Map<Integer, String> statesMap= new HashMap<>();
		
		List<StateEntity> statesList = stateRepo.findByCountryId(countryId);
		statesList.forEach(s-> {
			statesMap.put(s.getStateId(), s.getStateName());
		});
		return statesMap;
	}

	@Override
	public Map<Integer, String> getCitis(Integer stateId) {
		Map<Integer, String> citiesMap= new HashMap<>();
		List<CityEntity> citiesList = cityRepo.findByStateId(stateId);
		citiesList.forEach(c->{
			citiesMap.put(c.getCityId(), c.getCityName());
		});
		return citiesMap;
	}

	@Override
	public boolean duplicatEmailCheck(String email) {
		UserEntity byEmail = userRepo.findByEmail(email);
		//return byEmail !=null;
		if(byEmail !=null) {
			
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean saveUser(RegisterFormDTO registerFormDTO) {
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(registerFormDTO, userEntity);
		
		CountryEntity country = countryRepo.findById(registerFormDTO.getCountryId()).orElse(null);
		userEntity.setCountry(country);
		
		StateEntity state = stateRepo.findById(registerFormDTO.getStateId()).orElse(null);
		userEntity.setState(state);
		
		CityEntity city = cityRepo.findById(registerFormDTO.getCityId()).orElse(null);
		userEntity.setCity(city);
		
		String randomPwd = generateRandomPwd();
		
		userEntity.setPwd(randomPwd);
		userEntity.setPwdUpdated("No");
		
		UserEntity savedUser = userRepo.save(userEntity);
		
		if(null !=savedUser.getUserId()) {
			
			String subject ="Your Account created";
			String body ="Your Password To Login : "+randomPwd;
			String to = registerFormDTO.getEmail();
			emailService.sendEmail(subject, body, to);
			
			return true;
		}
		return false;
	}

	@Override
	public UserDTO login(LoginFormDTO loginFormDTO) {
		UserEntity userEntity = userRepo.findByEmailAndPwd(loginFormDTO.getEmail(), loginFormDTO.getPwd());
		
		if(userEntity != null) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			return userDTO;
			
		}
		return null;
	}

	@Override
	public boolean resetPwd(ResetpwdFormDTO resetpwdFormDTO) {
		
		String email = resetpwdFormDTO.getEmail();
		
		UserEntity entity = userRepo.findByEmail(email);
		entity.setPwd(resetpwdFormDTO.getNewPwd());
		entity.setPwdUpdated("yes");
		
		return true;
	}
	
	private String generateRandomPwd() {
		String upperCaseLetters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerCaseLetters="abcdefghijklmnopqrstuvwxyz";
		
		String alphabets = upperCaseLetters + lowerCaseLetters;
		
		Random random = new Random();
		
		StringBuffer generatedPwd = new StringBuffer();
		
		for(int i=0; i<5; i++) {
			//give  any  number form 0 to 51
			int randomIndex = random.nextInt(alphabets.length());
			generatedPwd.append(alphabets.charAt(randomIndex));
			
		}
		return generatedPwd.toString();
	}
	

}
