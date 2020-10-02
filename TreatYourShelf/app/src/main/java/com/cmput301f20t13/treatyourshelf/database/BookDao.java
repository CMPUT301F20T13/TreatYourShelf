package com.cmput301f20t13.treatyourshelf.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Query("Select * from book")
    LiveData<List<Book>> getAllBooks();

    @Insert
    void insert(Book book);
}
