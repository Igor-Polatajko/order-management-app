package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final int PAGE_SIZE = 10;

    private static final Sort SORT = Sort.by("active").descending().and(Sort.by("name").ascending());

    private ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public Page<Client> findAllByActivity(Integer pageNumber, boolean isActive, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
        return repository.findByActiveAndUserId(isActive, userId, pageable);
    }

    @Override
    public Page<Client> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
        return repository.findByActiveAndUserIdAndNameContains(isActive, userId, name, pageable);
    }

    @Override
    public void create(Client client, Long userId) {
        Client clientWithUserId = client.toBuilder()
                .userId(userId)
                .build();

        repository.save(clientWithUserId);
    }

    @Override
    public void update(Client client, Long userId) {
        findClientByIdOrThrowException(client.getId(), userId);
        repository.save(client);
    }

    @Override
    public void delete(Long id, Long userId) {
        Client client = findClientByIdOrThrowException(id, userId);
        if (client.isActive()) {
            client = client.toBuilder().active(false).build();
            repository.save(client);
        } else {
            repository.delete(client);
        }
    }

    @Override
    public Client findById(Long id, Long userId) {
        return findClientByIdOrThrowException(id, userId);
    }

    @Override
    public void activate(Long id, Long userId) {
        Client client = findClientByIdOrThrowException(id, userId);
        client = client.toBuilder().active(true).build();
        repository.save(client);
    }

    private Client findClientByIdOrThrowException(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ServiceException("Client not found!"));
    }
}
