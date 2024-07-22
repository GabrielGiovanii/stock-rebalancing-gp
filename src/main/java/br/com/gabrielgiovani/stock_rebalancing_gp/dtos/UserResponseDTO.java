package br.com.gabrielgiovani.stock_rebalancing_gp.dtos;

import br.com.gabrielgiovani.stock_rebalancing_gp.entities.User;

public class UserResponseDTO {

    private Integer id;
    private String username;
    private String fullName;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}