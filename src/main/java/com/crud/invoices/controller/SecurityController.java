package com.crud.invoices.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.crud.invoices.domain.AppUserDto;
import com.crud.invoices.domain.MyUserDetails;
import com.crud.invoices.registration.RegisterCredentialsDto;
import com.crud.invoices.security.JwtToken;
import com.crud.invoices.service.RegistrationService;
import com.crud.invoices.service.UserService;
import com.crud.invoices.validationGroup.OrderChecks;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/invoice")
@RestController
public class SecurityController {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    JwtToken jwtToken;


    @PostMapping("/token/refresh")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String header = request.getHeader(AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            try {
                String refresh_token = header.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserDetails user = userService.loadUserByUsername(username);
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                System.out.println(request.getRequestURL().toString());
                response.setHeader("access_token", access_token);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                return access_token;
            } catch (Exception ex) {
                logger.error("Error loggin in: {}", new Throwable(ex.getMessage()));
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
        return header;
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletResponse response) throws Exception {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Validated(value = {OrderChecks.class}) @Valid @RequestBody AppUserDto appUserDto, Errors errors,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        String jwToken = "";

        if (errors.hasErrors()) {
            Map<String, ArrayList> errorsLoggingMap = new HashMap<>();

            errors.getFieldErrors().stream().forEach(fieldError -> {
                String key = fieldError.getField();
                String value = fieldError.getDefaultMessage();
                if (!errorsLoggingMap.containsKey(key)) {
                    errorsLoggingMap.put(key, new ArrayList<>());
                }
                errorsLoggingMap.get(key).add(fieldError.getDefaultMessage());
            });
            errorsLoggingMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsLoggingMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    appUserDto.getUsername(),
                                    appUserDto.getPassword()
                            )
                    );
            MyUserDetails userIn = (MyUserDetails) authentication.getPrincipal();
            jwToken = jwtToken.generateToken( userIn, request, response);
        } catch (BadCredentialsException ex) {

            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(jwToken, HttpStatus.CREATED);
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(@Validated(value = {OrderChecks.class}) @Valid @RequestBody RegisterCredentialsDto registerCredentialsDto, Errors errors) throws Exception {

        if(errors.hasErrors()) {
            Map<String,ArrayList<Object>>errorsMap = new HashMap<>();
            errors.getFieldErrors().stream().forEach((fieldError -> {
                String key = fieldError.getField();
                if(!errorsMap.containsKey(key)) {
                    errorsMap.put(key, new ArrayList<>());
                }
                errorsMap.get(key).add(fieldError.getDefaultMessage());
            }));
            errorsMap.values().stream().findFirst();
            return new ResponseEntity<>(errorsMap, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            registrationService.register(registerCredentialsDto);
            return new ResponseEntity<>( "User has been registered", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("User hasn't been registered",HttpStatus.UNPROCESSABLE_ENTITY);

        }
    }

    @GetMapping(path = "/register/confirm")
    public String confirm(@RequestParam("token") String token) {
        try {
            registrationService.confirmToken(token);
            return "Token has been confirmed";
        } catch (Exception e) {
            System.out.println("Token hasn't been confirmed");
            return e.getMessage();
        }
    }
}

