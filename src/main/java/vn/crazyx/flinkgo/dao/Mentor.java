package vn.crazyx.flinkgo.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mentor")
public class Mentor {
    @Id
    @Column
    private String id;
    
    @Column
    private String name;
    
    @Column
    private Integer level;
    
    @Column
    private String organization;
    
    @Column
    private String email;
    
    @Column
    private String phoneNumber;
    
    @Column
    private String social;
    
    @Column
    private String address;
    
    @Column
    private Double latitude;
    
    @Column
    private Double longitude;
    
    public Mentor() {
        super();
    }

    public Mentor(String id, String name, Integer level, String organization, String email, String phoneNumber,
            String social, String address, Double latitude, Double longitude) {
        super();
        this.id = id;
        this.name = name;
        this.level = level;
        this.organization = organization;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.social = social;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
