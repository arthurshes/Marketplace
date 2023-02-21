package mychati.app.Client.ClientShopsHolders;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.paging.PagingConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.paging.DatabasePagingOptions;
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import mychati.app.Client.ClientShopsModel.ShopmModel;
import mychati.app.Client.MyListener;
import mychati.app.Client.SearchFragment;
import mychati.app.Client.TovarBetaFragment;
import mychati.app.Holders.ChildHolders.GlavChildHolder;
import mychati.app.R;


public class HomeFragment extends Fragment {
    private DatabaseReference proverka;
    private Boolean bottom = false;
    ///Scroll///
    private TovarBetaFragment tovarBetaFragment;
    private Query query1234;
    private int itemCount = 4;
    ///Scroll///
    private DatabaseReference otzyv;
    View view;
    //    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference prover;
    private ShimmerFrameLayout shimmerFrameLayout;
    private SearchFragment searchFragment;
    private TextView textesti, textlog, search_click;
    private static HomeFragment instance = null;
    private Double itog;
    private Double itogt;
    private Double otztvValues;
    private Double otztvValuestwo;
    private DatabaseReference otzest;


    private String saveCurrentDate, saveCurrentTime, ProductRandomKey;
    private DatabaseReference myname;
    private FirebaseAuth mAuth;
    private RecyclerView rectwohome;
    private FirebaseRecyclerPagingAdapter<ShopmModel, ClientShopHolder> magAdapter;
    private FirebaseRecyclerAdapter<ShopmModel, GlavChildHolder> glavAdapter;
    private DatabaseReference shops;
    private RecyclerView recAptek;
    private DatabaseReference shopipht;
    private ValueEventListener refTwoRaw;
    private Dialog dialog;
    private List<MyListener> myListenerList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        if (instance == null) {
            instance = new HomeFragment();
            return instance;
        } else {
            return instance;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        otzyv = reference.child("otzyv");
        myname = reference.child("client");
        proverka = reference.child("oformzakaz");
        search_click = view.findViewById(R.id.search_click);
        dialog = new Dialog(getContext());
        shops = reference.child("shops");

//layoutManager=new LinearLayoutManager(this.getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        otzest = reference.child("otzyv");
        recAptek = view.findViewById(R.id.recAptek);
        prover = reference.child("otzyv");
        recAptek.setHasFixedSize(false);

        recAptek.setLayoutManager(layoutManager);
        textesti = view.findViewById(R.id.textesti);
        textlog = view.findViewById(R.id.textestobmag);
        shimmerFrameLayout = view.findViewById(R.id.shimmlayoutone);
        mAuth = FirebaseAuth.getInstance();

        shopipht = reference.child("DoCart");

        rectwohome = view.findViewById(R.id.rectwohome);
        rectwohome.setHasFixedSize(false);
        rectwohome.setLayoutManager(
                new LinearLayoutManager(this.getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));


        shimmerFrameLayout.startShimmer();


        search_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFragment = SearchFragment.newInstance();
                FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame, searchFragment).commit();
            }
        });


        ValueEventListener mRefUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        ShopmModel shopmModel = ds.getValue(ShopmModel.class);
                        assert shopmModel != null;

                        Log.d("jipe", shopmModel.getProductId());

                        dialog.setContentView(R.layout.layot_otzyvov);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        AppCompatButton appCompatButton = dialog.findViewById(R.id.button_otzyv_otprav);
                        RatingBar ratingBar = dialog.findViewById(R.id.ratingBarotzyvdia);
                        TextView texrstyle = dialog.findViewById(R.id.textstatusOtzyv);
                        TextView zavershitzakaz = dialog.findViewById(R.id.zavershitzakaz);
                        EditText opisanieotzyv = dialog.findViewById(R.id.opisanieotzyv);
                        TextView textViewzaverh = dialog.findViewById(R.id.textViewzaverh);

                        textViewzaverh.setHint(shopmModel.getShopId());
                        zavershitzakaz.setHint(shopmModel.getProductId());

                        refTwoRaw = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                    textViewzaverh.setText(shopmModel.getZaverName() +
                                            " завершил заказ. "
                                            + snapshot.child("clientName").getValue().toString()
                                            + ",оцените нас");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        };
                        Query query = myname.child(mAuth.getCurrentUser().getUid());
                        query.addValueEventListener(refTwoRaw);
                        myListenerList.add(new MyListener(refTwoRaw, query));
                        zavershitzakaz.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                proverka.child(mAuth.getCurrentUser().getUid() + zavershitzakaz.getHint().toString() + textViewzaverh.getHint().toString()).removeValue();
                                dialog.dismiss();
                            }
                        });
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                String text = "";
                                if (v < 1) {
                                    text = "Отвратительно";
                                } else if (v < 2) {
                                    text = "Плохо";
                                } else if (v < 3) {
                                    text = "Cредне";
                                } else if (v < 4) {
                                    text = "Нормально";
                                } else if (v < 5) {
                                    text = "Хорошо";
                                } else if (v < 6) {
                                    text = "Отлично";
                                }
                                texrstyle.setText(text);
                                texrstyle.setHint(String.valueOf(v));

                            }
                        });
                        dialog.show();

                        appCompatButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (TextUtils.isEmpty(opisanieotzyv.getText().toString())) {
                                    Toast.makeText(getContext(), "Опишите ваше мнение ", Toast.LENGTH_SHORT).show();
                                } else {
                                    Calendar calendar = Calendar.getInstance();

                                    SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
                                    saveCurrentDate = currentDate.format(calendar.getTime());

                                    SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
                                    saveCurrentTime = currentTime.format(calendar.getTime());

                                    ProductRandomKey = saveCurrentDate + saveCurrentTime;

                                    HashMap<String, Object> men = new HashMap<>();
                                    men.put("Value", texrstyle.getHint().toString());
                                    men.put("ShopUid", shopmModel.getShopUid());
                                    men.put("text", opisanieotzyv.getText().toString());
                                    otzyv.child(shopmModel.getShopId()).child(mAuth.getCurrentUser().getUid() + ProductRandomKey).updateChildren(men).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Отзыв отправлен,спасибо", Toast.LENGTH_SHORT).show();

                                                proverka.child(mAuth.getCurrentUser().getUid() + shopmModel.getProductId() + shopmModel.getShopId()).removeValue();
                                                dialog.dismiss();
                                            } else {

                                                String message = task.getException().toString();
                                                Toast.makeText(getContext(), "Ошибка" + message, Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                } else {
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        Query query = proverka.orderByChild("Zakazstatus").equalTo(mAuth.getCurrentUser().getUid());
        query.addValueEventListener(mRefUserListener);
        myListenerList.add(new MyListener(mRefUserListener, query));


//        FirebaseRecyclerOptions<ShopmModel> Options = new FirebaseRecyclerOptions.Builder<ShopmModel>()
//                .setQuery(shops.limitToFirst(itemCount), ShopmModel.class).build();
//        magAdapter = new FirebaseRecyclerAdapter<ShopmModel, ClientShopHolder>(Options) {
//            @Override
//            protected void onBindViewHolder(@androidx.annotation.NonNull ClientShopHolder holder,
//                                            int position, @androidx.annotation.NonNull ShopmModel model) {
//
//
//                Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(7).oval(false).build();
//                Picasso.get().load(model.getMagLogo()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.imageLogoApteka, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        recAptek.setVisibility(View.VISIBLE);
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                        shimmerFrameLayout.stopShimmer();
//                        textlog.setVisibility(View.VISIBLE);
//                        rectwohome.setVisibility(View.VISIBLE);
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                        shimmerFrameLayout.stopShimmer();
//                        textesti.setVisibility(View.VISIBLE);
//                        search_click.setVisibility(View.VISIBLE);
//                    }
//
//                    @Override
//                    public void onError(Exception e) {
//                        Picasso.get().load(model.getMagLogo()).transform(transformation).into(holder.imageLogoApteka);
//                        recAptek.setVisibility(View.VISIBLE);
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                        shimmerFrameLayout.stopShimmer();
//                        textlog.setVisibility(View.VISIBLE);
//                        rectwohome.setVisibility(View.VISIBLE);
//                        shimmerFrameLayout.setVisibility(View.GONE);
//                        shimmerFrameLayout.stopShimmer();
//                        textesti.setVisibility(View.VISIBLE);
//                        search_click.setVisibility(View.VISIBLE);
//                    }
//                });
//                if (rectwohome.getVisibility() != View.INVISIBLE) {
//                    Log.d("pr", "естть");
//                } else {
//                    Log.d("pr", "нет");
//                }
//                holder.aotekaname.setText(model.getMagName());
//                holder.aotekaname.setHint(model.getMagUid());
//
//                holder.card_item.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(view.getContext(), TovarActivity.class);
//                        intent.putExtra("ShopUid", holder.aotekaname.getHint().toString());
//                        view.getContext().startActivity(intent);
//                        getActivity().finish();
//                    }
//                });
//
//                ValueEventListener listener = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        testint = Integer.valueOf("" + snapshot.getChildrenCount());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//
//                };
//                Query query1 = prover.child(holder.aotekaname.getHint().toString());
//                query1.addListenerForSingleValueEvent(listener);
//                myListenerList.add(new MyListener(listener, query1));
//
//
//                ValueEventListener list1 = new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
//                            double diablo = 0.0;
//
//                            for (DataSnapshot ds : snapshot.getChildren()) {
//                                ShopmModel shopmModel = ds.getValue(ShopmModel.class);
//                                assert shopmModel != null;
//                                String string = shopmModel.getValue();
//                                otztvValues = Double.parseDouble(string);
//
//                                diablo = diablo + otztvValues;
//                                itog = diablo / testint;
//
//                                holder.rateyellow1.setVisibility(getRateVisibility(itog >= 0.5));
//                                holder.rateyellow2.setVisibility(getRateVisibility(itog >= 1.5));
//                                holder.rateyellow3.setVisibility(getRateVisibility(itog >= 2.5));
//                                holder.rateyellow4.setVisibility(getRateVisibility(itog >= 3.5));
//                                holder.rateyellow5.setVisibility(getRateVisibility(itog >= 4.5));
//
//
//                                String result = String.format("%.3f", itog);
//
//                                holder.textstar.setText(result);
//                            }
//                        }
//                    }
//
//                    private int getRateVisibility(boolean b) {
//                        return b ? View.VISIBLE : View.INVISIBLE;
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                };
//
//                Query query2 = otzest.child(holder.aotekaname.getHint().toString());
//                query2.addValueEventListener(list1);
//                myListenerList.add(new MyListener(list1, query2));
//
//            }
//
//            @Override
//            public ClientShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apteka
//                        , parent, false);
//                return new ClientShopHolder(view);
//            }
//        };
//        recAptek.setAdapter(magAdapter);
//        magAdapter.startListening();

///Scroll///


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query base=shops;
        PagingConfig pagingConfig=new PagingConfig(8,4,false);
        DatabasePagingOptions<ShopmModel>options1=new DatabasePagingOptions.Builder<ShopmModel>()
                .setLifecycleOwner(this)
                .setQuery(base,pagingConfig,ShopmModel.class)
                .build();
        magAdapter = new FirebaseRecyclerPagingAdapter<ShopmModel, ClientShopHolder>(options1){


            @NonNull
            @Override
            public ClientShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apteka
                        , parent, false);
                return new ClientShopHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ClientShopHolder holder, int position, @NonNull ShopmModel model) {
                Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(7).oval(false).build();
                Picasso.get().load(model.getMagLogo()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.imageLogoApteka, new Callback() {
                    @Override
                    public void onSuccess() {
                        recAptek.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textlog.setVisibility(View.VISIBLE);
                        rectwohome.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textesti.setVisibility(View.VISIBLE);
                        search_click.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(model.getMagLogo()).transform(transformation).into(holder.imageLogoApteka);
                        recAptek.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textlog.setVisibility(View.VISIBLE);
                        rectwohome.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textesti.setVisibility(View.VISIBLE);
                        search_click.setVisibility(View.VISIBLE);
                    }
                });
                if (rectwohome.getVisibility() != View.INVISIBLE) {
                    Log.d("pr", "естть");
                } else {
                    Log.d("pr", "нет");
                }
                holder.aotekaname.setText(model.getMagName());
                holder.aotekaname.setHint(model.getMagUid());

                holder.card_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Intent intent = new Intent(view.getContext(), TovarActivity.class);
//                        intent.putExtra("ShopUid", holder.aotekaname.getHint().toString());
//                        view.getContext().startActivity(intent);
//                        getActivity().finish();
                        Bundle data=new Bundle();
                        data.putString("ShopUid", holder.aotekaname.getHint().toString());
                        tovarBetaFragment=TovarBetaFragment.newInstance();
                        FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                        tovarBetaFragment.setArguments(data);
                        fm.replace(R.id.frame,tovarBetaFragment).commit();
                    }
                });


                ValueEventListener list1 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            double diablo = 0.0;
int testint=(int) snapshot.getChildrenCount();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                ShopmModel shopAdapter = ds.getValue(ShopmModel.class);
                                assert shopAdapter != null;
                                String string = shopAdapter.getValue();
                                otztvValues = Double.parseDouble(string);

                                diablo = diablo + otztvValues;
                                itog = diablo / testint;

                                holder.rateyellow1.setVisibility(getRateVisibility(itog >= 0.5));
                                holder.rateyellow2.setVisibility(getRateVisibility(itog >= 1.5));
                                holder.rateyellow3.setVisibility(getRateVisibility(itog >= 2.5));
                                holder.rateyellow4.setVisibility(getRateVisibility(itog >= 3.5));
                                holder.rateyellow5.setVisibility(getRateVisibility(itog >= 4.5));


                                String result = String.format("%.1f", itog);

                                holder.textstar.setText(result);
                            }
                        }
                    }

                    private int getRateVisibility(boolean b) {
                        return b ? View.VISIBLE : View.INVISIBLE;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                Query query2 = otzest.child(holder.aotekaname.getHint().toString());
                query2.addValueEventListener(list1);
                myListenerList.add(new MyListener(list1, query2));

            }


        };
        recAptek.setAdapter(magAdapter);
        magAdapter.startListening();
        FirebaseRecyclerOptions<ShopmModel> options = new FirebaseRecyclerOptions.Builder<ShopmModel>()
                .setQuery(shops, ShopmModel.class).build();
        glavAdapter = new FirebaseRecyclerAdapter<ShopmModel, GlavChildHolder>(options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull GlavChildHolder holder, int position, @androidx.annotation.NonNull ShopmModel model) {
                holder.glavmagname.setText(model.getMagName());
                holder.glavmagname.setHint(model.getMagUid());




                ValueEventListener list2 = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            double diablot = 0.0;
int testint2=(int)snapshot.getChildrenCount();
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                ShopmModel shopmModel = ds.getValue(ShopmModel.class);
                                assert shopmModel != null;
                                String string = shopmModel.getValue();
                                otztvValuestwo = Double.parseDouble(string);

                                diablot = diablot + otztvValuestwo;
                                itogt = diablot / testint2;

                                holder.rateyellow1g.setVisibility(getRateVisibility(itogt >= 0.5));
                                holder.rateyellow2g.setVisibility(getRateVisibility(itogt >= 1.5));
                                holder.rateyellow3g.setVisibility(getRateVisibility(itogt >= 2.5));
                                holder.rateyellow4g.setVisibility(getRateVisibility(itogt >= 3.5));
                                holder.rateyellow5g.setVisibility(getRateVisibility(itogt >= 4.5));

                                String result = String.format("%.1f", itogt);

                                holder.textstarg.setText(result);

                            }
                        }
                    }

                    private int getRateVisibility(boolean b) {
                        return b ? View.VISIBLE : View.INVISIBLE;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                Query query2 = otzest.child(holder.glavmagname.getHint().toString());
                query2.addValueEventListener(list2);
                myListenerList.add(new MyListener(list2, query2));


                Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(15).oval(false).build();
                Picasso.get().load(model.getMagLogo()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.imageGlavMag, new Callback() {
                    @Override
                    public void onSuccess() {
                        rectwohome.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        search_click.setVisibility(View.VISIBLE);
                        textesti.setVisibility(View.VISIBLE);
                        recAptek.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textlog.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(model.getMagLogo()).transform(transformation).into(holder.imageGlavMag);
                        rectwohome.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textesti.setVisibility(View.VISIBLE);
                        search_click.setVisibility(View.VISIBLE);
                        recAptek.setVisibility(View.VISIBLE);
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.stopShimmer();
                        textlog.setVisibility(View.VISIBLE);
                    }
                });


            }

            @Override
            public GlavChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child_glavmag, parent, false);
                GlavChildHolder holder = new GlavChildHolder(view);


                return holder;
            }
        };
        rectwohome.setAdapter(glavAdapter);
        glavAdapter.startListening();
    }

    ///Scroll///
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener : myListenerList) {
            listener.unsubscribe();
        }

        magAdapter.stopListening();
        glavAdapter.stopListening();
        myListenerList.clear();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shimmerFrameLayout = null;
        dialog = null;
        recAptek = null;
        rectwohome = null;
        textlog = null;
        textesti = null;
        view = null;
        search_click = null;
        if (magAdapter!=null){
            magAdapter=null;
        }
        if (glavAdapter!=null){
            glavAdapter=null;
        }
        tovarBetaFragment=null;

        searchFragment = null;
    }
}