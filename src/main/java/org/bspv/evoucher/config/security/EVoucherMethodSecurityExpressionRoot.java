package org.bspv.evoucher.config.security;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.bspv.security.expressionhandler.CustomMethodSecurityExpressionRoot;
import org.springframework.security.core.Authentication;

/**
 * @see org.springframework.security.access.expression.method.MethodSecurityExpressionRoot
 * @author guillaume
 *
 */
public class EVoucherMethodSecurityExpressionRoot extends CustomMethodSecurityExpressionRoot {

    public EVoucherMethodSecurityExpressionRoot(Authentication authentication, String serviceName) {
        super(authentication, serviceName);
    }

    public boolean isUserTeam(Team team) {
        User user = ((User) this.getPrincipal());
        return user.getTeam().equals(team);
    }

}
