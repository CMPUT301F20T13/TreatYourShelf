package com.cmput301f20t13.treatyourshelf.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cmput301f20t13.treatyourshelf.data.Book;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    private static volatile BookDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // creates number of threads for writing to database;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * Creates a single Instance of the room database that is synchronized across multiple threads if it does not exist, else it returns the instance
     *
     * @param context
     * @return The instance of the room database
     */
    static BookDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BookDatabase.class, "book_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
