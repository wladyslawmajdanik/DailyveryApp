package pl.dailyveryapp.ui.activities.productslist;


import java.util.List;

import pl.dailyveryapp.model.products.Products;

public interface ProductsListPresenter {

    void setView(ProductsListView view);

    void getProducts(int restaurantId);

    void changeBasket(Products products);

    void checkProducts(List<Products> productList);

}
