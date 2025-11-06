package br.com.ferreiradev.fmu.core.infrastructure.security;

import br.com.ferreiradev.fmu.core.application.service.UserService;
import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import br.com.ferreiradev.fmu.core.infrastructure.security.enums.UserRole;
import br.com.ferreiradev.fmu.core.presentation.dto.UserRecord;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.INVALID_CREDENTIALS_MESSAGE;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken token =  (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttribute("email");

        UserRecord user;

        try{
            user = service.findByEmail(email);
        } catch (ResourceNotFoundException notFoundEx){
            user = registerUserByOauth(email);
        } catch (Exception ex) {
            throw new BadCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }

        authentication = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request,response,authentication);

    }

    private UserRecord registerUserByOauth(String email){
        UserRecord record = UserRecord.builder()
                .email(email)
                .username(extractUsernameFrom(email))
                .roles(List.of(UserRole.USER.name()))
                .build();
        return service.create(record);
    }

    private String extractUsernameFrom(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
