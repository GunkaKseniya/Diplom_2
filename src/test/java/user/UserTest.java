package user;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserTest {
    private final UserGenerate generator = new UserGenerate();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    public String token;
    User user;

    @After
    public void deleteUser() {
        if (token != null) {
            client.delete(token);
        }
    }

    @Step("Создание рандомного пользователя")
    public void createRandomUser() {
        user = generator.genericRandom();
        ValidatableResponse creationResponse = client.create(user);
        token = check.userCreateSuccessfully(creationResponse);
    }

    @Step("Создание заданного пользователя")
    public void createStaticUser() {
        user = generator.generic();
        ValidatableResponse creationResponse = client.create(user);
        token = check.userCreateSuccessfully(creationResponse);
    }

    @DisplayName("Успешное создание рандомного пользователя")
    @Test
    public void createUserTest() {
        createRandomUser();
    }

    @DisplayName("Неуспешное создание существующего пользователя")
    @Test
    public void createRegisteredUserTest() {
        createStaticUser();
        ValidatableResponse creationResponse = client.create(user);
        check.registeredUserUnsuccessfulCreate(creationResponse);

    }

    @DisplayName("Неуспешное создание пользователя без email")
    @Test
    public void createUserWithoutEmailTest() {
        user = generator.genericWithoutEmail();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutPasswordOrName(creationResponse);
    }

    @DisplayName("Неуспешное создание пользователя без пароля")
    @Test
    public void createUserWithoutPasswordTest() {
        user = generator.genericWithoutPassword();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutPasswordOrName(creationResponse);
    }

    @DisplayName("Неуспешное создание пользователя без имени")
    @Test
    public void unsuccessfulСreateWithoutNameTest() {
        user = generator.genericWithoutName();
        ValidatableResponse creationResponse = client.create(user);
        check.createUserWithoutPasswordOrName(creationResponse);
    }

    @DisplayName("Успешная авторизация пользователя")
    @Test
    public void successfulLogInSuccessfullyTest() {
        createRandomUser();
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.registeredUserLogInSuccessful(logInResponse);
    }

    @DisplayName("Неуспешная авторизация пользователя с неверным email")
    @Test
    public void unsuccessfulLogInWithInvalidEmail() {
        createRandomUser();
        user.setEmail("test321@ya.ru");
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInUnsuccessful(logInResponse);
    }

    @DisplayName("Неуспешная авторизация пользователя с неверным паролем")
    @Test
    public void unsuccessfulLogInWithInvalidPassword() {
        createRandomUser();
        user.setPassword("test_pass");
        UserCredential userCredential = UserCredential.from(user);
        ValidatableResponse logInResponse = client.logIn(userCredential);
        check.logInUnsuccessful(logInResponse);
    }

    @DisplayName("Успешное изменение данных пользователя")
    @Test
    public void successfulChangeUserFields() {
        createRandomUser();
        user = generator.generic();
        ValidatableResponse changeUserFieldsResponse = client.changeUserFields(token, user);
        check.authorizedUserChangeFieldsSuccessful(changeUserFieldsResponse);

    }

    @DisplayName("Неуспешное изменение данных пользователя без авторизации")
    @Test
    public void unsuccessfulChangeUserFields() {
        createRandomUser();
        user = generator.generic();
        ValidatableResponse changeUserFieldsResponse = client.changeUserFieldsWithoutAuthorization(user);
        check.authorizedUserChangeFieldsUnsuccessful(changeUserFieldsResponse);
    }
}
