package api.test;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.test.api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {
	@Test(priority = 1,dataProvider ="Data" ,dataProviderClass =DataProviders.class)
	public void testPostUser(String user_id ,String userName ,String fname ,String lastname ,String useremail ,String pwd ,String ph,String userStatus) {
		User userPayload =new User();
		userPayload.setId(Integer.parseInt(user_id));
		userPayload.setUsername(userName);
		userPayload.setFirstname(fname);
		userPayload.setLastname(lastname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		userPayload.setUserStatus(Integer.parseInt(userStatus));
		Response response = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	@Test (priority=2,dataProvider ="UserNames" ,dataProviderClass =DataProviders.class)
	public void testDeleteUserByName(String userName) {
		Response response = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(response.statusCode(), 200);
	}
}
