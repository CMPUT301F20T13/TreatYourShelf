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

    Boolean ownerScannedCheck = false;
    Boolean borrowerScannedCheck = false;
    MutableLiveData<String> ownBorrowedScannedIsbn = new MutableLiveData<>();
    MutableLiveData<String> borBorrowedScannedIsbn = new MutableLiveData<>();
    MutableLiveData<String> ownReturnedScannedIsbn = new MutableLiveData<>();
    MutableLiveData<String> borReturnedScannedIsbn = new MutableLiveData<>();

    /**
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
     * @return
     */
    public LiveData<Request> getRequest() {
        return liveData.request;
    }

    /**
     * @param isbn
     * @param status
     */
    public void updateBookStatusByIsbn(String isbn, String status) {
        repository.updateBookStatusByIsbn(isbn, status);
    }

    /**
     * @param isbn
     * @param requester
     * @param status
     */
    public void updateRequestStatusByIsbn(String isbn, String requester, String status) {
        repository.updateRequestStatusByIsbn(isbn, requester, status);
    }

    /**
     * @param isbn
     * @param requester
     */
    public void updateBookBorrower(String isbn, String requester) {
        repository.updateBookBorrowerByIsbn(isbn, requester);
    }


    public Boolean getOwnerScannedCheck() {
        return ownerScannedCheck;
    }

    public void resetOwnerScannedCheck() {
        this.ownerScannedCheck = false;
    }

    public Boolean getBorrowerScannedCheck() {
        return borrowerScannedCheck;
    }

    public void resetBorrowerScannedCheck() {
        this.borrowerScannedCheck = false;
    }

    public void setOwnBorrowedScannedIsbn(String scannedIsbn) {
        this.ownBorrowedScannedIsbn.setValue(scannedIsbn);
        this.ownerScannedCheck = true;
    }

    public void setBorBorrowedScannedIsbn(String scannedIsbn) {
        this.borBorrowedScannedIsbn.setValue(scannedIsbn);
        this.borrowerScannedCheck = true;
    }

    public void setOwnReturnedScannedIsbn(String scannedIsbn) {
        this.ownReturnedScannedIsbn.setValue(scannedIsbn);
        this.ownerScannedCheck = true;
    }

    public void setBorReturnedScannedIsbn(String scannedIsbn) {
        this.borReturnedScannedIsbn.setValue(scannedIsbn);
        this.borrowerScannedCheck = true;

    }

    /**
     * resets the ownerScannedIsbn MutableLiveData and borrowerScannedIsbn MutableLiveData
     */
    public void clearState() {
        ownBorrowedScannedIsbn.setValue(null);
        borBorrowedScannedIsbn.setValue(null);
        ownReturnedScannedIsbn.setValue(null);
        borReturnedScannedIsbn.setValue(null);
    }

    public void setLocation(String isbn, String requester, String coordinates) {
        repository.setLocation(isbn, requester, coordinates);
    }


}