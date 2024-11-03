package com.foundation.core.entity;

import com.foundation.core.constants.OpsType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Profile;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "MONEY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Profile("dev")
public class Money extends BaseEntity {
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "IBAN")
    private String iban;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 8)
    private OpsType opsType;
}
