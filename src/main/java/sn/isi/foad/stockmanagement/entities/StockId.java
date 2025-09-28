package sn.isi.foad.stockmanagement.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class StockId implements Serializable {
    private Integer magasinId;
    private Integer produitId;
}
