package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.is;

public class OrderAssertions {

    @Step ("Заказ создан при успешной авторизации")
    public void createOrderWithAuthorizationSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(true))
                .and()
                .statusCode(HTTP_OK);
    }

    @Step ("Заказ не создан без авторизации")
    public void createOrderWithAuthorizationUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step ("Заказ не создан без ингредиентов")
    public void createOrderWithoutIngredientsUnSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("Ingredient ids must be provided"))
                .and()
                .statusCode(HTTP_BAD_REQUEST);
    }

    @Step ("Заказ не получен без авторизации")
    public void getOrderWithoutAutorization(ValidatableResponse response) {
        response.assertThat()
                .body("success", is(false))
                .body("message", is("You should be authorised"))
                .and()
                .statusCode(HTTP_UNAUTHORIZED);
    }
}