package api.test;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpointsByRoutesPropertyFile;
import api.payload.User;
import io.restassured.response.Response;

public class UserTestBYPropertyFile {
	Faker faker;
	User userPayload;
	public Logger logger;

	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayload = new User(); // Correctly initializing the class-level variable

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());

	}

	@Test(priority = 1)
	public void testPostUser() throws InterruptedException {
		Assert.assertNotNull(userPayload, "User payload is null!"); // Ensure object is initialized

		Response response = UserEndpointsByRoutesPropertyFile.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("user Name In post method " + this.userPayload.getUsername());

	}

	@Test(priority = 2)
	public void testGetUserByName() {

		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
		System.out.println("user Name In get method " + this.userPayload.getUsername());
		Response response = UserEndpointsByRoutesPropertyFile.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);

	}

	@Test(priority = 3)
	public void testUpdateUserByName() throws InterruptedException {

		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		Assert.assertNotNull(userPayload, "User payload is null!"); // Ensure object is initialized

		Response response = UserEndpointsByRoutesPropertyFile.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("user Name In update method " + this.userPayload.getUsername());
		Thread.sleep(10000);

	}

	@Test(priority = 4)
	public void testGetUserByName1() {

		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
		System.out.println("user Name In get method " + this.userPayload.getUsername());
		Response response = UserEndpointsByRoutesPropertyFile.readUser(this.userPayload.getUsername());
		response.then().log().body().statusCode(200); // we can write this also
		Assert.assertEquals(response.getStatusCode(), 200);

	}

	@Test(priority = 5)
	public void testDeleteUserByName() {

		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
		System.out.println("user Name In delete method " + this.userPayload.getUsername());
		Response response = UserEndpointsByRoutesPropertyFile.deleteUser(this.userPayload.getUsername());
		response.then().log().body().statusCode(200); // we can write this also
		Assert.assertEquals(response.getStatusCode(), 200);

	}

	@Test(priority = 6)
	public void testGetUserByName2() {

		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
		System.out.println("user Name In get method " + this.userPayload.getUsername());
		Response response = UserEndpointsByRoutesPropertyFile.readUser(this.userPayload.getUsername());
		response.then().log().body().statusCode(404); // we can write this also
		Assert.assertEquals(response.getStatusCode(), 404);

	}
}
