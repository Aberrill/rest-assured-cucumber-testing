
Feature: Features that can be tested after creation of a booking ID
  I want to use this template for my feature file
  
  Background: creating a booking for testing.
  	Given I execute a 'post' booking request using the 'postRequest' data

	Scenario: The post request response is as expected
		Then the response should match the 'postResponse' data  	

  Scenario: I am able to update the whole booking
  	Given I execute a 'put' booking request using the 'putRequest' data
    Then the response should match the 'putResponse' data
    
  Scenario Outline: I am able to patch each individual field
  	Given I execute a 'patch' booking request using the '<patchRequest>' data 
  	Then the response should match the '<patchResponse>' data
  	
  	Examples:
  	| patchRequest                  | patchResponse                  |
  	| nameDatePatchRequest          | nameDatePatchResponse          |
  	| priceDepositNeedsPatchRequest | priceDepositNeedsPatchResponse | 