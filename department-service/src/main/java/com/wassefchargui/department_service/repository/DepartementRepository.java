package com.wassefchargui.department_service.repository;

import com.wassefchargui.department_service.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    Optional<Departement> findByNom(String nom);

    boolean existsByNom(String nom);
}
