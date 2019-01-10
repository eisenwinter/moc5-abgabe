package com.example.canteenchecker.canteenmanager.core;

import com.google.gson.annotations.SerializedName;

public class LoginCredentialsModel {
    @SerializedName("username")
    private String username = null;
    @SerializedName("password")
    private String password = null;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginCredentialsModel loginCredentialsModel = (LoginCredentialsModel) o;
        return (this.username == null ? loginCredentialsModel.username == null : this.username.equals(loginCredentialsModel.username)) &&
                (this.password == null ? loginCredentialsModel.password == null : this.password.equals(loginCredentialsModel.password));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.username == null ? 0: this.username.hashCode());
        result = 31 * result + (this.password == null ? 0: this.password.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class LoginCredentialsModel {\n");

        sb.append("  username: ").append(username).append("\n");
        sb.append("  password: ").append(password).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
