package com.compstore.repo;

import com.compstore.model.Motherboards;
import com.compstore.model.Processors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotherboardsRepo extends JpaRepository<Motherboards, Long> {
    List<Motherboards> findAllByNameContaining(String name);

}
