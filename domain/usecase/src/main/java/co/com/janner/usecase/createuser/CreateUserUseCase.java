package co.com.janner.usecase.createuser;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

import co.com.janner.model.user.User;
import co.com.janner.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    //private final NotificationGateway notificationGateway;
    
    public Mono<User> execute(User user) {
        return validateUserData(user)
                .then(checkUserDoesNotExist(user))
                .then(createUser(user));
    }
    
    private Mono<Void> validateUserData(User user) {
        if (!user.isValidAge()) {
            return Mono.error(new RuntimeException("El usuario debe ser mayor de edad"));
        }
        
        if (user.getBaseSalary() == null || user.getBaseSalary() < 0) {
            return Mono.error(new RuntimeException("El salario base debe ser mayor a 0"));
        }
        
        if (user.getBaseSalary() > 15000000) {
            return Mono.error(new RuntimeException("El salario base no puede exceder 15,000,000"));
        }
        
        return Mono.empty();
    }
    
    private Mono<Void> checkUserDoesNotExist(User user) {
        return userRepository.existsByEmail(user.getEmail())
                .flatMap(emailExists -> {
                    if (emailExists) {
                        return Mono.error(new RuntimeException("Correo electrónico ya registrado"));
                    }

                    return userRepository.existsByDocumentNumber(user.getDocumentNumber())
                            .flatMap(docExists -> {
                                if (docExists) {
                                    return Mono
                                            .error(new RuntimeException("Número de documento ya registrado"));
                                }
                                return Mono.empty();
                            });
                });
    }
    
    private Mono<User> createUser(User user) {
        User newUser = user.toBuilder()
                .userId(UUID.randomUUID().toString())
                //.role(UserRole.SOLICITANTE) // Por defecto, todos son solicitantes
                //.status(UserStatus.ACTIVE)
                .creationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        
        return userRepository.save(newUser);
    }
    
    // private Mono<User> sendWelcomeNotification(User user) {
    //     return notificationGateway.sendWelcomeEmail(user)
    //             .then(Mono.just(user))
    //             .onErrorResume(error -> {
    //                 log.warn("Error enviando notificación de bienvenida: {}", error.getMessage());
    //                 return Mono.just(user); // No fallar el caso de uso por error de notificación
    //             });
    // }
}


