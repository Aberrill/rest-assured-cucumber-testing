package stepDefinitions;

import globalvariables.World;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import globalvariables.StaticVars;
import io.cucumber.java.en.Given;

import org.junit.Assert;

public class GivenSteps {
	private final World world;

	public GivenSteps(World world) {
		this.world = world;
	}

	@Given("I log in as an admin")
	public void i_log_in_as_an_admin() {
		RestAssured.baseURI = StaticVars.getUrl();
		RequestSpecification request = RestAssured.given().header("Content-Type", "application/json");
		Response givenResponse = request.body("{\n" +
				"    \"username\" : \"admin\",\n" +
				"    \"password\" : \"password123\"\n" +
				"}").when().post("/auth");
		Assert.assertEquals(givenResponse.getStatusCode(), 200);
		world.setCookie(givenResponse);
	}
}
