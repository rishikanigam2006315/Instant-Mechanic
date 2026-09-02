package com.instantmechanic.mechanic_api.repository;

import com.instantmechanic.mechanic_api.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final List<User> users = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(100);

    public UserRepository() {
        // Pre-seed demo user matching frontend demo credentials
        users.add(new User(
                1L,
                "Rahul Sharma",
                "rahul.sharma@example.com",
                "+91 98765 12345",
                "Car",
                "KA 01 MJ 4521",
                "Password@123",
                "CUSTOMER"
        ));
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public Optional<User> findById(Long id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<User> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return users.stream()
                .filter(u -> email.equalsIgnoreCase(u.getEmail()))
                .findFirst();
    }

    public Optional<User> findByEmailOrPhone(String emailOrPhone) {
        if (emailOrPhone == null) return Optional.empty();
        String normalized = emailOrPhone.replaceAll("\\s+", "").toLowerCase();
        return users.stream()
                .filter(u -> {
                    String uEmail = u.getEmail() != null ? u.getEmail().toLowerCase() : "";
                    String uPhone = u.getPhone() != null ? u.getPhone().replaceAll("\\s+", "") : "";
                    return uEmail.equals(normalized) || uPhone.equals(normalized) || (normalized.length() >= 10 && uPhone.endsWith(normalized));
                })
                .findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.incrementAndGet());
        } else {
            users.removeIf(u -> u.getId().equals(user.getId()));
        }
        users.add(user);
        return user;
    }
}
