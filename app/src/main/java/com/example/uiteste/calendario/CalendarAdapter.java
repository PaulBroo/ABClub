package com.example.uiteste.calendario;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uiteste.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private ArrayList<String> DiasComTreinos;
    private ArrayList<String> DiasComJogos;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener,
                           ArrayList<String> DiasComTreinos, ArrayList<String> DiasComJogos)
    {
        this.days = days;
        this.onItemListener = onItemListener;
        this.DiasComTreinos = DiasComTreinos;
        this.DiasComJogos = DiasComJogos;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if(date == null)
            holder.dayOfMonth.setText("");
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.selectedDate))
                holder.parentView.setBackgroundColor(Color.LTGRAY);

            if (DiaTemJogo(date) && DiaTemTreino(date))
                holder.dayOfMonth.setTextColor(Color.RED);

            else if(DiaTemJogo(date))
                holder.dayOfMonth.setTextColor(Color.CYAN);

            else if(DiaTemTreino(date))
                holder.dayOfMonth.setTextColor(Color.GREEN);
        }
    }

    public boolean DiaTemJogo(LocalDate date) {
        String data = CalendarUtils.formattedDate(date);

        for (String dia: DiasComJogos) {
            if (dia.equals(data))
                return true;
        }
        return false;
    }

    public boolean DiaTemTreino(LocalDate date) {
        String data = CalendarUtils.formattedDate(date);

        for (String dia: DiasComTreinos) {
            if (dia.equals(data))
                return true;
        }
        return false;
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}

