package pharmacie.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pharmacie.entity.Fournisseur;
import pharmacie.dao.FournisseurRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fournisseurs")

public class FournisseurController {

    @Autowired
    private FournisseurRepository fournisseurRepository;


    @GetMapping
    public List<Fournisseur> getAll() {
        return fournisseurRepository.findAll();
    }


    @GetMapping("/{id}")
    public Optional<Fournisseur> getById(@PathVariable Long id) {
        return fournisseurRepository.findById(id);
    }


    @PostMapping
    public Fournisseur create(@RequestBody Fournisseur fournisseur) {
        return fournisseurRepository.save(fournisseur);
    }





    @GetMapping("/categorie/{categorieId}")
    public List<Fournisseur> getByCategorie(@PathVariable Integer categorieId) {
        // Utilisation du repository corrigé
        return fournisseurRepository.findByCategories_Code(categorieId);
    }
}
