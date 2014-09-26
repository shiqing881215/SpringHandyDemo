package CosumingRestWebService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author shiqing
 *
 *	Data POJO to hold the web service restful call response
 *	http://spring.io/guides/gs/consuming-rest/
 */
@JsonIgnoreProperties(ignoreUnknown = true)   // This will ignore any value not included here
public class Page {
	private String id;
	private String name;
	private String about;
	private String phone;
	private String website;
	private Parking parking;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAbout() {
		return about;
	}
	public String getPhone() {
		return phone;
	}
	public String getWebsite() {
		return website;
	}
	public Parking getParking() {
		return parking;
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)   // This will ignore any value not included here
	public static class Parking {
		private String lot;
		private String street;
		private String valet;
		
		public String getLot() {
			return lot;
		}
		public String getStreet() {
			return street;
		}
		public String getValet() {
			return valet;
		}
	}
}
