package cucumberOptions;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

//can do e.g. src/test/java/features/Test.feature
//The glue points to the package name
@RunWith(Cucumber.class)
@CucumberOptions (
		features = "src/test/java/features",
		glue = "stepDefinitions")
public class TestRunner {

}
