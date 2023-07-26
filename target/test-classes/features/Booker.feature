Feature: Restful Booker
  For practise of API testing using Cucumber
  
  Background: Background check to make sure service is active
  	 Given The service is functional
			
  	Scenario: All created booking IDs can be retrieved
   		Given I complete a get request using the booking URL
    	Then I expect the status code to equal 200
    	And A list of booking IDs should be returned
