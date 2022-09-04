package by.teachmeskills.eshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Table(name = "orders")
@Entity
public class Order extends BaseEntity {
    @Column(name = "PRICE")
    private int price;
    @Column(name = "DATE")
    private LocalDateTime date;
    @ManyToOne
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyJoinColumn(table ="order_products", name = "product_id")
    @Column(name = "quantity", nullable = false)
    private Map<Product, Integer> products = new HashMap<>();
}
