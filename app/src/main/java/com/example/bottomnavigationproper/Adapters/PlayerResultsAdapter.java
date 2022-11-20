//package com.example.bottomnavigationproper.utils;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bottomnavigationproper.Models.Player;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.example.bottomnavigationproper.R;
//
//
//public class PlayerResultsAdapter extends RecyclerView.Adapter<PlayerResultsAdapter.PlayerResultsHolder> {
//    private List<Player> results = new ArrayList<>();
//
//    @NonNull
//    @Override
//    public PlayerResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.player_item, parent, false);
//
//        return new PlayerResultsHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PlayerResultsHolder holder, int position) {
//        Player player = results.get(position);
//
//        Log.d("playerData", player.getFirstname());
//
//        holder.firstNameTextView.setText(player.getFirstname());
//        holder.lastNameTextView.setText(player.getLastname());
//
//        /**
//         * For image binding
//         */
////        if (player.getImageLinks() != null) {
////            String imageUrl = player..getImageLinks().getSmallThumbnail()
////                    .replace("http://", "https://");
////
////            Glide.with(holder.itemView)
////                    .load(imageUrl)
////                    .into(holder.smallThumbnailImageView);
////        }
///**
//    For list of something within player
// */
////        StringBuilder authors = new StringBuilder();
////
////        for(String s: player.getVolumeInfo().getAuthors())
////            authors.append(s);
////
////        holder.authorsTextView.setText(authors.toString());
//    }
//
//    @Override
//    public int getItemCount() {
//        return results.size();
//    }
//
//    public void setResults(List<Player> results) {
//        this.results = results;
//        notifyDataSetChanged();
//    }
//
//    class PlayerResultsHolder extends RecyclerView.ViewHolder {
//        private TextView firstNameTextView;
//        private TextView lastNameTextView;
//
//
//        public PlayerResultsHolder(@NonNull View itemView) {
//            super(itemView);
//
//            firstNameTextView = itemView.findViewById(R.id.player_first_name);
//            lastNameTextView = itemView.findViewById(R.id.player_last_name);
//           }
//    }
//}
