package mychati.app.Shops;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mychati.app.R;
import mychati.app.databinding.ActivityShopHomeBinding;

public class ShopHomeActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
private DatabaseReference shopRef;
private BottomNavigationView bottomNavigationView;
private ShopProfileBetaFragment shopProfileBetaFragment=ShopProfileBetaFragment.newInstance();
  private ZakazShopopFragment zakazShopopFragment=ZakazShopopFragment.newInstance();
  private NewTovarBetaFragment newTovarBetaFragment=NewTovarBetaFragment.newInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_home);
        bottomNavigationView=findViewById(R.id.bottom_home_shop);

        mAuth=FirebaseAuth.getInstance();
        shopRef= FirebaseDatabase.getInstance().getReference().child("shops");
       if (savedInstanceState==null){
           getSupportFragmentManager().beginTransaction().replace(R.id.frame_shop,zakazShopopFragment).commit();
       }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.zakaz_shop:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_shop,zakazShopopFragment).commit();
                        return true;
                    case R.id.chat_shop:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_shop,newTovarBetaFragment).commit();
                        return true;
                    case R.id.shop_profile:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_shop,shopProfileBetaFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_shop,zakazShopopFragment).commit();
        MenuItem item=bottomNavigationView.getMenu().findItem(R.id.zakaz_shop);
        item.setCheckable(true);
        item.setChecked(true);
    }
}