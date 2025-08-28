package base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class Client {
    static final String STELLARBURGERS_SITE = "https://stellarburgers.nomoreparties.site/api";

    protected static RequestSpecification getRequestSpecification() {
        return given()
                .log()
                .all()
                .contentType(ContentType.JSON)
                .baseUri(STELLARBURGERS_SITE);

    }
}