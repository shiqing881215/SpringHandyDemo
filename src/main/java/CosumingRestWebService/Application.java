package CosumingRestWebService;

import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author shiqing
 *
 *	Simple servlet to do the Restful service call
 *	http://spring.io/guides/gs/consuming-rest/
 */
public class Application {
	public static void main(String[] args) {
		// RestTemplate can do HTTP GET,POST,DELETE,PUT 
		RestTemplate restTemplate = new RestTemplate();
		Page page = restTemplate.getForObject("http://graph.facebook.com/salesforce", Page.class);
		
		System.out.println(page.getId());
		System.out.println(page.getAbout());
		System.out.println(page.getName());
		System.out.println(page.getPhone());
		System.out.println(page.getWebsite());
		System.out.println(page.getParking().getLot());
		System.out.println(page.getParking().getStreet());
		System.out.println(page.getParking().getValet());
	}
}
