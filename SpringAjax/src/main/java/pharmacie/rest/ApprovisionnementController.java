package pharmacie.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pharmacie.service. ApprovisionnementService;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.io.IOException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/approvisionnement")
public class ApprovisionnementController {

    @Autowired
    private  ApprovisionnementService service;


    @PostMapping("/lancer")
    public ResponseEntity<Map<String, String>> lancer() {
        try {
            service.lancerApprovisionnement();
            // ✅ Retourne un JSON valide
            return ResponseEntity.ok(Map.of(
                "status", "OK",
                "message", "Service exécuté"
            ));
        } catch (IOException e) {
            // ✅ Gestion d'erreur
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "status", "ERROR",
                "message", e.getMessage()
            ));
        }
    }
}
