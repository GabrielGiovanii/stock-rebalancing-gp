package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;

public class UserResponseDTO {

    private Integer id;
    private String userName;
    private String fullName;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}