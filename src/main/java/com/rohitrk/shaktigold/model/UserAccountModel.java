package com.rohitrk.shaktigold.model;

import lombok.Data;

@Data
public class UserAccountModel {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;
    private String newPassword;
    private String createdDate;
    private UserDetailsModel userDetailsModel;
}
