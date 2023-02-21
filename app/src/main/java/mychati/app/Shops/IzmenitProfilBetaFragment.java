package mychati.app.Shops;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mychati.app.Client.MyListener;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IzmenitProfilBetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IzmenitProfilBetaFragment extends Fragment {
View view;
    private CircleImageView myphoto;
    private EditText myname;
    private TextView saveizmen;
    private ImageView imagearrowbacks;
    private DatabaseReference shops;
    private String DownloadUrl;
    private StorageReference magPhoto;
    private ShopProfileBetaFragment shopProfileBetaFragment;
    private ProgressDialog progressDialog;
    private ValueEventListener valueEventListenerProfile;
    private List<MyListener> myListeners=new ArrayList<>();
    private Uri imageUri;
    private static final int GALLERYPICK = 1;
    private FirebaseAuth mAuth;
private static IzmenitProfilBetaFragment instance=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IzmenitProfilBetaFragment() {
        // Required empty public constructor
    }
public static IzmenitProfilBetaFragment newInstance(){
        if (instance==null){
            instance=new IzmenitProfilBetaFragment();
            return instance;
        }else{
            return instance;
        }
}
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment IzmenitProfilBetaFragment.
     */
    // TODO: Rename and change types and number of parameters



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     view=inflater.inflate(R.layout.fragment_izmenit_profil_beta, container, false);
        myname = view. findViewById(R.id.editmynames);
        myphoto = view. findViewById(R.id.myphoto);
        saveizmen =view. findViewById(R.id.textsaveizmen);
        mAuth = FirebaseAuth.getInstance();
        imagearrowbacks=view.findViewById(R.id. imagearrowbacks);
        progressDialog = new ProgressDialog(getContext());
        magPhoto = FirebaseStorage.getInstance().getReference().child("LogoMagazine");
        shops = FirebaseDatabase.getInstance().getReference().child("shops");
        imagearrowbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopProfileBetaFragment=ShopProfileBetaFragment.newInstance();
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_shop,shopProfileBetaFragment).commit();
            }
        });
        valueEventListenerProfile=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    myname.setText(snapshot.child("MagName").getValue().toString());

                    Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).into(myphoto, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).into(myphoto);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query= shops.child(mAuth.getCurrentUser().getUid());
        query.addValueEventListener(valueEventListenerProfile);
        myListeners.add(new MyListener(valueEventListenerProfile,query));
        saveizmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveIzmen();
            }
        });
        myphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
     return view;
    }

    private void OpenGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GALLERYPICK);

    }

    private void SaveIzmen() {
if (imageUri!=null) {
    progressDialog.setTitle("загрузка данных....");
    progressDialog.setMessage("Пожалуйста подождите");
    progressDialog.setCanceledOnTouchOutside(false);
    progressDialog.show();
    StorageReference filePath = magPhoto.child(imageUri.getLastPathSegment() + ".jpg");
    final UploadTask uploadTask = filePath.putFile(imageUri);
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


                        SavenewInfo();
                    }


                }
            });
        }
    });
}else {
    Toast.makeText(getContext(), "Нет изменений", Toast.LENGTH_SHORT).show();
}
    }

    private void SavenewInfo() {


        if (TextUtils.isEmpty(myname.getText().toString())) {


            Toast.makeText(getContext(), "Введите название магазина", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String,Object> Izmena=new HashMap<>();
            Izmena.put("MagName",myname.getText().toString());
            Izmena.put("MagLogo",DownloadUrl);
            shops.child(mAuth.getCurrentUser().getUid()).updateChildren(Izmena).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Изменения сохранены", Toast.LENGTH_SHORT).show();


                    } else {
                        progressDialog.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            myphoto.setImageURI(imageUri);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }
        myListeners.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        progressDialog=null;
        imagearrowbacks=null;
        myname=null;
        myphoto=null;
        shopProfileBetaFragment=null;
    }
}