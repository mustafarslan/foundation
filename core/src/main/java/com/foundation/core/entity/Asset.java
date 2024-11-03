package com.foundation.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.context.annotation.Profile;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ASSETS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Profile("dev")
public class Asset extends BaseEntity {
    private Long customerId;
    private String assetName;
    private int size;
    private int usableSize;
}
