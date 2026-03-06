package pharmacie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pharmacie.entity.*;
import pharmacie.dao.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApprovisionnementService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private EmailService emailService;

    public void lancerApprovisionnement() throws IOException {

        // 1️⃣ Médicaments à réapprovisionner
        List<Medicament> medicaments = medicamentRepository.findAll()
            .stream()
            .filter(m -> m.getUnitesEnStock() <= m.getNiveauDeReappro())
            .toList();

// 🔹 Vérification des catégories et fournisseurs
        System.out.println("Médicaments à réapprovisionner :");
        for(Medicament m : medicaments) {
            System.out.println(m.getNom() + " -> Catégorie: " +
                (m.getCategorie() != null ? m.getCategorie().getLibelle() : "null"));
            if(m.getCategorie() != null) {
                System.out.println("Fournisseurs : " +
                    m.getCategorie().getFournisseurs().stream()
                        .map(Fournisseur::getNom)
                        .collect(Collectors.joining(", ")));
            }
        }

        // 2️⃣ Construire Map<Fournisseur, List<Medicament>>
        Map<Fournisseur, List<Medicament>> parFournisseur = new HashMap<>();

        for (Medicament medicament : medicaments) {

            Categorie categorie = medicament.getCategorie();

            for (Fournisseur fournisseur : categorie.getFournisseurs()) {

                parFournisseur
                    .computeIfAbsent(fournisseur, f -> new ArrayList<>())
                    .add(medicament);
            }
        }

        // 3️⃣ Envoi email par fournisseur
        for (Map.Entry<Fournisseur, List<Medicament>> entry : parFournisseur.entrySet()) {

            Fournisseur fournisseur = entry.getKey();
            List<Medicament> meds = entry.getValue();

            String contenu = construireMessage(fournisseur, meds);

            emailService.envoyerEmail(
                fournisseur.getEmail(),
                "Demande de devis - Réapprovisionnement",
                contenu
            );
        }
    }

    private String construireMessage(Fournisseur fournisseur,
                                     List<Medicament> medicaments) {

        StringBuilder sb = new StringBuilder();

        sb.append("Bonjour ").append(fournisseur.getNom()).append("\n\n");
        sb.append("Veuillez nous transmettre un devis pour les médicaments suivants :\n\n");

        Map<Categorie, List<Medicament>> parCategorie =
            medicaments.stream()
                .collect(Collectors.groupingBy(Medicament::getCategorie));

        for (Map.Entry<Categorie, List<Medicament>> entry : parCategorie.entrySet()) {

            sb.append("Categorie : ")
                .append(entry.getKey().getLibelle())
                .append("\n");

            for (Medicament m : entry.getValue()) {
                sb.append(" - ")
                    .append(m.getNom())
                    .append(" (Stock : ")
                    .append(m.getUnitesEnStock())
                    .append(")\n");
            }

            sb.append("\n");
        }

        sb.append("Cordialement,\nVotre pharmacie");

        return sb.toString();
    }
}
