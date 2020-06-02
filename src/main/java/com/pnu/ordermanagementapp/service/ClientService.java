package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.dto.client.ClientFormSubmitDto;
import com.pnu.ordermanagementapp.model.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    List<Client> findAll(Long userId);

    List<Client> findAllActive(Long userId);

    Page<Client> findAllByActivity(Integer pageNumber, boolean isActive, Long userId);

    Page<Client> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId);

    Client findById(Long id, Long userId);

    void create(ClientFormSubmitDto clientFormDto, Long userId);

    void update(ClientFormSubmitDto clientFormDto, Long userId);

    void delete(Long id, Long userId);

    void activate(Long id, Long userId);

}
