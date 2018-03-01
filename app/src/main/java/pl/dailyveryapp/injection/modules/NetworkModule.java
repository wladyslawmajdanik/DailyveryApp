package pl.dailyveryapp.injection.modules;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.dailyveryapp.injection.scopes.AppScope;
import pl.dailyveryapp.repository.remote.RemoteRepository;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module(includes = {ContextModule.class})
public class NetworkModule {

    private String dailyveryBaseUrl;

    public NetworkModule(String baseUrl) {
        this.dailyveryBaseUrl = baseUrl;
    }

    @Provides
    @AppScope
    public RemoteRepository remoteRepository(@Named("dailyveryapi") Retrofit dailyveryRetrofit) {
        return dailyveryRetrofit.create(RemoteRepository.class);
    }

    @Provides
    @AppScope
    public HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String s) {
                        Timber.d("interceptor message: %s", s);
                    }
                });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @AppScope
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient();
        return gsonBuilder.create();
    }

    @Provides
    @AppScope
    public OkHttpClient
    provideHttpClient(HttpLoggingInterceptor loggingInterceptor) {

        return new OkHttpClient()
                .newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @AppScope
    @Named("dailyveryapi")
    public Retrofit provideDailyeryRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new  Retrofit.Builder()
                .baseUrl(dailyveryBaseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }


}
