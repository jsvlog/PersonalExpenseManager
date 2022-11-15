package com.personal.simpleexpensetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.personal.simpleexpensetracker.R;
import com.personal.simpleexpensetracker.models.AddExpenseModel;
import com.personal.simpleexpensetracker.models.Category;

public class ExpenseRecyAdapter extends FirebaseRecyclerAdapter<AddExpenseModel,ExpenseRecyAdapter.expenseViewholder> {
    private DatabaseReference budgetRef;
    private FirebaseAuth mAuth;
    private Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ExpenseRecyAdapter(@NonNull FirebaseRecyclerOptions<AddExpenseModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull expenseViewholder holder, int position, @NonNull AddExpenseModel model) {
        holder.date.setText(model.getDate());
        holder.category.setText(model.getCategory());
        holder.amount.setText(String.valueOf(model.getAmount()));
    }

    @NonNull
    @Override
    public expenseViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_expense,parent,false);
        return new expenseViewholder(v);
    }

    public class expenseViewholder extends RecyclerView.ViewHolder {
        TextView date, category, amount;
        public expenseViewholder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateRecyDisplay);
            category = itemView.findViewById(R.id.categoryRecyDisplay);
            amount = itemView.findViewById(R.id.amountRecyDisplay);
        }
    }
}
