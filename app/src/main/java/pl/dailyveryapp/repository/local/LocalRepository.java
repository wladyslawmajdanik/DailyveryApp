package pl.dailyveryapp.repository.local;

import java.util.List;

import pl.dailyveryapp.model.products.Products;

public interface LocalRepository {
    void checkProducts(List<Products> productList);

    void changeBasket(Products products);
}
