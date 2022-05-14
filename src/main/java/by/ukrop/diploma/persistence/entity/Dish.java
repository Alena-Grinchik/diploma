package by.ukrop.diploma.persistence.entity;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.util.Locale;
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
    private String nameEng;

    @Column
    private double price;

    @Column
    private String description;

    @Column
    private String descriptionEng;

    @Column
    private String image;

    public Dish() {
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Double.compare(dish.price, price) == 0 && Objects.equals(id, dish.id) && Objects.equals(category, dish.category) && Objects.equals(name, dish.name) && Objects.equals(nameEng, dish.nameEng) && Objects.equals(description, dish.description) && Objects.equals(descriptionEng, dish.descriptionEng) && Objects.equals(image, dish.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, nameEng, price, description, descriptionEng, image);
    }

    public String getLocalizedName(){
        Locale locale = LocaleContextHolder.getLocale();
        if(locale.getLanguage().equals("en")){
            return nameEng;
        } else {
            return name;
        }
    }

    public String getLocalizedDescription(){
        Locale locale = LocaleContextHolder.getLocale();
        if(locale.getLanguage().equals("en")){
            return descriptionEng;
        } else {
            return description;
        }
    }
}
