package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class OrderAssertions {

    @Step("Заказ создан при успешной авторизации")
    public void createOrderWithAuthorizationSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .log().all()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Step("Заказ не создан без ингредиентов")
    public void createOrderWithoutIngredientsUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .log().all()
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Step("Заказ не получен без авторизации")
    public void getOrderWithoutAuthorization(ValidatableResponse response) {
        response.assertThat()
                .log().all()
                .body("success", is(false))
                .body("message", is("You should be authorised"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }

    @Step("Проверка ошибки при невалидных ингредиентах")
    public void invalidIngredientsError(ValidatableResponse response) {
        response.assertThat()
                .log().all()
                .statusCode(HTTP_INTERNAL_ERROR)
                .body(containsString("Internal Server Error"));
    }

    @Step("Проверка ошибки при отсутствии авторизации")
    public void unauthorizedError(ValidatableResponse response) {
        response.assertThat()
                .log().all()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false))
                .body("message", is("You should be authorised"));
    }
}
