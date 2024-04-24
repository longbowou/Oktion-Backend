package miu.edu.auction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miu.edu.auction.domain.User;
import miu.edu.auction.domain.auth.AuthRequest;
import miu.edu.auction.domain.auth.AuthResponse;
import miu.edu.auction.exception.ApiRequestException;
import miu.edu.auction.util.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static miu.edu.auction.util.AppConstant.SUCCESS;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthController extends BaseController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        //authenticate
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
            User u = (User) authentication.getPrincipal();
            String token = jwtTokenUtil.generateToken(u);

            return getResponse(SUCCESS, new AuthResponse(u.getId(), u.getEmail(), u.getName(), u.getRoles(), token), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            throw new ApiRequestException("Unable to login", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //failed -> throw unauthorized
    }

}
