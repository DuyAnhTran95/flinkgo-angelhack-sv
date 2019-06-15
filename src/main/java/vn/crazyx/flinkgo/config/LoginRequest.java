package vn.crazyx.flinkgo.config;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {
    
    @NotEmpty
    String userName;
    
    @NotEmpty
    String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
