package pl.dailyveryapp.ui.activities.productslist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.dailyveryapp.R;
import pl.dailyveryapp.model.products.Products;
import pl.dailyveryapp.ui.activities.productslist.ProductsListActivity;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.OrderTabViewHolder> {
    private LayoutInflater layoutInflater;
    private ProductsListActivity activity;
    private List<Products> productsList;


    public ProductListAdapter(ProductsListActivity activity, List<Products> productsList) {
        this.activity = activity;
        this.layoutInflater = activity.getLayoutInflater();
        this.productsList = productsList;
    }

    @Override
    public OrderTabViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.product_list_item_view, viewGroup, false);
        return new OrderTabViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final OrderTabViewHolder viewHolder, int i) {
        viewHolder.products = productsList.get(i);
        viewHolder.productName.setText(viewHolder.products.getName());
        viewHolder.productPrice.setText(viewHolder.products.getPrice());
        viewHolder.howMany.setText(String.valueOf(viewHolder.products.getProductsInBasket()));

        viewHolder.addProductToBasket.setOnClickListener(v -> {
            viewHolder.products.setProductsInBasket(viewHolder.products.getProductsInBasket() + 1);
            activity.modifyBasket(productsList.get(i));
            notifyDataSetChanged();
        });
        viewHolder.subtractProductFromBasket.setOnClickListener(v -> {
            if (viewHolder.products.getProductsInBasket() > 0) {
                viewHolder.products.setProductsInBasket(viewHolder.products.getProductsInBasket() - 1);
                activity.modifyBasket(productsList.get(i));
                notifyDataSetChanged();
            }

        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public class OrderTabViewHolder extends RecyclerView.ViewHolder {
        Products products;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.product_price)
        TextView productPrice;
        @BindView(R.id.how_many)
        TextView howMany;
        @BindView(R.id.plus)
        TextView addProductToBasket;
        @BindView(R.id.minus)
        TextView subtractProductFromBasket;

        private OrderTabViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

