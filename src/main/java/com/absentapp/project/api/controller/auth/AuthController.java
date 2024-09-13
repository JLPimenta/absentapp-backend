package com.absentapp.project.api.controller.auth;

import com.absentapp.project.domain.entity.User;
import com.absentapp.project.domain.model.auth.IAuthMapper;
import com.absentapp.project.domain.model.auth.SignUpRequest;
import com.absentapp.project.domain.model.auth.AuthUser;
import com.absentapp.project.domain.model.auth.LoggedUser;
import com.absentapp.project.domain.service.auth.TokenService;
import com.absentapp.project.domain.service.user.IUserService;
import com.absentapp.project.domain.service.user.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/auth")
@CrossOrigin
@Tag(name = "Autenticação", description = "Geração de token JWT para autenticação na API.")
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final IUserService userService;
    private final IAuthMapper mapper;

    public AuthController(AuthenticationManager manager, TokenService tokenService, IUserService userService, IAuthMapper mapper) {
        this.manager = manager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso.")
    })
    public ResponseEntity<LoggedUser> login(@RequestBody @Valid AuthUser user) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(user.login(), user.senha());
        Authentication authentication = manager.authenticate(authenticationToken);

        User contextUser = (User) authentication.getPrincipal();

        String tokenJWT = tokenService.token(contextUser);

        LoggedUser loggedUser = mapper.toResponse(contextUser);
        loggedUser.setToken(tokenJWT);

        return ResponseEntity.ok().body(loggedUser);
    }

    @PostMapping(value = "/signup", consumes = {"multipart/form-data"})
    @Transactional(rollbackFor = Exception.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso.")
    })
    public ResponseEntity<LoggedUser> register(@ModelAttribute @Valid SignUpRequest signUpRequest, UriComponentsBuilder uriBuilder) throws IOException {
        MultipartFile profileImage = signUpRequest.getProfileImage();

        User user = userService.create(mapper.fromRequest(signUpRequest), profileImage);
        LoggedUser loggedUser = mapper.toResponse(user);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(loggedUser);
    }
}