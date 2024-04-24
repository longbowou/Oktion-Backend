package miu.edu.oktion.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.oktion.dto.RegistrationDTO;
import miu.edu.oktion.dto.UserDTO;
import miu.edu.oktion.exception.ApiRequestException;
import miu.edu.oktion.service.BiddingService;
import miu.edu.oktion.service.ProductService;
import miu.edu.oktion.service.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static miu.edu.oktion.util.AppConstant.SUCCESS;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends BaseController {

    private final UserService userService;
    private final BiddingService biddingService;
    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@Validated @RequestBody UserDTO userDTO) {
        try {
            RegistrationDTO result = userService.save(userDTO);
            return getResponse(SUCCESS, result, HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiRequestException("Unable to register", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") String id) {
        try {
            return getResponse(SUCCESS, userService.get(id), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new ApiRequestException("Unable to fetch user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/users/dashboard
    @GetMapping("/dashboard")
    public ResponseEntity<?> getUserDashboard() {
        try {
            return getResponse(SUCCESS, userService.getDashboard(getUser()), HttpStatus.OK);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        throw new ApiRequestException("Unable to fetch Dashboard data", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
