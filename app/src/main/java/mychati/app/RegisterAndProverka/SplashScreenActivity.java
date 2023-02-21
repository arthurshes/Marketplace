package mychati.app.RegisterAndProverka;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mychati.app.CategUserActivity;
import mychati.app.Client.MyListener;
import mychati.app.HelloActivity.HelloClientActivity;
import mychati.app.HelloActivity.HelloDayActivity;
import mychati.app.HelloActivity.HelloFrags.HelloMorningFragment;
import mychati.app.HelloActivity.HelloNightActivity;
import mychati.app.HelloActivity.HelloVecherActivity;
import mychati.app.R;


public class SplashScreenActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference shop;
private HelloMorningFragment helloMorningFragment;

    private String name;
    private DatabaseReference clientse;

    private List<MyListener> myListenerList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mAuth = FirebaseAuth.getInstance();


        shop = FirebaseDatabase.getInstance().getReference().child("shops");
        clientse = FirebaseDatabase.getInstance().getReference().child("client");
        currentUser = mAuth.getCurrentUser();


        if (currentUser != null) {
       openShop();

        } else {
            startActivity(new Intent(SplashScreenActivity.this, RegisterPhoneActivity.class));
            finish();

        }
    }

    private void openShop() {
//        mk.putExtra("name", name);
//        mk.putExtra("ident", "1");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {

                    name = snapshot.child("MagName").getValue().toString();
//                    helloMorningFragment=HelloMorningFragment.newInstance();
//                    Bundle data=new Bundle();
//                    data.putString("name",name);
//                    data.putString("ident","1");
//                    FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
//                    helloMorningFragment.setArguments(data);
//                    fm.replace(R.id.splashframe,helloMorningFragment).commit();
                    helloMorningFragment=HelloMorningFragment.newInstance();
                    Bundle data=new Bundle();
                    data.putString("name",name);
                    data.putString("ident","1");
                    FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
                    helloMorningFragment.setArguments(data);
                    fm.replace(R.id.splashframe,helloMorningFragment).commit();
//                    Date currentdate=new Date();
//                    int hours=currentdate.getHours();
//                   if (hours >= 6 && hours < 12) {
//                     Intent mk=new Intent(SplashScreenActivity.this,HelloClientActivity.class);
//
//                       startActivity(mk);
//                       finish();
//                   }
//                  else if (hours >= 12 && hours < 16) {
//                        Intent mks=new Intent(SplashScreenActivity.this,HelloDayActivity.class);
//                        mks.putExtra("name", name);
//                        mks.putExtra("ident", "1");
//                       startActivity(mks);
//                       finish();
//                   }
//                    else if (hours >= 16 && hours < 24) {
//                        Intent mksi=new Intent(SplashScreenActivity.this,HelloVecherActivity.class);
//                        mksi.putExtra("name", name);
//                       mksi.putExtra("ident", "1");
//                        startActivity(mksi);
//                        finish();
//                   }else if (hours>=0&&hours<6){
//                       Intent mksi=new Intent(SplashScreenActivity.this, HelloNightActivity.class);
//                       mksi.putExtra("name", name);
//                       mksi.putExtra("ident", "1");
//                       startActivity(mksi);
//                       finish();
//                   }

                } else {
                    openClient();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        Query query= shop.child(currentUser.getUid());
        query.addValueEventListener(listener);
        myListenerList.add(new MyListener(listener,query));

    }



    private void openClient() {
        ValueEventListener listenert = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    name = snapshot.child("clientName").getValue().toString();
                    helloMorningFragment=HelloMorningFragment.newInstance();
                    Bundle data=new Bundle();
                    data.putString("name",name);
                    data.putString("ident","2");
                    FragmentTransaction fm=getSupportFragmentManager().beginTransaction();
                    helloMorningFragment.setArguments(data);
                    fm.replace(R.id.splashframe,helloMorningFragment).commit();
//                    Date currentdate=new Date();
//
//                    int hours=currentdate.getHours();
//                    Log.d("name", String.valueOf(hours));
//                    if (hours>= 6 && hours < 12) {
//                        Intent mk=new Intent(SplashScreenActivity.this,HelloClientActivity.class);
//                        mk.putExtra("name", name);
//                        mk.putExtra("ident", "2");
//                        startActivity(mk);
//                        finish();
//                    }
//                    else if (hours >= 12 && hours < 16) {
//                        Intent mks=new Intent(SplashScreenActivity.this,HelloDayActivity.class);
//                        mks.putExtra("name", name);
//                        mks.putExtra("ident", "2");
//                        startActivity(mks);
//                        finish();
//                    }
//                    else if (hours >= 16 && hours < 24) {
//                        Intent mksi=new Intent(SplashScreenActivity.this,HelloVecherActivity.class);
//                        mksi.putExtra("name", name);
//                        mksi.putExtra("ident", "2");
//                        startActivity(mksi);
//                        finish();
//                    }else if (hours>=0&&hours<6){
//                        Intent mksi=new Intent(SplashScreenActivity.this, HelloNightActivity.class);
//                        mksi.putExtra("name", name);
//                        mksi.putExtra("ident", "2");
//                        startActivity(mksi);
//                        finish();
//                    }

                } else {
                    Log.d("testInta",currentUser.getUid()+"currentUser");
                    startActivity(new Intent(SplashScreenActivity.this, CategUserActivity.class));
                    finish();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query= clientse.child(currentUser.getUid());
        query.addValueEventListener(listenert);
        myListenerList.add(new MyListener(listenert,query));
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();
        for (MyListener listener : myListenerList) {
            listener.unsubscribe();
        }
        myListenerList.clear();
//        helloMorningFragment.onDestroyView();
//        helloMorningFragment=null;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mAuth=null;


//        currentUser=null;
        name=null;
        shop=null;
        clientse=null;
    }
}