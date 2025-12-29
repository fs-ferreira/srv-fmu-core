package br.com.ferreiradev.fmu.core.presentation.controller.auth.impl;

import br.com.ferreiradev.fmu.core.infrastructure.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DebugViewController {

        @GetMapping("/")
        @ResponseBody
        public String home(Authentication auth) {
            if(auth instanceof CustomAuthentication cAuth){
                System.out.println("User ID: " + cAuth.getUser());
            }
            return "Hello!";
        }

    @GetMapping("/authorized")
    @ResponseBody
    public String home(@RequestParam("code") String code) {
        return "Authorized with code: " + code;
    }
}
