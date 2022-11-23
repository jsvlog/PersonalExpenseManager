package com.personal.simpleexpensetracker.adapters;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personal.simpleexpensetracker.R;
import com.personal.simpleexpensetracker.models.AddExpenseModel;
import com.personal.simpleexpensetracker.models.Category;

import java.util.Calendar;

public class ExpenseRecyAdapter extends FirebaseRecyclerAdapter<AddExpenseModel,ExpenseRecyAdapter.expenseViewholder> {
    private String idKey = "";
    private FirebaseAuth mAuth;
    private Context context;
    private Category category;
    private String nCategory;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatabaseReference reference;
    private String onlineUserId;


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ExpenseRecyAdapter(@NonNull FirebaseRecyclerOptions<AddExpenseModel> options, Context context, Category category) {
        super(options);
        this.context = context;
        this.category = category;
    }

    @Override
    protected void onBindViewHolder(@NonNull expenseViewholder holder, int position, @NonNull AddExpenseModel model) {
//        if (position %2 == 1){
//            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//        }else{
//            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
//        }
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
                final Button editBtn, deleteBtn;
                deleteBtn = myView.findViewById(R.id.deleteBtn);
                tCategory = myView.findViewById(R.id.showCategory);
                tDate = myView.findViewById(R.id.showDate);
                tAmount = myView.findViewById(R.id.showAmount);
                tNotes = myView.findViewById(R.id.showNotes);
                editBtn = myView.findViewById(R.id.editBtn);

                tCategory.setText(model.getCategory());
                tDate.setText(model.getDate());
                tAmount.setText(String.valueOf(model.getAmount()));
                tNotes.setText(model.getNotes());

                idKey = getRef(holder.getAbsoluteAdapterPosition()).getKey();
                mAuth = FirebaseAuth.getInstance();
                onlineUserId = mAuth.getCurrentUser().getUid();
                reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(onlineUserId);

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        reference.child(idKey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(myView.getContext(), "successfully deleted ", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }else{
                                Toast.makeText(myView.getContext(), "error:" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                            }
                        });
                    }
                });

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder myDialog = new AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View myView = inflater.inflate(R.layout.expense_update, null);
                        myDialog.setView(myView);

                        AlertDialog alertDialog2 = myDialog.create();
                        alertDialog2.show();
                        alertDialog2.setCancelable(true);

                        final Spinner spinner2;
                        final EditText amount, notes;
                        final TextView udate;
                        final Button cancelBtn, addBtn;

                        spinner2 = myView.findViewById(R.id.spinnerInput);
                        udate = myView.findViewById(R.id.datePicker);
                        amount = myView.findViewById(R.id.amount);
                        notes = myView.findViewById(R.id.notes);
                        cancelBtn = myView.findViewById(R.id.cancelBtn);
                        addBtn = myView.findViewById(R.id.addBtn);


                        //this is for date picker in expense fragment
                        udate.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onClick(View view) {
                                Calendar cal = Calendar.getInstance();
                                int year = cal.get(Calendar.YEAR);
                                int month = cal.get(Calendar.MONTH);
                                int day = cal.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog dialog = new DatePickerDialog(
                                        myView.getContext(),
                                        android.R.style.Theme_Holo_Dialog_MinWidth,
                                        mDateSetListener,
                                        year,month,day);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
                                dialog.show();
                            }
                        });
                        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month = month +1;
                                String date = month + "/" + day + "/" + year;
                                udate.setText(date);

                            }
                        };

                        SpinnerAdapter spinnerAdapter2 = new SpinnerAdapter(myView.getContext(),R.layout.spinner_layout,category.getCategoryList());
                        spinner2.setAdapter(spinnerAdapter2);

                        //this is to preselect the spinner
                        int items = category.getCategoryList().size();
                        for (int i = 0;i<items;i++ ){
                            String r = String.valueOf(category.getCategoryList().get(i).getCategory());
                            if (r.equals(model.getCategory())){
                                spinner2.setSelection(i);
                                Log.d("spinner", "onClick: " + i);

                            }
                        }

                        // i use this to put string category in realtime database and not the object
                        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                nCategory = String.valueOf(category.getCategoryList().get(i).getCategory());

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        udate.setText(model.getDate());
                        amount.setText(String.valueOf(model.getAmount()));
                        notes.setText(model.getNotes());

                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog2.dismiss();
                            }
                        });

                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                 String sDate = udate.getText().toString();
                                 String uAmount = amount.getText().toString();
                                 String sNotes = notes.getText().toString();

                                if(uAmount.isEmpty()){
                                    amount.setError("please input amount");
                                }else if(sNotes.isEmpty()){
                                    notes.setError("please input notes");
                                }else{
                                    AddExpenseModel addExpenseModel = new AddExpenseModel(sNotes,sDate,nCategory,idKey,Integer.parseInt(uAmount));




                                    reference.child(idKey).setValue(addExpenseModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(myView.getContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                                                alertDialog2.dismiss();
                                                alertDialog.dismiss();
                                            } else {
                                                Toast.makeText(myView.getContext(), "error" + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                            Log.d("keyy", "onComplete: " + sNotes + " " + idKey + " " );
                                        }
                                    });
                                }


                            }
                        });
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
