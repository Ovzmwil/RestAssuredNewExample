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
import io.restassured.http.Header;
import io.restassured.response.Response;
import me.lsantana.RestAssuredNewExample.util.ConfigReader;
import me.lsantana.RestAssuredNewExample.util.Data;
import me.lsantana.RestAssuredNewExample.util.GenerateReport;
import me.lsantana.RestAssuredNewExample.util.RestHelper;

public class ExampleClassTest extends GenerateReport {

	final static String USER_ENDPOINT_KEY = "user.endpoint";
	final static String GRAFANA_BASE_URL_KEY = "grafana.base.url";
	final static String PLACEHOLDER_BASE_URL_KEY = "placeholder.base.url";
	final static String PLACEHOLDER_POSTS_ENDPOINT_KEY = "posts.enpoint";

	Response response;
	Map<String, Object> bodyMap = new HashMap<String, Object>();
	List<Header> headerList = new ArrayList<Header>();

	@DataProvider(name = "dataInvalidPostUser")
	public Object[][] dataPostUser() {
		return new Object[][] {
			{"", Data.getRandomPassword(), "username field is empty"},
			{Data.getRandomUsername(), "", "password is empty"},
		};
	}

	@BeforeMethod
	public void set() {

		bodyMap.clear();
	}

	@AfterMethod
	public void finish() {
		afterAPITest(bodyMap, response);
	}

	@Test
	public void getMethodTest() {
		RestAssured.baseURI = ConfigReader.getProperty(GRAFANA_BASE_URL_KEY);
		response = when().get("/");

		Assert.assertEquals(response.getHeader("Content-Type"), "text/html; charset=utf-8");
		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test
	public void createUserTest() {
		RestAssured.baseURI = ConfigReader.getProperty(GRAFANA_BASE_URL_KEY);
		String username = Data.getRandomUsername();
		bodyMap.put("username", username);
		bodyMap.put("password", Data.getRandomPassword());

		response = RestHelper.sendPostRequest(ConfigReader.getProperty(USER_ENDPOINT_KEY), bodyMap);

		Assert.assertEquals(response.statusCode(), 201);
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
		Assert.assertNotEquals(response.getBody(), null);
		Assert.assertEquals(response.jsonPath().getString("username"), username);
	}

	@Test(dataProvider = "dataInvalidPostUser")
	public void createInvalidUserTest(String username, String password, String expectedError) {
		RestAssured.baseURI = ConfigReader.getProperty(GRAFANA_BASE_URL_KEY);
		bodyMap.put("username", username);
		bodyMap.put("password", password);

		response = RestHelper.sendPostRequest(ConfigReader.getProperty(USER_ENDPOINT_KEY), bodyMap);

		Assert.assertEquals(response.statusCode(), 400);
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
		Assert.assertNotEquals(response.getBody(), null);
		Assert.assertEquals(response.jsonPath().getString("error"), expectedError);
	}

	@Test
	public void putPostPlaceHolderTest() {
		RestAssured.baseURI = ConfigReader.getProperty(PLACEHOLDER_BASE_URL_KEY);

		int id = Data.getRandomPostId();
		String title = Data.getRandomString();
		String body = Data.getRandomPhrase(10);

		String endpoint = ConfigReader.getProperty(PLACEHOLDER_POSTS_ENDPOINT_KEY) + "/" + id;

		bodyMap.put("id", id);
		bodyMap.put("title", title);
		bodyMap.put("body", body);

		response = RestHelper.sendPutRequest(endpoint, bodyMap);

		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
		Assert.assertNotEquals(response.getBody(), null);
		Assert.assertEquals(response.jsonPath().getString("title"), title);
		Assert.assertEquals(response.jsonPath().getString("body"), body);
		Assert.assertEquals(response.jsonPath().getInt("id"), id);
	}

	@Test
	public void deletePostPlaceHolderTest() {
		RestAssured.baseURI = ConfigReader.getProperty(PLACEHOLDER_BASE_URL_KEY);

		int id = Data.getRandomPostId();

		String endpoint = ConfigReader.getProperty(PLACEHOLDER_POSTS_ENDPOINT_KEY) + "/" + id;

		response = RestHelper.sendDeleteRequest(endpoint);

		Assert.assertEquals(response.statusCode(), 200);
	}

	@Test
	public void patchPostPlaceHolderTest() {
		RestAssured.baseURI = ConfigReader.getProperty(PLACEHOLDER_BASE_URL_KEY);

		int id = Data.getRandomPostId();
		String body = Data.getRandomPhrase(10);

		String endpoint = ConfigReader.getProperty(PLACEHOLDER_POSTS_ENDPOINT_KEY) + "/" + id;

		bodyMap.put("body", body);

		response = RestHelper.sendPatchRequest(endpoint, bodyMap);

		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
		Assert.assertNotEquals(response.getBody(), null);
		Assert.assertNotEquals(response.jsonPath().getString("title"), null);
		Assert.assertEquals(response.jsonPath().getString("body"), body);
		Assert.assertEquals(response.jsonPath().getInt("id"), id);
	}
}
