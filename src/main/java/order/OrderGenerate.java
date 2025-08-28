package order;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class OrderGenerate {

    public Order genericWithValidateIngredients() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
    }

    public Order genericWithUnValidateIngredients() {
        return new Order(List.of(RandomStringUtils.randomAlphanumeric(24), RandomStringUtils.randomAlphanumeric(24)));
    }

}