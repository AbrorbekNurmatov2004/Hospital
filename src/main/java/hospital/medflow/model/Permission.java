package hospital.medflow.model;

import hospital.medflow.model.entity.IdEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission extends IdEntity {

    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private List<Role> roles;

}