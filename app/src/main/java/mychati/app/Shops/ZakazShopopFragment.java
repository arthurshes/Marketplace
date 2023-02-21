package mychati.app.Shops;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import mychati.app.Client.ClientShopsHolders.ClientShopHolder;
import mychati.app.Client.ClientShopsModel.ShopmModel;
import mychati.app.Holders.ChildHolders.GlavChildHolder;
import mychati.app.R;
import mychati.app.Shops.ZakazAdapter.ZakazAdapter;
import mychati.app.Shops.ZakazHolder.ZakazHolders;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ZakazShopopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ZakazShopopFragment extends Fragment {
    private FirebaseAuth mAuth;
private ShimmerFrameLayout shimmershopop;
private static ZakazShopopFragment instance=null;
    private RecyclerView recyclerView;
private DatabaseReference zakazy;
    RecyclerView.LayoutManager layoutManager;
private FirebaseRecyclerAdapter<ZakazAdapter, ZakazHolders> adapters;
private ShopZakazInfoBetaFragment shopZakazInfoBetaFragment;




View vbj;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ZakazShopopFragment() {
        // Required empty public constructor
    }
public static ZakazShopopFragment newInstance(){
        if (instance==null){
            instance=new ZakazShopopFragment();
            return instance;
        }else{
            return instance;
        }
}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ZakazShopopFragment.
     */
    // TODO: Rename and change types and number of parameters




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    vbj= inflater.inflate(R.layout.fragment_zakaz_shopop, container, false);
        shimmershopop=vbj.findViewById(R.id.shimmershopop);
    zakazy= FirebaseDatabase.getInstance().getReference().child("oformzakaz");
    mAuth=FirebaseAuth.getInstance();
    recyclerView=vbj.findViewById(R.id.reczakazov);
        layoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        Log.d("Arthur",mAuth.getCurrentUser().getUid());

//    @NonNull
//    @Override
//    public ZakazHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return null;
//    }

//        adapters=new FirebaseRecyclerPagingAdapter<ZakazAdapter, ZakazHolders>(options1) {
//            @Override
//            protected void onBindViewHolder(@NonNull ZakazHolders holder, int position, @NonNull ZakazAdapter model) {

//
//            }

//        };
//        recyclerView.setAdapter(adapters);
//        adapters.startListening();


    return vbj;
    }


    @Override
    public void onStart() {
        super.onStart();
        shimmershopop.startShimmer();
        FirebaseRecyclerOptions<ZakazAdapter> options = new FirebaseRecyclerOptions.Builder<ZakazAdapter>()
                .setQuery(zakazy.orderByChild("Zakazstatus").equalTo(mAuth.getCurrentUser().getUid()), ZakazAdapter.class).build();
        adapters =new FirebaseRecyclerAdapter<ZakazAdapter, ZakazHolders>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ZakazHolders holder, int position, @NonNull ZakazAdapter model) {
                holder.price.setText(model.getItogPrice());
                holder.phone.setText("Контактный номер: "+model.getPhone());
                holder.phone.setHint(model.getClientUid());
                holder.price.setHint(model.getProductId());
                if (holder.price.getText()!=null){
                    shimmershopop.stopShimmer();
                    shimmershopop.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent=new Intent(getContext(),ShopZakazInfoActivivty.class);
//                        intent.putExtra("uidc",holder.phone.getHint().toString());
//                        intent.putExtra("prodid",holder.price.getHint().toString());
//                        startActivity(intent);
                        Bundle data=new Bundle();
                        data.putString("uidc",holder.phone.getHint().toString());
                        data.putString("prodid",holder.price.getHint().toString());
                        shopZakazInfoBetaFragment=ShopZakazInfoBetaFragment.newInstance();
                        FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                        shopZakazInfoBetaFragment.setArguments(data);
                        fm.replace(R.id.frame_shop,shopZakazInfoBetaFragment).commit();
                    }
                });
            }

            @NonNull

            @Override
            public ZakazHolders onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zakaz_shopfor,parent,false);
                ZakazHolders holder=new ZakazHolders(view);


                return holder;
            }
        };
        adapters.startListening();
        recyclerView.setAdapter(adapters);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapters.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        adapters=null;
        layoutManager=null;
        shimmershopop=null;
        mAuth=null;
        recyclerView=null;

    }
}