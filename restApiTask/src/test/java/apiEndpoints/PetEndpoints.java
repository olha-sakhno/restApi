package apiEndpoints;

import static io.restassured.RestAssured.given;
import apiPayload.Pet;
import apiPayload.PetInvalid;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

public class PetEndpoints {
	
	public static Response createPet(Pet Payload){
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(Payload)
		.when()
			.post(Routes.post_url);
		return response;
	}
	public static Response findPet(int petId){
		Response response = given()
			.pathParam("petId", petId)
		.when()
			.get(Routes.get_url);
		return response;
	}
	public static Response updatePet(Pet Payload){
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(Payload)
		.when()
			.put(Routes.update_url);
		return response;
	}
	public static Response updatePetInvalid(PetInvalid petPayloadInvalid){
		Response response = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(petPayloadInvalid)
		.when()
			.put(Routes.update_url);
		return response;
	}
	public static Response deletePet(int petId){
		Response response = given()
			.pathParam("petId", petId)
		.when()
			.delete(Routes.delete_url);
		return response;
	}
}
