package com.ceeprel.service;

import com.ceeprel.models.User;
import com.ceeprel.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LocationService locationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Example IP Address (In real use-case, retrieve from request context)
        String ipAddress = "192.168.1.1"; // Placeholder; replace with actual IP retrieval logic
        String currentLocation = getCurrentLocation(ipAddress);

        if (!user.getRegion().equalsIgnoreCase(currentLocation)) {
            triggerAdditionalSecurityChecks(user);
        }

        // Assign roles/authorities
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorityList
        );
    }

    private String getCurrentLocation(String ipAddress) {
        return locationService.getLocationFromIp(ipAddress).getCity(); // Adjust based on your Location DTO structure
    }

    private void triggerAdditionalSecurityChecks(User user) {
        // Example logic for triggering security checks
        System.out.println("Triggering additional security checks for user: " + user.getEmail());
        // Integrate face recognition or send a security alert here
    }
}
