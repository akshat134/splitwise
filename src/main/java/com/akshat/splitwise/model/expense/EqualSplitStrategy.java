package com.akshat.splitwise.model.expense;

import com.akshat.splitwise.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EqualSplitStrategy implements SplitStrategy {

    @Override
    public List<ExpenseSplit> calculateSplits(
            double totalAmount,
            User paidBy,
            Map<User, Double> input
    ) {
        int n = input.size();
        if(n == 0) {
            throw new IllegalArgumentException("No users to split among");
        }

        double eachShare = totalAmount / n;
        List<ExpenseSplit> splits = new ArrayList<>();

        for(User user : input.keySet()) {
            splits.add(new ExpenseSplit(user, eachShare));
        }

        return splits;
    }
}
