package miu.edu.auction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();

}
