package user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerate {
    public User genericRandom() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 10) + "@ya.ru", RandomStringUtils.randomAlphanumeric(4, 10), RandomStringUtils.randomAlphabetic(2, 10));
    }

    public User generic() {
        return new User("test48@ya.ru", "GK048@", "test048");
    }

    public User genericWithoutEmail() {
        return new User(null, RandomStringUtils.randomAlphanumeric(4, 10), RandomStringUtils.randomAlphabetic(2, 10));
    }

    public User genericWithoutPassword() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 10) + "@ya.ru", null, RandomStringUtils.randomAlphabetic(2, 10));
    }

    public User genericWithoutName() {
        return new User(RandomStringUtils.randomAlphanumeric(5, 10) + "@ya.ru", RandomStringUtils.randomAlphanumeric(4, 10), null);
    }
}