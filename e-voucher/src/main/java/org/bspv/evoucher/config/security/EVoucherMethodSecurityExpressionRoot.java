package org.bspv.evoucher.config.security;

import org.bspv.evoucher.core.model.Team;
import org.bspv.evoucher.core.model.User;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *@see  org.springframework.security.access.expression.method.MethodSecurityExpressionRoot
 * @author guillaume
 *
 */
public class EVoucherMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {
    
    private Object filterObject;
    private Object returnObject;
    private Object target;

    public EVoucherMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }
    
    public boolean isAdmin() {
        User user = ((User) this.getPrincipal());
        return user.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }
    
    public boolean isUserTeam(Team team) {
        User user = ((User) this.getPrincipal());
        return user.getTeam().equals(team);
    }

    /**
     * @return the filterObject
     */
    public Object getFilterObject() {
        return filterObject;
    }

    /**
     * @param filterObject the filterObject to set
     */
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    /**
     * @return the returnObject
     */
    public Object getReturnObject() {
        return returnObject;
    }

    /**
     * @param returnObject the returnObject to set
     */
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    /**
     * @return the target
     */
    public Object getTarget() {
        return target;
    }

    /**
     * Sets the "this" property for use in expressions. Typically this will be the "this"
     * property of the {@code JoinPoint} representing the method invocation which is being
     * protected.
     *
     * @param target the target object on which the method in is being invoked.
     */
    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object getThis() {
        return this;
    }

}
