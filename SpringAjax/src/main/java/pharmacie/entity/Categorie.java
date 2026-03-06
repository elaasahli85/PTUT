package pharmacie.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor @ToString
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Integer code;

    @NonNull
    @Size(min = 1, max = 255)
    @Column(unique=true, length = 255)
    @NotBlank
    private String libelle;

    @Size(max = 255)
    @Column(length = 255)
    private String description;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorie")
    @JsonIgnoreProperties({"categorie", "lignes"})
    private List<Medicament> medicaments = new LinkedList<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
        name = "fournisseur_categorie",
        joinColumns = @JoinColumn(name = "categorie_code"),
        inverseJoinColumns = @JoinColumn(name = "fournisseur_id")
    )
    @JsonIgnoreProperties("categories")
    private Set<Fournisseur> fournisseurs = new HashSet<>();
}
