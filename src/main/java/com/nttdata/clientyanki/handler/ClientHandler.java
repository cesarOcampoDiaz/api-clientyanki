package com.nttdata.clientyanki.handler;

import com.nttdata.clientyanki.document.Client;
import com.nttdata.clientyanki.repository.ClientRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ClientHandler {

    private final ClientRepository clientRepository;
    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    @Autowired
    public ClientHandler(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        var clientMono = serverRequest.bodyToMono(Client.class);

        return clientMono.flatMap(t -> ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientRepository.save(t), Client.class)
        );
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        var id = Integer.parseInt(serverRequest.pathVariable("id"));
        var clientItem = clientRepository.findById(id);

        return clientItem.flatMap(t -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientItem, Client.class)
                .switchIfEmpty(notFound)
        );
    }

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(clientRepository.findAll().log("Func"), Client.class).switchIfEmpty(notFound);


    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        var id = Integer.parseInt(serverRequest.pathVariable("id"));
        var clientItem = clientRepository.findById(id);
        var client = serverRequest.bodyToMono(Client.class);

        return clientItem.flatMap(
                t -> {
                    return client.flatMap(x -> {
                        t.setEmail(x.getEmail());
                        t.setDirection(x.getDirection());
                        t.setFirstName(x.getFirstName());
                        t.setLastName(x.getLastName());
                        return ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(clientRepository.save(t), Client.class);
                    });
                });

    }


}
