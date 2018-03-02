package pl.dailyveryapp.ui.activities.productslist;


import java.util.List;

import pl.dailyveryapp.model.products.Products;
import pl.dailyveryapp.ui.View;

public interface ProductsListView extends View {
    void onSuccessDownloadProductList(List<Products> productList);

    void onErrorDownloadProductList();

    void onMakeOrderSuccess();

    void onMakeOrderError();
}
