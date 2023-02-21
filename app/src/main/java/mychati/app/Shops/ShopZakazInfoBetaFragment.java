package mychati.app.Shops;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mychati.app.Client.CartAdapter.CartAdapter;
import mychati.app.Client.MyListener;
import mychati.app.R;
import mychati.app.Shops.BottomFragShopZakInfo.bottomInfoZakazshopsFragment;
import mychati.app.Shops.ZakaznoyTovarHolder.ZakaznyTovarHolder;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ShopZakazInfoBetaFragment extends Fragment {
View view;
private static ShopZakazInfoBetaFragment instance=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private FirebaseAuth mAuth;
    private DatabaseReference zakaz;
    private       bottomInfoZakazshopsFragment bottomInfoZakazshopsFragments;
    private ImageView callshopcl;
    private ShimmerFrameLayout shimmlayoutshopbeta;
    private FirebaseRecyclerAdapter<CartAdapter, ZakaznyTovarHolder> adapters;
    private AppCompatButton btnopen;
    private RecyclerView recyclerView;
    private ValueEventListener valueEventListenerValue;
    private List<MyListener> myListeners=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopZakazInfoBetaFragment() {
        // Required empty public constructor
    }
public static ShopZakazInfoBetaFragment newInstance(){
        if (instance==null){
            instance=new ShopZakazInfoBetaFragment();
            return instance;
        }else{
            return instance;
        }
}
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     */
    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     view= inflater.inflate(R.layout.fragment_shop_zakaz_info_beta, container, false);
        recyclerView=view.findViewById(R.id.reccartshopsdops) ;
        layoutManager=new LinearLayoutManager(getContext());

        btnopen=view.findViewById(R.id.buttonopendialog) ;
        shimmlayoutshopbeta=view.findViewById(R.id.shimmlayoutshopbeta);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);

        mAuth=FirebaseAuth.getInstance();
        zakaz= FirebaseDatabase.getInstance().getReference().child("oformzakaz");
        valueEventListenerValue=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0){

                    btnopen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            bottomInfoZakazshopsFragments=bottomInfoZakazshopsFragment.newInstanceb();

                            Bundle datainf=new Bundle();
                            datainf.putString("uidClient",snapshot.child("ClientUid").getValue().toString());
                            datainf.putString("name",snapshot.child("ClientName").getValue().toString());
                            datainf.putString("keyOk",getArguments().getString("prodid"));
                            datainf.putString("itogPrice",snapshot.child("itogPrice").getValue().toString());
                            datainf.putString("adress",snapshot.child("adress").getValue().toString());
                            datainf.putString("podezd",snapshot.child("podezd").getValue().toString());
                            datainf.putString("lvl",snapshot.child("lvl").getValue().toString());
                            datainf.putString("kvart",snapshot.child("kvartira").getValue().toString());
                            datainf.putString("domophone",snapshot.child("domophone").getValue().toString());
                            datainf.putString("phone",snapshot.child("phone").getValue().toString());
                            bottomInfoZakazshopsFragments.setArguments(datainf);
                            bottomInfoZakazshopsFragments.show(getActivity().getSupportFragmentManager(),"TAG2");


                            HashMap<String,Object> testi=new HashMap<>();
                            testi.put("Prochitan","2");
                            zakaz.child(snapshot.child("ClientUid").getValue().toString()+getArguments().getString("prodid")+mAuth.getCurrentUser().getUid()).updateChildren(testi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query= zakaz.child(getArguments().getString("uidc")+getArguments().getString("prodid")+mAuth.getCurrentUser().getUid());
        query.addValueEventListener(valueEventListenerValue);
        myListeners.add(new MyListener(valueEventListenerValue,query));

     return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        shimmlayoutshopbeta.startShimmer();
        FirebaseRecyclerOptions<CartAdapter> options=new FirebaseRecyclerOptions.Builder<CartAdapter>()
                .setQuery(zakaz.child(getArguments().getString("uidc")+getArguments().getString("prodid")+mAuth.getCurrentUser().getUid()).child("Zakaz").orderByChild("tovarcartShopuid").equalTo(mAuth.getCurrentUser().getUid()),CartAdapter.class).build();
        adapters=new FirebaseRecyclerAdapter<CartAdapter, ZakaznyTovarHolder>(options) {
            @Override
            protected void onBindViewHolder( @androidx.annotation.NonNull ZakaznyTovarHolder holder, int position,  @androidx.annotation.NonNull CartAdapter model) {

                holder.tovarnameshop.setText(model.getTovarname());
                holder.tovarvalueshop.setText(model.getTovarValue()+"шт");
                holder.tovarpriceshop.setText(model.getPrice()+"₽");
                Transformation transformation=new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(10).oval(false).build();


                Picasso.get().load(model.getTovarImage()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.tovarphotoshop, new Callback() {
                    @Override
                    public void onSuccess() {
shimmlayoutshopbeta.stopShimmer();
shimmlayoutshopbeta.setVisibility(View.GONE);
recyclerView.setVisibility(View.VISIBLE);
btnopen.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(model.getTovarImage()).transform(transformation).into(holder.tovarphotoshop);
                        shimmlayoutshopbeta.stopShimmer();
                        shimmlayoutshopbeta.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        btnopen.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public ZakaznyTovarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zakazno_tovars,parent,false);
                ZakaznyTovarHolder holder=new ZakaznyTovarHolder(view);


                return holder;
            }
        };
        recyclerView.setAdapter(adapters);
        adapters.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener : myListeners) {
            listener.unsubscribe();
        }
        myListeners.clear();
        adapters.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView=null;
        view=null;
        adapters=null;
        bottomInfoZakazshopsFragments=null;
        shimmlayoutshopbeta=null;
        layoutManager=null;
        btnopen=null;
        mAuth=null;
    }
}