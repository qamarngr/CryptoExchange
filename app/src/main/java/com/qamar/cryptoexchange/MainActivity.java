package com.qamar.cryptoexchange;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.qamar.cryptoexchange.databinding.ActivityMainBinding;
import com.qamar.cryptoexchange.model.Currency;
import com.qamar.cryptoexchange.ui.CryptoListAdapter;
import com.qamar.cryptoexchange.ui.MainActivityViewModel;
import com.qamar.cryptoexchange.util.Timer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import needle.Needle;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private final String TAG = "MainActivity";

    static {
        System.loadLibrary("cryptoexchange");
    }

    private ActivityMainBinding binding;

    MainActivityViewModel viewModel;
    CryptoListAdapter adapter = new CryptoListAdapter(new ArrayList<>());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListing();
        new Timer(86400000, 5000, this, () -> viewModel.getCurrencyList()).start();

    }

    private void writeInFile(List<String> list) {
        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                builder.append(list.get(i));
            }
            FileOutputStream fos = this.openFileOutput("log_file.txt", Context.MODE_PRIVATE);
            fos.write(builder.toString().getBytes());
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListing() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        binding.rlCurrency.setLayoutManager(new LinearLayoutManager(this));
        binding.rlCurrency.setAdapter(adapter);
        viewModel.getCurrencyList().observe(this, response -> {
            if (response.isSuccess()) {
                Toast.makeText(this, R.string.message_network_success, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Response Successful " + response.getData());
                adapter.setData(response.getData());
                getLogsFromNative(response.getData());
                Needle.onBackgroundThread().execute(() -> writeInFile(getLogsFromNative(response.getData())));
            } else {
                Toast.makeText(this, R.string.message_error_loading, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    private List<String> getLogsFromNative(List<Currency> currencyList) {
        List<String> logs = new ArrayList<>();
        for (int i = 0; i < currencyList.size(); i++) {
            logs.add(stringFromJNICrypto(currencyList.get(i), Calendar.getInstance().getTime().toString()));
        }
        return logs;
    }

    //native function
    native String stringFromJNICrypto(Currency ob, String timeStamp);
}