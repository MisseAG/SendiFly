package co.uniquindio.edu.sendifly.dtos;

import co.uniquindio.edu.sendifly.models.User;

public class UserDTO {
    private String idUser;
    private String name;
    private String phone;
    private String email;

    public UserDTO(String idUser, String name, String phone, String email) {
        this.idUser = idUser;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setIdUser(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }
}
