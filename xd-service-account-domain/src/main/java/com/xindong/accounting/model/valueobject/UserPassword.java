package com.xindong.accounting.model.valueobject;

import lombok.Data;

@Data
public class UserPassword {
    private String username;
    private String password;
    private String newPassword;
}
