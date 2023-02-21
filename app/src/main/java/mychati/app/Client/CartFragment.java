package mychati.app.Client;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import mychati.app.Client.CartAdapter.CartAdapter;
import mychati.app.Client.CartAdapter.MartAdapter;
import mychati.app.Client.ClientBottomInfo.ClientInfoBetaFragment;
import mychati.app.Client.ClientBottomInfo.ClientInfoFromZakaz;
import mychati.app.Client.ClientTovarsAdaoter.TovarsAdapter;
import mychati.app.Client.HorizontalCartTovar.HorizontalTovarHolderCart;
import mychati.app.Client.OpisanieBottomFrag.TovarBottomOpisFragment;
import mychati.app.R;
import mychati.app.TestHolders.ChildHolder;
import mychati.app.TestHolders.ParentHolder;


/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
private TovarBottomOpisFragment tovarBottomOpisFragment;
    private int test;
    private int priceint;
    private FirebaseRecyclerAdapter<CartAdapter, ParentHolder> adapters;
    private FirebaseRecyclerAdapter<TovarsAdapter, HorizontalTovarHolderCart>adapterthree;
    private FirebaseRecyclerAdapter<MartAdapter, ChildHolder> adapterTwo;
    private DatabaseReference karzinaRef;
    private ShimmerFrameLayout shimmerFrameLayout;
View view;private DatabaseReference tovarRecRef;
private ValueEventListener valueEventListenerc;
    private LottieAnimationView lottieAnimationView;
    private RoundedImageView shopimege;
    private ClientInfoBetaFragment clientInfoBetaFragment;
    private int totalnyPrice;
    private int diablo;

    private ValueEventListener mRefUserListener;
    private ValueEventListener valueEventListener222;
    //   int dodo = 0;
    private int kol;
    private List<MyListener>myListeners=new ArrayList<>();
    private static CartFragment instance=null;
    private AppCompatButton buybutton;
    private int overTovar;
    private TextView textpricecarti, textkarzininziv, textViewnocart;
    private RecyclerView reccart;
    private FirebaseAuth mAuth;
    private DatabaseReference hophh;

    private ImageView deletezakazcart;
    RecyclerView.LayoutManager layoutManager;


    public static CartFragment newInstance(){
        if (instance==null){
            instance=new CartFragment();
            return instance;
        }else{
            return instance;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    view= inflater.inflate(R.layout.fragment_cart, container, false);

        reccart = view.findViewById(R.id.reccart);
        layoutManager = new LinearLayoutManager(this.getContext());



        reccart.setHasFixedSize(true);
        reccart.setLayoutManager(layoutManager);
        karzinaRef = FirebaseDatabase.getInstance().getReference().child("DoCart");

tovarRecRef=FirebaseDatabase.getInstance().getReference().child("Tovars");
        mAuth = FirebaseAuth.getInstance();
           lottieAnimationView = view.findViewById(R.id.lottieAnimationViewnottovarcart);
        textViewnocart = view.findViewById(R.id.texxtnottovercart);

        shimmerFrameLayout=view.findViewById(R.id.shiimertwo);
        shimmerFrameLayout.startShimmer();
        valueEventListenerc=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {




                }else {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    textViewnocart.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query22= karzinaRef.child(mAuth.getCurrentUser().getUid());
        query22.addValueEventListener(valueEventListenerc);
        myListeners.add(new MyListener(valueEventListenerc,query22));


    return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<CartAdapter> options = new FirebaseRecyclerOptions.Builder<CartAdapter>()
                .setQuery(karzinaRef.child(mAuth.getCurrentUser().getUid()), CartAdapter.class).build();
        adapters = new FirebaseRecyclerAdapter<CartAdapter, ParentHolder>(options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull ParentHolder parentHolder, int position, @androidx.annotation.NonNull CartAdapter model) {


                Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(8).oval(false).build();
                Picasso.get().load(model.getMagLogo()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(parentHolder.roundedImagemaglogoparent, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(model.getMagLogo()).transform(transformation).into(parentHolder.roundedImagemaglogoparent);

                    }
                });
                parentHolder.buylist.setText("оформить заказ из  <<" + model.getMagName() + ">>");
                parentHolder.buylist.setHint(model.getShopUid());
                parentHolder.dodo = 0;
                parentHolder.imagedeletemag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        karzinaRef.child(mAuth.getCurrentUser().getUid()).child(parentHolder.buylist.getHint().toString()).removeValue();
                    }
                });




                FirebaseRecyclerOptions<MartAdapter> options2 = new FirebaseRecyclerOptions.Builder<MartAdapter>()
                        .setQuery(karzinaRef.child(mAuth.getCurrentUser().getUid()).child(parentHolder.buylist.getHint().toString()).orderByChild("tovarcartShopuid").equalTo(parentHolder.buylist.getHint().toString()), MartAdapter.class).build();
                adapterTwo = new FirebaseRecyclerAdapter<MartAdapter, ChildHolder>(options2) {
                    int soul;
                    @Override
                    protected void onBindViewHolder(@androidx.annotation.NonNull ChildHolder holder, int position, @androidx.annotation.NonNull MartAdapter model) {
                        holder.textnamechild.setText(model.getTovarname());
                        holder.textpricechildtovar.setHint(model.getFixprice());
                        holder.tovarpluschildto.setHint(model.getProductId());

                        holder.tovarcartchild.setHint(model.getPrice());
                        holder.textnamechild.setHint(model.getTovarcartShopuid());

//        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {
//                karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textnamechild.getHint().toString()).child((holder.tovarpluschildto.getHint().toString()+mAuth.getCurrentUser().getUid())).removeValue();
//            }
//
//            @Override
//            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                        .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
//                        .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_2422)
//                        .addSwipeRightLabel("Удалить")
//                        .setSwipeRightLabelColor(ContextCompat.getColor(getContext(),R.color.white))
//                        .create()
//                        .decorate();
//
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//
//            }
//        };
//        new ItemTouchHelper(simpleCallback).attachToRecyclerView(parentHolder.nestedrecer);
                        Picasso.get().load(model.getTovarImage()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imagetovarchild, new Callback() {
                            @Override
                            public void onSuccess() {
                                reccart.setVisibility(View.VISIBLE);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                shimmerFrameLayout.stopShimmer();
                            }

                            @Override
                            public void onError(Exception e) {
                                Picasso.get().load(model.getTovarImage()).into(holder.imagetovarchild);
                                reccart.setVisibility(View.VISIBLE);
                                shimmerFrameLayout.setVisibility(View.GONE);
                                shimmerFrameLayout.stopShimmer();
                            }
                        });


                        soul = Integer.valueOf(model.getPrice());



                        parentHolder.dodo = parentHolder.dodo + soul;
                        Log.d("pr", String.valueOf(parentHolder.dodo));

                        parentHolder.textitogParent.setText(""+parentHolder.dodo+"₽");

                        parentHolder.buylist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Intent nmk = new Intent(getActivity(), ClientInfoFromZakaz.class);
//                                nmk.putExtra("Uid", parentHolder.buylist.getHint().toString());
//                                nmk.putExtra("price",parentHolder.textitogParent.getText().toString());
//                                startActivity(nmk);
//                                getActivity().finish();
                                Bundle data=new Bundle();
                                data.putString("Uid", parentHolder.buylist.getHint().toString());
                                data.putString("price",parentHolder.textitogParent.getText().toString());
                                clientInfoBetaFragment=ClientInfoBetaFragment.newInstance();
                                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                                clientInfoBetaFragment.setArguments(data);
                                fm.replace(R.id.frame,clientInfoBetaFragment).commit();
                            }
                        });
                        mRefUserListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    holder.tovarcartchild.setText(snapshot.child("TovarValue").getValue().toString());
                                    holder.textpricechildtovar.setText(snapshot.child("Price").getValue().toString() +"₽");
                                    holder.tovarminuschildto.setHint(snapshot.child("Price").getValue().toString());


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };

                        Query query= karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textnamechild.getHint().toString()).child(holder.tovarpluschildto.getHint().toString() + mAuth.getCurrentUser().getUid());
                        query.addValueEventListener(mRefUserListener);
                        myListeners.add(new MyListener(mRefUserListener,query));



                        holder.tovarpluschildto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String CurrentValue = holder.tovarcartchild.getText().toString();
                                int value = Integer.parseInt(CurrentValue);
                                value++;
                                holder.tovarcartchild.setText(String.valueOf(value));
                                int pricInt = Integer.valueOf(holder.textpricechildtovar.getHint().toString());
                                int valInt = Integer.valueOf(holder.tovarcartchild.getHint().toString());
                                int itogov = valInt + pricInt;
                                parentHolder.dodo = parentHolder.dodo + pricInt;
                                Log.d("pr", String.valueOf(parentHolder.dodo));
                                String iogovStr = String.valueOf(itogov);
                                HashMap<String, Object> hip = new HashMap<>();
                                hip.put("TovarValue", holder.tovarcartchild.getText().toString());
                                hip.put("Price", iogovStr);
                                karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textnamechild.getHint().toString()).child(holder.tovarpluschildto.getHint().toString() + mAuth.getCurrentUser().getUid()).updateChildren(hip).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {


                                        } else {
                                            String message = task.getException().toString();
                                            Toast.makeText(getContext(), "РћС€РёР±РєР°" + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });


                        holder.tovarminuschildto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String CurrentValue = holder.tovarcartchild.getText().toString();
                                int value = Integer.parseInt(CurrentValue);
                                value--;
                                holder.tovarcartchild.setText(String.valueOf(value));


                                int pricInt = Integer.valueOf(holder.textpricechildtovar.getHint().toString());
                                int valInt = Integer.valueOf(holder.tovarcartchild.getHint().toString());
                                int itogov = valInt - pricInt;
                                parentHolder.dodo = parentHolder.dodo - pricInt;
                                String iogovStr = String.valueOf(itogov);

                                Log.d("pr", String.valueOf(parentHolder.dodo));
                                parentHolder.textitogParent.setText(String.valueOf(parentHolder.dodo));
                                HashMap<String, Object> hip = new HashMap<>();
                                hip.put("TovarValue", holder.tovarcartchild.getText().toString());
                                hip.put("Price", iogovStr);
                                karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textnamechild.getHint().toString()).child(holder.tovarpluschildto.getHint().toString() + mAuth.getCurrentUser().getUid()).updateChildren(hip).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                        } else {
                                            String message = task.getException().toString();
                                            Toast.makeText(getContext(), "РћС€РёР±РєР°" + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });


                    }

                    @Override
                    public ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_item, parent, false);
                        ChildHolder holder = new ChildHolder(view);
                        return holder;
                    }

                };
                parentHolder.nestedrecer.setAdapter(adapterTwo);
                adapterTwo.startListening();

                FirebaseRecyclerOptions<TovarsAdapter> options11 = new FirebaseRecyclerOptions.Builder<TovarsAdapter>()
                        .setQuery(tovarRecRef.orderByChild("ShopUid").equalTo(parentHolder.buylist.getHint().toString()).limitToLast(5), TovarsAdapter.class).build();
                adapterthree=new FirebaseRecyclerAdapter<TovarsAdapter, HorizontalTovarHolderCart>(options11) {

                    @Override
                    protected void onBindViewHolder(@NonNull HorizontalTovarHolderCart holder, int position, @NonNull TovarsAdapter model) {

                        holder.tovarminuscarthorizontal.setEnabled(false);
                        holder.tovarpluscarthorizontal.setEnabled(false);
                        if (model.getTovarImage1()!=null){
                            Picasso.get().load(model.getTovarImage1()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imagehorizontaltovar, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(model.getTovarImage1()).into(holder.imagehorizontaltovar);

                                }
                            });
                        }else if (model.getTovarImage2()!=null){
                            Picasso.get().load(model.getTovarImage2()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imagehorizontaltovar, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(model.getTovarImage2()).into(holder.imagehorizontaltovar);

                                }
                            });
                        }else if (model.getTovarImage3()!=null){
                            Picasso.get().load(model.getTovarImage3()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imagehorizontaltovar, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(model.getTovarImage3()).into(holder.imagehorizontaltovar);

                                }
                            });
                        }else if (model.getTovarImage4()!=null){
                            Picasso.get().load(model.getTovarImage4()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imagehorizontaltovar, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(model.getTovarImage4()).into(holder.imagehorizontaltovar);

                                }
                            });
                        }else if (model.getTovarImage5()!=null){
                            Picasso.get().load(model.getTovarImage5()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imagehorizontaltovar, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Picasso.get().load(model.getTovarImage5()).into(holder.imagehorizontaltovar);

                                }
                            });

                        }
                        holder.textpricehorizontaltovar.setText(model.getTovarPrice()+"₽");
                        holder.textnamehorizontaltovar.setText(model.getTovarName());
                        holder.textnamehorizontaltovar.setHint(model.getProductTime());
                        holder.textpricehorizontaltovar.setHint(model.getShopUid());
                        holder.addbuttonhorizonttaltovar.setHint(model.getMagLogo());
                        holder.cardhorizontaltovar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                tovarBottomOpisFragment=TovarBottomOpisFragment.newInstance();
                                Bundle data=new Bundle();
                                data.putString("Uid",    holder.textpricehorizontaltovar.getHint().toString());
                                data.putString("ShopUid",    holder.textpricehorizontaltovar.getHint().toString());
                                data.putString("ProdId",  holder.textnamehorizontaltovar.getHint().toString());
                                tovarBottomOpisFragment.setArguments(data);
                                tovarBottomOpisFragment.show(getActivity().getSupportFragmentManager(),"Tag");
                            }
                        });
holder.tovarminuscarthorizontal.setHint(model.getMagName());
if (model.getTovarImage1()!=null){
    holder.tovarpluscarthorizontal.setHint(model.getTovarImage1());
}else if (model.getTovarImage2()!=null){
    holder.tovarpluscarthorizontal.setHint(model.getTovarImage2());
}else if (model.getTovarImage3()!=null){
    holder.tovarpluscarthorizontal.setHint(model.getTovarImage3());
}else if (model.getTovarImage4()!=null){
    holder.tovarpluscarthorizontal.setHint(model.getTovarImage4());
}else if (model.getTovarImage5()!=null){
    holder.tovarpluscarthorizontal.setHint(model.getTovarImage5());
}
holder.tovarcarthorizontal.setHint(model.getTovarPrice());
valueEventListener222=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
            holder.addbuttonhorizonttaltovar.setEnabled(false);
            holder.tovarminuscarthorizontal.setEnabled(true);
            holder.tovarpluscarthorizontal.setEnabled(true);
            holder.addbuttonhorizonttaltovar.setVisibility(View.INVISIBLE);

            holder.tovarpluscarthorizontal.setVisibility(View.VISIBLE);
            holder.tovarminuscarthorizontal.setVisibility(View.VISIBLE);
            holder.tovarcarthorizontal.setVisibility(View.VISIBLE);
            holder.tovarcarthorizontal.setText(snapshot.child("TovarValue").getValue().toString());
            int s2=Integer.parseInt(holder.tovarcarthorizontal.getText().toString());
            String s1="0";
            int s=Integer.parseInt(s1);
            if (s2==s){
                holder.addbuttonhorizonttaltovar.setEnabled(true);
                holder.tovarminuscarthorizontal.setEnabled(false);
                Log.d("falses log","блять");
                holder.tovarpluscarthorizontal.setEnabled(false);
                holder.addbuttonhorizonttaltovar.setVisibility(View.VISIBLE);

                holder.tovarpluscarthorizontal.setVisibility(View.INVISIBLE);
                holder.tovarminuscarthorizontal.setVisibility(View.INVISIBLE);
                holder.tovarcarthorizontal.setVisibility(View.INVISIBLE);
                karzinaRef.child(mAuth.getCurrentUser().getUid()).child( holder.textpricehorizontaltovar.getHint().toString()).child(  holder.textnamehorizontaltovar.getHint().toString()+mAuth.getCurrentUser().getUid()).removeValue();
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
};
Query query=karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textpricehorizontaltovar.getHint().toString()).child(   holder.textnamehorizontaltovar.getHint().toString()+mAuth.getCurrentUser().getUid());
query.addValueEventListener(valueEventListener222);
myListeners.add(new MyListener(valueEventListener222,query));

holder.addbuttonhorizonttaltovar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        holder.addbuttonhorizonttaltovar.setEnabled(false);
        holder.tovarminuscarthorizontal.setEnabled(true);
        holder.tovarpluscarthorizontal.setEnabled(true);
        holder.addbuttonhorizonttaltovar.setVisibility(View.INVISIBLE);
        holder.tovarpluscarthorizontal.setVisibility(View.VISIBLE);
        holder.tovarminuscarthorizontal.setVisibility(View.VISIBLE);
        holder.tovarcarthorizontal.setVisibility(View.VISIBLE);
        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("MagLogo",  holder.addbuttonhorizonttaltovar.getHint().toString());
        hashMap.put("MagName",holder.tovarminuscarthorizontal.getHint().toString());
        hashMap.put("MyUID",mAuth.getCurrentUser().getUid());
        hashMap.put("ShopUid", holder.textpricehorizontaltovar.getHint().toString());
        karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textpricehorizontaltovar.getHint().toString()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    HashMap<String,Object>hashMap1=new HashMap<>();
                    hashMap1.put(
                            "Fixprice",holder.tovarcarthorizontal.getHint().toString());
                    hashMap1.put(
                            "MyUID",mAuth.getCurrentUser().getUid());
                    hashMap1.put("tovarname", holder.textnamehorizontaltovar.getText().toString());
                    hashMap1.put("tovarcartShopuid",holder.textpricehorizontaltovar.getHint().toString());
                    hashMap1.put("tovarImage", holder.tovarpluscarthorizontal.getHint().toString());
                    hashMap1.put("ProductId", holder.textnamehorizontaltovar.getHint().toString());
                    hashMap1.put("TovarValue","1");
                    hashMap1.put("Price",holder.tovarcarthorizontal.getHint().toString());
                    karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textpricehorizontaltovar.getHint().toString()).child( holder.textnamehorizontaltovar.getHint().toString()+mAuth.getCurrentUser().getUid()).updateChildren(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getContext(), "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
});
holder.tovarminuscarthorizontal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String  CurrentValue=holder.tovarcarthorizontal.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value--;
        holder.tovarcarthorizontal.setText(String.valueOf(value));
        test=Integer.valueOf(holder.tovarcarthorizontal.getText().toString());
        priceint=Integer.valueOf(holder.tovarcarthorizontal.getHint().toString());

        String dollar=String.valueOf(test*priceint);
        HashMap<String,Object>docart=new HashMap<>();

        docart.put("TovarValue",holder.tovarcarthorizontal.getText().toString());
        docart.put("Price",dollar);

        karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textpricehorizontaltovar.getHint().toString()).child(holder.textnamehorizontaltovar.getHint().toString()+mAuth.getCurrentUser().getUid()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
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
holder.tovarpluscarthorizontal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String  CurrentValue=holder.tovarcarthorizontal.getText().toString();
        int value=Integer.parseInt(CurrentValue);
        value++;
        holder.tovarcarthorizontal.setText(String.valueOf(value));
        test=Integer.valueOf(holder.tovarcarthorizontal.getText().toString());
        priceint=Integer.valueOf(holder.tovarcarthorizontal.getHint().toString());

        String dollar=String.valueOf(test*priceint);
        HashMap<String,Object>docart=new HashMap<>();

        docart.put("TovarValue",holder.tovarcarthorizontal.getText().toString());
        docart.put("Price",dollar);

      karzinaRef.child(mAuth.getCurrentUser().getUid()).child(holder.textpricehorizontaltovar.getHint().toString()).child(holder.textnamehorizontaltovar.getHint().toString()+mAuth.getCurrentUser().getUid()).updateChildren(docart).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                        Log.d("fffff",holder.textnamehorizontaltovar.getText().toString());
                    }

                    @NonNull
                    @Override
                    public HorizontalTovarHolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal_recommend, parent, false);
                        HorizontalTovarHolderCart holder = new HorizontalTovarHolderCart(view);
                        return holder;
                    }
                };
                parentHolder.horizontaltovarRecycler.setAdapter(adapterthree);
                adapterthree.startListening();
            }

            @Override
            public ParentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                ParentHolder holder = new ParentHolder(view);
                return holder;
            }
        };
        reccart.setAdapter(adapters);
        adapters.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener:myListeners){
            listener.unsubscribe();
        }
        myListeners.clear();
        if (adapterthree!=null){
            adapterthree.stopListening();
        }if (adapterthree!=null){
            adapterTwo.stopListening();
        }if (adapters!=null){
            adapters.stopListening();

        }





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        shimmerFrameLayout=null;
        myListeners.clear();
        lottieAnimationView=null;
        mAuth=null;
        reccart=null;
        if (adapterthree!=null){
            adapterthree.stopListening();
            adapterthree=null;
        }
        if (adapterTwo!=null){
            adapterTwo.stopListening();
            adapterTwo=null;
        }
        if (adapters!=null){
            adapters.stopListening();
            adapters=null;
        }
    }

}