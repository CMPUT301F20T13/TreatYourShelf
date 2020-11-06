package com.cmput301f20t13.treatyourshelf.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.ui.navigation_menu.NavigationItem;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.MyViewHolder> {

    private List<SettingsItem> settingsItemList;
    private Context context;
    private OnSettingsItemClick onSettingsItemClick;

    public interface OnSettingsItemClick {
        public void onClick(SettingsItem settingsItem);
    }

    public SettingsAdapter(List<SettingsItem> settingsItemList, Context context, OnSettingsItemClick onSettingsItemClick) {
        this.settingsItemList = settingsItemList;
        this.context = context;
        this.onSettingsItemClick = onSettingsItemClick;
    }

    @NonNull
    @Override
    public SettingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_list_item, parent, false);
        return new SettingsAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.MyViewHolder holder, int position) {
        holder.settingsDescription.setText(settingsItemList.get(position).getSettingDescription());
        holder.settingsIcon.setImageDrawable(ContextCompat.getDrawable(context, settingsItemList.get(position).getIcon()));
        holder.itemView.setOnClickListener(view -> onSettingsItemClick.onClick(settingsItemList.get(position)));
    }

    @Override
    public int getItemCount() {
        return settingsItemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView settingsDescription;
        private ImageView settingsIcon;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            settingsIcon = itemView.findViewById(R.id.settingsIcon);
            settingsDescription = itemView.findViewById(R.id.settingDescription);

        }
    }

    public void setSettingsItemList(List<SettingsItem> settingsItemList) {
        this.settingsItemList = settingsItemList;
        notifyDataSetChanged();
    }

}
