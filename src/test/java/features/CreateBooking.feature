Feature: Create Booking
  For happy and unhappy paths associated with the restful booker POST requests

  Scenario Outline: I am able to create a booking <withOrWithout> the optional field
    When I execute a 'post' request using the '<postRequest>' data
    Then I expect the status code to equal 200
    And the response should match the '<postResponse>' data

    Examples: 
      | withOrWithout | postRequest                | postResponse                |
      | with          | postRequest                | postResponse                |
      | without       | postRequestWithoutOptional | postResponseWithoutOptional |

  Scenario: I am able to create a booking using Cucumber and RESTAssured
    When I execute a 'post' request using the 'postRequest' data
    Then I expect the status code to equal 200
    And the response should match the 'postResponse' data

  Scenario: I am able to create a booking using the POJO method to set the required fields
    When I execute a post request via the POJO method
    Then I expect the status code to equal 200
    And the response using POJO data should match the set data

  Scenario: Invalid 'bookingdates' fields are either improperly formatted or will return a default date
    When I execute a "post" request using the 'faultyDatesPostRequest' data
    Then the response should match the 'faultyDatesPostResponse' data

  Scenario: A badly formatted post request will return a bad request error
    When I complete a "post" request using the "postRequest" data where the "firstname" attribute is replaced with ""
    Then I expect the status code to equal 400

  Scenario Outline: Post request returns internal server error on invalid name values
    When I complete a "post" request using the "postRequest" data where the "<attribute>" attribute is replaced with "<invalidAttribute>"
    Then I expect the status code to equal 500

    Examples: 
      | attribute   | invalidAttribute |
      | firstname   |              123 |
      | lastname    |              123 |
      | totalprice  | null             |
      | depositpaid | null             |

  Scenario Outline: Post request generated from POJO data returns internal server error on invalid name values
    When I complete a POJO post request where the "<attribute>" attribute is null
    Then I expect the status code to equal 500

    Examples: 
      | attribute   |
      | firstname   |
      | lastname    |
      | totalprice  |
      | depositpaid |