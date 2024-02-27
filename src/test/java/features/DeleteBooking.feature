Feature: Delete Booking
  For happy and unhappy paths associated with the restful booker DELETE requests

  Background: creating a booking for testing.
    Given I log in as an admin
    When I execute a 'post' request using the 'postRequest' data
    Then I expect the status code to equal 200

  Scenario: I am able to delete the booking
    When I execute a 'delete' request on the freshly made booking
    Then I expect the status code to equal 201
    
  Scenario: Not Found error returned when a delete request has no booking ID
    When I complete a "delete" request using the "deleteRequest" data without a booking ID
    Then I expect the status code to equal 404