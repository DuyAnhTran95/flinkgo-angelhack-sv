package vn.crazyx.flinkgo.config;

import java.lang.reflect.Field;

import vn.crazyx.flinkgo.dao.User;

public class AuthUser {

    private String userId;

    private String userName;

    private String userAvatar;

    public AuthUser(String userName, String uid, String userAvatar) {
        this.userId = uid;
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    public AuthUser(User user) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.userAvatar = user.getAvatar();
    }

    public AuthUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public AuthUser() {
        // TODO Auto-generated constructor stub
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        Field[] fields = this.getClass().getDeclaredFields();
        boolean firstIter = true;

        for (Field field : fields) {
            try {
                if (field.get(this) != null) {
                    if (!firstIter) {
                        builder.append(",");
                    }
                    firstIter = false;

                    builder.append("\"" + field.getName() + "\":");

                    if (field.get(this) instanceof String) {
                        builder.append("\"" + field.get(this) + "\"");
                    } else {
                        builder.append(field.get(this).toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return builder.append("}").toString();
    }
}
