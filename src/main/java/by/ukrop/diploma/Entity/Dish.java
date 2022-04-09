package by.ukrop.diploma.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private Category category;

    @Column
    private String name;

    @Column
    private double price;

    @Column
    private String description;

    @Column
    private String image;

    public Dish() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dishes = (Dish) o;
        return id == dishes.id && Double.compare(dishes.price, price) == 0 && Objects.equals(category, dishes.category) && Objects.equals(name, dishes.name) && Objects.equals(description, dishes.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, price, description);
    }
}
