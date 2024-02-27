package stepDefinitions;

import java.io.IOException;

import globalvariables.StaticVars;
import globalvariables.World;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import methodhelper.MethodHelper;
import pojo.BookingRequest;


public class WhenSteps {

	private final World world;
	private Response response;
	private String cookie;
	public WhenSteps(World world) {
		this.world = world;
	}

	@When("I complete a get request using the booking URL")
	public void i_complete_a_get_request_using_the_booking_URL() {
		RestAssured.baseURI = StaticVars.getBookingUrl();
		RequestSpecification request = RestAssured.given();
		response = request.get();
		world.setResponse(response);
	}
	
	
	@When("I execute a post request via the POJO method")
	public void i_complete_a_post_request_via_the_POJO_method() {
		RestAssured.baseURI = StaticVars.getBookingUrl();
		RequestSpecification request = RestAssured.given();
		response = request.body(MethodHelper.setPostBookingRequestWithDefaultValues()).contentType(ContentType.JSON).post();
		world.setResponse(response);
	}

	@When("I execute a {string} request using the {string} data")
	public void i_complete_a_request_using_the_data(String APICall, String JSONData) throws IOException {
		if (world.getCookie() != null) {
			cookie = world.getCookie().path("token");
		}
		RestAssured.baseURI = StaticVars.getBookingUrl();
		String requestBody = MethodHelper.getJSONData("request", JSONData);
		response = MethodHelper.chooseRequestTypeWithAuthAndBookingID(response, APICall, requestBody, true, cookie);
		world.setResponse(response);
	}

	@When("I complete a {string} request using the {string} data where the {string} attribute is replaced with {string}")
	public void i_complete_a_request_using_the_data_where_the_attribute_is_replaced_with(String APICall, String JSONData, String attribute, String wrongAttribute) throws IOException {
		if (world.getCookie() != null) {
			cookie = world.getCookie().path("token");
		}
		RestAssured.baseURI = StaticVars.getBookingUrl();
		String requestBody = MethodHelper.getJSONData("request", JSONData);
		String idSearcher = String.format("((?<=\"%s\":).*),", attribute);
		requestBody = requestBody.replaceAll(idSearcher, wrongAttribute + ",");
		response = MethodHelper.chooseRequestTypeWithAuthAndBookingID(response, APICall, requestBody, true, cookie);
		world.setResponse(response);
	}
	
	@When("I complete a POJO post request where the {string} attribute is null")
	public void i_complete_a_pojo_request_where_the_attribute_is_replaced_with(String attribute) {
		RestAssured.baseURI = StaticVars.getBookingUrl();
		RequestSpecification request = RestAssured.given();
		BookingRequest bookingRequest = MethodHelper.setPostBookingRequestWithDefaultValues();
		switch(attribute) {
		case "firstname":
			bookingRequest.setFirstname(null);
			break;
		case "lastname":
			bookingRequest.setLastname(null);
			break;
		case "totalprice":
			bookingRequest.setTotalprice(null);
			break;
		case "depositpaid":
			bookingRequest.setDepositpaid(null);
			break;
		default:
			System.out.println("Not covered");
		}
		response = request.body(bookingRequest).contentType(ContentType.JSON).post();
		world.setResponse(response);
	}

	@When("I execute a {string} request on the freshly made booking")
	public void i_execute_a_request_on_the_freshly_made_booking (String APICall) {
		RestAssured.baseURI = StaticVars.getBookingUrl();
		RequestSpecification request = RestAssured.given();
		JsonPath responseJSON = new JsonPath(response.asString());
		String bookingId = "/" + responseJSON.getString("bookingid");
		switch(APICall) {
		case "get":
			response = request.get(bookingId);
			break;
		case "delete":
			response = request.header("Authorization", System.getenv("auth"))
			.delete(bookingId);
			break;
		default:
			System.out.println("API call not recognised");
		}
		world.setResponse(response);
	}
	
	@When("I complete a {string} request using the {string} data without a booking ID")
	public void i_complete_a_request_using_the_data_without_a_booking_ID(String APICall, String JSONData) {
		if (world.getCookie() != null) {
			cookie = world.getCookie().path("token");
		}
		RestAssured.baseURI = StaticVars.getBookingUrl();
		response = MethodHelper.chooseRequestTypeWithAuthAndBookingID(response, APICall, JSONData, false, cookie);
		world.setResponse(response);
	}
}
