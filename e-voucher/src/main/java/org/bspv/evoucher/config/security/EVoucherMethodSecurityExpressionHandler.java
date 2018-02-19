/**
 * 
 */
package org.bspv.evoucher.config.security;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

/**
 * @author guillaume
 *
 */
public class EVoucherMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
            MethodInvocation invocation) {
        EVoucherMethodSecurityExpressionRoot root = new EVoucherMethodSecurityExpressionRoot(authentication);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.access.expression.AbstractSecurityExpressionHandler#getPermissionEvaluator()
     */
    @Override
    protected PermissionEvaluator getPermissionEvaluator() {
        return super.getPermissionEvaluator();
    }
    
    
}
