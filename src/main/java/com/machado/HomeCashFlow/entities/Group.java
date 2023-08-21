package com.machado.HomeCashFlow.entities;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Group {
    private UUID id;
    private Integer inviteCode;
    private String name;
    private ArrayList<User> users;

    public Group(String name, ArrayList<User> users) {
        if(name != null && !name.equals("")
            && users.size() == 1){
            this.id = UUID.randomUUID();
            this.name = name;
            this.users = users;
            this.inviteCode = Objects.hashCode(id);
        }

    }

    public UUID getId() {
        return id;
    }

    public Integer getInviteCode() {
        return inviteCode;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
