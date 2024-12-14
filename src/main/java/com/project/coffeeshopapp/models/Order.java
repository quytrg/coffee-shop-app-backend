package com.project.coffeeshopapp.models;

import com.project.coffeeshopapp.enums.OrderStatus;
import com.project.coffeeshopapp.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted=false")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "order_code", nullable = false, unique = true, length = 20)
    private String orderCode;

    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Column(name = "total_amount", precision = 11, scale = 2, nullable = false)
    @NotNull(message = "Total amount is mandatory")
    @PositiveOrZero(message = "Total amount must be non-negative value")
    @DecimalMax(value = "999999999.99", message = "Total amount cannot exceed 999,999,999.99")
    private BigDecimal totalAmount;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Order status is mandatory")
    private OrderStatus status;

    @Column(name = "received_amount", precision = 11, scale = 2, nullable = false)  // Tiền khách đưa
    @NotNull(message = "Received amount is mandatory")
    @PositiveOrZero(message = "Received amount must be non-negative value")
    @DecimalMax(value = "999999999.99", message = "Received amount cannot exceed 999,999,999.99")
    private BigDecimal receivedAmount;

    @Column(name = "return_amount", precision = 11, scale = 2, nullable = false)  // Tiền thối lại
    @NotNull(message = "Return amount is mandatory")
    @PositiveOrZero(message = "Return amount must be non-negative value")
    @DecimalMax(value = "999999999.99", message = "Return amount cannot exceed 999,999,999.99")
    private BigDecimal returnAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
