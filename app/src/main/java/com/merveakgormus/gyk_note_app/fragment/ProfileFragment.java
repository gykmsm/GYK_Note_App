package com.merveakgormus.gyk_note_app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.merveakgormus.gyk_note_app.R;
import com.merveakgormus.gyk_note_app.activities.AddPhotoActivity;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {


    private static final int IMAGE_REQUEST = 111;

    FirebaseStorage firebaseStorage;
    FirebaseAuth mAuth;
    Uri filePath;
    StorageReference storageRef;
    ImageView imgProfilePicture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getReference();

        imgProfilePicture = view.findViewById(R.id.imgProfilePicture);

        showPhoto();

        imgProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddPhotoActivity.class));
                //SelectPhoto();
                //SavePhotoInDatabase();
            }
        });

        return view;
    }

    private void showPhoto(){
        StorageReference storageRef = firebaseStorage.getReference();
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                //imgProfilePicture.setImageURI(uri);
                Picasso.with(getContext()).load(uri).fit().centerCrop().into(imgProfilePicture);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //dismissProgressDialog();
                Toast.makeText(getContext(), "Fotoğraf Yükleme işlemi başarısız", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void SelectPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Fotoğraf Seçiniz"), IMAGE_REQUEST);
    }

    private void SavePhotoInDatabase(){
        if (filePath != null) {
            StorageReference storageRef = firebaseStorage.getReference();
            storageRef.child("userprofilephoto").putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Fotoğraf başarılı bir şekilde kaydedildi.", Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(), "Fotoğraf Kaydedilemedi", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().startActivityForResult(data, requestCode);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

        }

    }
}
