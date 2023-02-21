package mychati.app.Shops;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.squareup.picasso.Transformation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mychati.app.Client.MyListener;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTovarBetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTovarBetaFragment extends Fragment {
private static NewTovarBetaFragment instance=null;
    // TODO: Rename parameter arguments, choose names that match
    private HashMap<String,Object> hashMap=new HashMap<>();
    private ShopProfileBetaFragment shopProfileBetaFragment;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RoundedImageView imagenewtovar1,imagenewtovar2,imagenewtovar3,imagenewtovar4,imagenewtovar5;
    private Uri uri,uri2,uri3,uri4,uri5;
    private static final int GALLERYPICK = 1;
    private static final int GALLERYPICK3 = 3;
    private ProgressDialog progressDialog;
    private ZakazShopopFragment zakazShopopFragment;
    private BottomNavigationView bottomNavigationView;
    private AppCompatButton buttonAddt;
    private DatabaseReference shop;
    private ImageView imagearrow77;
    private Transformation transformation;
    private static final int GALLERYPICK2 = 2;
    private EditText editTextNamei2,editTextPricei3,editTextOpisi4;
    private static final int GALLERYPICK4 = 4;
    private String DownloadUrl,DownloadUrl2,DownloadUrl3,DownloadUrl4,DownloadUrl5,saveCurrentDate,saveCurrentTime,ProductRandomKey,MyRanKey,MyName,MyLogo;
    private List<MyListener> myListenerList=new ArrayList<>();
    private static final int GALLERYPICK5 = 5;
    private ValueEventListener valueEventListenert;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
View view;
    public NewTovarBetaFragment() {
        // Required empty public constructor
    }
public static NewTovarBetaFragment newInstance(){
        if (instance==null){
            instance=new NewTovarBetaFragment();
            return instance;
        }else{
            return instance;
        }
}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment NewTovarBetaFragment.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_new_tovar_beta, container, false);

        imagearrow77=view.findViewById(R.id.imagearrow77);
        storageReference= FirebaseStorage.getInstance().getReference().child("TovarImage");
        shop= FirebaseDatabase.getInstance().getReference().child("shops");
        editTextOpisi4=view.findViewById(R.id.editTextOpisi4);
        editTextPricei3=view.findViewById(R.id.editTextPricei3);
        editTextNamei2=view.findViewById(R.id.editTextNamei2);
        progressDialog=new ProgressDialog(getContext());
        buttonAddt=view.findViewById(R.id.buttonAddt);
        bottomNavigationView=getActivity().findViewById(R.id.bottom_home_shop);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Tovars");

        imagenewtovar1=view.findViewById(R.id.  imagenewtovar1);
        imagenewtovar2=view.findViewById(R.id.  imagenewtovar2);
        imagenewtovar3=view.findViewById(R.id.  imagenewtovar3);
        imagenewtovar4=view.findViewById(R.id.  imagenewtovar4);
        imagenewtovar5=view.findViewById(R.id.  imagenewtovar5);
        mAuth=FirebaseAuth.getInstance();
        imagearrow77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zakazShopopFragment=ZakazShopopFragment.newInstance();
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_shop,zakazShopopFragment).commit();
                MenuItem item=        bottomNavigationView.getMenu().findItem(R.id.zakaz_shop);
                item.setCheckable(true);
                item.setChecked(true);
            }
        });
        valueEventListenert=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    MyName=snapshot.child("MagName").getValue().toString();
                    MyLogo=snapshot.child("MagLogo").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query= shop.child(mAuth.getCurrentUser().getUid());
        query.addValueEventListener(valueEventListenert);
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
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HHmmss");
        saveCurrentTime=currentTime.format(calendar.getTime());

        ProductRandomKey=saveCurrentDate+saveCurrentTime;
        MyRanKey=mAuth.getCurrentUser().getUid()+ProductRandomKey;
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
                    Toast.makeText(getContext(), "Изображение загружено", Toast.LENGTH_SHORT).show();
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
                Toast.makeText( getContext(), "Изображение загружено", Toast.LENGTH_SHORT).show();
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
                Toast.makeText( getContext(), "Изображение загружено", Toast.LENGTH_SHORT).show();
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
    }

    private void loaddata() {
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
            hashMap.put("MagName",MyName);
            hashMap.put("MagLogo",MyLogo);
            hashMap.put("ProductTime",MyRanKey);
            databaseReference.child(MyRanKey).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                      shopProfileBetaFragment=ShopProfileBetaFragment.newInstance();
                        FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                        fm.replace(R.id.frame_shop,shopProfileBetaFragment).commit();
                        MenuItem item=        bottomNavigationView.getMenu().findItem(R.id.shop_profile);
                        item.setCheckable(true);
                        item.setChecked(true);
                    }
                }
            });
        }
    }

    private void Image3() {
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
                Toast.makeText( getContext(), "Изображение загружено", Toast.LENGTH_SHORT).show();
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
                Toast.makeText( getContext(), "Изображение загружено", Toast.LENGTH_SHORT).show();
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
    public void onDestroyView() {
        super.onDestroyView();
        bottomNavigationView=null;
        buttonAddt=null;
        editTextNamei2=null;
        editTextPricei3=null;
        editTextOpisi4=null;
        imagearrow77=null;
        view=null;
        progressDialog=null;
        imagenewtovar1=null;
        imagenewtovar2=null;
        imagenewtovar3=null;
        imagenewtovar4=null;
        imagenewtovar5=null;
mAuth=null;
    }
}