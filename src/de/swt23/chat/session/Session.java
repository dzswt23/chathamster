package de.swt23.chat.session;

/**
 * temporarily saves the login data of the user
 */
public class Session {
    private final String username;
    private final String password;

    public Session(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
