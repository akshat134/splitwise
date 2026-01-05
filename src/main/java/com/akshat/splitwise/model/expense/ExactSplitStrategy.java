package com.akshat.splitwise.model.expense;

import com.akshat.splitwise.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExactSplitStrategy implements SplitStrategy {
    private static final double EPSILON = 0.01;

    @Override
    public List<ExpenseSplit> calculateSplits(
            double totalAmount,
            User paidBy,
            Map<User, Double> input
    ) {
        double sum = 0.0;
        for(Double value : input.values()) {
            sum += value;
        }
        if(Math.abs(totalAmount - sum) > EPSILON) {
            throw new IllegalArgumentException("Exact split amounts must sum to total amount");
        }

        List<ExpenseSplit> splits = new ArrayList<>();
        for(Map.Entry<User, Double> entry : input.entrySet()) {
            splits.add(new ExpenseSplit(entry.getKey(), entry.getValue()));
        }

        return splits;
    }
}
