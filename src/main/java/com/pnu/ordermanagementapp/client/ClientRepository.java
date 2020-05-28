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

    List<Client> findAllByUserId(Long userId);

    @Override
    <S extends Client> S save(S s);

    @Override
    void delete(Client client);


    Page<Client> findByActiveAndUserId(boolean active, long userId, Pageable pageable);

    Page<Client> findByActiveAndUserIdAndNameContains(boolean active, long userId, String name, Pageable pageable);

}
