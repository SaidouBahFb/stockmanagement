package sn.isi.foad.stockmanagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commande")
public class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer dateCommande;
    private Short statut;
    private String dateLivraison;
    private String dateLivraisonVoulue;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "magasin_id")
    private Magasin magasin;

    @ManyToOne
    @JoinColumn(name = "vendeur_id")
    private Employe vendeur;

    @OneToMany(mappedBy = "commande")
    private List<ArticleCommande> articlesCommande;
}
