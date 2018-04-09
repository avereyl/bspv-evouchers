package org.bspv.evoucher.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

/**
 * 
 *
 */
@Configuration
public class SecurityConfig {

    @Value("${bspv.security.service.name}")
    private String serviceName;
    /**
     * Adding a custom {@link MethodSecurityExpressionHandler} to enhance security expressions.
     * @return a new {@link EVoucherMethodSecurityExpressionHandler}
     */
    @Bean
    protected EVoucherMethodSecurityExpressionHandler createExpressionHandler() {
        EVoucherMethodSecurityExpressionHandler expressionHandler = new EVoucherMethodSecurityExpressionHandler(serviceName);
        expressionHandler.setPermissionEvaluator(expressionHandler.getPermissionEvaluator());
        return expressionHandler;
    }

}
