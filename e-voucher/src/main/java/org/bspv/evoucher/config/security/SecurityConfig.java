package org.bspv.evoucher.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;

/**
 * 
 *
 */
@Configuration
public class SecurityConfig {

    /**
     * Adding a custom {@link MethodSecurityExpressionHandler} to enhance security expressions.
     * @return a new {@link EVoucherMethodSecurityExpressionHandler}
     */
    @Bean
    protected EVoucherMethodSecurityExpressionHandler createExpressionHandler() {
        EVoucherMethodSecurityExpressionHandler expressionHandler = new EVoucherMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(expressionHandler.getPermissionEvaluator());
        return expressionHandler;
    }

}
