package sn.isi.foad.stockmanagement.dto;

import lombok.Data;
import sn.isi.foad.stockmanagement.entities.Marque;

@Data
public class MarqueResponse {
    private Integer id;
    private String nom;
    private String description;

    public static MarqueResponse fromEntity(Marque marque) {
        MarqueResponse dto = new MarqueResponse();
        dto.setId(marque.getId().intValue());
        dto.setNom(marque.getNom());
        dto.setDescription(marque.getDescription());
        return dto;
    }
}
