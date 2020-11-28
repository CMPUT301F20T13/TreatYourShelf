package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class BookSearchRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public BookSearchLiveData getBookByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    public BookSearchLiveData getBookByOwnerLiveData(String owner) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("owner", owner)
                .whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    public BookSearchLiveData getBookByTitleLiveData(String title) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("title", title)
                .whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }
    public List<Book> searchBooks(String keyword) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("isbn", keyword)
                .whereEqualTo("status", "available");
        Query query1 = firebaseFirestore.collection("books")
                .whereEqualTo("title", keyword)
                .whereEqualTo("status", "available");
        Query query2 = firebaseFirestore.collection("books")
                .whereEqualTo("owner", keyword)
                .whereEqualTo("status", "available");
        List<Book> bookListTemp = new ArrayList<>();
        Collection<Task<QuerySnapshot>> tasksList = new ArrayList<>();

        tasksList.add(query.get());

        Task<Void> searchTaskList = Tasks.whenAll(tasksList);

        searchTaskList.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tasksList.forEach(searchList -> {
                    QuerySnapshot documentSnapshots = searchList.getResult();
                    documentSnapshots.forEach(queryDocumentSnapshot -> {
                        Book itemToAdd = new Book();
                        Map<String, Object> bookDetails = queryDocumentSnapshot.getData();
                        itemToAdd.setTitle((String) bookDetails.getOrDefault("title", "default title"));
                        itemToAdd.setAuthor((String) bookDetails.getOrDefault("author", "default author"));
                        itemToAdd.setIsbn((String) bookDetails.getOrDefault("isbn", "default isbn"));
                        itemToAdd.setBorrower((String) bookDetails.getOrDefault("borrower", ""));
                        itemToAdd.setOwner((String) bookDetails.getOrDefault("owner", "default owner"));
                        itemToAdd.setStatus((String) bookDetails.getOrDefault("status", "default status"));
                        bookListTemp.add(itemToAdd);
                    });
                });

            }
        });
        return bookListTemp;
    }

    public BookSearchLiveData getAllBooksLiveData() {
        Query query = firebaseFirestore.collection("books");
                //.whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }
}
