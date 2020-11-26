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

    public void removeRequest(String isbn, String owner, String requester){
        repository.removeRequest(isbn, owner, requester);
    }

    public LiveData<List<Request>> getRequestByIsbnLiveData(String isbn) {
        liveData = repository.getRequestByIsbnLiveData(isbn);
        return liveData;
    }

    public LiveData<List<Request>> getRequestByOwnerLiveData(String owner){
        liveData = repository.getRequestByOwnerLiveData(owner);
        return liveData;
    }

    public LiveData<List<Request>> getRequestByIsbnOwnerLiveData(String isbn, String owner){
        liveData = repository.getRequestByIsbnOwnerLiveData(isbn, owner);
        return liveData;
    }

    public LiveData<List<Request>> getRequestByIsbnRequesterLiveData(String isbn, String requester){
        liveData = repository.getRequestByIsbnRequesterLiveData(isbn, requester);
        return liveData;
    }

    public LiveData<List<Request>> getRequestList() {
        return liveData.requestList;
    }

    public void updateStatusByIsbn(String requester, String isbn, String status){
        repository.updateStatusByIsbn(requester, isbn, status);
    }

    public void requestBook(Book book, String requester) {
        repository.addRequest(book,requester);
    }

}
