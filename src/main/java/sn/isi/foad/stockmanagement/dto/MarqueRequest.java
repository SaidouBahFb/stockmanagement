package sn.isi.foad.stockmanagement.dto;

import lombok.Data;
import sn.isi.foad.stockmanagement.entities.Marque;

@Data
public class MarqueRequest {
    private String nom;
    private String description;

    public static Marque toEntity(MarqueRequest request) {
        Marque marque = new Marque();
        marque.setNom(request.getNom());
        marque.setDescription(request.getDescription());
        return marque;
    }
}
