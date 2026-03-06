package pharmacie.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.HashSet;

@Entity
@Getter
@Setter
@ToString
public class Fournisseur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;

    @ToString.Exclude
    @ManyToMany(mappedBy = "fournisseurs")
    private Set<Categorie> categories = new HashSet<>();
}
