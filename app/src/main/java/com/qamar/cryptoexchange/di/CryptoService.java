package com.qamar.cryptoexchange.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qamar.cryptoexchange.BuildConfig;
import com.qamar.cryptoexchange.api.CryptoApi;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class CryptoService {
    private static final String TAG = "CryptoService";
    @Provides
    public static CryptoApi provideAnalyticsService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(new OkHttpClient.Builder()
                        /*.addInterceptor(chain -> {
                            Response response = chain.proceed(chain.request());
                            Log.d(TAG,response.body().string() );
                            return response;
                        })*/
                        .callTimeout(50, TimeUnit.SECONDS)
                        .build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(CryptoApi.class);
    }
}
