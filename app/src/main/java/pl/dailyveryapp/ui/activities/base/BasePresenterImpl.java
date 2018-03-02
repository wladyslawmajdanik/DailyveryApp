package pl.dailyveryapp.ui.activities.base;


public class BasePresenterImpl implements BasePresenter {


    private BaseView view;

    public BasePresenterImpl() {
    }

    @Override
    public void setView(BaseView view) {
        this.view = view;
    }


}
