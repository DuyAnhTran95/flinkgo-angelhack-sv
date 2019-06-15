package vn.crazyx.flinkgo.dto;

public class ContactInfo {
    String phoneNumber;
    
    String email;
    
    String socialMedia;

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

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }
}
