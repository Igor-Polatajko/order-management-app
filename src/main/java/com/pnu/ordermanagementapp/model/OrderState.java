package com.pnu.ordermanagementapp.model;

public enum OrderState {
    PENDING, RESOLVED, CANCELLED;

    static public OrderState fetch(String state) {
        try {
            return OrderState.valueOf(state);
        } catch (Exception ex) {
            return PENDING;
        }
    }
}
