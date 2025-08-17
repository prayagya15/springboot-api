package prayagya.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import prayagya.dto.LoginFormDTO;
import prayagya.dto.QuoteApiResponseDTO;
import prayagya.dto.RegisterFormDTO;
import prayagya.dto.ResetpwdFormDTO;
import prayagya.dto.UserDTO;
import prayagya.repos.CountryRepo;
import prayagya.service.DashboardService;
import prayagya.service.UserService;

@Controller
public class UserController {

    private final CountryRepo countryRepo;
	
	@Autowired
	private UserService userService;
	@Autowired
	private DashboardService dashboardService;

    UserController(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }
	
	@GetMapping("/register")
	public  String loadRegisterPage( Model model) {
		Map<Integer, String> countriesMap = userService.getCountries();
		 model.addAttribute("countries", countriesMap);
		 RegisterFormDTO registerFormDTO = new  RegisterFormDTO();
		 model.addAttribute("registerForm", registerFormDTO);
		return"register";
	}
	
	@GetMapping("/states/{countryId}")
	@ResponseBody
	public  Map<Integer, String> getStates(@PathVariable Integer countryId) {
		Map<Integer, String> statesMap = userService.getStates(countryId);
		System.out.println(statesMap);
		 //model.addAttribute("states", statesMap);
		return statesMap;	
	}
	
	@GetMapping("/cities/{stateId}")
	@ResponseBody
	public  Map<Integer, String> getCities(@PathVariable Integer stateId) {
		Map<Integer, String> citiesMap = userService.getCitis(stateId);
		// model.addAttribute("cities", citiesMap);
		return citiesMap;	
	}
	
	@PostMapping("/register")
	public  String handelRegisterPage(RegisterFormDTO registerFormDTO, Model model) {
		boolean status = userService.duplicatEmailCheck(registerFormDTO.getEmail());
		if(status) {
			model.addAttribute("emsg", "Dupicat email found");
		}else {
			boolean saveUser = userService.saveUser(registerFormDTO);
			if(saveUser) {
				//user seved
				model.addAttribute("smsg", "Registration Successfull, Please check your email..!!");
			}else {
				//failed to seve
				model.addAttribute("emsg", "Registration Failed!");
			}
		} 
		model.addAttribute("registerForm", new RegisterFormDTO());
		model.addAttribute("countries", userService.getCountries());
		return"register";
	}
    @GetMapping("/")
	public String index(Model model) {
    	
		LoginFormDTO loginFormDTO = new LoginFormDTO();
		model.addAttribute("loginForm", loginFormDTO);
		
		return "login";
	}
    
    @PostMapping("/login")
	public String handelUserLogin(LoginFormDTO loginFormDTO, Model model) {
    	
		UserDTO userDTO = userService.login(loginFormDTO);
		if(userDTO==null) {
			model.addAttribute("emsg", "Invalid Credentials");
			model.addAttribute("loginForm",  new LoginFormDTO());
			
		}else {
			String pwdUpdated = userDTO.getPwdUpdated();
			if("Yes" .equals(pwdUpdated)) {
				//display deshboard
				return "redirect:deshboard";
			}else {
				//display reset pwd page
				return "redirect:reset-pwd-page?email" + userDTO.getEmail();
				//return "redirect:reset-pwd-page";
			}
		}
		
		return "login";
	}
    
    @GetMapping("/deshboard")
    public String dashboard(Model model) {
    	QuoteApiResponseDTO quoteApiResponseDTO = dashboardService.getQuote();
    	model.addAttribute("quote", quoteApiResponseDTO);
    	return "deshboard";
    }
    
    @GetMapping("/reset-pwd-page")
    public String loadResetPwdPage(@RequestParam String email,Model model) {
    	
    	ResetpwdFormDTO resetpwdFormDTO = new ResetpwdFormDTO();
    	resetpwdFormDTO.setEmail(email);
    	model.addAttribute("resetPwd", resetpwdFormDTO);
    	return "resetPwd";
    }

    @PostMapping("/resetPwd")
    public String handelPwdReset(ResetpwdFormDTO resetpwdFormDTO,Model model) {
    	boolean resetPwd = userService.resetPwd(resetpwdFormDTO);
    	if(resetPwd) {
    		return "redirect:deshboard";
    	}
    	return "resetPwd";
    }
}
