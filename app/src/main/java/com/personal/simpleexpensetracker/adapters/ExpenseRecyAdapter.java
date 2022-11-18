package com.personal.simpleexpensetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.personal.simpleexpensetracker.R;
import com.personal.simpleexpensetracker.models.AddExpenseModel;

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View myView = inflater.inflate(R.layout.expense_view_item, null);
                myDialog.setView(myView);

                AlertDialog alertDialog = myDialog.create();
                alertDialog.show();
                alertDialog.setCancelable(true);

                final TextView tCategory, tDate, tAmount, tNotes;
                final Button editBtn;
                tCategory = myView.findViewById(R.id.showCategory);
                tDate = myView.findViewById(R.id.showDate);
                tAmount = myView.findViewById(R.id.showAmount);
                tNotes = myView.findViewById(R.id.showNotes);
                editBtn = myView.findViewById(R.id.editBtn);

                tCategory.setText(model.getCategory());
                tDate.setText(model.getDate());
                tAmount.setText(String.valueOf(model.getAmount()));
                tNotes.setText(model.getNotes());

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View myView = inflater.inflate(R.layout.expense_input, null);
                        myDialog.setView(myView);

                        AlertDialog alertDialog2 = myDialog.create();
                        alertDialog2.show();
                        alertDialog2.setCancelable(true);




                    }
                });

            }
        });
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
