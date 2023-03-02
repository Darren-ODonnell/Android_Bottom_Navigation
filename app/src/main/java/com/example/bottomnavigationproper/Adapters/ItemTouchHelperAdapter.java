package com.example.bottomnavigationproper.Adapters;

import java.util.List;

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
    List<String> getItems();

    void setItems(List<String> items);
}