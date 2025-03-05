package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest {

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
		//logs
		logger =LogManager.getLogger(this.getClass());
		logger.debug("debugging........");
	}

	@Test(priority = 1)
	public void testPostUser() throws InterruptedException {
		logger.info("*******S******** Creating User ***************");
		Assert.assertNotNull(userPayload, "User payload is null!"); // Ensure object is initialized

		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		System.out.println("user Name In post method " + this.userPayload.getUsername());
		logger.info("*******S******** User is Created ***************");
	}

//	@Test(priority = 2)
//	public void testGetUserByName() {
//		logger.info("*******S******** Reading User Info ***************");
//		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
//		System.out.println("user Name In get method " + this.userPayload.getUsername());
//		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
//		response.then().log().all();
//		Assert.assertEquals(response.getStatusCode(), 200);
//		logger.info("*******S******** User Info Is Displayed ***************");
//	}
//
//	@Test(priority = 3)
//	public void testUpdateUserByName() throws InterruptedException {
//		logger.info("*******S******** Updating User ***************");
//		userPayload.setFirstname(faker.name().firstName());
//		userPayload.setLastname(faker.name().lastName());
//		userPayload.setEmail(faker.internet().safeEmailAddress());
//		Assert.assertNotNull(userPayload, "User payload is null!"); // Ensure object is initialized
//
//		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
//		response.then().log().all();
//		Assert.assertEquals(response.getStatusCode(), 200);
//		System.out.println("user Name In update method " + this.userPayload.getUsername());
//		Thread.sleep(10000);
//		logger.info("*******S******** User Is Updated ***************");
//	}
//
//	@Test(priority = 4)
//	public void testGetUserByName1() {
//		logger.info("*******S******** Reading User Info ***************");
//		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
//		System.out.println("user Name In get method " + this.userPayload.getUsername());
//		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
//		response.then().log().body().statusCode(200); // we can write this also
//		Assert.assertEquals(response.getStatusCode(), 200);
//		logger.info("*******S******** User Info Is Displayed ***************");
//	}
//
//	@Test(priority = 5)
//	public void testDeleteUserByName() {
//		logger.info("*******S******** Deleting User ***************");
//		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
//		System.out.println("user Name In delete method " + this.userPayload.getUsername());
//		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
//		response.then().log().body().statusCode(200); // we can write this also
//		Assert.assertEquals(response.getStatusCode(), 200);
//		logger.info("*******S******** User Deleted ***************");
//	}
//
//	@Test(priority = 6)
//	public void testGetUserByName2() {
//		logger.info("*******S******** Reading User Info ***************");
//		Assert.assertNotNull(userPayload, "User payload is null!"); // Prevent null reference error
//		System.out.println("user Name In get method " + this.userPayload.getUsername());
//		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
//		response.then().log().body().statusCode(404); // we can write this also
//		Assert.assertEquals(response.getStatusCode(), 404);
//		logger.info("*******S******** User Info Is Displayed ***************");
//	}
}
