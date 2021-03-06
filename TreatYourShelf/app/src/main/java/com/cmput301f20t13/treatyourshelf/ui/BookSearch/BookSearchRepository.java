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

/**
 * the repository object that contains the methods used by the BookSearchViewModel
 */
public class BookSearchRepository {
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    /**
     * returns available books from the database based on the provided isbn
     * @param isbn the provided isbn
     * @return BookSearchLiveData that holds the result of the query
     */
    public BookSearchLiveData getBookByIsbnLiveData(String isbn) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("isbn", isbn)
                .whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    /**
     * returns available books from the database based on the provided owner
     * @param owner the provided owner
     * @return BookSearchLiveData that holds the result of the query
     */
    public BookSearchLiveData getBookByOwnerLiveData(String owner) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("owner", owner)
                .whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    /**
     * returns available books from the database based on the provided title
     * @param title the provided title
     * @return BookSearchLiveData that holds the result of the query
     */
    public BookSearchLiveData getBookByTitleLiveData(String title) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("title", title)
                .whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }

    /**
     * this function searches for a keyword in the titles, author's and isbn's of
     * all available books that do not belong to the current user
     * @param keyword (String): the keyword used for the current search
     * @param owner (String): the username of the current user
     * @return BookSearchLiveData that holds the result of the query
     */
    public List<Book> searchBooks(String keyword, String owner) {
        Query query = firebaseFirestore.collection("books")
                .whereEqualTo("isbn", keyword).whereNotEqualTo("owner", owner)
                .whereEqualTo("status", "available");
        Query query1 = firebaseFirestore.collection("books")
                .whereEqualTo("title", keyword).whereNotEqualTo("owner", owner)
                .whereEqualTo("status", "available");
        Query query2 = firebaseFirestore.collection("books")
                .whereEqualTo("author", keyword).whereNotEqualTo("owner", owner)
                .whereEqualTo("status", "available");
        Query query3 = firebaseFirestore.collection("books")
                .whereEqualTo("isbn", keyword).whereNotEqualTo("owner", owner)
                .whereEqualTo("status", "requested");
        Query query4 = firebaseFirestore.collection("books")
                .whereEqualTo("title", keyword).whereNotEqualTo("owner", owner)
                .whereEqualTo("status", "requested");
        Query query5 = firebaseFirestore.collection("books")
                .whereEqualTo("author", keyword).whereNotEqualTo("owner", owner)
                .whereEqualTo("status", "requested");
        List<Book> bookListTemp = new ArrayList<>();
        Collection<Task<QuerySnapshot>> tasksList = new ArrayList<>();

        tasksList.add(query.get());
        tasksList.add(query1.get());
        tasksList.add(query2.get());
        tasksList.add(query3.get());
        tasksList.add(query4.get());
        tasksList.add(query5.get());

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

    /**
     * returns all books from the database
     * @return BookSearchLiveData that holds the result of the query
     */
    public BookSearchLiveData getAllBooksLiveData() {
        Query query = firebaseFirestore.collection("books");
                //.whereEqualTo("status", "available");
        return new BookSearchLiveData(query);
    }
}
