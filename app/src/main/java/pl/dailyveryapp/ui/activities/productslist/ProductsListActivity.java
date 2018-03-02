package pl.dailyveryapp.ui.activities.productslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.dailyveryapp.DailyveryApplication;
import pl.dailyveryapp.R;
import pl.dailyveryapp.model.products.Products;
import pl.dailyveryapp.model.restaurants.Restaurant;
import pl.dailyveryapp.ui.activities.base.BaseActivity;
import pl.dailyveryapp.ui.activities.productslist.adapter.ProductListAdapter;

import static pl.dailyveryapp.utils.Constants.RESTAURANT_DATA;

public class ProductsListActivity extends BaseActivity implements ProductsListView {
    @Inject
    public ProductsListPresenter productsListPresenter;
    Restaurant restaurant;
    @BindView(R.id.restaurant_name)
    TextView restaurantName;
    @BindView(R.id.restaurant_image)
    ImageView restaurantImage;
    @BindView(R.id.products_list_cointainer)
    RelativeLayout productsListCointainer;
    @BindView(R.id.products_list_recycler)
    RecyclerView productsRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        ButterKnife.bind(this);
        ((DailyveryApplication) getApplication()).getComponent().inject(this);
        productsListPresenter.setView(this);
        restaurant = getIntent().getParcelableExtra(RESTAURANT_DATA);

        initView();
        getProduct();
    }


    @Override
    public void onSuccessDownloadProductList(List<Products> productList) {
        setProductsListAdapter(productList);
        hideWaitDialog();
    }

    @Override
    public void onErroDownloadProductList() {
        hideWaitDialog();
        showError(productsListCointainer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideWaitDialog();
    }

    private void initView() {
        restaurantName.setText(restaurant.getName());
        Glide.with(this)
                .load(restaurant.getImageUrl())
                .into(restaurantImage);
    }

    private void getProduct() {
        if (isNetworkAvailable()) {
            waitDialog.show();
            productsListPresenter.getProducts(restaurant.getId());
        } else {
            handleNoInternetState(productsListCointainer);
        }

    }

    private void setProductsListAdapter(List<Products> productList) {


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        productsRecycler.setLayoutManager(layoutManager);
        productsListPresenter.checkProducts(productList);
        ProductListAdapter productListAdapter = new ProductListAdapter(this, productList);
        productsRecycler.setAdapter(productListAdapter);
    }

    public void modifyBasket(Products products) {
        productsListPresenter.changeBasket(products);
    }

}
