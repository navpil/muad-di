package io.github.navpil.muaddi.core;

import java.util.List;

public class UserResource {

    private final UserService userService;

    @MuadInject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    public List<String> list() {
        return userService.list();
    }
}
