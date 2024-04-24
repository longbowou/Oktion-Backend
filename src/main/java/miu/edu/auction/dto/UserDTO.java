package miu.edu.auction.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String name;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 64)
    private String password;
    private String licenseNumber;
    private List<String> roles;


}
