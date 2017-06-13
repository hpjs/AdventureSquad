package com.adventuresquad.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.adventuresquad.api.interfaces.RetrieveDataRequest;
import com.adventuresquad.model.Adventure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    private AdventureApi mAdventureApi;

    public static final String ADVENTURE_IMAGE_STORE = "/adventures";
    public static final String ADVENTURE_IMAGE_TITLE = "adventureimage.jpg";
    public static final String DEBUG_STORAGE_API = "storage_api";

    public StorageApi() {
        mStorage = FirebaseStorage.getInstance();
        mImageStore = mStorage.getReference("images");
        //mProfileImageStore = mStorage.getReference("images/users/ProfileActivity");
        //mUploadsImageStore = mStorage.getReference("images/users")
    }

    public StorageApi(AdventureApi api) {
        this();
        mAdventureApi = api;
        //mProfileImageStore = mStorage.getReference("images/users/ProfileActivity");
        //mUploadsImageStore = mStorage.getReference("images/users")
    }

    public String getAdventureImagePath(String adventureId) {
        return ADVENTURE_IMAGE_STORE + "/" + adventureId + "/" + ADVENTURE_IMAGE_TITLE;
    }

    /**
     * Stores an image related to a specific adventure
     * @param pathToImage The image to upload
     * @param adventure The adventure that this image is for
     */
    public void storeAdventureImage(Uri pathToImage, final Adventure adventure) {

        String filePath = getAdventureImagePath(adventure.getAdventureId());

        //Prepare success listener for when storage is complete
        OnSuccessListener successListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                @SuppressWarnings("VisibleForTests") Uri imageUri = taskSnapshot.getDownloadUrl();
                String imagePath = "";
                if (imageUri != null) { imagePath = imageUri.toString(); }

                //Store this download URL on a specific Adventure in the database
                adventure.setAdventureImageUri(imagePath);
                mAdventureApi.putAdventure(adventure, adventure.getAdventureId());

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
     * Gets a formatted URL to download an image
     * @param adventureId The ID of the adventure to get the photo for
     * @param callback A listener which is called when the complete listener is done
     */
    public void retrieveAdventureImageUri(String adventureId, final RetrieveDataRequest<Uri> callback) {
        String filePath = getAdventureImagePath(adventureId);
        StorageReference imageRef = mImageStore.child(filePath);
        imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    callback.onRetrieveData(downloadUri);
                } else {
                    callback.onRetrieveDataFail(task.getException());
                }
            }
        });
    }
}
