/**
 * 
 */
package org.bspv.evoucher.config.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.bspv.evoucher.core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author guillaume
 *
 */
@Slf4j
@Aspect
@Configuration
public class FunctionalTraceAspect {
    
    private static final String LOG_OF_PROOF = "proof";
    
    private static final Logger PROOF_LOGGER = LoggerFactory.getLogger(LOG_OF_PROOF);
    
    @Pointcut("within(org.bspv.evoucher.process.*)")
    public void processLayerExecution(){
        // pointcut method
    }
    

    @Before("org.bspv.evoucher.config.logging.FunctionalTraceAspect.processLayerExecution()")
    public void before(JoinPoint joinPoint){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = (User) auth.getPrincipal();
            PROOF_LOGGER.info("User {}({}) calling {} with args {}.", user.getUsername(), user.getId(), joinPoint.getSignature(), joinPoint.getArgs());
        } catch (ClassCastException | NullPointerException e) {
            log.error("", e);
        }
    }

}
