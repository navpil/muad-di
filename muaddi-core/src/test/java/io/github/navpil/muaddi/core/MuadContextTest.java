package io.github.navpil.muaddi.core;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

public class MuadContextTest {

    @Test
    public void testPlainInjection() {
        UserResource userResource = new UserResource(new UserService());

        Assertions.assertThat(userResource.list()).isEqualTo(List.of("John", "Jill"));
    }
    @Test
    public void testContextInjection() {
        MuadContext muadContext = new MuadContext()
                .register(UserService.class)
                .register(UserResource.class)
                .initialize();
        UserResource userResource = muadContext.get(UserResource.class);

        Assertions.assertThat(userResource.list()).isEqualTo(List.of("John", "Jill"));
    }


}