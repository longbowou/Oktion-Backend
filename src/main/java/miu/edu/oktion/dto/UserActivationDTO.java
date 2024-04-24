package miu.edu.oktion.dto;

import lombok.Data;

@Data
public class UserActivationDTO {
    private String email;
    private String activationCode;
    private String status;
}
