package com.foundation.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CUSTOMERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Profile("dev")
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;  // Store hashed password

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "CUSTOMER_ROLES", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "ROLE")
    private Set<String> roles;  // e.g., ROLE_USER, ROLE_ADMIN
}
