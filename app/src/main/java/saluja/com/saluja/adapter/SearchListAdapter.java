package saluja.com.saluja.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import saluja.com.saluja.ProductModel;
import saluja.com.saluja.R;


public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> implements Filterable {

    private List<ProductModel> filteredAllUserLists;
    private List<ProductModel> allUserLists;
    private Context context;
    private SearchAdapterListener searchAdapterListener;
    private View.OnClickListener onClickListener;

    public SearchListAdapter(List<ProductModel> allUser, Context context, SearchAdapterListener searchAdapterListener , View.OnClickListener onClickListener) {
        filteredAllUserLists = new ArrayList<>();
        allUserLists = new ArrayList<>();
        this.allUserLists = allUser;
        this.filteredAllUserLists = allUser;
        this.context = context;
        this.searchAdapterListener = searchAdapterListener;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater li = LayoutInflater.from(context);
        View viewt = li.inflate(R.layout.custom_product_item, null);
        return new ViewHolder(viewt);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        ProductModel product = filteredAllUserLists.get(i);


        viewHolder.product_name.setText(product.getPro_name());
        viewHolder.product_price.setText("Rs. "+product.getPro_price());
        // holder.product_img.setImageResource(product.getPro_img());
        Picasso.with(context).load(product.getPro_image()).placeholder(R.drawable.mobile_icon)
                .error(R.drawable.mobile_icon).into(viewHolder.product_img);
        viewHolder.old_price.setText("Rs. "+product.getOld_price());


        viewHolder.llProduct.setTag(i);
        viewHolder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchAdapterListener.onSearchSelected(filteredAllUserLists.get(i));
            }
        });
       /* viewHolder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAdapterListener.onSearchSelected(filteredAllUserLists.get(i));
            }
        });*/

        if (i == 0 || i == 1) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(24, 56, 24, 24);
            viewHolder.llItem.setLayoutParams(params);
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(24, 12, 24, 12);
            viewHolder.llItem.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return filteredAllUserLists.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredAllUserLists = allUserLists;
                } else {
                    List<ProductModel> filteredList = new ArrayList<>();
                    for (ProductModel row : allUserLists) {
                        String regex = "(.)*(\\d)(.)*";
                        Pattern pattern = Pattern.compile(regex);
                        String msg = row.getPro_name();
                        boolean containsNumber = pattern.matcher(msg).matches();
                        if (containsNumber) {
                            filteredList.add(row);
                        } else {
                            if (row.getPro_name().toLowerCase().contains(charString.toLowerCase()) ||
                                    row.getPro_name().toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }
                    }
                    filteredAllUserLists = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredAllUserLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredAllUserLists = (ArrayList<ProductModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llProduct;
        TextView product_name, product_price, old_price;
        ImageView product_img, product_fave;
        private CardView llItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llProduct = itemView.findViewById(R.id.ll_product1);
            llItem = itemView.findViewById(R.id.llItem);
            product_name = itemView.findViewById(R.id.proName);
            product_price = itemView.findViewById(R.id.proPrice);
            product_img = itemView.findViewById(R.id.proImage);
            old_price = itemView.findViewById(R.id.proPrice1);
        }
    }

    public interface SearchAdapterListener {
        void onSearchSelected(ProductModel contact);


    }
}

