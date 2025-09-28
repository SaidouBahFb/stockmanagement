package sn.isi.foad.stockmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.isi.foad.stockmanagement.dto.CategorieResponse;
import sn.isi.foad.stockmanagement.dto.MarqueRequest;
import sn.isi.foad.stockmanagement.dto.MarqueResponse;
import sn.isi.foad.stockmanagement.dto.ProductResponse;
import sn.isi.foad.stockmanagement.services.MarqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/marques")
public class MarqueController {
    @Autowired
    private MarqueService marqueService;

    @Operation(summary = "Lister toutes les marques", description = "Retourne la liste complète des marques enregistrées.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des marques récupérée avec succès"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping
    public ResponseEntity<List<MarqueResponse>> listerMarques() {
        return ResponseEntity.ok(marqueService.listerMarques());
    }

    @Operation(summary = "Ajouter une nouvelle marque", description = "Crée une nouvelle marque à partir des données fournies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marque ajoutée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides pour la création de la marque"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping
    public ResponseEntity<MarqueResponse> ajouterMarque(@RequestBody MarqueRequest marqueRequest) {
        if (marqueRequest.getNom() == null || marqueRequest.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom de la marque est obligatoire.");
        }
        MarqueResponse saved = marqueService.ajouterMarque(marqueRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Modifier une marque", description = "Modifie les informations d'une marque existante par son identifiant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marque modifiée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides pour la modification de la marque"),
        @ApiResponse(responseCode = "404", description = "Marque non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MarqueResponse> modifierMarque(@PathVariable Integer id, @RequestBody MarqueRequest marqueRequest) {
        try {
            if (marqueRequest.getNom() == null || marqueRequest.getNom().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            MarqueResponse updated = marqueService.modifierMarque(id, marqueRequest);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Rechercher une marque par nom", description = "Recherche une marque en fonction de son nom.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marque trouvée avec succès"),
        @ApiResponse(responseCode = "404", description = "Aucune marque trouvée avec ce nom"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/search/{nom}")
    public ResponseEntity<MarqueResponse> rechercherParNom(@PathVariable String nom) {
        try {
            MarqueResponse found = marqueService.rechercherParNom(nom);
            return ResponseEntity.ok(found);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Afficher une marque par son id", description = "Retourne les informations d'une marque selon son identifiant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marque trouvée avec succès"),
        @ApiResponse(responseCode = "404", description = "Marque non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MarqueResponse> afficherMarque(@PathVariable Integer id) {
        try {
            MarqueResponse marque = marqueService.afficherMarque(id);
            return ResponseEntity.ok(marque);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Supprimer une marque par son id", description = "Supprime une marque selon son identifiant.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Marque supprimée avec succès"),
        @ApiResponse(responseCode = "404", description = "Marque non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerMarque(@PathVariable Integer id) {
        try {
            marqueService.supprimerMarque(id);
            return ResponseEntity.ok("Marque supprimée avec succès.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Lister les produits d'une marque", description = "Retourne la liste des produits associés à une marque donnée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des produits récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Marque non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{id}/produits")
    public ResponseEntity<List<ProductResponse>> produitsDuneMarque(@PathVariable Integer id) {
        try {
            List<ProductResponse> produits = marqueService.produitsDuneMarque(id);
            return ResponseEntity.ok(produits);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Lister les catégories d'une marque", description = "Retourne la liste des catégories distinctes des produits d'une marque donnée.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste des catégories récupérée avec succès"),
        @ApiResponse(responseCode = "404", description = "Marque non trouvée"),
        @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("/{id}/categories")
    public ResponseEntity<List<CategorieResponse>> categoriesDuneMarque(@PathVariable Integer id) {
        try {
            List<CategorieResponse> categories = marqueService.categoriesDuneMarque(id);
            return ResponseEntity.ok(categories);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
