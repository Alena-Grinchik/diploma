package by.ukrop.diploma.persistence.entity;

import by.ukrop.diploma.service.OrderStatus;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private List <Address> address;

    @ManyToOne
    @JoinColumn(name="role_id", referencedColumnName = "id")
    private Role role;

    @ManyToOne
    @JoinColumn(name="discount_id", referencedColumnName = "id")
    private Discount discount;

    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List <Order> ordersList;

    public User() {
    }

    public List<Address> getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(getRole());
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public List<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Order> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(phoneNumber, user.phoneNumber) && Objects.equals(address, user.address) && Objects.equals(role, user.role) && Objects.equals(discount, user.discount) && Objects.equals(ordersList, user.ordersList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, phoneNumber, address, role, discount, ordersList);
    }

    public boolean isManager(){
        return role.getName().equals("ROLE_MANAGER");
    }

    public boolean isAdmin(){
        return role.getName().equals("ROLE_ADMIN");
    }

    public boolean isUser(){
        return role.getName().equals("ROLE_USER");
    }

    public Long applicableDiscountId(){
        int completedOrders = 0;
        for (Order order: ordersList) {
            if (order.getStatus()== OrderStatus.DELIVERED){
                completedOrders+=1;
            }
        }

        if (isManager()){
            return 4L;
        }else if (completedOrders < 5){
            return 1L;
        } else if (completedOrders < 10){
            return 2L;
        } else {
            return 3L;
        }
    }
}
