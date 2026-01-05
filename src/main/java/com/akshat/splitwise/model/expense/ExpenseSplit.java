package com.akshat.splitwise.model.expense;

import com.akshat.splitwise.model.user.User;

import java.util.Objects;

public class ExpenseSplit {
    private final User user;
    private final double amount;

    public ExpenseSplit(User user, double amount) {
        if(user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (Double.isNaN(amount) || amount < 0) {
            throw new IllegalArgumentException("Split amount must be non-negative");
        }
        this.amount = amount;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof ExpenseSplit expenseSplit))
            return false;
        return Double.compare(amount, expenseSplit.amount) == 0 && Objects.equals(user, expenseSplit.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, amount);
    }
}
