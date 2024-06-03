package com.compstore.repo;

import com.compstore.model.Processors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessorsRepo extends JpaRepository<Processors, Long> {
    List<Processors> findAllByNameContaining(String name);
}
