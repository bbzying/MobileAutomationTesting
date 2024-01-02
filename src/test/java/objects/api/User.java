package objects.api;

import helper.APIConstant;
import helper.APIHelper;
import helper.BaseObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;

public class User {
    private static final Logger logger = LogManager.getLogger(User.class);
    public void add(String payload){
        logger.info("Add user with payload " + payload);
        APIHelper helper = new APIHelper("public/v2/users/", APIConstant.ApiMethods.POST);
        helper.executeWithBody(payload);
    }

    public void get(String id){
        logger.info("Get user with id " + id);
        APIHelper helper = new APIHelper("public/v2/users/" + id, APIConstant.ApiMethods.GET);
        helper.executeAPI();
    }

    public void update(String id, String payload){
        logger.info("Update user " + id + " with payload " + payload);
        APIHelper helper = new APIHelper("public/v2/users/" + id, APIConstant.ApiMethods.PUT);
        helper.executeWithBody(payload);
    }

    public void delete(String id){
        logger.info("Delete user with payload " + id);
        APIHelper helper = new APIHelper("public/v2/users/" + id, APIConstant.ApiMethods.DELETE);
        helper.executeAPI();
    }

    public void verifyUserNotExist(String id){
        logger.info("Verify user doesn't exist");
        get(id);
        String message = BaseObject.getVariableByKey("message");
        Assertions.assertEquals(message, "Resource not found");
    }

    public void verifyUserNameShouldBe(String id, String name){
        logger.info("Verify user name should be " + name);
        get(id);
        String userName = BaseObject.getVariableByKey("name");
        Assertions.assertEquals(userName, name);
    }
}
