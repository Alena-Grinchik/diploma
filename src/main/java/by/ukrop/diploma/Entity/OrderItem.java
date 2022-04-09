package by.ukrop.diploma.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="dishes_id", referencedColumnName = "id")
    private Dish dishes;

    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private Order order;

    @Column(name="quantity")
    private Long quantity;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dish getDishes() {
        return dishes;
    }

    public void setDishes(Dish dishes) {
        this.dishes = dishes;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(id, that.id) && Objects.equals(dishes, that.dishes) && Objects.equals(order, that.order) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dishes, order, quantity);
    }
}
