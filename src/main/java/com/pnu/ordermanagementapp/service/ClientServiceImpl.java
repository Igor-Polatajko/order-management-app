package com.pnu.ordermanagementapp.service;

import com.pnu.ordermanagementapp.exception.ServiceException;
import com.pnu.ordermanagementapp.model.Client;
import com.pnu.ordermanagementapp.repository.ClientRepository;
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

    private static final Sort SORT = Sort.by("active").descending().and(Sort.by("id").descending());

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public List<Client> findAll(Long userId) {
        return clientRepository.findByUserId(userId);
    }

    @Override
    public List<Client> findAllActive(Long userId) {
        return clientRepository.findByActiveAndUserId(true, userId);
    }

    @Override
    public Page<Client> findAllByActivity(Integer pageNumber, boolean isActive, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return clientRepository.findByActiveAndUserId(isActive, userId, pageable);
    }

    @Override
    public Page<Client> findAllByNameAndActivity(Integer pageNumber, String name, boolean isActive, Long userId) {
        Pageable pageable = createPageable(pageNumber);
        return clientRepository.findByFirstNameContainsOrLastNameContainsAndActiveAndUserId(
                name, name, isActive, userId, pageable);
    }

    @Override
    public void create(Client client, Long userId) {

        if (clientRepository.existsClientByEmailAndUserId(client.getEmail(), userId)) {
            throw new ServiceException(String.format(
                    "Client with email '%s' already exists (active?: %s )", client.getEmail(), client.isActive()
            ));
        }

        Client clientWithUserId = client.toBuilder()
                .userId(userId)
                .active(true)
                .build();

        clientRepository.save(clientWithUserId);
    }

    @Override
    public void update(Client client, Long userId) {

        if (clientRepository
                .existsClientByEmailAndUserIdAndIdNot(client.getEmail(), userId, client.getId())) {
            throw new ServiceException(String.format(
                    "Client with email '%s' already exists (active?: %s )", client.getEmail(), client.isActive()
            ));
        }

        Client clientFromDb = findClientByIdOrThrowException(client.getId(), userId);

        clientFromDb = clientFromDb.toBuilder()
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getLastName())
                .build();

        clientRepository.save(clientFromDb);
    }

    @Override
    public void delete(Long id, Long userId) {
        Client client = findClientByIdOrThrowException(id, userId);
        if (client.isActive()) {
            client = client.toBuilder().active(false).build();
            clientRepository.save(client);
        } else {
            clientRepository.delete(client);
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
        clientRepository.save(client);
    }

    private Client findClientByIdOrThrowException(Long id, Long userId) {
        return clientRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ServiceException("Client not found!"));
    }

    private Pageable createPageable(int pageNumber) {

        if (pageNumber < 1) {
            throw new ServiceException("Incorrect page number!");
        }

        return PageRequest.of(pageNumber - 1, PAGE_SIZE, SORT);
    }

}
