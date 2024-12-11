package org.example.dto;

public class SignUpDto {
    private String username;
    private String password;
    private String email;
    private String role;
    private Integer userId;
    private String address;

    public Integer getUserId;

    public SignUpDto(String username, String password, String email, String role, String address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.address=address;
    }
    public SignUpDto(Integer userId, String username, String password, String email, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getAddress(){
        return  address;
    }
    public void setAddress(String address){
        this.address=address;
    }
}