package com.rittik.MyQuizzApp.controller;

import com.rittik.MyQuizzApp.dto.JwtResponseDTO;
import com.rittik.MyQuizzApp.dto.LoginRequestDTO;
import com.rittik.MyQuizzApp.dto.MessageResponse;
import com.rittik.MyQuizzApp.dto.SignupRequestDTO;
import com.rittik.MyQuizzApp.entity.Role;
import com.rittik.MyQuizzApp.entity.User;
import com.rittik.MyQuizzApp.exception.DuplicateResourceException;
import com.rittik.MyQuizzApp.repository.RoleRepository;
import com.rittik.MyQuizzApp.repository.UserRepository;
import com.rittik.MyQuizzApp.security.UserDetailsImpl;
import com.rittik.MyQuizzApp.security.jwt.JwtProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signupRequestDTO){
        if(userRepository.existsByUsername(signupRequestDTO.getUsername())){
            throw new DuplicateResourceException("Error! Username is already taken");
        }
        User user = new User();
        user.setUsername(signupRequestDTO.getUsername());
        user.setPasswordHash(encoder.encode(signupRequestDTO.getPassword()));

        Role role = roleRepository.findByName("ROLE_USER").
                orElseThrow(()->new RuntimeException("Error! Role not found"));
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User Registered Successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDTO(userDetails.getUsername(),jwt,roles,userDetails.getId()));
    }

}
