package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>, PagingAndSortingRepository<Client, Long> {

    Optional<Client> findByIdAndUserId(Long id, Long userId);

    List<Client> findAllByActiveAndUserId(boolean active, long userId);

    Page<Client> findByActiveAndUserId(boolean active, long userId, Pageable pageable);

    Page<Client> findByFirstNameContainsOrLastNameContainsAndActiveAndUserId(String firstName, String lastName,
                                                                             boolean active, long userId,
                                                                             Pageable pageable);

}
