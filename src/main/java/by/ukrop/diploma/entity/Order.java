package by.ukrop.diploma.entity;

import by.ukrop.diploma.PaymentMethod;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity (name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Column
    private String status;

    @Column
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name="address_id", referencedColumnName = "id")
    private Address address;

    @Column(name = "order_comment")
    private String orderComment;

    @Column(name= "payment_method")
    @Enumerated
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", fetch=FetchType.EAGER)
    private List<OrderItem> orderItemsList;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(String orderNotes) {
        this.orderComment = orderNotes;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItem> getOrderItemsList() {
        return orderItemsList;
    }

    public void setOrderItemsList(List<OrderItem> orderItemsList) {
        this.orderItemsList = orderItemsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user) && Objects.equals(status, order.status) && Objects.equals(date, order.date) && Objects.equals(address, order.address) && Objects.equals(orderComment, order.orderComment) && paymentMethod == order.paymentMethod && Objects.equals(orderItemsList, order.orderItemsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, status, date, address, orderComment, paymentMethod, orderItemsList);
    }
}
