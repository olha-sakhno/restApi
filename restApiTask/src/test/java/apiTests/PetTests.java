package apiTests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import apiEndpoints.PetEndpoints;
import apiEndpoints.Routes;
import apiPayload.Pet;
import apiPayload.PetInvalid;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class PetTests {	
	Pet petPayload;
	PetInvalid petPayloadInvalid;

	@BeforeClass
	public void setupData() {
		petPayload = new Pet();
		petPayloadInvalid = new PetInvalid();
		
		petPayload.setId(123);
		petPayload.setName("Winkie");
		petPayload.setStatus("available");
		
		petPayloadInvalid.setId("invalid");
		petPayloadInvalid.setName("Winkie");
		petPayloadInvalid.setStatus("available");
	
		
	}
	
	@Test(priority = 1)
	public void postPetPositive(){
		Response response = PetEndpoints.createPet(petPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2)
	public void postPetNegative(){
		Response response = PetEndpoints.createPet(petPayload);
		response.then().log().all()
		.body("name", is(not("Terry")));
		
	}
	
	@Test(priority = 3)
	public void getPetByIdPositive(){
		Response response = PetEndpoints.findPet(this.petPayload.getId());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority = 4)
	public void getPetByIdNegative(){
		Response response = PetEndpoints.findPet(222);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 404);
	}
	@Test(priority = 5)
	public void updatePetPositive(){
		
		petPayload.setStatus("unavailable");
		
		Response response = PetEndpoints.updatePet(petPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test(priority = 6)
	public void updatePetNegative(){
		
	
		Response response = PetEndpoints.updatePetInvalid(petPayloadInvalid);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 500);
	}
	@Test(priority = 7)
	public void deletePetPositive(){
		
		Response response = PetEndpoints.createPet(petPayload);
		response.then().log().all();
		
		Response responseDeleted = PetEndpoints.deletePet(this.petPayload.getId());
		responseDeleted.then().log().all();
		Assert.assertEquals(responseDeleted.getStatusCode(), 200);
	}
	@Test(priority = 8)
	public void deletePetNegative(){
		
		Response response = PetEndpoints.createPet(petPayload);
		response.then().log().all();
		petPayload.setId(222);
		Response responseDeleted = PetEndpoints.deletePet(this.petPayload.getId());
		responseDeleted.then().log().all();
		Assert.assertEquals(responseDeleted.getStatusCode(), 404);
	}
}
