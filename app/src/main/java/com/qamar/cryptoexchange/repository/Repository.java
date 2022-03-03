package com.qamar.cryptoexchange.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.qamar.cryptoexchange.R;
import com.qamar.cryptoexchange.api.CryptoApi;
import com.qamar.cryptoexchange.di.NetworkResponse;
import com.qamar.cryptoexchange.model.Currency;
import com.qamar.cryptoexchange.model.CurrencyResponseModel;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository implements ICurrencyRepository {

    private final int OK = HttpURLConnection.HTTP_OK;
    @Inject
    CryptoApi api;

    @Inject
    Application app;

    @Inject
    Repository() {
    }

    @Override
    public void getCurrencyList(MutableLiveData<NetworkResponse<List<Currency>>> liveData) {
        api.getCurrencyRates().enqueue(new Callback<CurrencyResponseModel>() {
            @Override
            public void onResponse(Call<CurrencyResponseModel> call, Response<CurrencyResponseModel> response) {
                if (response.code() == OK) {
                    if (response != null && response.body() != null) {

                        Iterator<String> iterator = response.body().rates.keySet().iterator();
                        List<Currency> rawCurrencyList = new ArrayList<>();
                        Currency usdCurrency = null;
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            if (key.equals("usd")) {
                                usdCurrency = response.body().rates.get(key);
                            }
                            rawCurrencyList.add(response.body().rates.get(key));
                        }
                        if (rawCurrencyList != null) {
                            List<Currency> processedList = new ArrayList<>();
                            for (int i = 0; i < rawCurrencyList.size(); i++) {
                                if (rawCurrencyList.get(i).type.equals("crypto")) {
                                    Currency currencyObj = rawCurrencyList.get(i);
                                    if (usdCurrency != null) {
                                        currencyObj.priceUSD = usdCurrency.value / currencyObj.value;
                                    }
                                    processedList.add(currencyObj);
                                }
                            }
                            liveData.postValue(new NetworkResponse(true, processedList, app.getString(R.string.message_network_success)));
                        } else {
                            liveData.postValue(new NetworkResponse(true, null, app.getString(R.string.message_network_invalid_data)));
                        }
                    } else {
                        liveData.postValue(new NetworkResponse(true, null, app.getString(R.string.message_network_invalid_data)));
                    }

                }
            }

            @Override
            public void onFailure(Call<CurrencyResponseModel> call, Throwable t) {
                liveData.postValue(new NetworkResponse(false, null, app.getString(R.string.message_network_error)));
            }
        });
    }
}
