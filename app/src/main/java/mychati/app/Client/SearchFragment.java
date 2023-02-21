package mychati.app.Client;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import java.util.List;

import mychati.app.Client.ClientShopsHolders.ClientShopHolder;
import mychati.app.Client.ClientShopsHolders.HomeFragment;
import mychati.app.Client.ClientShopsModel.ShopmModel;
import mychati.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private EditText search_edits;
    private ImageView arrowhome;
    View view;
    private Double itog;
    private Double itogt;
    private Double otztvValues;
    private List<MyListener> myListenerList=new ArrayList<>();
    private Double otztvValuestwo;
    private DatabaseReference otzest;
    private int testint;
    private int testintTwo;
private TovarBetaFragment tovarBetaFragment;
    private DatabaseReference databaseReference;
    private RecyclerView search_recycler;
    private HomeFragment homeFragment;
    private static SearchFragment instance=null;
    private FirebaseRecyclerAdapter<ShopmModel, ClientShopHolder> magAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance(){
        if (instance==null){
            instance=new SearchFragment();
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
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
       view=inflater.inflate(R.layout.fragment_search, container, false);
       search_edits=view.findViewById(R.id.search_edits);
        otzest = FirebaseDatabase.getInstance().getReference().child("otzyv");
       search_recycler=view.findViewById(R.id.search_recycler);
       databaseReference= FirebaseDatabase.getInstance().getReference().child("shops");
       search_recycler.setHasFixedSize(false);
        arrowhome=view.findViewById(R.id.arrowhome);
        search_recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        arrowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             homeFragment=homeFragment.newInstance();
                FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                fm.replace(R.id.frame,homeFragment).commit();
            }
        });

        search_edits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString()!=null){

Loaddata(editable.toString());
magAdapter.startListening();
                }else{
                    Loaddata("");
                    magAdapter.startListening();
                }
            }
        });
       return view;
    }

    private void Loaddata(String toString) {
        Log.d("fff",toString);
        FirebaseRecyclerOptions<ShopmModel> Options = new FirebaseRecyclerOptions.Builder<ShopmModel>()
                .setQuery(databaseReference.orderByChild("MagName").startAt(toString).endAt(toString+"\uf8ff").limitToFirst(50), ShopmModel.class).build();
        magAdapter = new FirebaseRecyclerAdapter<ShopmModel, ClientShopHolder>(Options) {
            @Override
            protected void onBindViewHolder(@androidx.annotation.NonNull ClientShopHolder holder, int position, @androidx.annotation.NonNull ShopmModel model) {
                if (model.getMagName()!=null) {
                    Transformation transformation = new RoundedTransformationBuilder().borderColor(Color.WHITE).borderWidthDp(3).cornerRadius(7).oval(false).build();
                    Picasso.get().load(model.getMagLogo()).networkPolicy(NetworkPolicy.OFFLINE).transform(transformation).into(holder.imageLogoApteka, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(model.getMagLogo()).transform(transformation).into(holder.imageLogoApteka);

                        }
                    });

                    holder.aotekaname.setText(model.getMagName());
                    holder.aotekaname.setHint(model.getMagUid());
                    Log.d("Окси", holder.aotekaname.getHint().toString());
                    holder.card_item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            Intent intent = new Intent(view.getContext(), TovarActivity.class);
//                            intent.putExtra("ShopUid", holder.aotekaname.getHint().toString());
//                            view.getContext().startActivity(intent);
//                            getActivity().finish();
                            tovarBetaFragment=TovarBetaFragment.newInstance();
                            Bundle data=new Bundle();
                            data.putString("ShopUid",holder.aotekaname.getHint().toString());
                            FragmentTransaction fm=getActivity().getSupportFragmentManager().beginTransaction();
                            tovarBetaFragment.setArguments(data);
                            fm.replace(R.id.frame,tovarBetaFragment).commit();
                        }
                    });

                    ValueEventListener listener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            testint = Integer.valueOf("" + snapshot.getChildrenCount());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }

                    };
                    Query query1 = otzest.child(holder.aotekaname.getHint().toString());
                    query1.addListenerForSingleValueEvent(listener);
                    myListenerList.add(new MyListener(listener, query1));


                    ValueEventListener list1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                                double diablo = 0.0;

                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    ShopmModel shopmModel = ds.getValue(ShopmModel.class);
                                    assert shopmModel != null;
                                    String string = shopmModel.getValue();
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

            }

            @Override
            public ClientShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_apteka
                        , parent, false);
                return new ClientShopHolder(view);
            }
        };
        search_recycler.setAdapter(magAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        Loaddata("");
        magAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        for (MyListener listener:myListenerList){
            listener.unsubscribe();
        }
        myListenerList.clear();
        magAdapter.stopListening();
        search_edits.setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (magAdapter!=null){
            magAdapter=null;
        }

        myListenerList.clear();
        search_recycler=null;
        search_edits=null;
        otzest=null;
        view=null;
        arrowhome=null;
        homeFragment=null;
    }


}