package com.akshat.splitwise.model.expense;

import com.akshat.splitwise.model.user.User;

import java.util.List;
import java.util.Map;

public interface SplitStrategy {
    List<ExpenseSplit> calculateSplits(
            double totalAmount,
            User paidBy,
            Map<User, Double> input
    );
}
