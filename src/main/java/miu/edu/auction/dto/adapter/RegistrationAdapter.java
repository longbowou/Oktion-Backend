package miu.edu.auction.dto.adapter;

import miu.edu.auction.domain.User;
import miu.edu.auction.dto.RegistrationDTO;

public class RegistrationAdapter {
    public static RegistrationDTO getRegistrationDTOFromUser(User user) {
        RegistrationDTO registrationDTO = new RegistrationDTO();

        registrationDTO.setId(user.getId());
        registrationDTO.setEmail(user.getEmail());
        registrationDTO.setName(user.getName());
        registrationDTO.setRoles(user.getRoles());

        return registrationDTO;
    }
}
