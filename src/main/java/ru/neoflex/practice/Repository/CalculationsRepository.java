package ru.neoflex.practice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.neoflex.practice.models.Calculations;

@Repository
public interface CalculationsRepository extends JpaRepository<Calculations, Integer> {

}