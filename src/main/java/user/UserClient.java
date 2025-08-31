package user;

import base.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class UserClient extends Client {

    public static final String AUTH = "/auth";

    @Step ("Создание уникального пользователя")
    public ValidatableResponse create(User user) {
        return getRequestSpecification()
                .body(user)
                .when()
                .post(AUTH + "/register")
                .then()
                .log()
                .all();

    }

    @Step ("Удаление пользователя")
    public void delete(String token) {

        getRequestSpecification()
                .header("Authorization", token)
                //.auth().oauth2(token)
                .when()
                .delete(AUTH + "/user")
                .then()
                .log()
                .all();
    }

    @Step ("Логин пользователя")
    public ValidatableResponse logIn(UserCredential userCredential) {
        return getRequestSpecification()
                .body(userCredential)
                .when()
                .post(AUTH + "/login")
                .then()
                .log()
                .all();

    }

    @Step ("Изменение данных пользователя")
    public ValidatableResponse changeUserFields(String token, User user) {
        return getRequestSpecification()
                .header("Authorization", token)
                .body(user)
                .when()
                .patch(AUTH + "/user")
                .then()
                .log()
                .all();
    }

    @Step ("Изменение данных пользователя без авторизации")
    public ValidatableResponse changeUserFieldsWithoutAuthorization(User user) {
        return getRequestSpecification()
                .body(user)
                .when()
                .patch(AUTH + "/user")
                .then()
                .log()
                .all();
    }

}
