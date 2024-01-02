package testsuite.api;

import helper.BaseObject;
import objects.api.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Test User API")
@Tag("smoketest")
public class APIAddUpdateDeleteUserTest {
    public User user = new User();

    @DisplayName("Test Add User through API")
    @Test
    public void testAddUser() {
        String payload = "{'name':'Tenali Ramakrishna-copy', 'gender':'male', 'email':'Tenali.Ramakrishna.copy2@15ce.com', 'status':'active'}";
        user.add(payload);
        String userId = BaseObject.getVariableByKey("id");
        user.verifyUserNameShouldBe(userId, "Tenali Ramakrishna-copy");
    }

    @DisplayName("Test Update User through API")
    @Test
    public void testUpdateUser(){
        String userID = BaseObject.getVariableByKey("id");
        String payload = "{'name':'Tenali Ramakrishna-copy-update', 'gender':'male', 'email':'Tenali.Ramakrishna.copy2@15ce.com', 'status':'active'}";
        user.update(userID, payload);
        user.verifyUserNameShouldBe(userID, "Tenali Ramakrishna-copy-update");
    }

    @DisplayName("Test Update User through API - Negative Failed")
    @Test
    public void testUpdateUserNegativeFail(){
        String userID = BaseObject.getVariableByKey("id");
        String payload = "{'name':'Tenali Ramakrishna-copy-update', 'gender':'male', 'email':'13312teyunalishnfddfdfdfdfdfdfa.copy@15ce.com', 'status':'active'}";
        user.update(userID, payload);
        user.verifyUserNameShouldBe(userID, "Tenali Ramakrishna-copy-update-failed");

    }

    @DisplayName("Test Delete User through API")
    @Test
    public void testDeleteUser(){
        String userID = BaseObject.getVariableByKey("id");
        user.delete(userID);
        user.verifyUserNotExist(userID);
    }

}
