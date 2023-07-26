package stepDefinitions;

import io.cucumber.java.en.When;

public class WhenSteps {

	@When("I am checking to see if I can spread steps over multiple files")
	public void simple_edge_search() throws Throwable {
		System.out.println("There it is!");
	}
	
}
