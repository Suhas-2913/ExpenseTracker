package com.example.expensetracker;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseTableActivity extends AppCompatActivity {
    private List<Expense> expenseList;
    private TableLayout expenseTable;
    private String filename = "expenses.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_table);

        // Initialize UI elements
        expenseTable = findViewById(R.id.expenseTable);

        // Load expenses from file
        expenseList = loadExpenses();

        // Populate table with existing expenses
        for (Expense expense : expenseList) {
            addExpenseToTable(expense);
        }
    }

    private void addExpenseToTable(Expense expense) {
        TableRow row = new TableRow(this);

        TextView dateTextView = new TextView(this);
        dateTextView.setText(expense.getDate());
        dateTextView.setPadding(8, 8, 8, 8);
        row.addView(dateTextView);

        TextView categoryTextView = new TextView(this);
        categoryTextView.setText(expense.getCategory());
        categoryTextView.setPadding(8, 8, 8, 8);
        row.addView(categoryTextView);

        TextView amountTextView = new TextView(this);
        amountTextView.setText(String.valueOf(expense.getAmount()));
        amountTextView.setPadding(8, 8, 8, 8);
        row.addView(amountTextView);

        expenseTable.addView(row);
    }

    private List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();

        try {
            File file = new File(getFilesDir(), filename);
            FileReader fr = new FileReader(file.getAbsoluteFile());
            BufferedReader br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String date = parts[0];
                    String category = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    Expense expense = new Expense(date, category, amount);
                    expenses.add(expense);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to load expenses", Toast.LENGTH_SHORT).show();
        }

        return expenses;
    }
}
