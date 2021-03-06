package com.qamar.cryptoexchange.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qamar.cryptoexchange.model.GeneralResponseModel;
import com.qamar.cryptoexchange.model.Currency;
import com.qamar.cryptoexchange.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainActivityViewModel extends ViewModel {
    private static final String TAG = "MainViewModel";
    Repository repository;

    @Inject
    MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    private MutableLiveData<GeneralResponseModel<List<Currency>>> _currencyList;

    public LiveData<GeneralResponseModel<List<Currency>>> getCurrencyList() {
        if (_currencyList == null) {
            _currencyList = new MutableLiveData<>();
        }
        loadCurrencyRates();

        return _currencyList;
    }

    private void loadCurrencyRates() {
        Log.d(TAG, "loadCurrencyRates "+System.currentTimeMillis());
        repository.getCurrencyList(_currencyList);
    }
}
