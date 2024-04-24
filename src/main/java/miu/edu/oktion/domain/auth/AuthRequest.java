package miu.edu.oktion.domain.auth;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;

}
