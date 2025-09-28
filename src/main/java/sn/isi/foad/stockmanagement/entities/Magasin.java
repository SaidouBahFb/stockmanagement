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
@Table(name = "magasin")
public class Magasin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String adresse;
    private String ville;
    private String codeZip;
    private String etat;
    private String email;
    private String telephone;

    @OneToMany(mappedBy = "magasin")
    private List<Employe> employes;

    @OneToMany(mappedBy = "magasin")
    private List<Commande> commandes;

    @OneToMany(mappedBy = "magasin")
    private List<Stock> stocks;
}
