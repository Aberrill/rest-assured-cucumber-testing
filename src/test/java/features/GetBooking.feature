Feature: Get Booking
  For happy and unhappy paths associated with the restful booker GET requests

  Scenario: All created booking IDs can be retrieved
    When I complete a get request using the booking URL
    Then I expect the status code to equal 200
    And A list of booking IDs should be returned

  Scenario: A specific booking ID response body can be retrieved
    When I execute a 'post' request using the 'postRequest' data
    Then I expect the status code to equal 200
    When I execute a 'get' request on the freshly made booking
    Then I expect the status code to equal 200
    And the response should match the 'getResponse' data
