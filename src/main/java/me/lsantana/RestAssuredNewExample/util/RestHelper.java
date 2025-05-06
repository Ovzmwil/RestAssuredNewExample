package me.lsantana.RestAssuredNewExample.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RestHelper {

	public static Response sendPostRequest(String endpoint, Map<String, Object> bodyMap) {
		return given().contentType(ContentType.JSON).body(bodyMap).when().post(endpoint);
	}

	public static Response sendPutRequest(String endpoint, Map<String, Object> bodyMap) {
		return given().contentType(ContentType.JSON).body(bodyMap).when().put(endpoint);
	}

	public static Response sendDeleteRequest(String endpoint) {
		return given().delete(endpoint);
	}

	public static Response sendPatchRequest(String endpoint, Map<String, Object> bodyMap) {
		return given().contentType(ContentType.JSON).body(bodyMap).when().patch(endpoint);
	}

}
