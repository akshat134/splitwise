package com.akshat.splitwise.model.expense;

import com.akshat.splitwise.model.user.User;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Expense {
    private static final double EPSILON = 0.01;

    private final Long id;
    private final String description;
    private final double amount;
    // TODO : How to add multiple payees in a single expense?
    private final User paidBy;
    private final SplitType splitType;
    private final List<ExpenseSplit> splits;
    private final LocalDate expenseDate;

    public Expense(
            Long id,
            String description,
            double amount,
            User paidBy,
            SplitType splitType,
            List<ExpenseSplit> splits,
            LocalDate expenseDate
    ) {
        if(id == null) {
            throw new IllegalArgumentException("Expense id cannot be null");
        }
        if(description == null || description.isBlank()) {
            throw new IllegalArgumentException("Expense description cannot be null or blank");
        }
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(paidBy == null) {
            throw new IllegalArgumentException("PaidBy user cannot be null");
        }
        if(splitType == null) {
            throw new IllegalArgumentException("SplitType cannot be null");
        }
        if (splits == null || splits.isEmpty()) {
            throw new IllegalArgumentException("Splits cannot be empty");
        }
        if (expenseDate == null) {
            throw new IllegalArgumentException("Expense date cannot be null");
        }

        boolean paidByIncluded = false;
        double splitSum = 0.0;

        // TODO : Not validating split entries
        // You are assuming:
        // expenseSplit != null
        // expenseSplit.getUser() != null
        // expenseSplit.getAmount() >= 0

        for(ExpenseSplit expenseSplit : splits) {
            splitSum += expenseSplit.getAmount();
            if(expenseSplit.getUser().equals(paidBy)) {
                paidByIncluded = true;
            }
        }
        if(!paidByIncluded) {
            throw new IllegalArgumentException("PaidBy user must be part of splits");
        }
        if(Math.abs(splitSum - amount) > EPSILON) {
            throw new IllegalArgumentException("Split total must be equal to the expense amount");
        }

        this.id = id;
        this.description = description;
        this.amount = amount;
        this.paidBy = paidBy;
        this.splitType = splitType;
        this.splits = List.copyOf(splits);
        this.expenseDate = expenseDate;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public User getPaidBy() {
        return paidBy;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public List<ExpenseSplit> getSplits() {
        return Collections.unmodifiableList(splits);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof Expense expense))
            return false;
        return Objects.equals(expense.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // TODO : Check for duplicates in User
    // User A -> 30
    // User A -> 70

}
