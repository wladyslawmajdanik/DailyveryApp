package pl.dailyveryapp.ui.activities.productslist;


import java.util.List;

import pl.dailyveryapp.model.products.Products;
import pl.dailyveryapp.ui.View;

/**
 * Created by samsung on 2018-03-02.
 */

public interface ProductsListView extends View {
    void onSuccessDownloadProductList(List<Products> productList);

    void onErroDownloadProductList();
}
