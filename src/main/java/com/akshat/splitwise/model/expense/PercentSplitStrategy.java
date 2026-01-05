package com.akshat.splitwise.model.expense;

import com.akshat.splitwise.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PercentSplitStrategy implements SplitStrategy{
    private static final double EPSILON = 0.01;

    @Override
    public List<ExpenseSplit> calculateSplits(
            double totalAmount,
            User paidBy,
            Map<User, Double> input
    ) {
        double percentSum = 0.0;
        for(Double value : input.values()) {
            percentSum += value;
        }
        if(Math.abs(percentSum - 100.0) > EPSILON) {
            throw new IllegalArgumentException("Sum of percent splits must be equal to 100");
        }

        List<ExpenseSplit> splits = new ArrayList<>();
        for(Map.Entry<User, Double> entry : input.entrySet()) {
            double amount = (entry.getValue() / 100) * totalAmount;
            splits.add(new ExpenseSplit(entry.getKey(), amount));
        }

        return splits;
    }
}
