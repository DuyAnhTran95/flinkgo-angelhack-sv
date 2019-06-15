package vn.crazyx.flinkgo.dto;

import vn.crazyx.flinkgo.dao.Mentor;

public class MentorData {
    String name;
    
    String organization;
    
    Integer level;
    
    AddressData address;
    
    ContactInfo contactInfo;
    
    public MentorData() {
        super();
    }

    public MentorData(Mentor mentor) {
        super();
        this.name = mentor.getName();
        this.organization = mentor.getOrganization();
        this.level = mentor.getLevel();
        

        this.address = new AddressData();
        this.address.setAddress(mentor.getAddress());
        this.address.setLatitude(mentor.getLatitude());
        this.address.setLongitude(mentor.getLongitude());
        
        this.contactInfo = new ContactInfo();
        this.contactInfo.setEmail(mentor.getEmail());
        this.contactInfo.setPhoneNumber(mentor.getPhoneNumber());
        this.contactInfo.setSocialMedia(mentor.getSocial());
    }

    public Mentor createMentor(String id) {
        Mentor mentor = new Mentor(id, name, level, organization, contactInfo.getEmail(),
                contactInfo.getPhoneNumber(), contactInfo.getSocialMedia(), address.getAddress(),
                address.getLatitude(), address.getLongitude());
        
        return mentor;
    }
    
    public AddressData getAddress() {
        return address;
    }

    public void setAddress(AddressData address) {
        this.address = address;
    }


    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
