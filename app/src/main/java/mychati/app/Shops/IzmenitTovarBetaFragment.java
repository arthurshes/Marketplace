package mychati.app.Shops;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mychati.app.Client.MyListener;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IzmenitTovarBetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IzmenitTovarBetaFragment extends Fragment {
private static IzmenitTovarBetaFragment instance=null;
View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RoundedImageView imagenewtovar1,imagenewtovar2,imagenewtovar3,imagenewtovar4,imagenewtovar5;
    private Uri uri,uri2,uri3,uri4,uri5;
    private static final int GALLERYPICK = 1;
    private static final int GALLERYPICK3 = 3;
    private ProgressDialog progressDialog;
    private ShopProfileBetaFragment zakazShopopFragment;
    private AppCompatButton buttonAddt;
    private ImageView imagearrowa;
    private Transformation transformation;
    private static final int GALLERYPICK2 = 2;
    private EditText editTextNamei2,editTextPricei3,editTextOpisi4;
    private static final int GALLERYPICK4 = 4;
    private String DownloadUrl,DownloadUrl2,DownloadUrl3,DownloadUrl4,DownloadUrl5;
    private List<MyListener> myListenerList=new ArrayList<>();
    private static final int GALLERYPICK5 = 5;
    private ValueEventListener valueEventListenert;
    private DatabaseReference databaseReference;

    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    public IzmenitTovarBetaFragment() {
        // Required empty public constructor
    }
public static IzmenitTovarBetaFragment newInstance(){
        if (instance==null){
            instance=new IzmenitTovarBetaFragment();
            return instance;
        }else{
            return instance;
        }
}
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IzmenitTovarBetaFragment.
     */
    // TODO: Rename and change types and number of parameters



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_izmenit_tovar_beta, container, false);
        mAuth=FirebaseAuth.getInstance();
        imagearrowa=view.findViewById(R.id.imagearrowa);
        imagenewtovar1=view.findViewById(R.id.  imagenewtovar1i);
        editTextOpisi4=view.findViewById(R.id.editTextOpisi4i);
        transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(6).oval(false).build();
        editTextPricei3=view.findViewById(R.id.editTextPricei3i);
        editTextNamei2=view.findViewById(R.id.editTextNamei2i);

        imagenewtovar2=view.findViewById(R.id.  imagenewtovar2i);
        imagenewtovar3=view.findViewById(R.id.  imagenewtovar3i);
        imagenewtovar4=view.findViewById(R.id.  imagenewtovar4i);
        progressDialog=new ProgressDialog(getContext());
        buttonAddt=view.findViewById(R.id.buttonAddt1);
        imagenewtovar5=view.findViewById(R.id.  imagenewtovar5i);
        storageReference= FirebaseStorage.getInstance().getReference().child("TovarImage");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Tovars");
        imagearrowa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         zakazShopopFragment=ShopProfileBetaFragment.newInstance();
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_shop,zakazShopopFragment).commit();

            }
        });
        valueEventListenert=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    editTextNamei2.setText(snapshot.child("TovarName").getValue().toString());
                    editTextPricei3.setText(snapshot.child("TovarPrice").getValue().toString());
                    editTextOpisi4.setText(snapshot.child("TovarOpisanie").getValue().toString());

                    if (snapshot.child("TovarImage1").getValue()!=null) {
                        Picasso.get().load(snapshot.child("TovarImage1").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(imagenewtovar1, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(snapshot.child("TovarImage1").getValue().toString()).transform(transformation).into(imagenewtovar1);
                            }
                        });
                    }
                    if (snapshot.child("TovarImage2").getValue()!=null) {
                        Picasso.get().load(snapshot.child("TovarImage2").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(imagenewtovar2, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(snapshot.child("TovarImage2").getValue().toString()).transform(transformation).into(imagenewtovar2);
                            }
                        });
                    }if (snapshot.child("TovarImage3").getValue()!=null) {
                        Picasso.get().load(snapshot.child("TovarImage3").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(imagenewtovar3, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(snapshot.child("TovarImage3").getValue().toString()).transform(transformation).into(imagenewtovar3);
                            }
                        });
                    }if (snapshot.child("TovarImage4").getValue()!=null) {
                        Picasso.get().load(snapshot.child("TovarImage4").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(imagenewtovar4, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(snapshot.child("TovarImage4").getValue().toString()).transform(transformation).into(imagenewtovar4);
                            }
                        });
                    }if (snapshot.child("TovarImage5").getValue()!=null) {
                        Picasso.get().load(snapshot.child("TovarImage5").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(imagenewtovar5, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(snapshot.child("TovarImage5").getValue().toString()).transform(transformation).into(imagenewtovar5);
                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query= databaseReference.child(getArguments().getString("ProdId"));
        query .addValueEventListener(valueEventListenert);
        myListenerList.add(new MyListener(valueEventListenert,query));
        buttonAddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                load();
            }
        });
        imagenewtovar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opnGallery1();
            }
        });

        imagenewtovar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opnGallery2();
            }
        });

        imagenewtovar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opnGallery3();
            }
        });

        imagenewtovar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opnGallery4();
            }
        });

        imagenewtovar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opnGallery5();


            }
        });

       return view;
    }
    private void load() {
        progressDialog.setTitle("загрузка данных....");
        progressDialog.setMessage("Пожалуйста подождите");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (uri!=null) {


            StorageReference filePath = storageReference.child(uri.getLastPathSegment() + ".jpg");
            byte[] bytes = new byte[0];
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                bytes = byteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            final UploadTask uploadTask = filePath.putBytes(bytes);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(getContext(), "error" + message, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            DownloadUrl = filePath.getDownloadUrl().toString();

                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                DownloadUrl = task.getResult().toString();

                                if (uri2 != null) {
                                    Image2();
                                }
                                else if (uri3!=null){
                                    Image3();
                                }else if (uri4!=null){
                                    Image4();
                                }else if (uri5!=null){
                                    Image5();
                                }else {
                                    loaddata();
                                }

                            }


                        }
                    });
                }
            });
        }else if (uri2!=null){
            Image2();
        }else if (uri3!=null){
            Image3();
        }else if (uri4!=null){
            Image4();
        }else if (uri5!=null){
            Image5();
        }else{
            Toast.makeText(getContext(), "Нет фото", Toast.LENGTH_SHORT).show();
        }

    }
    private void Image5() {
        StorageReference filePath = storageReference.child(uri5.getLastPathSegment() + ".jpg");
        byte[] bytes=new byte[0];
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri5);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final UploadTask uploadTask = filePath.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getContext(), "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadUrl5 = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadUrl5 = task.getResult().toString();



                            loaddata();


                        }


                    }
                });
            }
        });
    }

    private void Image4() {
        StorageReference filePath = storageReference.child(uri4.getLastPathSegment() + ".jpg");
        byte[] bytes=new byte[0];
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri4);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final UploadTask uploadTask = filePath.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getContext(), "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadUrl4 = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadUrl4 = task.getResult().toString();



                            if (uri5!=null){
                                Image5();
                            }else {
                                loaddata();
                            }
                        }


                    }
                });
            }
        });
    }  private void Image3() {
        StorageReference filePath = storageReference.child(uri3.getLastPathSegment() + ".jpg");
        byte[] bytes=new byte[0];
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri3);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final UploadTask uploadTask = filePath.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getContext(), "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadUrl3 = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadUrl3 = task.getResult().toString();



                            if (uri4!=null){
                                Image4();
                            }else if (uri5!=null){
                                Image5();
                            }else {
                                loaddata();
                            }
                        }


                    }
                });
            }
        });
    }

    private void Image2() {

        StorageReference filePath = storageReference.child(uri2.getLastPathSegment() + ".jpg");
        byte[] bytes=new byte[0];
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri2);
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            bytes=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final UploadTask uploadTask = filePath.putBytes(bytes);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(getContext(), "error" + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadUrl2 = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadUrl2 = task.getResult().toString();



                            if (uri3!=null){
                                Image3();
                            }else if (uri4!=null){
                                Image4();
                            }else if (uri5!=null){
                                Image5();
                            }else {
                                loaddata();
                            }
                        }


                    }
                });
            }
        });
    }

    private void loaddata() {
        Toast.makeText(getContext(), "Изображения загружены", Toast.LENGTH_SHORT).show();
        HashMap<String,Object> hashMap=new HashMap<>();
        if (DownloadUrl!=null){
            hashMap.put("TovarStatus","1");
            hashMap.put("TovarImage1",DownloadUrl);
        }if (DownloadUrl2!=null){
            hashMap.put("TovarStatus","1");
            hashMap.put("TovarImage2",DownloadUrl2);
        }if (DownloadUrl3!=null){
            hashMap.put("TovarStatus","1");
            hashMap.put("TovarImage3",DownloadUrl3);
        }if (DownloadUrl4!=null){
            hashMap.put("TovarStatus","1");
            hashMap.put("TovarImage4",DownloadUrl4);
        }if (DownloadUrl5!=null){
            hashMap.put("TovarStatus","1");
            hashMap.put("TovarImage5",DownloadUrl5);
        }
        if (TextUtils.isEmpty(editTextNamei2.getText().toString())){

        }else if (TextUtils.isEmpty(editTextOpisi4.getText().toString())){

        }else if (TextUtils.isEmpty(editTextPricei3.getText().toString())){

        }else{
            hashMap.put("TovarName",editTextNamei2.getText().toString());
            hashMap.put("ShopUid",mAuth.getCurrentUser().getUid());
            hashMap.put("TovarOpisanie",editTextOpisi4.getText().toString());
            hashMap.put("TovarPrice",editTextPricei3.getText().toString());
            databaseReference.child(getArguments().getString("ProdId")).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getContext(), "Товар изменен", Toast.LENGTH_SHORT).show();
                        zakazShopopFragment=ShopProfileBetaFragment.newInstance();
                        FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                        fm.replace(R.id.frame_shop,zakazShopopFragment).commit();
                    }
                }
            });
        }
    }

    private void opnGallery1() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);
    }
    private void opnGallery2() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK2);
    }
    private void opnGallery3() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK3);
    }
    private void opnGallery4() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK4);
    }
    private void opnGallery5() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK5);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            uri=data.getData();
            imagenewtovar1.setImageURI(uri);
        }
        if (requestCode == GALLERYPICK2 && resultCode == RESULT_OK && data != null) {
            uri2=data.getData();
            imagenewtovar2.setImageURI(uri2);
        }
        if (requestCode == GALLERYPICK3 && resultCode == RESULT_OK && data != null) {
            uri3=data.getData();
            imagenewtovar3.setImageURI(uri3);
        }
        if (requestCode == GALLERYPICK4 && resultCode == RESULT_OK && data != null) {
            uri4=data.getData();
            imagenewtovar4.setImageURI(uri4);
        }
        if (requestCode == GALLERYPICK5 && resultCode == RESULT_OK && data != null) {
            uri5=data.getData();
            imagenewtovar5.setImageURI(uri5);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener:myListenerList){
            listener.unsubscribe();
        }
        myListenerList.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        buttonAddt=null;
        editTextNamei2=null;
        editTextOpisi4=null;
        view=null;
        transformation=null;
        imagearrowa=null;
        progressDialog=null;
        mAuth=null;
        editTextPricei3=null;
        imagenewtovar2=null;
        imagenewtovar1=null;
        imagenewtovar3=null;
        imagenewtovar4=null;
        imagenewtovar5=null;
    }
}