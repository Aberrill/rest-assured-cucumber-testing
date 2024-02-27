Feature: Edit Booking
  For happy and unhappy paths associated with the restful booker PUT and PATCH requests

  Background: Generating the required cookie
    Given I log in as an admin

  Scenario Outline: I am able to update the whole booking <condition>
    When I execute a 'post' request using the '<postRequest>' data
    Then I expect the status code to equal 200
    And I execute a 'put' request using the '<putRequest>' data
    Then I expect the status code to equal 200
    And the response should match the '<putResponse>' data

    Examples: 
      | condition                         | postRequest                | putRequest                | putResponse                |
      | including the optional field      | postRequest                | putRequest                | putResponse                |
      | without adding the optional field | postRequestWithoutOptional | putRequestWithoutOptional | putResponseWithoutOptional |
      | whilst adding the optional field  | postRequestWithoutOptional | putRequest                | putResponse                |

  Scenario Outline: I am able to patch each individual field
    When I execute a 'post' request using the 'postRequest' data
    Then I expect the status code to equal 200
    When I execute a 'patch' request using the '<patchRequest>' data
    Then I expect the status code to equal 200
    And the response should match the '<patchResponse>' data

    Examples: 
      | patchRequest                  | patchResponse                  |
      | nameDatePatchRequest          | nameDatePatchResponse          |
      | priceDepositNeedsPatchRequest | priceDepositNeedsPatchResponse |
    
  Scenario Outline: Put requests returns <errorType> on invalid name values
    When I execute a 'post' request using the 'postRequest' data
    Then I expect the status code to equal 200
    When I complete a "put" request using the "putRequest" data where the "<attribute>" attribute is replaced with "<invalidAttribute>"
    Then I expect the status code to equal <code>
    
    Examples:
    | errorType             | attribute   | invalidAttribute | code |
    | internal server error | firstname   | 123              | 500  |
    | internal server error | lastname    | 123              | 500  |
    | bad request error     | totalprice  | null             | 400  |
    | bad request error     | depositpaid | null             | 400  |

  Scenario Outline: Not Found error returned when a <request> request has no booking ID
    When I execute a 'post' request using the 'postRequest' data
    Then I expect the status code to equal 200
    When I complete a "<request>" request using the "<requestType>" data without a booking ID
    Then I expect the status code to equal 400
    
    Examples:
    | request | requestType   |
    | put     | putRequest    |
    | patch   | patchRequest  |