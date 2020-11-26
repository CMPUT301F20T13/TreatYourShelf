package com.cmput301f20t13.treatyourshelf.ui.RequestDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListLiveData;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListRepository;

import java.util.List;

public class RequestDetailsViewModel extends ViewModel {

    private final RequestDetailsRepository repository = new RequestDetailsRepository();
    RequestDetailsLiveData liveData = null;


    public LiveData<Request> getRequestLiveData(String isbn, String requester, String owner) {
        liveData = repository.getRequestLiveData(isbn, requester, owner);
        return liveData;
    }

    public LiveData<Request> getRequest() {
        return liveData.request;
    }

    public void updateBookStatus(String isbn, String status){
        repository.updateBookStatusByIsbn(isbn, status);
    }

    public void updateRequestStatus(String requester, String status){
        //repository.updateBookStatus();
    }

    public void updateBookBorrower(String isbn, String requester){
        repository.updateBookBorrowerByIsbn(isbn, requester);
    }



}