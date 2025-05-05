package me.lsantana.RestAssuredNewExample.test;

import static io.restassured.RestAssured.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import me.lsantana.RestAssuredNewExample.util.Data;
import me.lsantana.RestAssuredNewExample.util.GenerateReport;

public class ExampleClassTest extends GenerateReport {

	static final String USERS_ENDPOINT = "/api/users";

	Response response;

	Map<String, Object> bodyMap = new HashMap<String, Object>();

	List<Header> headerList = new ArrayList<Header>();

	@DataProvider(name = "dataPostUser")
	public Object[][] dataPostUser() {
		return new Object[][] {
			{"", "123456"},
			{"newUser", ""},
		};
	}

	@BeforeMethod
	public void set() {
		RestAssured.baseURI = "https://quickpizza.grafana.com";
		bodyMap.clear();
	}

	@AfterMethod
	public void finish() {
		afterAPITest(bodyMap, response);
	}

	public Response sendPostRequest(String endpoint) {
		return given().contentType(ContentType.JSON).body(bodyMap).when().post(endpoint);
	}

	@Test
	public void getMethodTest() {
		response = when().get("/");

		Assert.assertEquals(response.getHeader("Content-Type"), "text/html; charset=utf-8");
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test
	public void createUserTest() {
		bodyMap.put("username", "newUser");
		bodyMap.put("password", "12345678");

		response = sendPostRequest(USERS_ENDPOINT);


		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
		Assert.assertNotEquals(response.getBody(), null);
		Assert.assertEquals(response.jsonPath().getString("username"), "newUser");
	}

	@Test(dataProvider = "dataPostUser")
	public void createInvalidUserTest(String username, String password) {
		bodyMap.put("username", username);
		bodyMap.put("password", password);

		response = sendPostRequest(USERS_ENDPOINT);


		Assert.assertEquals(response.statusCode(), 400);
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
		Assert.assertNotEquals(response.getBody(), null);
		Assert.assertNotEquals(response.jsonPath().getString("error"), null);
	}

	@Test
	public void createUserRandomData() {
		String username = Data.getRandomUsername();
		bodyMap.put("username", username);
		bodyMap.put("password", Data.getRandomPassword());
		
		response = sendPostRequest(USERS_ENDPOINT);
		
		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
		Assert.assertNotEquals(response.getBody(), null);
		Assert.assertEquals(response.jsonPath().getString("username"), username);
	}
}
