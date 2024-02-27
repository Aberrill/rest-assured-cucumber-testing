package stepDefinitions;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import globalvariables.World;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import methodhelper.MethodHelper;
import pojo.BookingResponse;

public class ThenSteps {
	
	private final World world;

	public ThenSteps(World world) {
		this.world = world;
	}
	
	@Then("I expect the status code to equal {int}")
	public void i_expect_the_status_code_to_equal(Integer statusCode) {
		Assert.assertEquals(statusCode.intValue(), world.getResponse().getStatusCode());
	}
	
	@Then("A list of booking IDs should be returned")
	public void i_expected_the_response_to_match_the_value() {
	    List<Object> result = world.getResponse().jsonPath().getList("bookingid");
		for (int i=0; i < 10; i++) {
			String actualClass = result.get(i).getClass().getName();
			String expectedClass = "java.lang.Integer";
			Assert.assertEquals(expectedClass, actualClass);
		}
	}
	

	@Then("the response should match the {string} data")
	public void the_response_should_match_the_data(String responseJSON) throws IOException {
		String actualResponse = world.getResponse().asString();
		final ObjectMapper mapper = new ObjectMapper();
		JsonNode actualResponseJSON = (mapper.readTree(actualResponse));
		String expectedResponse = MethodHelper.getJSONData("response", responseJSON);
		boolean idCheck2 = actualResponseJSON.has("bookingid");
		if (idCheck2) {
			expectedResponse = expectedResponse.replaceFirst("1", new JsonPath(actualResponse).getString("bookingid"));
		};
		Assert.assertEquals(expectedResponse, actualResponse);
	}
	
	@Then("the response using POJO data should match the set data")
	public void the_response_using_pojo_data_should_match_the_set_data() {
		BookingResponse actualResponse = world.getResponse().as(BookingResponse.class);
		Assert.assertEquals("POJO", actualResponse.getBooking().getFirstname());
		Assert.assertEquals("Test", actualResponse.getBooking().getLastname());
		Assert.assertEquals(123, actualResponse.getBooking().getTotalprice().intValue());
		Assert.assertEquals(true, actualResponse.getBooking().getDepositpaid());
		Assert.assertEquals("2024-06-06", actualResponse.getBooking().getBookingdates().getCheckin());
		Assert.assertEquals("2024-06-10", actualResponse.getBooking().getBookingdates().getCheckout());
		Assert.assertEquals("Optional", actualResponse.getBooking().getAdditionalneeds());
	}
}