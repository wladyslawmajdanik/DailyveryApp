package pl.dailyveryapp.ui.activities.productslist;


import org.json.JSONArray;

import java.util.List;

import pl.dailyveryapp.model.products.Products;

public interface ProductsListPresenter {

    void setView(ProductsListView view);

    void getProducts(int restaurantId);

    void changeBasket(Products products);

    void checkProducts(List<Products> productList);

    void orderProducts(int restaurantId, JSONArray productsToOrder);

    void removeBasket();
}
