package saluja.com.saluja.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import saluja.com.saluja.ProductModel;
import saluja.com.saluja.R;
import saluja.com.saluja.ui.activity.ProductDetailActivity;


/**
 * Created by amar on 7/23/2018.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private List<ProductModel> list;
    private ArrayList<String> productLink;
    Context ctx;
    ArrayList<String> fav_id_list;

    public ProductAdapter(List<ProductModel> moviesList, Context context, ArrayList<String> productLink) {
        this.list = moviesList;
        this.productLink = productLink;
        this.ctx = context;
    }

    public ProductAdapter(List<ProductModel> moviesList, Context context) {
        this.list = moviesList;
        this.ctx = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_item, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView product_name, product_price, old_price;
        ImageView product_img, product_fave;

        public MyViewHolder(View view) {
            super(view);
            product_name = view.findViewById(R.id.proName);
            product_price = view.findViewById(R.id.proPrice);
            product_img = view.findViewById(R.id.proImage);
            old_price = view.findViewById(R.id.proPrice1);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ctx , ProductDetailActivity.class);
            intent.putExtra("Product_ID", list.get(getAdapterPosition()).getPro_id());
            ctx.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ProductModel product = list.get(position);
        holder.product_name.setText(product.getPro_name());
        holder.product_price.setText("Rs. "+product.getPro_price());
       // holder.product_img.setImageResource(product.getPro_img());
        Picasso.with(ctx).load(product.getPro_image()).placeholder(R.drawable.mobile_icon)
                .error(R.drawable.mobile_icon).into(holder.product_img);
        holder.old_price.setText("Rs. "+product.getOld_price());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
