package restassuredtests;


import org.json.simple.JSONObject;
import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class RestAssuredTests {

	
	@Test
	public void getUsersTest() {
		
		Response res = RestAssured.get("https://reqres.in/api/users?page=2");
		
		System.out.println(res.body().asPrettyString());
		
		int status = res.getStatusCode();
		Assert.assertEquals("Status code validation failed!!!", 200, status);
		
		int id = res.jsonPath().getInt("data.id[0]");
		Assert.assertEquals("User id validation failed!!!", 7, id);			
					
	}
	
	@Test
	public void postUserTest() {
		
		RestAssured.baseURI = "https://reqres.in/api";
		
		JSONObject obj = new JSONObject();
		obj.put("name", "Darsh");
		obj.put("job", "Automation Tester");
		System.out.println(obj);
		
		RequestSpecification req = RestAssured.given();
		req.header("content-type", "application/json");
		
		//Create user
		Response res = req.body(obj.toJSONString()).post("/users");
		System.out.println(res.body().asPrettyString());
		
		int statusCode = res.getStatusCode();
		Assert.assertEquals(201, statusCode);
		
		String name = res.jsonPath().getString("name");
		Assert.assertEquals("Darsh", name);
		
		String job = res.jsonPath().getString("job");
		Assert.assertEquals("Automation Tester", job);		
		
	}
	
	@Test
	public void putUserTest() {
		
		RestAssured.baseURI = "https://reqres.in/api";
		Response res = null;
		
		JSONObject obj = new JSONObject();
		obj.put("name", "Darshit");
		obj.put("job", "UI Automation Tester");
		
		RequestSpecification req = RestAssured.given();
		
		//post new user
		req.header("content-type", "application/json");
		res = req.body(obj.toJSONString()).post("/users");
		System.out.println(res.body().asPrettyString());
		
		String id = res.jsonPath().getString("id");
		System.out.print("Getting id " + id);
		
		JSONObject updateObj = new JSONObject();
		updateObj.put("name", "Dee");
		updateObj.put("job", "Automation Tester");
		
		//update user
		res = req.body(updateObj.toJSONString()).put("/users/" + id + "");
		System.out.println(res.body().asPrettyString());
		
		int statusCode = res.getStatusCode();
		Assert.assertEquals(200, statusCode);
	}
	
	@Test
	public void deleteUserTest() {
		
		RestAssured.baseURI = "https://reqres.in/api";
		Response res = null;
		
		JSONObject obj = new JSONObject();
		obj.put("name", "Darsh");
		obj.put("job", "Automation Tester");
		System.out.println(obj);
		
		RequestSpecification req = RestAssured.given();		
		req.header("content-type", "application/json");
		
		//Create user
		res = req.body(obj.toJSONString()).post("/users");
		String id = res.jsonPath().getString("id");
		System.out.println(res.body().asPrettyString());
		
		//Delete user
		res = req.delete("/users/" + id + "");
		
		int statusCode = res.getStatusCode();
		Assert.assertEquals(204, statusCode);
		
	}
}
