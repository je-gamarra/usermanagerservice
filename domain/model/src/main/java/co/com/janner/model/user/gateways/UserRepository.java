package co.com.janner.model.user.gateways;

import co.com.janner.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> save(User user);

    Mono<User> findById(Long id);

    Mono<User> findByUserId(String userId);

    Mono<User> findByEmail(String email);

    Mono<User> findByDocumentNumber(String documentNumber);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> existsByDocumentNumber(String documentNumber);

    Mono<Void> deleteById(Long id);

}
