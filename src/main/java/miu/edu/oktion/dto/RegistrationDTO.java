package miu.edu.oktion.dto;

import lombok.Data;
import miu.edu.oktion.domain.Role;

import java.util.List;

@Data
public class RegistrationDTO {
    private String id;
    private String name;
    private String email;
    private List<Role> roles;
}
