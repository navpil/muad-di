package io.github.navpil.muaddi.core;

/**
 * Example of Muad'DI usage in Java SE context
 */
public class MainExample {

    public static void main(String[] args) {
        MuadContext muadContext = new MuadContext()
                .register(UserService.class)
                .register(UserResource.class)
                .initialize();

        UserResource userResource = muadContext.get(UserResource.class);

        System.out.println(userResource.list());
    }
}
