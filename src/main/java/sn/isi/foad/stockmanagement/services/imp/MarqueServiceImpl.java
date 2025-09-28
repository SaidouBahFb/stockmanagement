package sn.isi.foad.stockmanagement.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.isi.foad.stockmanagement.dto.MarqueRequest;
import sn.isi.foad.stockmanagement.dto.MarqueResponse;
import sn.isi.foad.stockmanagement.dto.ProductResponse;
import sn.isi.foad.stockmanagement.dto.CategorieResponse;
import sn.isi.foad.stockmanagement.entities.Marque;
import sn.isi.foad.stockmanagement.entities.Produit;
import sn.isi.foad.stockmanagement.repositories.MarqueRepository;
import sn.isi.foad.stockmanagement.services.MarqueService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarqueServiceImpl implements MarqueService {
    @Autowired
    private MarqueRepository marqueRepository;

    @Override
    public List<MarqueResponse> listerMarques() {
        return marqueRepository.findAll()
                .stream()
                .map(MarqueResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MarqueResponse ajouterMarque(MarqueRequest marqueRequest) {
        if (marqueRepository.findByNom(marqueRequest.getNom()).isPresent()) {
            throw new IllegalArgumentException("Une marque avec ce nom existe déjà.");
        }
        Marque marque = MarqueRequest.toEntity(marqueRequest);
        Marque saved = marqueRepository.save(marque);
        return MarqueResponse.fromEntity(saved);
    }

    @Override
    public MarqueResponse modifierMarque(Integer id, MarqueRequest marqueRequest) {
        Marque marque = marqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
        marqueRepository.findByNom(marqueRequest.getNom())
            .filter(m -> !m.getId().equals(id))
            .ifPresent(m -> { throw new IllegalArgumentException("Une autre marque avec ce nom existe déjà."); });
        marque.setNom(marqueRequest.getNom());
        marque.setDescription(marqueRequest.getDescription());
        Marque updated = marqueRepository.save(marque);
        return MarqueResponse.fromEntity(updated);
    }

    @Override
    public MarqueResponse rechercherParNom(String nom) {
        Marque marque = marqueRepository.findAll().stream()
            .filter(m -> m.getNom().equalsIgnoreCase(nom))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
        return MarqueResponse.fromEntity(marque);
    }

    @Override
    public MarqueResponse afficherMarque(Integer id) {
        Marque marque = marqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
        return MarqueResponse.fromEntity(marque);
    }

    @Override
    public void supprimerMarque(Integer id) {
        Marque marque = marqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
        marqueRepository.delete(marque);
    }

    @Override
    public List<ProductResponse> produitsDuneMarque(Integer id) {
        Marque marque = marqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
        return marque.getProduits().stream()
            .map(ProductResponse::fromEntity)
            .toList();
    }

    @Override
    public List<CategorieResponse> categoriesDuneMarque(Integer id) {
        Marque marque = marqueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
        return marque.getProduits().stream()
            .map(Produit::getCategorie)
            .distinct()
            .map(CategorieResponse::fromEntity)
            .toList();
    }
}
