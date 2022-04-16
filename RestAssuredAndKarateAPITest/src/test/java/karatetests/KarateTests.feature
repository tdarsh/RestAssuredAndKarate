Feature: API Tests

  Background: 
    * url 'https://reqres.in/api'
    * header accept = 'application/json'
    * def outPut = read("responseTestData.json")

  #Get Request
  Scenario: validate GET user test
    Given path '/users'
    And param page = 2
    When method GET
    Then status 200
    Then print response
    And match response.data[0].first_name != null
    And assert response.data.length == 6

  Scenario: validate POST user test with response file
    Given path '/users'
    And request {"name": "Darsh Thakkar", "job": "Automation Tester"}
    When method POST
    Then status 201
    Then print response
    And assert response.name == 'Darsh Thakkar'
    And match response == outPut

  Scenario: validate POST user test with request file
    Given path '/users'
    And def requestBody = read("userRequestTestData.json")
    And request requestBody
    When method POST
    Then status 201
    And assert response.name == 'Darshit Thakkar'
    And assert response.job == 'UI Automation Tester'
