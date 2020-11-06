package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListLiveData;
import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListRepository;

import java.util.List;

public class RequestListViewModel extends ViewModel {

    private final RequestListRepository repository = new RequestListRepository();
    RequestListLiveData liveData = null;
    public MutableLiveData<Request> selectedRequest = new MutableLiveData<>();

    public LiveData<List<Request>> getRequestByIdLiveData(String bookId) {
        liveData = repository.getRequestByIdLiveData(bookId);
        return liveData;
    }


    public LiveData<List<Request>> getRequestList() {
        return liveData.requestList;
    }

    public void select(Request request){selectedRequest.setValue(request);}

    public LiveData<Request> getSelected() {return selectedRequest;}
}
