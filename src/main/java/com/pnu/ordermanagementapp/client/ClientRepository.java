package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.model.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
