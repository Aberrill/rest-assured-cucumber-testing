package stepDefinitions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import methodhelper.MethodHelper;
import globalvariables.World;

import java.io.IOException;

import globalvariables.StaticVars;
import io.cucumber.java.en.Given;

public class GivenSteps {
		
	private World world;
	private Response response;
	
	public GivenSteps(World world) {
		this.world = world;
		response = world.getResponse();
	}
	
	@Given("I am looking to link the test file to the feature file")
	public void simple_google_search() throws Throwable {
		System.out.println("Here it is!");
	}
	
	@Given("I complete a get request using the booking URL")
	public void i_complete_a_get_request_using_the_booking_URL() {
		RestAssured.baseURI = StaticVars.getBookingUrl();
		RequestSpecification request = RestAssured.given();
		response = request.get();
		world.setResponse(response);
	}
	
	@Given("The service is functional")
	public void the_service_is_functional() {
		RestAssured.baseURI = StaticVars.getUrl();
		RequestSpecification request = RestAssured.given();
		response = request.get("/ping");
		Assert.assertEquals(201, response.getStatusCode());

	}
	
	@Given("I execute a {string} booking request using the {string} data")
	public void i_execute_a_booking_request_using_the_data (String APICall, String JSONData) throws IOException {
		RestAssured.baseURI = StaticVars.getBookingUrl();
		RequestSpecification request = RestAssured.given();
		String bookingId = "";
		if (this.response != null) {
			JsonPath responseJSON = new JsonPath(this.response.asString());
			bookingId = "/" + responseJSON.getString("bookingid");
		}
		switch(APICall) {
			case "post":
				response = request.body(MethodHelper.getJSONData("request", JSONData))
				.contentType(ContentType.JSON).post();
				break;
			case "get":
				response = request.get(bookingId);
				break;
			case "put":
				response = request.body(MethodHelper.getJSONData("request", JSONData))
				.header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
				.contentType(ContentType.JSON).put(bookingId);
				break;
			case "patch":
				response = request.body(MethodHelper.getJSONData("request", JSONData))
				.header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
				.contentType(ContentType.JSON).patch(bookingId);
				break;
			case "delete":
				response = request.delete(bookingId);
				break;
			default:
				System.out.println("API call not recognised");
		}
	    world.setResponse(response);
	}
}