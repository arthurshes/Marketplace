package mychati.app.Client.HorizontalCartTovar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import mychati.app.Client.ItemClickListner.ItemClickListener;
import mychati.app.R;

public class HorizontalTovarHolderCart extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textnamehorizontaltovar,textpricehorizontaltovar,tovarcarthorizontal;
    public AppCompatButton addbuttonhorizonttaltovar,tovarpluscarthorizontal,tovarminuscarthorizontal;
    public CardView cardhorizontaltovar;
    public ItemClickListener itemClickListener;
    public ImageView imagehorizontaltovar;
    public HorizontalTovarHolderCart(View itemView){
        super(itemView);
        textnamehorizontaltovar=itemView.findViewById(R.id. textnamehorizontaltovar);
        textpricehorizontaltovar=itemView.findViewById(R.id.  textpricehorizontaltovar);
        tovarcarthorizontal=itemView.findViewById(R.id.tovarcarthorizontal);
        tovarminuscarthorizontal=itemView.findViewById(R.id. tovarminuscarthorizontal);
        tovarpluscarthorizontal=itemView.findViewById(R.id.tovarpluscarthorizontal);
        addbuttonhorizonttaltovar=itemView.findViewById(R.id.addbuttonhorizonttaltovar);
        imagehorizontaltovar=itemView.findViewById(R.id.imagehorizontaltovar);
        cardhorizontaltovar=itemView.findViewById(R.id.cardhorizontaltovar);
    }
    public void setItemClickListner(ItemClickListener listner){this.itemClickListener=listner;}

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
