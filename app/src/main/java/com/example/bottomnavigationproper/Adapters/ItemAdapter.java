//package com.example.bottomnavigationproper.Adapters;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.bottomnavigationproper.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
//
//    String stat;
//    private List<String> results;
//    private List<String> selectedItems = new ArrayList<>();
//    private OnNoteListener onNoteListener;
//    private OnMultiSelectModeListener multiSelectModeListener;
//    private boolean isMultiSelectMode;
//
//
//    public void setItemClickListener(OnNoteListener listener) {
//        this.onNoteListener = listener;
//    }
//
//    public void setMultiSelectModeListener(OnMultiSelectModeListener listener) {
//        this.multiSelectModeListener = listener;
//    }
//
//    public boolean isMultiSelectMode(){
//        return isMultiSelectMode;
//    }
//
//    public void toggleMultiSelectMode() {
//        isMultiSelectMode = !isMultiSelectMode;
//        notifyDataSetChanged();
//
//        if (isMultiSelectMode) {
//            multiSelectModeListener.onMultiSelectModeEnabled();
//        } else {
//            multiSelectModeListener.onMultiSelectModeDisabled();
//            selectedItems.clear();
//        }
//    }
//
//    public void toggleItemSelection(String item, int position) {
//        if (selectedItems.contains(item)) {
//            selectedItems.remove(item);
//        } else {
//            selectedItems.add(item);
//        }
//        notifyItemChanged(position);
//        multiSelectModeListener.onItemSelectedCountChanged(selectedItems.size());
//    }
//
//    public List<String> getSelectedItems(){
//        return selectedItems;
//    }
//
//    public void clearSelectedItems() {
//        selectedItems.clear();
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fav_stat_item, parent, false);
//
//        return new ViewHolder(itemView, onNoteListener);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        stat = results.get(position);
//
//        holder.nameTV.setText(stat);
//    }
//    public void moveItem(int fromPosition, int toPosition) {
//        String item = results.remove(fromPosition);
//        results.add(toPosition, item);
//        notifyItemMoved(fromPosition, toPosition);
//    }
//
//    @Override
//    public int getItemCount() {
//        return results.size();
//    }
//
//    public void setResults(List<String> results){
//        this.results = results;
//        this.selectedItems.clear();
//    }
////    public List<String> getSelected(){
////        return selected;
////    }
//
//
//    // Constructor, onCreateViewHolder, and onBindViewHolder methods
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView nameTV;
//
//        OnNoteListener onNoteListener;
//
//        public ViewHolder(View itemView, OnNoteListener onNoteListener) {
//            super(itemView);
//            nameTV = itemView.findViewById(R.id.stat_name_tv);
//            this.onNoteListener = onNoteListener;
//
//            onNoteListener.onGetSelected(stat);
//        }
//    }
//    public interface OnNoteListener {
//        void onItemClick(String item, int position);
//    }
//    public interface OnMultiSelectModeListener {
//        void onMultiSelectModeEnabled();
//        void onMultiSelectModeDisabled();
//        void onItemSelectedCountChanged(int count);
//    }
//}
