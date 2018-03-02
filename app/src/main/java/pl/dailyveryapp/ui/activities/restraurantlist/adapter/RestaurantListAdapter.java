package pl.dailyveryapp.ui.activities.restraurantlist.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.dailyveryapp.R;
import pl.dailyveryapp.model.restaurants.Restaurant;
import pl.dailyveryapp.ui.activities.productslist.ProductsListActivity;

import static pl.dailyveryapp.utils.Constants.RESTAURANT_DATA;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.OrderTabViewHolder> {
    private LayoutInflater layoutInflater;
    private Activity activity;
    private List<Restaurant> restaurantList;

    public RestaurantListAdapter(Activity activity, List<Restaurant> restaurantList) {
        this.activity = activity;
        this.layoutInflater = activity.getLayoutInflater();
        this.restaurantList = restaurantList;
    }

    @Override
    public OrderTabViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.restaurant_list_item_view, viewGroup, false);

        return new OrderTabViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final OrderTabViewHolder viewHolder, int i) {
        viewHolder.restaurantName.setText(restaurantList.get(i).getName());
        Glide.with(activity)
                .load(restaurantList.get(i).getImageUrl())
                .into(viewHolder.restaurantImage);
        viewHolder.cardView.setOnClickListener(v ->
                activity.startActivity(new Intent(activity, ProductsListActivity.class).putExtra(RESTAURANT_DATA, restaurantList.get(i))));
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }


    public class OrderTabViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_name)
        TextView restaurantName;
        @BindView(R.id.restaurant_image)
        ImageView restaurantImage;
        @BindView(R.id.card_view)
        CardView cardView;

        private OrderTabViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

