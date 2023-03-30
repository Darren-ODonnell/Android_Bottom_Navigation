package com.example.bottomnavigationproper;

import static com.example.bottomnavigationproper.MainActivity.API_KEY;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationproper.APIs.FavouriteStatsSingleton;
import com.example.bottomnavigationproper.APIs.TokenSingleton;
import com.example.bottomnavigationproper.Adapters.DragDropAdapter;

import com.example.bottomnavigationproper.Adapters.ItemTouchHelperAdapter;
import com.example.bottomnavigationproper.Adapters.TargetAdapter;
import com.example.bottomnavigationproper.Models.StatName;
import com.example.bottomnavigationproper.ViewModels.StatViewModel;
import com.example.bottomnavigationproper.databinding.BottomNavBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BottomNavActivity extends AppCompatActivity {
    BottomNavBinding binding;
    List<String> allStats = new ArrayList<>();
    List<String> favStats = new ArrayList<>();
    private DragDropAdapter sourceAdapter;
    private TargetAdapter targetAdapter;
    private RecyclerView sourceRecyclerView;
    private RecyclerView targetRecyclerView;
    private ItemTouchHelper sourceItemTouchHelper;
    private ItemTouchHelper targetItemTouchHelper;

    StatViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder(StrictMode.getVmPolicy())
                .detectLeakedClosableObjects()
                .build());

        setContentView(R.layout.activity_login);
        readFromSharedPreferences();

        viewModel = new ViewModelProvider(this).get(StatViewModel.class);
        viewModel.init();


        onLogin();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favourites) {
            getStatNames();
            return true;
        } else if (id == R.id.action_log_out) {
            SharedPreferences preferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(API_KEY); // replace "my_key" with the key you want to delete
            editor.apply();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private void filterStats(){
//        allStats = allStats.stream()
//                .filter(stat -> !favStats.contains(stat))
//                .collect(Collectors.toList());
//    }

    private void createFavouriteStatsDialog() {
        //Pop up dialog
        //Recycler view: all stats
        //Select multiple



        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View fView = getLayoutInflater().inflate(R.layout.fav_stats_selection, null);

        sourceAdapter = new DragDropAdapter(allStats);
        sourceRecyclerView = fView.findViewById(R.id.left_recycler_view);
        sourceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sourceRecyclerView.setAdapter(sourceAdapter);
        sourceItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(sourceAdapter));
        sourceItemTouchHelper.attachToRecyclerView(sourceRecyclerView);

        targetAdapter = new TargetAdapter(favStats);
        targetRecyclerView = fView.findViewById(R.id.right_recycler_view);
        targetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        targetRecyclerView.setAdapter(targetAdapter);
        targetItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(targetAdapter));
        targetItemTouchHelper.attachToRecyclerView(targetRecyclerView);

        mBuilder.setView(fView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

        fView.findViewById(R.id.fav_stats_back_button).setOnClickListener(v -> {
            List<String> favourites = targetAdapter.getItems();
            favourites.size();
            saveToSharedPreferences(favourites);
            FavouriteStatsSingleton.getInstance().setFavouriteStats(favourites);
            dialog.dismiss();
        });
    }

    private void saveToSharedPreferences(List<String> favourites) {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        Set<String> itemSet = new HashSet<>(favourites);;
        editor.putStringSet("favouriteStats", itemSet);
        // Commit the edits!
        editor.apply();
    }
    private void readFromSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, 0);

        // Retrieve the set of items from shared preferences
        Set<String> itemSet = sharedPreferences.getStringSet("favouriteStats", null);

        // Convert the set back to a list
        if(itemSet != null){
            favStats = new ArrayList<>(itemSet);
        }else
            favStats = new ArrayList<>();
        FavouriteStatsSingleton.getInstance().setFavouriteStats(favStats);
    }

    private void getStatNames() {

        viewModel.getStatNameLiveData().observe(this, new Observer<List<StatName>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<StatName> statNames) {
                allStats = statNames.stream()
                        .map(StatName::getName)
                        .collect(Collectors.toList());

                if(favStats.size() > 0)
                    allStats = allStats.stream()
                            .filter(stat -> !favStats.contains(stat))
                                    .collect(Collectors.toList());
                createFavouriteStatsDialog();
            }
        });
        viewModel.getStatNames();

    }


    private void onLogin(){
        binding = BottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){
                case R.id.homeFragment:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.gameFragment:
                    replaceFragment(new GameFragment());
                    break;
                case R.id.statsFragment:
                    replaceFragment(new StatsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView2,fragment);
        fragmentTransaction.commit();
    }

    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        private ItemTouchHelperAdapter adapter;
        private ItemTouchHelperAdapter adapter2;

        public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
            this.adapter = adapter;
            if(adapter instanceof DragDropAdapter){
                adapter2 = new TargetAdapter(favStats);
            }else if(adapter instanceof TargetAdapter){
                adapter2 = new DragDropAdapter(allStats);
            }
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int i = viewHolder.getAdapterPosition();
            // Get items before it is removed from list
            List<String> leftItems = adapter.getItems();
            String item = leftItems.get(i);
            //remove from list
            adapter.onItemDismiss(i);

            //Add to favourites
            List<String> items = adapter2.getItems();
//            if(items.contains(item)){
//                items.add(item);
//            }
            items.add(item);
            adapter2.setItems(items);

        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }
    }

    @Override
    public void onStop() {
        if(viewModel.getStatNameLiveData() != null)
            viewModel.getStatNameLiveData().removeObservers(this);
        super.onStop();

    }

}



