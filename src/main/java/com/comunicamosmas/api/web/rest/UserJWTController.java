package com.comunicamosmas.api.web.rest;

import com.comunicamosmas.api.domain.SidebarRelations;
import com.comunicamosmas.api.domain.User;
import com.comunicamosmas.api.security.jwt.JWTFilter;
import com.comunicamosmas.api.security.jwt.TokenProvider;
import com.comunicamosmas.api.service.ISidebarRelationsService;
import com.comunicamosmas.api.service.IUsuarioService;
import com.comunicamosmas.api.service.UserService;
import com.comunicamosmas.api.web.rest.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate users.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    UserService userService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    ISidebarRelationsService sidebarRelationsService;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginVM.getUsername(),
            loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication, loginVM.isRememberMe());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        //informacion del user;
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(loginVM.getUsername());
        //traer el rol
        System.out.print(loginVM.getUsername());

        String rol = usuarioService.findRolByUser(loginVM.getUsername());

        List<SidebarRelations> menu = sidebarRelationsService.findByIdRole(rol);

        return new ResponseEntity<>(new JWTToken(jwt, user, menu), httpHeaders, HttpStatus.OK);
    }

    /**
     * validar token */
    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String token) {
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = tokenProvider.getAuthentication(token);
            Optional<User> user = userService.getUserWithAuthoritiesByLogin(authentication.getName());
            String rol = usuarioService.findRolByUser(authentication.getName());

            List<SidebarRelations> menu = sidebarRelationsService.findByIdRole(rol);
            //response.put("id_token","ok");

            return new ResponseEntity<>(new JWTToken(token, user, menu), HttpStatus.OK);
        } catch (Exception e) {
            response.put("response", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        private Optional<User> user;

        private List<SidebarRelations> menu;

        JWTToken(String idToken, Optional<User> user, List<SidebarRelations> menu) {
            this.idToken = idToken;
            this.user = user;
            this.menu = menu;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        @JsonProperty("usuario")
        Optional<User> getUser() {
            return user;
        }

        @JsonProperty("menu")
        List<SidebarRelations> getMenu() {
            return menu;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
