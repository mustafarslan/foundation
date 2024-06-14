package com.foundation.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "GREET")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Profile("dev")
public class Greet extends BaseEntity {
    @Id
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String identifier;

    @Column(name = "GREET_NAME", unique = true)
    private String name;

    @PrePersist
    void prePersist() {
        this.identifier = UUID.randomUUID().toString();
    }
}
