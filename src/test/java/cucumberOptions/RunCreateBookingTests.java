package cucumberOptions;

import globalvariables.StaticVars;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions (
		features = "src/test/java/features/CreateBooking.feature",
		plugin = {"me.jvt.cucumber.report.PrettyReports:target/cucumber"},
		glue = {"stepDefinitions"}
		)
public class RunCreateBookingTests {

	@BeforeClass
	public static void assertFunctionalService() {
		RestAssured.baseURI = StaticVars.getUrl();
		RequestSpecification request = RestAssured.given();
		request.when().get("/ping").then().assertThat().statusCode(201);
	}

}
