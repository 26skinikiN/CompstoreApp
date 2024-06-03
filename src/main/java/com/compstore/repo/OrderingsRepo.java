package com.compstore.repo;

import com.compstore.model.Orderings;
import com.compstore.model.enums.OrderingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingsRepo extends JpaRepository<Orderings, Long> {

    List<Orderings> findAllByStatusAndOwner_Id(OrderingStatus status, Long id);

    List<Orderings> findAllByStatus(OrderingStatus status);


}
