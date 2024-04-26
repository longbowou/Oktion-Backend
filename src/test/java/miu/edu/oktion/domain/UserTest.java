package miu.edu.oktion.domain;

import miu.edu.oktion.dto.UserDTO;
import miu.edu.oktion.repository.BiddingRepository;
import miu.edu.oktion.repository.ProductRepository;
import miu.edu.oktion.repository.RoleRepository;
import miu.edu.oktion.repository.UserRepository;
import miu.edu.oktion.service.security.UserService;
import miu.edu.oktion.service.security.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class UserTest {
    @Test
    public void testUserName() {
        User user = new User();
        user.setName("testuser");
        assertEquals("testuser", user.getName());
    }

    @Test
    public void testUserEmail() {
        User user = new User();
        user.setEmail("test@okiton.app");
        assertEquals("test@okiton.app", user.getEmail());
    }

    @Test
    public void testUserSave() {
        // Mocking a repository
        UserRepository userRepository = mock(UserRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        ProductRepository productRepository = mock(ProductRepository.class);
        BiddingRepository biddingRepository = mock(BiddingRepository.class);

        Role roleCustomer = new Role();
        roleCustomer.setRole("CUSTOMER");
        roleRepository.save(roleCustomer);

        // Creating a user
        UserDTO user = new UserDTO();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testPassword");

        user.setRoles(List.of(roleCustomer.getRole()));

        // Creating a service and saving the user
        UserService userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder, productRepository, biddingRepository);
        userService.save(user);

        assertTrue(userRepository.findByEmail("test@example.com").isPresent());
    }
}

