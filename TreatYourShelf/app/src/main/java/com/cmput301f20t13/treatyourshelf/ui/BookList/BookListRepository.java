package com.cmput301f20t13.treatyourshelf.ui.BookList;

import com.cmput301f20t13.treatyourshelf.ui.BookList.BookListLiveData;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookListRepository {
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public BookListLiveData getFirestoreLiveData() {
        DocumentReference documentReference = firebaseFirestore.collection("books").document("all_books");
        return new BookListLiveData(documentReference);
    }
}
