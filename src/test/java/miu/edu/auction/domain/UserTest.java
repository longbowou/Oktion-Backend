package miu.edu.auction.domain;

import miu.edu.auction.dto.UserDTO;
import miu.edu.auction.repository.BiddingRepository;
import miu.edu.auction.repository.ProductRepository;
import miu.edu.auction.repository.RoleRepository;
import miu.edu.auction.repository.UserRepository;
import miu.edu.auction.service.security.UserService;
import miu.edu.auction.service.security.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

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

        // Creating a user
        UserDTO user = new UserDTO();
        user.setName("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testPassword");

        // Creating a service and saving the user
        UserService userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder, productRepository, biddingRepository);
        userService.save(user);

        assertTrue(userRepository.findByEmail("test@example.com").isPresent());
    }
}

