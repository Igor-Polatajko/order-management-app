package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.model.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    List<Client> findAll();

    Page<Client> findAll(int pageNumber);

    Client findById(Long id);

    void create(Client client);

    void update(Client client);

    void delete(Long id);
}
