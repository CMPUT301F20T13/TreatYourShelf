package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * the adapter that binds to the recyclerview in BorrRequestListFragment
 */
public class BorrRequestedListAdapter extends RecyclerView.Adapter<BorrRequestedListAdapter.MyViewHolder> {

    private List<Request> requestList;
    private final RequestListViewModel requestListViewModel;

    /**
     * constructor for the adapter
     * @param requestList the list of request objects
     * @param requestListViewModel the RequestListViewModel used by the BorrRequestListFragment
     */
    public BorrRequestedListAdapter(List<Request> requestList, RequestListViewModel requestListViewModel) {
        this.requestList = requestList;
        this.requestListViewModel = requestListViewModel;
    }

    /**
     * creates the view holder of the adapter and binds the layout to borr_requested_list_item.xml
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BorrRequestedListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.borr_requested_list_item, parent, false);
        return new BorrRequestedListAdapter.MyViewHolder(view);
    }

    /**
     * binds the requests to the viewholder properties
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull BorrRequestedListAdapter.MyViewHolder holder, int position) {
        holder.owner.setText(requestList.get(position).getOwner());
        holder.title.setText(requestList.get(position).getTitle());
        holder.author.setText(requestList.get(position).getAuthor());
        holder.status.setText(requestList.get(position).getStatus());
        holder.requestItem.setOnClickListener(v -> {
            NavDirections action =
                    RequestListFragmentDirections.actionRequestListFragmentToRequestDetailsFragment()
                            .setISBN(requestList.get(position).getIsbn())
                            .setREQUESTER(requestList.get(position).getRequester())
                            .setOWNER(requestList.get(position).getOwner());
            Navigation.findNavController(v).navigate(action);
        });
    }

    /**
     * returns the number of requests
     * @return the size of the request list
     */
    @Override
    public int getItemCount() {
        return requestList.size();
    }

    /**
     * clears the request list
     */
    public void clear(){
        requestList.clear();
    }

    /**
     * sets the request list
     * @param requestList the provided list of requests
     */
    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
        notifyDataSetChanged();
    }

    /**
     * binds the viewholder properties to the ui elements
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView owner;
        private final TextView title;
        private final TextView author;
        private final TextView status;
        private final MaterialCardView requestItem;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            owner = itemView.findViewById(R.id.profile_username);
            title = itemView.findViewById(R.id.request_title);
            author = itemView.findViewById(R.id.request_author);
            status = itemView.findViewById(R.id.request_status);
            requestItem = itemView.findViewById(R.id.request_list_item_cardview);
        }
    }
}

