package com.nttdata.clientyanki.repository;

import com.nttdata.clientyanki.document.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client,Integer> {
}
