package vn.crazyx.flinkgo.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column
    private String id;
    
    @Column
    private String userName;
    
    @Column
    private String password;
    
    @Column
    private Integer age;
    
    @Column
    private String avatar;
    
    @Column
    private String name;
    
    @Column 
    private Integer sex;
    
    @Column
    private String social;
    
    @Column 
    private String phoneNumber;
    
    @Column
    private String email;
    
    @Column
    private String address;
    
    @Column
    private Double latitude;
    
    @Column
    private Double longitude;
    
    @Column
    private String refreshToken;
    
    @Column
    private Integer level;
    
    public User() {
        super();
    }

    public User(String id, String userName, String password, Integer age, String avatar, String name, Integer sex,
            String social, String phoneNumber, String email, String address, Double latitude, Double longitude,
            String refreshToken, Integer level) {
        super();
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.avatar = avatar;
        this.name = name;
        this.sex = sex;
        this.social = social;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.refreshToken = refreshToken;
        this.level = level;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
