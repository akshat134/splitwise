package com.akshat.splitwise.model.group;
import com.akshat.splitwise.model.user.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Group {
    private final Long id;
    private String name;

    private final Set<User> members;
    private final Set<User> admins;

    public Group(Long id, String name, User creator) {
        validateId(id);
        validateName(name);
        validateUser(creator);

        this.members = new HashSet<>();
        this.admins = new HashSet<>();

        this.id = id;
        this.name = name;
        this.members.add(creator);
        this.admins.add(creator);
    }

    private void updateName(User actor, String newName) {
        validateUser(actor);
        if(!members.contains(actor)) {
            throw new IllegalArgumentException("Only group members can rename the group");
        }
        validateName(newName);
        this.name = newName;
    }

    private void addMember(User actor, User user) {
        validateUser(actor);
        validateUser(user);
        if(!admins.contains(actor)) {
            throw new IllegalArgumentException("Only admins can add users");
        }
        members.add(user);
    }

    private void removeMember(User actor, User user) {
        validateUser(actor);
        validateUser(user);
        if(!admins.contains(actor)) {
            throw new IllegalArgumentException("Only admins can remove users");
        }
        if(!members.contains(user)) {
            return;
        }
        if(admins.contains(user) && admins.size() == 1) {
            throw new IllegalStateException("Cannot remove the only admin from the group");
        }
        members.remove(user);
        if(admins.contains(user)) {
            admins.remove(user);
        }
    }

    private void addMAdmin(User actor, User user) {
        validateUser(actor);
        validateUser(user);
        if(!admins.contains(actor)) {
            throw new IllegalArgumentException("Only admins can promote an admin");
        }
        if(!members.contains(user)) {
            throw new IllegalArgumentException("Admin must be a group member");
        }
        admins.add(user);
    }

    private void removeAdmin(User actor, User user) {
        validateUser(actor);
        validateUser(user);
        if(!admins.contains(actor)) {
            throw new IllegalArgumentException("Only admins can demote an admin");
        }
        if(!admins.contains(user)) {
            return;
        }
        if(admins.size() == 1) {
            throw new IllegalStateException("Group must have atleast one admin");
        }
        admins.remove(user);
    }

    // Getters

    public Long getId() {
        return id;
    }

    public String getName () {
        return name;
    }

    public Set<User> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public Set<User> getAdmins() {
        return Collections.unmodifiableSet(admins);
    }

    // Equality Check

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof Group group))
            return false;
        return Objects.equals(group.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Validation Helpers

    private void validateId(Long id) {
        if(id == null) {
            throw new IllegalArgumentException("Group id cannot be null");
        }
    }

    private void validateName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Group name cannot be null or blank");
        }
    }

    private void validateUser(User user) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
    }
}
