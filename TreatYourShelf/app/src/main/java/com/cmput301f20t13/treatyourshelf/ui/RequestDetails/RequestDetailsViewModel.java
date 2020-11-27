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
    MutableLiveData<String> ownerScannedIsbn = new MutableLiveData<>();
    MutableLiveData<String> borrowerScannedIsbn = new MutableLiveData<>();

    public LiveData<Request> getRequestLiveData(String isbn, String requester, String owner) {
        liveData = repository.getRequestLiveData(isbn, requester, owner);
        return liveData;
    }

    public LiveData<Request> getRequest() {
        return liveData.request;
    }

    public void updateBookStatusByIsbn(String isbn, String status){
        repository.updateBookStatusByIsbn(isbn, status);
    }

    public void updateRequestStatusByIsbn(String isbn, String requester, String status){
        //repository.updateBookStatus();
    }

    public void updateBookBorrower(String isbn, String requester){
        repository.updateBookBorrowerByIsbn(isbn, requester);
    }

    public void setOwnerScannedIsbn(String scannedIsbn) {
        this.ownerScannedIsbn.postValue(scannedIsbn);
    }

    public void setBorrowerScannedIsbn(String scannedIsbn) {
        this.ownerScannedIsbn.postValue(scannedIsbn);
    }

    public void clearState() {
        ownerScannedIsbn.setValue(null);
        borrowerScannedIsbn.setValue(null);
    }
}