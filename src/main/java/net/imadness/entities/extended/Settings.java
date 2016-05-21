package net.imadness.entities.extended;

import java.io.Serializable;

/**
 * Сущность для администраторских настроек
 */
public class Settings implements Serializable {
    private String login;
    private String password;

    public Settings() {
    }

    public Settings(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
