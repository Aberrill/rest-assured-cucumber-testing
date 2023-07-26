package stepDefinitions;

import java.io.IOException;
import java.util.List;

import globalvariables.World;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;
import methodhelper.MethodHelper;

public class ThenSteps {
	
	private World world;
	private Response response;
	
	public ThenSteps(World world) {
		this.world = world;
		response = world.getResponse();
	}
	
	@Then("I expect the status code to equal {int}")
	public void i_expect_the_status_code_to_equal(Integer statusCode) {
		Assert.assertEquals(statusCode.intValue(), response.getStatusCode());
	}
	
	@Then("A list of booking IDs should be returned")
	public void i_expected_the_response_to_match_the_value() {
	    List<Object> result =response.jsonPath().getList("bookingid");
		for (int i=0; i < 10; i++) {
			String actualClass = result.get(i).getClass().getName().toString();
			String expectedClass = "java.lang.Integer";
			Assert.assertEquals(expectedClass, actualClass);
		}
	}
	
	@Then("the response should match the {string} data")
	public void the_response_should_match_the_data(String responseJSON) throws IOException {
		String actualResponse = this.response.asString();
		String expectedResponse = MethodHelper.getJSONData("response", responseJSON);
		String idCheck = new JsonPath(actualResponse).getString("bookingid");
		if (idCheck != null) {
			expectedResponse = expectedResponse.replaceFirst("1", new JsonPath(actualResponse).getString("bookingid"));
		};
		Assert.assertEquals(expectedResponse, actualResponse);
	}
}