package mychati.app.Client.ClientBottomInfo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mychati.app.Client.CartFragment;
import mychati.app.Client.ClientShopsHolders.HomeFragment;
import mychati.app.Client.HomeActivity;
import mychati.app.Client.MyListener;
import mychati.app.Client.TovarBetaFragment;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientInfoBetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientInfoBetaFragment extends Fragment {
    private static ClientInfoBetaFragment instance=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FirebaseAuth mAuth;
    private DatabaseReference oform;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private EditText editKv, editPodezd, editAadress, editPhone, editlvl, editdomophon;
    private TextView price,textItogprice;
    private String myName;
    private AppCompatButton oformshopButton;
    private ValueEventListener valueEventListener;
    private List<MyListener> myListeners=new ArrayList<>();
    private DatabaseReference Korzina;
    private DatabaseReference my;
    private String itogPrice,uids, saveCurrentDate, saveCurrentTime, ProductRandomKey, Date,Moth,Hours,Minute,Tovarss;
    private DatabaseReference docart;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientInfoBetaFragment() {
        // Required empty public constructor
    }
    public static ClientInfoBetaFragment newInstance(){
        if (instance==null){
            instance=new ClientInfoBetaFragment();
            return instance;
        }else{
            return instance;
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientInfoBetaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientInfoBetaFragment newInstance(String param1, String param2) {
        ClientInfoBetaFragment fragment = new ClientInfoBetaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_client_info_beta, container, false);
        mAuth = FirebaseAuth.getInstance();
        textItogprice=view.findViewById(R.id.otextItogInf);
        editlvl = view. findViewById(R.id.edtlvl);
        my = FirebaseDatabase.getInstance().getReference().child("client");
        editKv = view. findViewById(R.id.kvartiraedit);
        Korzina = FirebaseDatabase.getInstance().getReference().child("DoCart");
        editPodezd = view. findViewById(R.id.editpodezd);
        editAadress =view.findViewById(R.id.adressmy);
        editPhone = view. findViewById(R.id.editkontektphone);
        bottomNavigationView=getActivity().findViewById(R.id.bottom_home);
        editdomophon = view. findViewById(R.id.editdomphone);
        oformshopButton = view. findViewById(R.id.buttonoformlenzak);
        oform = FirebaseDatabase.getInstance().getReference().child("oformzakaz");
        docart = FirebaseDatabase.getInstance().getReference().child("DoCart");
        price = view. findViewById(R.id.textpriceoform);
        editlvl =view. findViewById(R.id.edtlvl);


        uids=getArguments().getString("Uid");
        itogPrice=getArguments().getString("price");

        Log.d("iop", uids);
        textItogprice.setText("Итого "+itogPrice);

        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    myName = snapshot.child("clientName").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query=    my.child(mAuth.getCurrentUser().getUid());
        query.addValueEventListener(valueEventListener);
        myListeners.add(new MyListener(valueEventListener,query));

        oformshopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaddata();

            }
        });






       return view;
    }
    private void moveData(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String,Object> kjd=new HashMap<>();
                kjd.put("Zakaz",dataSnapshot.getValue());

                kjd.put("Zakazstatus",uids);
                kjd.put("phone",editPhone.getText().toString());
                kjd.put("ClientUid",mAuth.getCurrentUser().getUid());
                kjd.put("ClientName",myName);
                kjd.put("shopId",uids);
                kjd.put("Moth",Moth);
                kjd.put("Hour",Hours);
                kjd.put("itogPrice",itogPrice);
                kjd.put("Minute",Minute);
                kjd.put("Date",Date);
                kjd.put("Prochitan","1");
                kjd.put("ProductId",ProductRandomKey);
                kjd.put("adress",editAadress.getText().toString());
                kjd.put("podezd",editPodezd.getText().toString());
                kjd.put("kvartira",editKv.getText().toString());
                kjd.put("lvl",editlvl.getText().toString());
                kjd.put("domophone",editdomophon.getText().toString());
                toPath.setValue(kjd, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");
                            fromPath.removeValue();
                            Toast.makeText(getContext(), "Заказ оформлен", Toast.LENGTH_SHORT).show();
                          homeFragment= HomeFragment.newInstance();
                            FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                            fm.replace(R.id.frame,homeFragment).commit();
                            MenuItem item= bottomNavigationView.getMenu().findItem(R.id.home);
item.setCheckable(true);
item.setChecked(true);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void loaddata() {
        if (TextUtils.isEmpty(editAadress.getText().toString())) {
            Toast.makeText(getContext(), "Введите свой адрес", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editPhone.getText().toString())) {
            Toast.makeText(getContext(), "Введите свой номер", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editdomophon.getText().toString())) {
            Toast.makeText(getContext(), "Введите код домофона", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editlvl.getText().toString())) {
            Toast.makeText(getContext(), "Введите свой этаж", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editPodezd.getText().toString())) {
            Toast.makeText(getContext(), "Введите свой подъезд", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(editKv.getText().toString())) {
            Toast.makeText(getContext(), "Введите свою квартиру/офис", Toast.LENGTH_SHORT).show();
        } else {


            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());



            SimpleDateFormat currentday=new SimpleDateFormat("dd");

            Date=currentday.format(calendar.getTime());





            SimpleDateFormat hours=new SimpleDateFormat("HH");

            Hours=hours.format(calendar.getTime());
            SimpleDateFormat moth=new SimpleDateFormat("MM");
            Moth=moth.format(calendar.getTime());


            SimpleDateFormat minute=new SimpleDateFormat("mm");

            Minute=minute.format(calendar.getTime());



            SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
            saveCurrentTime = currentTime.format(calendar.getTime());

            ProductRandomKey = saveCurrentDate + saveCurrentTime;


            moveData(docart.child(mAuth.getCurrentUser().getUid()).child(uids), oform.child(mAuth.getCurrentUser().getUid() + ProductRandomKey + uids));


        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myName=null;
        editdomophon=null;
        editPhone=null;
        editAadress=null;
        editlvl=null;
        editPodezd=null;
        editKv=null;
        valueEventListener=null;
        Moth=null;
        Hours=null;
        oformshopButton=null;
        Date=null;
        Minute=null;
        textItogprice=null;
        itogPrice=null;
        ProductRandomKey=null;
        uids=null;
        mAuth=null;
        homeFragment=null;
        price=null;
        view=null;
    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener : myListeners) {
            listener.unsubscribe();
        }
        myListeners.clear();


    }

}