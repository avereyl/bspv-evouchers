package org.bspv.evoucher.config.security;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bspv.evoucher.core.business.TeamMembersBusinessService;
import org.bspv.evoucher.core.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTeamCache {

    private static final ConcurrentHashMap<UUID, Team> CACHE = new ConcurrentHashMap<>();

    @Autowired
    private TeamMembersBusinessService userBusinessService;
    
    public Team getUserTeam(UUID uuid) {
        return CACHE.computeIfAbsent(uuid, key -> userBusinessService.findTeam(uuid)); 
    }

}
