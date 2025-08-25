package co.com.janner.r2dbc;

import co.com.janner.model.user.User;
import co.com.janner.model.user.gateways.UserRepository;
import co.com.janner.r2dbc.entity.UserEntity;
import co.com.janner.r2dbc.helper.ReactiveAdapterOperations;
import reactor.core.publisher.Mono;


import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
    User/* change for domain model */,
    UserEntity/* change for adapter model */,
    Long,
    UserReactiveRepository
> implements UserRepository {
    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.repository = repository;
        this.mapper = mapper;
    }


    private final UserReactiveRepository repository;
    private final ObjectMapper mapper;

    
    @Override
    public Mono<User> findByUserId(String userId) {
        return repository.findByUserId(userId)
                .map(entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<User> findByDocumentNumber(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber)
                .map(entity -> mapper.map(entity, User.class));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
         return repository.findByEmail(email)
                     .hasElement();
    }

    @Override
    public Mono<Boolean> existsByDocumentNumber(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber)
                     .hasElement();
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return null;
    }

}
