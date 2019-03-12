package saluja.com.saluja.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saluja.com.saluja.R;
import saluja.com.saluja.database.HelperManager;
import saluja.com.saluja.model.ProductDetail;


public class AdapterConfirmation extends RecyclerView.Adapter<AdapterConfirmation.MyViewHolder> {

    ArrayList<ProductDetail> list;
    Context context;
    HelperManager helperManager;

    public AdapterConfirmation(ArrayList<ProductDetail> list, Context context) {
        this.list = list;
        this.context = context;
        helperManager = new HelperManager(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_tv, size_tv, color_tv, price_tv, qty_tv,tv_adpcart_edit;
        ImageView pro_image_iv, plus_iv, minus_iv;

        public MyViewHolder(View view) {
            super(view);
            name_tv = view.findViewById(R.id.tv_adpcart_name);
            size_tv = view.findViewById(R.id.tv_adpcart_size);
            color_tv = view.findViewById(R.id.tv_adpcart_color);
            price_tv = view.findViewById(R.id.tv_adpcart_price);
            qty_tv = view.findViewById(R.id.tv_adpcart_qty);
            tv_adpcart_edit = view.findViewById(R.id.tv_adpcart_edit);

            pro_image_iv = view.findViewById(R.id.iv_adpcart_image);
            plus_iv = view.findViewById(R.id.iv_adpcart_plus);
            minus_iv = view.findViewById(R.id.iv_adpcart_minus);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adp_cartview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductDetail productDetail = list.get(position);
        holder.name_tv.setText(productDetail.getName());
        holder.price_tv.setText("₹ " + productDetail.getPrice());
        holder.size_tv.setText("Size : " + productDetail.getSelected_size());
        holder.color_tv.setText("Color : " + productDetail.getSelected_color());
        holder.qty_tv.setText(list.get(position).getQuantity() + "");

        Picasso.with(context)
                .load(productDetail.getImage())
                .placeholder(R.drawable.mobile_icon)
                .error(R.drawable.mobile_icon)
                .resize(300, 300)
                .into(holder.pro_image_iv);

        holder.tv_adpcart_edit.setVisibility(View.GONE);
        holder.plus_iv.setVisibility(View.GONE);
        holder.minus_iv.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
