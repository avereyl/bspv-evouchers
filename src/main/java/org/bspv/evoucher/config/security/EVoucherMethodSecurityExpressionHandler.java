/**
 * 
 */
package org.bspv.evoucher.config.security;

import org.aopalliance.intercept.MethodInvocation;
import org.bspv.security.expressionhandler.CustomMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author guillaume
 *
 */
public class EVoucherMethodSecurityExpressionHandler extends CustomMethodSecurityExpressionHandler {

    public EVoucherMethodSecurityExpressionHandler(String serviceName) {
        super(serviceName);
    }

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
            MethodInvocation invocation) {
        EVoucherMethodSecurityExpressionRoot root = new EVoucherMethodSecurityExpressionRoot(authentication,
                this.serviceName);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setTrustResolver(this.trustResolver);
        root.setRoleHierarchy(getRoleHierarchy());
        return root;
    }

}
