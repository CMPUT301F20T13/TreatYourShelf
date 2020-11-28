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

public class AddBookViewModel extends AndroidViewModel {
    private final AddBookRepository repository = new AddBookRepository();
    MutableLiveData<String> scannedIsbn = new MutableLiveData<>();
    AddBookLiveData liveData = null;

    private int prevSelectedPosition = -1;
    MutableLiveData<List<ImageFilePathSelector>> galleryImagesLiveData = new MutableLiveData<>();
    MutableLiveData<List<ImageFilePathSelector>> selectedImages = new MutableLiveData<>();
    MutableLiveData<ImageFilePathSelector> selectedImage = new MutableLiveData<>();

    public AddBookViewModel(@NonNull Application application) {
        super(application);
        selectedImages.setValue(new ArrayList<>());
    }


    public AddBookLiveData getBookBYIsbn(String isbn) {
        liveData = repository.getBookByIsbnLiveData(isbn);
        return liveData;
    }

    public LiveData<Book> getBook() {
        return liveData.book;
    }

    public void addBook(Book book) {
        uploadImages(book, 0);

    }

    public void deleteBook(String isbn) {
        repository.deleteBook(isbn);
    }

    public void editBook(Book book) {
        uploadImages(book, 1);

    }

    public void clearState() {
        scannedIsbn.setValue(null);
        selectedImage.setValue(null);
        selectedImages.setValue(new ArrayList<>());
    }

    public void setScannedIsbn(String scannedIsbn) {
        this.scannedIsbn.setValue(scannedIsbn);
    }

    public void setSelectedImage(ImageFilePathSelector imageFilePathSelector) {
        selectedImage.setValue(imageFilePathSelector);
    }

    public void addSelectedImageToAddBook() {
        if (selectedImage.getValue() != null) {
            List<ImageFilePathSelector> selectedImagesTempList = selectedImages.getValue();
            selectedImagesTempList.add(selectedImage.getValue());
            selectedImages.setValue(selectedImagesTempList);
            selectedImage.setValue(null);
        }
    }

    public void uploadImages(Book book, int category) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        Collection<UploadTask> tasksList = new ArrayList<>();
        for (ImageFilePathSelector image : Objects.requireNonNull(selectedImages.getValue()))
            if (image.getImageFilePath() != null) {

                StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                UploadTask uploadTask = ref.putFile(image.getImageFilePath());
                tasksList.add(uploadTask);
            }
        Task<Void> imageUploadTaskList = Tasks.whenAll(tasksList);
        imageUploadTaskList.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    Collection<Task<Uri>> tasksList2 = new ArrayList<>();
                    List<String> imageUrls = new ArrayList<>();
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
                                    repository.editBook(book);
                                }
                            }
                        }
                    });

                }
            }
        });
    }

    public void resetSelectedImages() {
        prevSelectedPosition = -1;
        selectedImage.setValue(null);
    }


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

    public void getGalleryImages() {
        TaskRunner taskRunner = new TaskRunner();
        taskRunner.executeAsync(new LoadGalleryImagesTask(getApplication().getApplicationContext()), (data) -> {
            galleryImagesLiveData.setValue(data);
        });
    }
}
