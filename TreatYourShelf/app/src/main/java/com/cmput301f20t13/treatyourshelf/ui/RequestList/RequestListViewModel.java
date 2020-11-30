package com.cmput301f20t13.treatyourshelf.ui.RequestList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.cmput301f20t13.treatyourshelf.data.Request;

import java.util.List;

/**
 * the viewmodel used in both BorrRequestedListFragment and RequestListFragment
 */
public class RequestListViewModel extends ViewModel {

    private final RequestListRepository repository = new RequestListRepository();
    RequestListLiveData liveData = null;

    /**
     * removes a request from the database based on the isbn, owner and requester using the
     * repository function
     * @param isbn the provided isbn
     * @param owner the provided owner
     * @param requester the provided requester
     */
    public void removeRequest(String isbn, String owner, String requester){
        repository.removeRequest(isbn, owner, requester);
    }

    /**
     * returns requests from the database based on the isbn, using the repository method
     * @param isbn the provided isbn
     * @return the livedata object that contains the result of the query
     */
    public LiveData<List<Request>> getRequestByIsbnLiveData(String isbn) {
        liveData = repository.getRequestByIsbnLiveData(isbn);
        return liveData;
    }

    /**
     * returns requests from the database based on the owner using the repository method
     * @param owner the provided owner
     * @return the livedata object that contains the result of the query
     */
    public LiveData<List<Request>> getRequestByOwnerLiveData(String owner){
        liveData = repository.getRequestByOwnerLiveData(owner);
        return liveData;
    }

    /**
     * returns requests from the database based on the isbn and owner using the repository
     * method
     * @param isbn the provided isbn
     * @param owner the provided owner
     * @return the livedata object that contains the result of the query
     */
    public LiveData<List<Request>> getRequestByIsbnOwnerLiveData(String isbn, String owner){
        liveData = repository.getRequestByIsbnOwnerLiveData(isbn, owner);
        return liveData;
    }

    /**
     * returns requests from the database based on the isbn and requester using the repository
     * method
     * @param isbn the provided isbn
     * @param requester the provided requester
     * @return the livedata object that contains the result of the query
     */
    public LiveData<List<Request>> getRequestByIsbnRequesterLiveData(String isbn, String requester){
        liveData = repository.getRequestByIsbnRequesterLiveData(isbn, requester);
        return liveData;
    }

    /**
     * returns requests from the database based on the requester using the repository
     * method
     * @param requester the provided request
     * @return the livedata object that contains the result of the query
     */
    public LiveData<List<Request>> getRequestByRequesterLiveData(String requester){
        liveData = repository.getRequestByRequesterLiveData(requester);
        return liveData;
    }

    /**
     * returns the MutableLiveData bound to the livedata that contains the result
     * @return the MutableLiveData bound to the livedata that contains the result
     */
    public LiveData<List<Request>> getRequestList() {
        return liveData.requestList;
    }

    /**
     * updates the status of a request based on the requester and isbn
     * @param requester the provided requester
     * @param isbn the provided isbn
     * @param status the status to update the request to
     */
    public void updateStatusByIsbn(String requester, String isbn, String status){
        repository.updateStatusByIsbn(requester, isbn, status);
    }

    /**
     * adds a new request to the database
     * @param book the book associated with the request
     * @param requester the one who is requesting the book
     */
    public void requestBook(Book book, String requester) {
        repository.addRequest(book,requester);
    }

}
