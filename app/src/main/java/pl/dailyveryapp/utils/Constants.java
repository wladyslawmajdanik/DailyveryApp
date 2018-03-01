package pl.dailyveryapp.utils;

import pl.dailyveryapp.BuildConfig;
import timber.log.Timber;

public class Constants {

    public final static String SHARED_PREFERENCES = "shared_preferences";

    public static String getBaseUrl() {
      return BuildConfig.BASE_URL ;
    }


}
