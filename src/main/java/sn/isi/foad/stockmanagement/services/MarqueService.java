package sn.isi.foad.stockmanagement.services;

import sn.isi.foad.stockmanagement.dto.MarqueRequest;
import sn.isi.foad.stockmanagement.dto.MarqueResponse;
import sn.isi.foad.stockmanagement.dto.ProductResponse;
import sn.isi.foad.stockmanagement.dto.CategorieResponse;

import java.util.List;

public interface MarqueService {
    List<MarqueResponse> listerMarques();
    MarqueResponse ajouterMarque(MarqueRequest marqueRequest);
    MarqueResponse modifierMarque(Integer id, MarqueRequest marqueRequest);
    MarqueResponse rechercherParNom(String nom);
    MarqueResponse afficherMarque(Integer id);
    void supprimerMarque(Integer id);
    List<ProductResponse> produitsDuneMarque(Integer id);
    List<CategorieResponse> categoriesDuneMarque(Integer id);
}
