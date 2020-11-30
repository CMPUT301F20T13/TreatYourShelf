package com.cmput301f20t13.treatyourshelf.ui.RequestDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListLiveData;
import com.cmput301f20t13.treatyourshelf.ui.RequestList.RequestListRepository;

import java.util.List;

/**
 * the viewmodel that holds the methods used in the RequestDetailsFragment
 */
public class RequestDetailsViewModel extends ViewModel {

    private final RequestDetailsRepository repository = new RequestDetailsRepository();
    RequestDetailsLiveData liveData = null;
    MutableLiveData<String> ownerScannedIsbn = new MutableLiveData<>();
    MutableLiveData<String> borrowerScannedIsbn = new MutableLiveData<>();

    /**
     *
     * @param isbn
     * @param requester
     * @param owner
     * @return
     */
    public LiveData<Request> getRequestLiveData(String isbn, String requester, String owner) {
        liveData = repository.getRequestLiveData(isbn, requester, owner);
        return liveData;
    }

    /**
     *
     * @return
     */
    public LiveData<Request> getRequest() {
        return liveData.request;
    }

    /**
     *
     * @param isbn
     * @param status
     */
    public void updateBookStatusByIsbn(String isbn, String status){
        repository.updateBookStatusByIsbn(isbn, status);
    }

    /**
     *
     * @param isbn
     * @param requester
     * @param status
     */
    public void updateRequestStatusByIsbn(String isbn, String requester, String status){
        //repository.updateBookStatus();
    }

    /**
     *
     * @param isbn
     * @param requester
     */
    public void updateBookBorrower(String isbn, String requester){
        repository.updateBookBorrowerByIsbn(isbn, requester);
    }

    /**
     *
     * @param scannedIsbn
     */
    public void setOwnerScannedIsbn(String scannedIsbn) {
        this.ownerScannedIsbn.postValue(scannedIsbn);
    }

    /**
     *
     * @param scannedIsbn
     */
    public void setBorrowerScannedIsbn(String scannedIsbn) {
        this.ownerScannedIsbn.postValue(scannedIsbn);
    }

    /**
     *  resets the ownerScannedIsbn MutableLiveData and borrowerScannedIsbn MutableLiveData
     */
    public void clearState() {
        ownerScannedIsbn.setValue(null);
        borrowerScannedIsbn.setValue(null);
    }
}