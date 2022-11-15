package com.personal.simpleexpensetracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.personal.simpleexpensetracker.adapters.ExpenseRecyAdapter;
import com.personal.simpleexpensetracker.adapters.SpinnerAdapter;
import com.personal.simpleexpensetracker.models.AddExpenseModel;
import com.personal.simpleexpensetracker.models.Category;

import java.util.ArrayList;
import java.util.Calendar;


public class ExpensesFragment extends Fragment {

    FloatingActionButton fab;
    ArrayList<String> categories = new ArrayList<>();
    DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView dateText;
    private Button cancelBtn, addBtn;
    private EditText amount, notes;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String onlineUserId;
    private String sCategory;
    private RecyclerView recyclerView;
    private Context gcontext;
    private ExpenseRecyAdapter expenseRecyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expenses, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //this is to initialize the category for spinner
        Category.initCategory();

        fab = view.findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.expenseRecyclerview);




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        mAuth = FirebaseAuth.getInstance();
        onlineUserId = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Expenses").child(onlineUserId);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpenses();
            }
        });




    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        gcontext = context;
    }

    @Override
    public void onStart() {
        FirebaseRecyclerOptions<AddExpenseModel> options =new FirebaseRecyclerOptions.Builder<AddExpenseModel>()
                .setQuery(reference,AddExpenseModel.class).build();
        expenseRecyAdapter = new ExpenseRecyAdapter(options,gcontext);

        expenseRecyAdapter.startListening();
        expenseRecyAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(expenseRecyAdapter);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        expenseRecyAdapter.stopListening();
    }

    //this is triggered when add button expenses was clicked
    private void addExpenses() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView = inflater.inflate(R.layout.expense_input, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.show();
        dialog.setCancelable(false);

        final Spinner spinner = myView.findViewById(R.id.spinnerInput);
        dateText = myView.findViewById(R.id.datePicker);
        addBtn = myView.findViewById(R.id.addBtn);
        cancelBtn = myView.findViewById(R.id.cancelBtn);
        amount = myView.findViewById(R.id.amount);
        notes = myView.findViewById(R.id.notes);
        Category category = new Category();



        //this is for spinner category
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(myView.getContext(),R.layout.spinner_layout, category.getCategoryList());
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sCategory = String.valueOf(category.getCategoryList().get(i).getCategory());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //this is for date picker in expense fragment
        dateText.setOnClickListener(new View.OnClickListener() {
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
                dateText.setText(date);

            }
        };

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sAmount = amount.getText().toString();
                String sNotes = notes.getText().toString();
                String sDate = dateText.getText().toString();

                String id = reference.push().getKey();


               AddExpenseModel addExpenseModel = new AddExpenseModel(sNotes,sDate,sCategory,id,Integer.parseInt(sAmount));

                if(sDate.isEmpty()){
                    Toast.makeText(myView.getContext(), "Please select Date", Toast.LENGTH_SHORT).show();
                }else if(sAmount.isEmpty()){
                    amount.setError("please input amount");
                }else if(sNotes.isEmpty()){
                    notes.setError("please input note");
                }else {

                reference.child(id).setValue(addExpenseModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(myView.getContext(), "Expense add successful", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(myView.getContext(), "Expense add error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                }


            }
        });



    }
}