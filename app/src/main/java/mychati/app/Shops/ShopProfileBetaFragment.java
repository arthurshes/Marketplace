package mychati.app.Shops;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import mychati.app.R;
import mychati.app.RegisterAndProverka.RegisterPhoneActivity;
import mychati.app.Shops.MyTovarAdapter.MyTovarAdapter;
import mychati.app.Shops.MyTovarHolder.MyTovarHolder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopProfileBetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopProfileBetaFragment extends Fragment {
View view;
private static ShopProfileBetaFragment instance=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ShimmerFrameLayout testshim;

    private ZakazShopopFragment zakazShopopFragment;
 private IzmenitProfilBetaFragment izmenitProfilBetaFragment;
    private CircleImageView profileImage;
    private DatabaseReference MyRef;
    private TextView textnamemag;
    private DatabaseReference MyTovar;
    private ImageView karandash,arrpwback;
    private ValueEventListener valueEventListener;
    private HashMap<DatabaseReference,ValueEventListener> hashMap=new HashMap<>();
    private FirebaseRecyclerAdapter<MyTovarAdapter, MyTovarHolder> adapters;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
private IzmenitTovarBetaFragment izmenitTovarBetaFragment;
    public ShopProfileBetaFragment() {
        // Required empty public constructor
    }
public static ShopProfileBetaFragment newInstance(){
        if (instance==null){
            instance=new ShopProfileBetaFragment();
            return instance;
        }else
        {
            return instance;
        }
}
    private BottomNavigationView bottomNavigationView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ShopProfileBetaFragment.
     */
    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_shop_profile_beta, container, false);


        bottomNavigationView=getActivity().findViewById(R.id.bottom_home_shop);
        testshim=view.findViewById(R.id.testshim);

        profileImage=view. findViewById(R.id.imageprofile);
        textnamemag=view. findViewById(R.id.textname);

        karandash=view. findViewById(R.id.progiledarts);
        mAuth=FirebaseAuth.getInstance();
        MyTovar= FirebaseDatabase.getInstance().getReference().child("Tovars");
        MyRef= FirebaseDatabase.getInstance().getReference().child("shops");

        recyclerView=view. findViewById(R.id.recmytovar);
        layoutManager=new GridLayoutManager(getContext(),2);
        arrpwback=view.findViewById(R.id.arrpwback);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);


        arrpwback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
      zakazShopopFragment=ZakazShopopFragment.newInstance();
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame_shop,zakazShopopFragment).commit();
                MenuItem item=bottomNavigationView.getMenu().findItem(R.id.zakaz_shop);
                item.setCheckable(true);
                item.setChecked(true);
            }
        });
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    textnamemag.setText(snapshot.child("MagName").getValue().toString());



                    Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).networkPolicy(NetworkPolicy.OFFLINE).into(profileImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(snapshot.child("MagLogo").getValue().toString()).into(profileImage);
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        MyRef.child(mAuth.getCurrentUser().getUid()).addValueEventListener( valueEventListener);
        hashMap.put(MyRef,valueEventListener);
        karandash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
izmenitProfilBetaFragment=IzmenitProfilBetaFragment.newInstance();
FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
fm.replace(R.id.frame_shop,izmenitProfilBetaFragment).commit();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        testshim.startShimmer();
        FirebaseRecyclerOptions<MyTovarAdapter> options=new FirebaseRecyclerOptions.Builder<MyTovarAdapter>()
                .setQuery(MyTovar.orderByChild("ShopUid").equalTo(mAuth.getCurrentUser().getUid()),MyTovarAdapter.class).build();
        adapters=new FirebaseRecyclerAdapter<MyTovarAdapter, MyTovarHolder>(options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull MyTovarHolder holder, int position, @androidx.annotation.NonNull MyTovarAdapter model) {

                holder.textMytovarname.setText(model.getTovarName());
                holder.textmytovarPrice.setText(model.getTovarPrice());
                holder.textmytovarPrice.setHint(model.getProductTime());
                holder.textMytovarname.setHint(model.getTovarStatus());


                Picasso.get().load(model.getTovarImage1()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.myTovarImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        testshim.stopShimmer();
                        testshim.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(model.getTovarImage1()).into(holder.myTovarImage);
                        testshim.stopShimmer();
                        testshim.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });


                String s="1";
                int n1=Integer.parseInt(model.getTovarStatus());
                int n2=Integer.parseInt(s);


                if (n2==n1){
                    holder.txtStatusyes.setVisibility(View.VISIBLE);
                }else{
                    holder.txtStatusno.setVisibility(View.VISIBLE);
                }










                holder.myTovarCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent tov=new Intent(getContext(),IzmenitTovarBetaActivity.class);
//                        tov.putExtra("ProdId",holder.textmytovarPrice.getHint().toString());
//                        startActivity(tov);
//                       getActivity(). finish();
                       izmenitTovarBetaFragment=IzmenitTovarBetaFragment.newInstance();
                        FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                        Bundle data=new Bundle();
                        data.putString("ProdId",holder.textmytovarPrice.getHint().toString());
                        izmenitTovarBetaFragment.setArguments(data);
                        fm.replace(R.id.frame_shop,izmenitTovarBetaFragment).commit();
                    }
                });







            }

            @Override
            public MyTovarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mytovar,parent,false);
                MyTovarHolder holder=new MyTovarHolder(view);


                return holder;
            }
        };
        recyclerView.setAdapter(adapters);
        adapters.startListening();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        izmenitTovarBetaFragment=null;
        recyclerView=null;
        testshim=null;
        bottomNavigationView=null;
        izmenitProfilBetaFragment=null;
        view=null;
        mAuth=null;
    }
}