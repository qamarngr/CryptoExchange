package com.qamar.cryptoexchange.repository;

import androidx.lifecycle.MutableLiveData;

import com.qamar.cryptoexchange.model.GeneralResponseModel;
import com.qamar.cryptoexchange.model.Currency;

import java.util.List;

public interface ICurrencyRepository {

    void getCurrencyList(MutableLiveData<GeneralResponseModel<List<Currency>>> liveData);
}
