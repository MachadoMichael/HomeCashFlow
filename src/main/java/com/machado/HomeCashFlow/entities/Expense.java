package com.machado.HomeCashFlow.entities;

import java.time.Instant;

public class Expense {
    private String name;
    private String note;
    private Instant date;
    private String user;
    private String group;
    private ExpenseCategory category;

    public Expense(String name, String note, Instant date, String user, String group, ExpenseCategory category) {
        if(name != null && !name.equals("") && date != null && category != null){
            this.name = name;
            this.note = note;
            this.date = date;
            this.user = user;
            this.group = group;
            this.category = category;
        }
    }

    public String getName() {
        return name;
    }

    public String getNote() {
        return note;
    }

    public Instant getDate() {
        return date;
    }

    public String getUser() {
        return user;
    }

    public String getGroup() {
        return group;
    }

    public ExpenseCategory getCategory() {
        return category;
    }
}
