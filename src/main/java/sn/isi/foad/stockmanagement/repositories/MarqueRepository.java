package sn.isi.foad.stockmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.isi.foad.stockmanagement.entities.Marque;

import java.util.Optional;

public interface MarqueRepository extends JpaRepository<Marque, Integer> {
    Optional<Marque> findByNom(String nom);
}
