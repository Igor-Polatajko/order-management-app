package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .filter(Client::isActive)
                .collect(Collectors.toList());
    }

    // ToDo implement
    @Override
    public Page<Client> findAll(int pageNumber, Long userId) {
        throw new NotImplementedException();
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
        client.setActive(false);
        repository.save(client);
    }

    @Override
    public Client findById(Long id, Long userId) {
        return findClientByIdOrThrowException(id, userId);
    }

    private Client findClientByIdOrThrowException(Long id, Long userId) {
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ServiceException("Client not found!"));
    }
}
