package mychati.app.Client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mychati.app.Client.ClientTovarsAdaoter.TovarsAdapter;
import mychati.app.Client.ClientTovarsHolder.ClientTivarHolder;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TovarBetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TovarBetaFragment extends Fragment {
    private DatabaseReference tovars;
    private RecyclerView recyclerView;
    private String uid,srav,otpravprice;
    private int test;
    private static TovarBetaFragment instance=null;
    private TovarBottomOpisFragment tovarBottomOpisFragment;
    private int tovarprice;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<MyListener> myListeners=new ArrayList<>();
    private int priceint;
View view;
  RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter<TovarsAdapter, ClientTivarHolder> adapters;
    private ValueEventListener mRefUserListener;
    private FirebaseAuth mAuth;
    private DatabaseReference tovarDocart;
    private DatabaseReference otzyv;
    private AppCompatButton predl;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TovarBetaFragment() {
        // Required empty public constructor
    }
    public static TovarBetaFragment newInstance(){
        if (instance==null){
            instance=new TovarBetaFragment();
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
     * @return A new instance of fragment TovarBetaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TovarBetaFragment newInstance(String param1, String param2) {
        TovarBetaFragment fragment = new TovarBetaFragment();
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
       view=inflater.inflate(R.layout.fragment_tovar_beta, container, false);
        layoutManager=new GridLayoutManager(getActivity(),2);
        Log.d("Uid",getArguments().getString("ShopUid"));
        tovarDocart= FirebaseDatabase.getInstance().getReference().child("DoCart");
        otzyv=FirebaseDatabase.getInstance().getReference().child("otzyv");

        recyclerView=view. findViewById(R.id.tovarrec);
        tovars= FirebaseDatabase.getInstance().getReference().child("Tovars");
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(layoutManager);
        shimmerFrameLayout=view.findViewById(R.id.tovar_shimmer_one);
        mAuth=FirebaseAuth.getInstance();

       return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        shimmerFrameLayout.startShimmer();
        FirebaseRecyclerOptions<TovarsAdapter> options=new FirebaseRecyclerOptions.Builder<TovarsAdapter>()
                .setQuery(tovars.orderByChild("ShopUid").equalTo(getArguments().getString("ShopUid")),TovarsAdapter.class).build();
        adapters=new FirebaseRecyclerAdapter<TovarsAdapter, ClientTivarHolder>(options) {
            @Override
            protected void onBindViewHolder( @androidx.annotation.NonNull ClientTivarHolder holder, int position,  @androidx.annotation.NonNull TovarsAdapter model) {
                holder.texttovarprice.setText(model.getTovarPrice()+"₽");
                holder.texttovarname.setText(model.getTovarName());
                holder.texttovarprice.setHint(model.getProductTime());
                holder.texttovarname.setHint(model.getShopUid());
                if (model.getTovarImage1()!=null){
                    holder.tovarpus.setHint(model.getTovarImage1());
                }else if (model.getTovarImage2()!=null){
                    holder.tovarpus.setHint(model.getTovarImage2());
                }else if (model.getTovarImage3()!=null){
                    holder.tovarpus.setHint(model.getTovarImage3());
                }else if (model.getTovarImage4()!=null){
                    holder.tovarpus.setHint(model.getTovarImage4());
                }else if (model.getTovarImage5()!=null){
                    holder.tovarpus.setHint(model.getTovarImage5());
                }

                holder.tovarcart.setHint(model.getMagLogo());
                holder.texttovarstatyesnalkli.setHint(model.getMagName());

                holder.tovarminus.setHint(model.getTovarPrice());
                if (model.getTovarImage1()!=null){
                    Picasso.get().load(model.getTovarImage1()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageTovar, new Callback() {
                        @Override
                        public void onSuccess() {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(model.getTovarImage1()).into(holder.imageTovar);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }else if (model.getTovarImage2()!=null){
                    Picasso.get().load(model.getTovarImage2()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageTovar, new Callback() {
                        @Override
                        public void onSuccess() {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(model.getTovarImage2()).into(holder.imageTovar);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }else if (model.getTovarImage3()!=null){
                    Picasso.get().load(model.getTovarImage3()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageTovar, new Callback() {
                        @Override
                        public void onSuccess() {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(model.getTovarImage3()).into(holder.imageTovar);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }else if (model.getTovarImage4()!=null){
                    Picasso.get().load(model.getTovarImage4()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageTovar, new Callback() {
                        @Override
                        public void onSuccess() {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(model.getTovarImage4()).into(holder.imageTovar);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }else if (model.getTovarImage5()!=null){
                    Picasso.get().load(model.getTovarImage5()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageTovar, new Callback() {
                        @Override
                        public void onSuccess() {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(model.getTovarImage5()).into(holder.imageTovar);
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                }

                holder.cardTovar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tovarBottomOpisFragment = TovarBottomOpisFragment.newInstance();
                        Bundle data=new Bundle();
                        data.putString("Uid",holder.texttovarname.getHint().toString());
                        data.putString("ShopUid",holder.texttovarname.getHint().toString());
                        data.putString("ProdId",holder.texttovarprice.getHint().toString());
                        tovarBottomOpisFragment.setArguments(data);
                        tovarBottomOpisFragment.show(getActivity().getSupportFragmentManager(),"Tag");
                        Log.d("Arthur",tovarBottomOpisFragment.toString());
                        if (tovarBottomOpisFragment!=null){

                        }else{

                        }
                    }
                });


                String sa="1";
                int nas1=Integer.parseInt(model.getTovarStatus());
                int nas2=Integer.parseInt(sa);


                if (nas2==nas1){
                    holder.texttovarstatyesnalkli.setVisibility(View.VISIBLE);
                    holder.texttovarprice.setEnabled(true);


                }else{
                    holder.texttovarstatnonalkli.setVisibility(View.VISIBLE);


                    holder.texttovarprice.setEnabled(false);
                }





















                holder.texttovarprice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.tovarcart.setVisibility(View.VISIBLE);
                        holder.tovarminus.setVisibility(View.VISIBLE);
                        holder.tovarpus.setVisibility(View.VISIBLE)
                        ;
                        holder.texttovarprice.setVisibility(View.INVISIBLE);

                        HashMap<String,Object> docart=new HashMap<>();















                        docart.put("MagName",holder.texttovarstatyesnalkli.getHint().toString());
                        docart.put("MagLogo",holder.tovarcart.getHint().toString());

                        docart.put("ShopUid",holder.texttovarname.getHint().toString());

                        docart.put("MyUID",mAuth.getCurrentUser().getUid());

                        tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    HashMap<String,Object>slatslat=new HashMap<>();
                                    slatslat.put("tovarname",holder.texttovarname.getText().toString());
                                    slatslat.put("MyUID",mAuth.getCurrentUser().getUid());
                                    slatslat.put("tovarcartShopuid",holder.texttovarname.getHint().toString());
                                    slatslat.put("Fixprice",holder.tovarminus.getHint().toString());
                                    slatslat.put("tovarImage",holder.tovarpus.getHint().toString());
                                    slatslat.put("Price",holder.tovarminus.getHint().toString());
                                    slatslat.put("ProductId",holder.texttovarprice.getHint().toString());
                                    slatslat.put("TovarValue","1");

                                    tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child((holder.texttovarprice.getHint().toString()+mAuth.getCurrentUser().getUid())).updateChildren(slatslat).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
                                            }else{
                                                String message = task.getException().toString();
                                                Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {

                                    String message = task.getException().toString();
                                    Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });









                    }
                });






                holder.tovarpus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String  CurrentValue=holder.tovarcart.getText().toString();
                        int value=Integer.parseInt(CurrentValue);
                        value++;
                        holder.tovarcart.setText(String.valueOf(value));
                        test=Integer.valueOf(holder.tovarcart.getText().toString());
                        priceint=Integer.valueOf(holder.tovarminus.getHint().toString());

                        String dollar=String.valueOf(test*priceint);
                        HashMap<String,Object>docart=new HashMap<>();

                        docart.put("TovarValue",holder.tovarcart.getText().toString());
                        docart.put("Price",dollar);

                        tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child(holder.texttovarprice.getHint().toString()+mAuth.getCurrentUser().getUid()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {


                                } else {

                                    String message = task.getException().toString();
                                    Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                holder.tovarminus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String  CurrentValue=holder.tovarcart.getText().toString();
                        int value=Integer.parseInt(CurrentValue);
                        value--;
                        holder.tovarcart.setText(String.valueOf(value));

                        test=Integer.valueOf(holder.tovarcart.getText().toString());
                        priceint=Integer.valueOf(holder.tovarminus.getHint().toString());
                        String dollar=String.valueOf(test*priceint);

                        HashMap<String,Object>docart=new HashMap<>();

                        docart.put("TovarValue",holder.tovarcart.getText().toString());
                        docart.put("Price",dollar);
                        tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child(holder.texttovarprice.getHint().toString()+mAuth.getCurrentUser().getUid()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {


                                } else {

                                    String message = task.getException().toString();
                                    Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });







                    }
                });
                mRefUserListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            holder.texttovarprice.setVisibility(View.INVISIBLE);
                            holder.tovarcart.setVisibility(View.VISIBLE);
                            holder.tovarminus.setVisibility(View.VISIBLE);
                            holder.tovarpus.setVisibility(View.VISIBLE);
                            holder.tovarcart.setText(snapshot.child("TovarValue").getValue().toString());
                            Log.d("ki",snapshot.child("TovarValue").getValue().toString());
                            String s="0";
                            int n1=Integer.parseInt(snapshot.child("TovarValue").getValue().toString());
                            int n2=Integer.parseInt(s);
                            if (n1==n2){
                                tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child(holder.texttovarprice.getHint().toString()+mAuth.getCurrentUser().getUid()).removeValue();
                                holder.tovarminus.setVisibility(View.INVISIBLE);
                                holder.tovarpus.setVisibility(View.INVISIBLE);
                                holder.tovarcart.setVisibility(View.INVISIBLE);
                                holder.texttovarprice.setVisibility(View.VISIBLE);
                            }else{
                                holder.tovarminus.setVisibility(View.VISIBLE);
                                holder.tovarpus.setVisibility(View.VISIBLE);
                                holder.tovarcart.setVisibility(View.VISIBLE);
                                holder.texttovarprice.setVisibility(View.INVISIBLE);

                            }
                        }else{
                            holder.texttovarprice.setVisibility(View.VISIBLE);
                            holder.tovarcart.setVisibility(View.INVISIBLE);
                            holder.tovarminus.setVisibility(View.INVISIBLE);
                            holder.tovarpus.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };
                Query query=tovarDocart.child(mAuth.getCurrentUser().getUid()).child(holder.texttovarname.getHint().toString()).child(model.getProductTime()+mAuth.getCurrentUser().getUid());
                query.addValueEventListener(mRefUserListener);
                myListeners.add(new MyListener(mRefUserListener,query));
















            }

            @Override
            public ClientTivarHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tovars,parent,false);
                ClientTivarHolder holder=new ClientTivarHolder(view);


                return holder;
            }
        };
        recyclerView.setAdapter(adapters);
        adapters.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }
        myListeners.clear();
        adapters.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shimmerFrameLayout=null;
        recyclerView=null;
        mAuth=null;
        layoutManager=null;
        view=null;
        tovarBottomOpisFragment=null;
    }
}