package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserSaveDTO {

    private Integer id;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 50, message = "{size.must.be.between}")
    private String userName;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 50, message = "{size.must.be.between}")
    private String password;

    @NotBlank(message = "{notBlank.must.not.be.blank}")
    @Size(min = 1, max = 200, message = "{size.must.be.between}")
    private String fullName;

    public UserSaveDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}