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
    public List<Client> findAll() {
        return repository.findAll().stream()
                .filter(Client::isActive)
                .collect(Collectors.toList());
    }

    // ToDo implement
    @Override
    public Page<Client> findAll(int pageNumber) {
        throw new NotImplementedException();
    }

    @Override
    public void create(Client obj) {
        repository.save(obj);
    }

    @Override
    public void update(Client obj) {
        findClientByIdOrThrowException(obj.getId());
        repository.save(obj);
    }

    @Override
    public void delete(Long id) {
        Client client = findClientByIdOrThrowException(id);
        client.setActive(false);
        repository.save(client);
    }

    @Override
    public Client findById(Long id) {
        return findClientByIdOrThrowException(id);
    }

    private Client findClientByIdOrThrowException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException("Client not found!"));
    }
}
