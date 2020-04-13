package com.pnu.ordermanagementapp.client;

import com.pnu.ordermanagementapp.adapter.DbAdapter;
import com.pnu.ordermanagementapp.exception.ClientNotFoundException;
import com.pnu.ordermanagementapp.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ClientDbAdapter implements DbAdapter<Client> {
    private ClientRepository repository;

    @Autowired
    public ClientDbAdapter(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> findAll() {
<<<<<<< e59b31498bdc4d73301689269d4a53353733608f
        return (List<Client>) repository.findAll();
=======
        return IntStream.range(0, 3)
                .mapToObj(i -> Client.builder()
                        .id(Long.valueOf(i))
                        .firstName("firstName_" + i)
                        .lastName("lastName_" + i)
                        .email("email_" + i)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Client findById(Long id) {
        return null;
>>>>>>> Form submition for new order done
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
        repository.delete(client);
    }

    @Override
    public Client findById(Long id) {
        return findClientByIdOrThrowException(id);
    }

    private Client findClientByIdOrThrowException(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found!"));
    }
}
