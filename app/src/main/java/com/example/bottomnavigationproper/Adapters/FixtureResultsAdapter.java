package com.example.bottomnavigationproper.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.Models.Fixture;
import com.example.bottomnavigationproper.R;

import java.util.ArrayList;
import java.util.List;


public class FixtureResultsAdapter extends RecyclerView.Adapter<FixtureResultsAdapter.FixtureResultsHolder> {
    private List<Fixture> results = new ArrayList<>();

    @NonNull
    @Override
    public FixtureResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fixture_date_row, parent, false);

        return new FixtureResultsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureResultsHolder holder, int position) {
        Fixture fixture = results.get(position);

        holder.fixtureTV.setText(fixture.getFixtureDate().toString());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Fixture> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class FixtureResultsHolder extends RecyclerView.ViewHolder {
        private TextView fixtureTV;


        public FixtureResultsHolder(@NonNull View itemView) {
            super(itemView);

            fixtureTV = itemView.findViewById(R.id.tvFixtureDate);
           }
    }
}
