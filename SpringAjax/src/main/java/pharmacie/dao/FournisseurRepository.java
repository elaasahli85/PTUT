package pharmacie.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pharmacie.entity.Fournisseur;
import pharmacie.entity.Ligne;
import pharmacie.entity.Medicament;

import java.util.List;


public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    List<Fournisseur> findByNom(String nom);
    List<Fournisseur> findByCategories_Code(Integer code);
}
