package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f20t13.treatyourshelf.R;
import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.BookList.AllBooksFragmentDirections;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListAdapter;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

/**
 * the adapter that binds to the recyclerview in RequestListFragment
 */
public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.MyViewHolder> {

    private List<Request> requestList;
    private RequestListViewModel requestListViewModel;

    /**
     * constructor for the adapter
     * @param requestList the list of request objects
     * @param requestListViewModel the RequestListViewModel used by the RequestListFragment
     */
    public RequestListAdapter(List<Request> requestList, RequestListViewModel requestListViewModel) {
        this.requestList = requestList;
        this.requestListViewModel = requestListViewModel;
    }

    /**
     * creates the view holder of the adapter and binds the layout to request_list_item.xml
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RequestListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_list_item, parent, false);
        return new RequestListAdapter.MyViewHolder(view);
    }

    /**
     * binds the requests to the viewholder properties
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RequestListAdapter.MyViewHolder holder, int position) {
        holder.requester.setText(requestList.get(position).getRequester());
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

        private final TextView requester;
        private final MaterialCardView requestItem;
        private final ImageView profileImage;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            requester = itemView.findViewById(R.id.profile_username);
            requestItem = itemView.findViewById(R.id.request_list_item_cardview);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }
}

