package miu.edu.auction.dto;

import lombok.Data;
import miu.edu.auction.domain.Role;

import java.util.List;

@Data
public class RegistrationDTO {
    private String id;
    private String name;
    private String email;
    private List<Role> roles;
}
