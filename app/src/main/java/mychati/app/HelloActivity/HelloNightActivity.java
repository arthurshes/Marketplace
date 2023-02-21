package mychati.app.HelloActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import mychati.app.Client.HomeActivity;
import mychati.app.R;
import mychati.app.Shops.ShopHomeActivity;

public class HelloNightActivity extends AppCompatActivity {
private TextView text_night;
private LottieAnimationView lottieAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_night);
        text_night=(TextView) findViewById(R.id.text_night);
       text_night.setText("Доброй ночи,"+getIntent().getExtras().get("name").toString()+"!");
lottieAnimationView=(LottieAnimationView) findViewById(R.id.lottienight);

        int shop=Integer.parseInt(getIntent().getExtras().get("ident").toString());
      text_night.animate().translationY(420).setDuration(2200).setStartDelay(0);
        if (shop==2) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mk = new Intent(HelloNightActivity.this, HomeActivity.class);
                    startActivity(mk);
                    finish();
                }
            }, 3000);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mka = new Intent(HelloNightActivity.this, ShopHomeActivity.class);
                    startActivity(mka);
                    finish();
                }
            }, 3000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lottieAnimationView=null;
        text_night=null;
    }
}