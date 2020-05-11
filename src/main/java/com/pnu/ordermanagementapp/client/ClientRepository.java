package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByIdAndUserId(Long id, Long userId);

    List<Client> findAllByUserId(Long userId);

}
