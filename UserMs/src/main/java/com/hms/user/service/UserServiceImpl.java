package com.hms.user.service;

import com.hms.user.dto.*;
import com.hms.user.entity.RefreshToken;
import com.hms.user.entity.Role;
import com.hms.user.entity.Rolename;
import com.hms.user.entity.User;
import com.hms.user.exception.EmailAlreadyExist;
import com.hms.user.exception.UserNotFoundException;
import com.hms.user.feign.PatientClient;
import com.hms.user.jwt.JwtUtil;
import com.hms.user.mapper.UserMapper;
import com.hms.user.repositry.RefreshTokenRepository;
import com.hms.user.repositry.RoleRepo;
import com.hms.user.repositry.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepo roleRepo;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PatientClient patientClient;

    @Override
    public UserTokenResponse registerUser(UserRequestDto userRequestDto) {
        Optional<User> opt = userRepo.findByEmail(userRequestDto.getEmail());
        if(opt.isPresent()){
            throw  new EmailAlreadyExist("this email is already registered");
        }



        User user = userMapper.convertToUser(userRequestDto);
        User user1 = userRepo.save(user);
        String accessToken = jwtUtil.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user.getEmail());
        Patient patient = Patient.builder()
                .email(user.getEmail())
                .build();

        patientClient.createPatientProfile(patient);


        return UserTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();




    }

    @Override
    public UserTokenResponse login(UserLoginRequest userLoginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getEmail(),
                            userLoginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.warn("Authentication failed for email: {}", userLoginRequest.getEmail(), e);
            throw new BadCredentialsException("Invalid email or password");
        }

        User user = userRepo.findByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String accessToken = jwtUtil.generateToken(user);

        RefreshToken refreshToken = refreshTokenRepository.findByUser(user).orElseThrow(()-> new RuntimeException("no token found "));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.deleteByUser(user);
            refreshToken = refreshTokenService.create(user.getEmail());
        }

        return UserTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public UserResposeDto getUser(Long id) {
       User user = userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("user not found"));

       return UserResposeDto.builder()
               .id(user.getId())
               .name(user.getName())
               .email(user.getEmail())
               .build();

    }

}
