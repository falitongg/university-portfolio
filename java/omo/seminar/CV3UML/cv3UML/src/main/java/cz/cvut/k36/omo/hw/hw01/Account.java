package cz.cvut.k36.omo.hw.hw01;

public class Account {
    private String username;
    private String password;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public boolean login(String password) {
        return this.password.equals(password);
    }

}
