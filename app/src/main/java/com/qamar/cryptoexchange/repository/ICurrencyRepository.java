package com.qamar.cryptoexchange.repository;

import androidx.lifecycle.MutableLiveData;

import com.qamar.cryptoexchange.di.NetworkResponse;
import com.qamar.cryptoexchange.model.Currency;

import java.util.List;

public interface ICurrencyRepository {

    public void getCurrencyList(MutableLiveData<NetworkResponse<List<Currency>>> liveData);
}
