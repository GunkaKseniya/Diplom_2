package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.is;

public class UserAssertions {

    @Step ("Успешное создание уникального пользователя")
    public String userCreateSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK)
                .and()
                .extract().path("accessToken");

    }

    @Step ("Неуспешное создание пользователя, который уже зарегистрирован")
    public void registeredUserUnsuccessfulCreate(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("User already exists"))
                .and()
                .statusCode(HTTP_FORBIDDEN);
    }

    @Step ("Создания пользователя без пароля или имени")
    public void createUserWithoutPasswordOrName(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("Email, password and name are required fields"))
                .and()
                .statusCode(HTTP_FORBIDDEN);

    }

    @Step ("Логин под существующим пользователем")
    public void registeredUserLogInSuccessful(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Step ("Неверный логин пользователя")
    public void logInUnsuccessful(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("email or password are incorrect"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Step ("Изменение данных пользователя с авторизацией")
    public void authorizedUserChangeFieldsSuccessful(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Step ("Изменение данных пользователя без авторизации")
    public void authorizedUserChangeFieldsUnsuccessful(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("You should be authorised"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }
}