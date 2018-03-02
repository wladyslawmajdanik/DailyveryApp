package pl.dailyveryapp.ui.activities.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import pl.dailyveryapp.DailyveryApplication;
import pl.dailyveryapp.R;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity implements BaseView {
    @Inject
    public BasePresenter basePresenter;
    public MaterialDialog waitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DailyveryApplication) getApplication()).getComponent().inject(this);
        basePresenter.setView(this);

        waitDialog = new MaterialDialog.Builder(this)
                .title(getResources().getString(R.string.prosze_czekac))
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .widgetColorRes(R.color.colorPrimary)
                .cancelable(true)
                .canceledOnTouchOutside(false)
                .cancelListener(dialogInterface -> finish())
                .build();
    }

    @Override
    public void showError(View view) {
        showErrorSnackBar(view, getResources().getString(R.string.wystapil_blad));

    }

    @Override
    public void handleNoInternetState(View view) {
        showErrorSnackBar(view, getResources().getString(R.string.brak_dostepu_do_internetu));

    }

    public void hideWaitDialog() {
        try {
            if (waitDialog != null && waitDialog.isShowing()) {
                waitDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showErrorSnackBar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextColor(getResources().getColor(R.color.white));
        snackBarView.setBackgroundColor(getResources().getColor(R.color.errorColor));
        snackbar.show();
    }

    public boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        Timber.d("NETWORK AVAIBLE? %S", haveConnectedWifi || haveConnectedMobile);
        return haveConnectedWifi || haveConnectedMobile;
    }
}
