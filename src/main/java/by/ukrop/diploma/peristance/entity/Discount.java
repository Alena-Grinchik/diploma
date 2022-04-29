package by.ukrop.diploma.peristance.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Long value;

    @OneToMany(mappedBy = "discount")
    private List<User> userList;

    public Discount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(id, discount.id) && Objects.equals(name, discount.name) && Objects.equals(value, discount.value) && Objects.equals(userList, discount.userList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, userList);
    }
}
