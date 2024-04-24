package miu.edu.oktion.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Setter
@Entity

public class ProductImage {
    @Id
    @UuidGenerator
    private String id;

    private String path;

    @ManyToOne
    @JsonIgnore
    private Product product;
}
