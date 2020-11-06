package com.cmput301f20t13.treatyourshelf.ui.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.SettingsCategory;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.CategoryViewHolder> {

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView title;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.settings_section_icon);
            title = itemView.findViewById(R.id.settings_section_title);
        }
    }

    private List<SettingsCategory> categories;

    public SettingsAdapter(List<SettingsCategory> list) {
        categories = list;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_section, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        // holder.icon.setImageDrawable();
        holder.title.setText(categories.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategoryList(List<SettingsCategory> list) {
        categories = list;
        notifyDataSetChanged();
    }

}
