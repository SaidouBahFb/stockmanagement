package sn.isi.foad.stockmanagement.dto;

import sn.isi.foad.stockmanagement.entities.Categorie;

public class CategorieResponse {
    public Integer id;
    public String nom;

    public static CategorieResponse fromEntity(Categorie categorie) {
        CategorieResponse dto = new CategorieResponse();
        dto.id = categorie.getId();
        dto.nom = categorie.getNom();
        return dto;
    }
}

