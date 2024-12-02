package com.ceeprel.models;

import com.ceeprel.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.CUSTOMER_ROLE;

    private String region;  // Track user's current region (e.g., country, city)

    private boolean isActive = true;  // Track if user account is active/inactive

    private LocalDateTime createdAt = LocalDateTime.now();  // Auto-set creation time
}
