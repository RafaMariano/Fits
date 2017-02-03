package br.inpe.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import br.inpe.database.Query;
import br.inpe.model.Country;


@Path("/images")
public class CountryRestService {
	
	private Map<Long,Country> messages;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Country> getCountries() {
		List<Country> listOfCountries = new ArrayList<Country>();
		listOfCountries = createCountryList();
		this.messages = createCountryLis();
		return listOfCountries;
	}

//	@GET
//	@Path("{id: \\d+}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Country getCountryById(@PathParam("id") int id) {
//		List<Country> listOfCountries = new ArrayList<Country>();
//		listOfCountries = createCountryList();
//		for (Country country : listOfCountries) {
//			if (country.getId() == id)
//				return country;
//		}
//		return null;
//	} // Utiliy method to create country list.

	@GET
	@Path("{id: \\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Object> getCountryById(@PathParam("id") int id) throws JsonGenerationException, JsonMappingException, IOException {
		
		return Query.findOne(id);
		//se for null erro 404?
	}
	
	public List<Country> createCountryList() {
		Country indiaCountry = new Country(1, "India");
		Country chinaCountry = new Country(4, "China");
		Country nepalCountry = new Country(3, "Nepal");
		Country bhutanCountry = new Country(2, "Bhutan");
		List<Country> listOfCountries = new ArrayList<Country>();
		listOfCountries.add(indiaCountry);
		listOfCountries.add(chinaCountry);
		listOfCountries.add(nepalCountry);
		listOfCountries.add(bhutanCountry);
		return listOfCountries;
	}
	
	public List <Country> getAllMessage(int start, int size){
		ArrayList <Country> a = new ArrayList<Country>(messages.values());
		return a.subList(start,  start+size);
	}
	
	public Map<Long, Country> createCountryLis() {
		Country indiaCountry = new Country(1, "India");
		Country chinaCountry = new Country(4, "China");
		Country nepalCountry = new Country(3, "Nepal");
		Country bhutanCountry = new Country(2, "Bhutan");
//		List<Country> listOfCountries = new ArrayList<Country>();
//		listOfCountries.add(indiaCountry);
//		listOfCountries.add(chinaCountry);
//		listOfCountries.add(nepalCountry);
//		listOfCountries.add(bhutanCountry);
		Map<Long,Country> mes = new HashMap<Long, Country>();
		mes.put((long) 1, indiaCountry);
		mes.put((long) 2, chinaCountry);
		mes.put((long) 3, nepalCountry);
		mes.put((long) 4, bhutanCountry);
		return mes;
	}
}
