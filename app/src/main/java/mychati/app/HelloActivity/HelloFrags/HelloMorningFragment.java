
package mychati.app.HelloActivity.HelloFrags;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Date;

import mychati.app.Client.HomeActivity;
import mychati.app.HelloActivity.HelloClientActivity;
import mychati.app.R;
import mychati.app.Shops.ShopHomeActivity;

/**
 * A simple {@link Fragment} subclass.
 factory method to
 * create an instance of this fragment.
 */
public class HelloMorningFragment extends Fragment {
View view;
private TextView fraghellotxt;
private LottieAnimationView fraghelloanim;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private static HelloMorningFragment instance=null;

    public HelloMorningFragment() {
        // Required empty public constructor
    }
public static HelloMorningFragment newInstance(){
        if (instance==null){
            instance=new HelloMorningFragment();
            return instance;
        }else{
            return instance;
        }
}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment HelloMorningFragment.
     */
    // TODO: Rename and change types and number of parameters



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view= inflater.inflate(R.layout.fragment_hello_morning, container, false);

        fraghelloanim=view.findViewById(R.id.fraghelloanim);
        fraghellotxt=view.findViewById(R.id. fraghellotxt);

        Date currentdate=new Date();

        int hours=currentdate.getHours();
        if (hours>=6&&hours<12){
            view.setBackgroundColor(getResources().getColor(R.color.darkgreen));
            fraghelloanim.setAnimation(R.raw.sun);
            fraghellotxt.setText("Доброе утро,"+getArguments().getString("name") +"!");
            fraghellotxt.animate().translationY(500).setDuration(2200).setStartDelay(0);

        }else if (hours>=12&&hours<16){
            view.setBackgroundColor(getResources().getColor(R.color.darkgreen));
            fraghelloanim.setAnimation(R.raw.shoppi);
         fraghellotxt.animate().translationY(500).setDuration(2200).setStartDelay(0);
            fraghellotxt.setText("Добрый день,"+getArguments().getString("name") +"!");
        }else if (hours>=16&&hours<24){
            view.setBackgroundColor(getResources().getColor(R.color.vecher));
            fraghelloanim.setAnimation(R.raw.vecherok);
            fraghellotxt.animate().translationY(500).setDuration(2200).setStartDelay(0);
            fraghellotxt.setText("Добрый вечер,"+getArguments().getString("name") +"!");
        }else if (hours>=0&&hours<6){
            view.setBackgroundColor(getResources().getColor(R.color.vecher));
            fraghelloanim.setAnimation(R.raw.night2anim);
           fraghellotxt.animate().translationY(420).setDuration(2200).setStartDelay(0);
            fraghellotxt.setText("Доброй ночи,"+getArguments().getString("name") +"!");
        }



        int shop=Integer.parseInt(getArguments().getString("ident"));
        if (shop==2) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mk = new Intent(getContext(), HomeActivity.class);
                    startActivity(mk);
                    getActivity(). finish();
                }
            }, 3000);


        }else {


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mka = new Intent(getContext(), ShopHomeActivity.class);
                    startActivity(mka);
                    getActivity(). finish();
                }
            }, 3000);


        }
       return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view=null;
        fraghelloanim=null;
        fraghellotxt=null;
    }
}