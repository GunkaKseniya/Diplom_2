package user;

public class UserCredential {
    private String email;
    private String password;


    public UserCredential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserCredential() {

    }

    public static UserCredential from(User user) {
        return new UserCredential(user.getEmail(), user.getPassword());
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}