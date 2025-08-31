package order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import user.UserTest;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.is;

public class OrderTest {
    private final OrderAssertions check = new OrderAssertions();
    private final OrderClient client = new OrderClient();
    private final OrderGenerate generator = new OrderGenerate();
    Order order;
    UserTest userTest = new UserTest();

    @After
    public void deleteUser() {
        userTest.deleteUser();
    }

    @DisplayName("Создание заказа с авторизацией, верными ингредиентами")
    @Test
    public void createOrderWithAuthorizationAndValidIngredientsTest() {
        userTest.createRandomUser();
        order = generator.genericWithValidateIngredients();
        ValidatableResponse creationResponse = client.createWithAuthorizationAndValidateIngredients(userTest.token, order);
        check.createOrderWithAuthorizationSuccessfully(creationResponse);
    }

    @DisplayName("Создание заказа без авторизации, с верными ингредиентами")
    @Test
    public void createOrderWithoutAuthorizationAndValidIngredientsTest() {
        userTest.createRandomUser();
        order = generator.genericWithValidateIngredients();
        ValidatableResponse creationResponse = client.createWithoutAuthorization(order);
        check.unauthorizedError(creationResponse); //тест падает, как я понимаю, так и надо
    }

    @DisplayName("Создание заказа с авторизацией, с неверными ингредиентами")
    @Test
    public void createOrderWithAuthorizationAndInvalidIngredientsTest() {
        userTest.createRandomUser();
        order = generator.genericWithUnValidateIngredients();
        ValidatableResponse creationResponse = client.createWithAuthorizationAndUnValidateIngredients(userTest.token, order);
        check.invalidIngredientsError(creationResponse);
    }

    @DisplayName("Создание заказа с авторизацией, без ингредиентов")
    @Test
    public void createOrderWithAuthorizationAndNoIngredientsTest() {
        userTest.createRandomUser();
        ValidatableResponse creationResponse = client.createWithAuthorizationAndWithoutIngredients(userTest.token);
        check.createOrderWithoutIngredientsUnSuccessfully(creationResponse);
    }

    @DisplayName("Получение заказа c авторизацией")
    @Test
    public void getOrderWithAuthorizationTest() {
        userTest.createRandomUser();
        ValidatableResponse response = client.getOrderWithAuthorization(userTest.token); // Исправлено использование метода
        check.createOrderWithAuthorizationSuccessfully(response);
    }

    @DisplayName("Получение списка заказов без авторизации")
    @Test
    public void getOrdersWithoutAuthorizationTest() {
        ValidatableResponse response = client.getOrderWithoutAuthorization();

        check.getOrderWithoutAuthorization(response);
    }

    @DisplayName("Получение списка всех заказов")
    @Test
    public void getAllOrdersTest() {
        userTest.createRandomUser();
        ValidatableResponse response = client.getOrderWithAuthorization(userTest.token);
        check.createOrderWithAuthorizationSuccessfully(response);
    }
}
