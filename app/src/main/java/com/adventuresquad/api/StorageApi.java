package com.adventuresquad.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.model.Adventure;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Api to access storage in the database / datastore
 * Created by Harrison on 23/05/2017.
 */
public class StorageApi {
    private FirebaseStorage mStorage;
    private StorageReference mImageStore;

    private AdventureApi mApi;

    public static final String ADVENTURE_IMAGE_STORE = "/adventures";
    public static final String DEBUG_STORAGE_API = "storage_api";

    private StorageApi() {
        mStorage = FirebaseStorage.getInstance();
        mImageStore = mStorage.getReference("images");
        //mProfileImageStore = mStorage.getReference("images/users/profile");
        //mUploadsImageStore = mStorage.getReference("images/users")
    }

    public StorageApi(AdventureApi api) {
        this();
        mApi = api;
        //mProfileImageStore = mStorage.getReference("images/users/profile");
        //mUploadsImageStore = mStorage.getReference("images/users")
    }

    /**
     * Stores an image related to a specific adventure
     * @param pathToImage The image to upload
     * @param adventureId The adventure that this image is for
     * @param imageTitle The title of this image
     */
    public void storeAdventureImage(Uri pathToImage, final Adventure adventure, String imageTitle) {
        String filePath = ADVENTURE_IMAGE_STORE + "/" + adventure.getAdventureId() + "/" + imageTitle;

        //Set up success listener for when complete
        OnSuccessListener successListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                @SuppressWarnings("VisibleForTests") String imageUri = taskSnapshot.getDownloadUrl().toString();

                //Store this download URL on a specific Adventure in the database
                adventure.setAdventureImageUri(imageUri);
                mApi.putAdventure(adventure, adventure.getAdventureId());

                Log.d(DEBUG_STORAGE_API, "Image uploaded successfully, for adventure " + adventure.getAdventureId());
            }
        };

        OnFailureListener failureListener = new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(DEBUG_STORAGE_API, "IMAGE UPLOAD FAILED :(");
            }
        };

        storeImage(pathToImage, filePath, successListener, failureListener);
    }

    /**
     * Stores an image in Firebase
     * @param pathToImage The URI to the image to upload (alternatively give the file directly?)
     * @param filePath The filepath under /images that this image should be posted to (include leading slash)
     */
    public void storeImage(Uri pathToImage, String filePath,
                           OnSuccessListener successListener,
                           OnFailureListener failureListener) {

        //Needs to be a unique ID - e.g. using the adventure's ID
        StorageReference imageRef = mImageStore.child(filePath);

        imageRef.putFile(pathToImage)
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    /**
     * Adds an image download URL to a specific adventure
     */

    /**
     * Retrieves a given image from firebase
     */
    public void retrieveImage() {
        /*
        File localFile = File.createTempFile("images", "jpg");
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });*/
        //StorageReference load = getImage(id);
    }
}
