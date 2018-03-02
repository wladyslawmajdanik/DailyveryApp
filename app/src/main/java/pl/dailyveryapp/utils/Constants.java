package pl.dailyveryapp.utils;

import pl.dailyveryapp.BuildConfig;

public class Constants {

    public final static String SHARED_PREFERENCES = "shared_preferences";
    public final static String RESTAURANT_DATA = "restaurant_data";

    public static String getBaseUrl() {
        return BuildConfig.BASE_URL;
    }


}
