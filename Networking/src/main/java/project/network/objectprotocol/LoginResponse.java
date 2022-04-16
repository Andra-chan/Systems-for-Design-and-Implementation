package project.network.objectprotocol;

import project.model.User;

public class LoginResponse implements Response{
    private Integer id;

    public LoginResponse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
