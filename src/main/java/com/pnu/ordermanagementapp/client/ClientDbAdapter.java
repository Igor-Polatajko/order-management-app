package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientDbAdapter implements DbAdapter<Client> {
    private ClientRepository repository;

    @Autowired
    public ClientDbAdapter(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
        return ((List<Client>)repository.findAll()).stream().filter(i->i.isActive()).collect(Collectors.toList());
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

    private Client findClientByIdOrThrowException(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ServiceException("Client not found!"));
    }
}
