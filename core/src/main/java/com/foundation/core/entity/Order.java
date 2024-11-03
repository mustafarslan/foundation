package com.foundation.core.entity;

import com.foundation.core.constants.OrderSide;
import com.foundation.core.constants.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ORDERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Profile("dev")
public class Order extends BaseEntity {
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "ASSET_NAME")
    private String assetName;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_SIDE", length = 4)
    private OrderSide orderSide; // BUY or SELL

    @Column(name = "SIZE")
    private int size;

    @Column(name = "PRICE", precision = 19, scale = 4)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 10)
    private OrderStatus status; // PENDING, MATCHED, CANCELED

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
}
