package com.compstore.repo;

import com.compstore.model.Powers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowersRepo extends JpaRepository<Powers, Long> {
    List<Powers> findAllByNameContaining(String name);

}
