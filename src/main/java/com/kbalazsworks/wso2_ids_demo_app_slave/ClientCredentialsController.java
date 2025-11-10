package com.kbalazsworks.wso2_ids_demo_app_slave;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ClientCredentialsController
{
    @GetMapping("/client-credentials")
    @PreAuthorize("hasAuthority(\"SCOPE_read\")")
    public String clientCredentialsAction(Authentication auth)
    {
        log.info("/client-credentials called");
        log.info("Authorities: {}", auth.getAuthorities());
        log.info("Details: {}", auth.getDetails());
        log.info("Principal: {}", auth.getPrincipal());

        return "Spring OK";
    }
}
