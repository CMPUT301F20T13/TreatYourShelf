package com.cmput301f20t13.treatyourshelf.ui.BookSearch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * the viewmodel used by the BookSearchFragment, contains the methods called by the BooksearchFragment
 * for searching
 */
public class BookSearchViewModel extends ViewModel {

    private final BookSearchRepository repository = new BookSearchRepository();
    BookSearchLiveData liveData = null;
    MutableLiveData<List<Book>> liveBookList = new MutableLiveData<>();
    private List<Book> allBooks;

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public void setAllBooks(List<Book> allBooks) {
        this.allBooks = allBooks;
    }

    /**
     * calls the repository function that provides the searching, updates
     * the MutableLiveData liveBookList with the results
     *
     * @param keyword (String): user entered keyword
     * @param owner   (String): the username of the current user
     */
    public void getBookSearch(String keyword, String owner) {
        //failed here
        liveBookList.setValue(repository.searchBooks(keyword, owner));
    }

    public void searchBooks(String keyword) {
        if (allBooks != null) {
            List<Book> filteredList = allBooks.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
            List<Book> filteredList2 = allBooks.stream()
                    .filter(book -> book.getIsbn().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
            List<Book> filteredList3 = allBooks.stream()
                    .filter(book -> book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
            List<Book> tempList1 = Stream.concat(filteredList.stream(), filteredList2.stream())
                    .collect(Collectors.toList());
            List<Book> tempList2 = Stream.concat(tempList1.stream(), filteredList3.stream())
                    .collect(Collectors.toList());
            liveBookList.setValue(tempList2);
        }
    }

    public LiveData<List<Book>> getAllBooksLiveData() {
        liveData = repository.getAllBooksLiveData();
        return liveData;
    }

    public LiveData<List<Book>> getBookList() {
        return liveData.bookList;
    }
}
