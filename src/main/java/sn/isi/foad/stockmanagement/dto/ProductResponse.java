package sn.isi.foad.stockmanagement.dto;

import sn.isi.foad.stockmanagement.entities.Produit;

public class ProductResponse {
    public Integer id;
    public String nom;
    public Integer anneeModel;
    public Integer prixDepart;
    public Integer categorieId;
    public String categorieNom;

    public static ProductResponse fromEntity(Produit produit) {
        ProductResponse dto = new ProductResponse();
        dto.id = produit.getId();
        dto.nom = produit.getNom();
        dto.anneeModel = produit.getAnneeModel();
        dto.prixDepart = produit.getPrixDepart();
        if (produit.getCategorie() != null) {
            dto.categorieId = produit.getCategorie().getId();
            dto.categorieNom = produit.getCategorie().getNom();
        }
        return dto;
    }
}

