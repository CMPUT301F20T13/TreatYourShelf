package com.cmput301f20t13.treatyourshelf.ui.navigation_menu;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;


import java.util.List;

/**
 * Recycler view adapter for Naviagtion items
 */
public class NavigationItemAdapter extends RecyclerView.Adapter<NavigationItemAdapter.MyViewHolder> {

    private List<NavigationItem> navigationItemList;
    private Context context;
    private OnNavigationItemClick onNavigationItemClick;

    public interface OnNavigationItemClick {
        public void onClick(NavigationItem navigationItem);
    }

    /**
     * Constructor for item adapter
     *
     * @param navigationItemList    navigation item list
     * @param context               the context needed to get icons.
     * @param onNavigationItemClick interface for item clicks
     */
    public NavigationItemAdapter(List<NavigationItem> navigationItemList, Context context, OnNavigationItemClick onNavigationItemClick) {
        this.navigationItemList = navigationItemList;
        this.context = context;
        this.onNavigationItemClick = onNavigationItemClick;
    }

    @NonNull
    @Override
    public NavigationItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_list_item, parent, false);
        return new NavigationItemAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NavigationItemAdapter.MyViewHolder holder, int position) {
        holder.navigationName.setText(navigationItemList.get(position).getDestinationName());
        holder.navigationIcon.setImageDrawable(ContextCompat.getDrawable(context, navigationItemList.get(position).getIcon()));
        holder.itemView.setOnClickListener(view -> onNavigationItemClick.onClick(navigationItemList.get(position)));
    }

    @Override
    public int getItemCount() {
        return navigationItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView navigationName;
        private ImageView navigationIcon;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            navigationIcon = itemView.findViewById(R.id.navigationIcon);
            navigationName = itemView.findViewById(R.id.navigationName);

        }
    }

    public void setNavigationItemList(List<NavigationItem> navigationItemList) {
        this.navigationItemList = navigationItemList;
        notifyDataSetChanged();
    }

}

