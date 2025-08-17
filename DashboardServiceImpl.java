package prayagya.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import prayagya.dto.QuoteApiResponseDTO;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	private String quoteApiURL ="https://dummyjson.com/quotes/random";

	@Override
	public QuoteApiResponseDTO getQuote() {
		
		RestTemplate rt = new RestTemplate();
		
		// send Http get req and store response into QuoteApiResponseDTO object
		ResponseEntity<QuoteApiResponseDTO> forEntity = 
				rt.getForEntity(quoteApiURL, QuoteApiResponseDTO.class);
		
		QuoteApiResponseDTO body = forEntity.getBody();
		
		return body;
		
	}

}
