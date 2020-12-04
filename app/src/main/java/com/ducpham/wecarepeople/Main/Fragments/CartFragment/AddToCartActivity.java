package com.ducpham.wecarepeople.Main.Fragments.CartFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ducpham.wecarepeople.Main.Data.Data;
import com.ducpham.wecarepeople.R;
import com.ducpham.wecarepeople.databinding.ActivityAddToCartBinding;
import com.ducpham.wecarepeople.model.CartItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.parceler.Parcels;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddToCartActivity extends AppCompatActivity {
    final static String TAG = "AddToCartActivity";
    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;

    ActivityAddToCartBinding binding;
    File photoFile;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Uri imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddToCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mStorageRef = FirebaseStorage.getInstance().getReference().child("images/");
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
      //  data = new Data(CartFragment.this);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
        binding.takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicture();
            }
        });
    }

    void addItem(){
        if(!existCondition()){
            Toast.makeText(this,"Please fill in every fields",Toast.LENGTH_SHORT).show();
        }
        else{
            final StorageReference imageName = mStorageRef.child("image"+imageData.getLastPathSegment());
            imageName.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Map<String,Object> cartItem = new HashMap<>();
                            cartItem.put("name",binding.name.getText().toString());
                            cartItem.put("category",binding.category.getText().toString());
                            cartItem.put("description",binding.description.getText().toString());
                            cartItem.put("situation",binding.situtaion.getText().toString());
                            cartItem.put("count",Integer.parseInt(binding.count.getText().toString()));
                            cartItem.put("url",url);
                            cartItem.put("userId",mAuth.getCurrentUser().getUid());
                           // cartItemService.createCartItem(db,mAuth,cartItem);
                            Intent intent = new Intent();
                            Parcelable wrapped = Parcels.wrap(cartItem);
                            intent.putExtra("item",wrapped);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.d(TAG,"cant add item");
                        }
                    });
        }
    }



    void getPicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(UUID.randomUUID().toString());
        imageData = Uri.fromFile(photoFile);
        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG,"got image");
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                binding.itemImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    public boolean existCondition(){
        String name = binding.name.getText().toString();
        String description = binding.description.getText().toString();
        int count = Integer.parseInt(binding.count.getText().toString());
        String situation = binding.situtaion.getText().toString();
        String category = binding.category.getText().toString();
        if(name.isEmpty() || description.isEmpty() || situation.isEmpty() || category.isEmpty() || count < 0){
            return false;
        }
        return true;
    }
}