package co.com.janner.r2dbc;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;

import co.com.janner.model.user.User;
import co.com.janner.r2dbc.entity.UserEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    UserRepositoryAdapter repositoryAdapter;

    @Mock
    UserReactiveRepository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {

        UserEntity userEntity = new UserEntity();
         User user = new User();

        when(repository.findById(1L)).thenReturn(Mono.just(userEntity));
        when(mapper.map("test", User.class)).thenReturn(user);

        Mono<User> result = repositoryAdapter.findById(1L);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

}
