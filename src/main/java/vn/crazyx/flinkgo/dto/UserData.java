package vn.crazyx.flinkgo.dto;

import org.apache.commons.lang3.RandomStringUtils;

import vn.crazyx.flinkgo.dao.User;
import vn.crazyx.flinkgo.utilities.RandomUtils;

public class UserData {
    String userId;
    
    AddressData address;
    
    Integer age;
    
    String avatar;
    
    String interest;
    
    Integer level;
    
    String name;
    
    Integer sex;
    
    private ContactInfo contactInfo;
    
    String userName;
    
    public UserData() {
        super();
    }
   
    public UserData(User user) {
        super();
        this.setUserId(user.getId());
        
        this.address = new AddressData();
        this.address.setAddress(user.getAddress());
        this.address.setLatitude(user.getLatitude());
        this.address.setLongitude(user.getLongitude());
        
        this.contactInfo = new ContactInfo();
        this.contactInfo.setEmail(user.getEmail());
        this.contactInfo.setPhoneNumber(user.getPhoneNumber());
        this.contactInfo.setSocialMedia(user.getSocial());
        
        this.age = user.getAge();
        this.avatar = user.getAvatar();
        this.level = user.getLevel();
        this.name = user.getName();
        this.sex = user.getSex();
        this.userName = user.getUserName();
    }



    public User createUser() {
        String id = RandomStringUtils.randomAlphanumeric(32);
        
        User user = new User(id, userName, null, age, avatar, name, sex, contactInfo.getSocialMedia(), 
                contactInfo.getPhoneNumber(), contactInfo.getEmail(), 
                address.getAddress(), address.getLatitude(), address.getLongitude(), 
                RandomUtils.genRandomString(32), level);
                
        return user;
    }

    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
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

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
