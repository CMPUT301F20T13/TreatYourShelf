package com.cmput301f20t13.treatyourshelf.ui.AddEditBook;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cmput301f20t13.treatyourshelf.data.Book;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * the viewmodel used by the AddBooksFragment
 */
public class AddBookViewModel extends AndroidViewModel {
    private final AddBookRepository repository = new AddBookRepository();
    MutableLiveData<String> scannedIsbn = new MutableLiveData<>();
    AddBookLiveData liveData = null;

    private int prevSelectedPosition = -1;
    MutableLiveData<List<ImageFilePathSelector>> galleryImagesLiveData = new MutableLiveData<>();
    MutableLiveData<List<ImageFilePathSelector>> selectedImages = new MutableLiveData<>();
    MutableLiveData<ImageFilePathSelector> selectedImage = new MutableLiveData<>();

    /**
     * sets the application
     * @param application the current application
     */
    public AddBookViewModel(@NonNull Application application) {
        super(application);
        selectedImages.setValue(new ArrayList<>());
    }

    /**
     * returns books based on the provided isbn
     * @param isbn the provided isbn
     * @return the livedata that contains the results
     */
    public AddBookLiveData getBookBYIsbn(String isbn) {
        liveData = repository.getBookByIsbnLiveData(isbn);
        return liveData;
    }

    /**
     * returns the MutableLiveData attached to the livedata object
     * @return
     */
    public LiveData<Book> getBook() {
        return liveData.book;
    }

    /**
     * adds a book to the database using the uploadImages method
     * @param book the book to add
     */
    public void addBook(Book book) {
        uploadImages(book, 0, null);

    }

    /**
     * deletes a book from the database based on isbn using the repository function
     * @param isbn the provided isbn
     */
    public void deleteBook(String isbn) {
        repository.deleteBook(isbn);
    }

    /**
     * edits a book in the database using the repository function based on isbn
     * @param book the book object containing the updated information
     * @param oldIsbn the isbn of the book
     */
    public void editBook(Book book, String oldIsbn) {
        uploadImages(book, 1, oldIsbn);

    }

    /**
     * deletes a user selected image from the list of images
     * @param position the position of the image
     */
    public void deleteImage(int position) {
        List<ImageFilePathSelector> tempSelectedImages = selectedImages.getValue();
        tempSelectedImages.remove(position);
        selectedImages.setValue(tempSelectedImages);
    }

    /**
     * for the camera scanner, clears the scannedIsbn, the selected image and the list of images
     */
    public void clearState() {
        scannedIsbn.setValue(null);
        selectedImage.setValue(null);
        selectedImages.setValue(new ArrayList<>());
    }

    /**
     * sets the scanned isbn
     * @param scannedIsbn the scanned isbn
     */
    public void setScannedIsbn(String scannedIsbn) {
        this.scannedIsbn.setValue(scannedIsbn);
    }

    /**
     * sets the selected image
     * @param imageFilePathSelector
     */
    public void setSelectedImage(ImageFilePathSelector imageFilePathSelector) {
        selectedImage.setValue(imageFilePathSelector);
    }

    /**
     * adds a selected image to the current book
     */
    public void addSelectedImageToAddBook() {
        if (selectedImage.getValue() != null) {
            List<ImageFilePathSelector> selectedImagesTempList = selectedImages.getValue();
            selectedImagesTempList.add(selectedImage.getValue());
            selectedImages.setValue(selectedImagesTempList);
            selectedImage.setValue(null);
        }
    }

    /**
     * uploads the images to a book and calls the repository function that adds a book to the
     * database
     * @param book the information of the book to add
     * @param category
     * @param oldisbn
     */
    public void uploadImages(Book book, int category, String oldisbn) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        List<String> imageUrls = new ArrayList<>();
        Collection<UploadTask> tasksList = new ArrayList<>();
        for (ImageFilePathSelector image : Objects.requireNonNull(selectedImages.getValue()))
            if (image.getImageFilePath() != null && !image.isImageUrl()) {

                StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                UploadTask uploadTask = ref.putFile(image.getImageFilePath());
                tasksList.add(uploadTask);
            } else if (image.getImageFilePath() != null && image.isImageUrl()) {
                imageUrls.add(image.getImageFilePath().toString());
            }
        Task<Void> imageUploadTaskList = Tasks.whenAll(tasksList);
        imageUploadTaskList.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    Collection<Task<Uri>> tasksList2 = new ArrayList<>();
                    for (UploadTask completedTask : tasksList) {

                        UploadTask.TaskSnapshot result = completedTask.getResult();
                        tasksList2.add(Objects.requireNonNull(Objects.requireNonNull(result.getMetadata()).getReference()).getDownloadUrl());
                    }
                    Tasks.whenAll(tasksList2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                for (Task<Uri> imageUrl : tasksList2) {
                                    imageUrls.add(Objects.requireNonNull(imageUrl.getResult()).toString());
                                }
                                book.setImageUrls(imageUrls);
                                if (category == 0) {
                                    repository.addBook(book);
                                } else {
                                    repository.editBook(book, oldisbn);
                                }
                            }
                        }
                    });

                }
            }
        });
    }

    /**
     * resets the selected images
     */
    public void resetSelectedImages() {
        prevSelectedPosition = -1;
        selectedImage.setValue(null);
    }

    /**
     * updates the list of images with a selected item
     * @param position the position of the selected item
     */
    public void updateListWithSelectedItem(int position) {

        if (selectedImage.getValue() != null) {

            List<ImageFilePathSelector> tempList = galleryImagesLiveData.getValue();

            if (prevSelectedPosition == -1) {
                // Clicked on an image with no previous image clicked

                // No other image was chosen before
                ImageFilePathSelector currSelectedImage = galleryImagesLiveData.getValue().get(position);
                currSelectedImage.toggleSelectedState();
                tempList.set(position, currSelectedImage);
                galleryImagesLiveData.setValue(tempList);
                prevSelectedPosition = position;

            } else if (prevSelectedPosition == position) {
                // Clicked on the previous selected image, this should deselect it
                ImageFilePathSelector currSelectedImage = galleryImagesLiveData.getValue().get(position);
                currSelectedImage.toggleSelectedState();
                tempList.set(position, currSelectedImage);
                galleryImagesLiveData.setValue(tempList);
                selectedImage.setValue(null);
                prevSelectedPosition = -1;
            } else {
                //Clicked on an image when another image is already selected.
                ImageFilePathSelector lastSelectedImage = tempList.get(prevSelectedPosition);
                lastSelectedImage.toggleSelectedState(); // set the image to no longer selected
                tempList.set(prevSelectedPosition, lastSelectedImage);

                ImageFilePathSelector currSelectedImage = tempList.get(position);
                currSelectedImage.toggleSelectedState();
                tempList.set(position, currSelectedImage);
                galleryImagesLiveData.setValue(tempList);
                prevSelectedPosition = position;

            }
        }
    }

    /**
     * gets a gallery of images
     */
    public void getGalleryImages() {
        TaskRunner taskRunner = new TaskRunner();
        taskRunner.executeAsync(new LoadGalleryImagesTask(getApplication().getApplicationContext()), (data) -> {
            galleryImagesLiveData.setValue(data);
        });
    }
}
