package com.qamar.cryptoexchange.api;

import com.qamar.cryptoexchange.model.CurrencyResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoApi {
    @GET("exchange_rates")
    Call<CurrencyResponseModel> getCurrencyRates();
}
