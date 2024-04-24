package miu.edu.oktion.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
public class Role {
    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false, length = 50, unique = true)
    private String role;
}
