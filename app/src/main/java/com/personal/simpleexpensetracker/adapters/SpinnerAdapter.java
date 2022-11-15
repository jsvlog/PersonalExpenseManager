package com.personal.simpleexpensetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.personal.simpleexpensetracker.R;
import com.personal.simpleexpensetracker.models.Category;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    LayoutInflater layoutInflater;

    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Category> category) {
        super(context, resource, category);

        layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = layoutInflater.inflate(R.layout.spinner_layout,null,true);
        Category category = (Category) getItem(position);
        TextView textView = rowView.findViewById(R.id.spinnerText);
        textView.setText(String.valueOf(category.getCategory()));
        return rowView;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = layoutInflater.inflate(R.layout.spinner_layout,parent,false);
        Category category = (Category) getItem(position);
        TextView textView = convertView.findViewById(R.id.spinnerText);
        textView.setText(category.getCategory());
        return convertView;


    }

}
