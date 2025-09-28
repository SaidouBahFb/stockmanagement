package sn.isi.foad.stockmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stock")
public class Stock implements Serializable {
    @EmbeddedId
    private StockId id;

    private Integer quantite;

    @ManyToOne
    @MapsId("magasinId")
    private Magasin magasin;

    @ManyToOne
    @MapsId("produitId")
    private Produit produit;
}
