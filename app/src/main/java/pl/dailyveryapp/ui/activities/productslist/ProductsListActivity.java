package pl.dailyveryapp.ui.activities.productslist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    List<Products> productsList;
    Restaurant restaurant;

    @BindView(R.id.restaurant_name)
    TextView restaurantName;
    @BindView(R.id.restaurant_image)
    ImageView restaurantImage;
    @BindView(R.id.products_list_cointainer)
    RelativeLayout productsListCointainer;
    @BindView(R.id.order_button_cointainer)
    RelativeLayout orderButtonCointainer;
    @BindView(R.id.products_list_recycler)
    RecyclerView productsRecycler;

    @OnClick(R.id.order_button)
    public void order() {
        if (isNetworkAvailable()) {
            OrderProducts();
        } else {
            handleNoInternetState(productsListCointainer);
        }

    }

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
        hideWaitDialog();
        setProductsListAdapter(productList);
    }

    @Override
    public void onErrorDownloadProductList() {
        hideWaitDialog();
        showError(productsListCointainer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideWaitDialog();
    }

    @Override
    public void onMakeOrderError() {
        hideWaitDialog();
        showError(productsListCointainer);
    }

    @Override
    public void onMakeOrderSuccess() {
        hideWaitDialog();
        new MaterialDialog.Builder(this)
                .title(R.string.gratulacje)
                .content(R.string.twoje_zamowinie_zostalo_wyslane)
                .widgetColorRes(R.color.colorPrimary)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .positiveText(getResources().getString(R.string.ok))
                .onPositive((dialog, which) -> {
                    productsListPresenter.removeBasket();
                    finish();
                })
                .show();
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
        productsList = productList;
        checkIfCanOrder();
        ProductListAdapter productListAdapter = new ProductListAdapter(this, productList);
        productsRecycler.setAdapter(productListAdapter);
    }

    public void modifyBasket(Products products) {
        productsListPresenter.changeBasket(products);
        checkIfCanOrder();
    }

    private void checkIfCanOrder() {
        int productsInBasket = 0;
        for (int a = 0; a < productsList.size(); a++) {
            if (productsList.get(a).getProductsInBasket() > 0) {
                productsInBasket++;
                break;
            }
        }
        if (productsInBasket > 0) {
            orderButtonCointainer.setVisibility(View.VISIBLE);
        } else {
            orderButtonCointainer.setVisibility(View.GONE);
        }
    }

    private void OrderProducts() {
        waitDialog.show();
        JSONArray orderedProducts = new JSONArray();
        for (int a = 0; a < productsList.size(); a++) {
            if (productsList.get(a).getProductsInBasket() > 0) {
                try {
                    JSONObject orderProduct = new JSONObject();
                    orderProduct.put("id", productsList.get(a).getId());
                    orderProduct.put("size", productsList.get(a).getProductsInBasket());
                    orderedProducts.put(orderProduct);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        productsListPresenter.orderProducts(restaurant.getId(), orderedProducts);
    }

}
