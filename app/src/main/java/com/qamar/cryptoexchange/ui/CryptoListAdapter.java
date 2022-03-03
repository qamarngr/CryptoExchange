package com.qamar.cryptoexchange.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qamar.cryptoexchange.R;
import com.qamar.cryptoexchange.databinding.ItemAssetBinding;
import com.qamar.cryptoexchange.model.Currency;
import com.qamar.cryptoexchange.util.Constants;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CryptoListAdapter extends RecyclerView.Adapter<CryptoListAdapter.ViewHolder> {

    private List<Currency> list;
    public CryptoListAdapter(List<Currency> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void setData(List<Currency> data) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAssetBinding binding;
        DecimalFormat format = new DecimalFormat(Constants.CURRENCY_FORMAT);

        public ViewHolder(ItemAssetBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public static ViewHolder from(ViewGroup parent) {
            ItemAssetBinding binding = ItemAssetBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);
        }

        public void bind(Currency currency) {
            Context context = binding.tvName.getContext();
            int drawableRes = context.getResources().getIdentifier("ic_" + currency.unit.toLowerCase(Locale.ROOT), "drawable",
                    context.getPackageName());
            binding.tvName.setText(currency.name);
            binding.tvPriceUsd.setText(Constants.DOLLAR + format.format(currency.priceUSD));
            binding.tvPriceBtc.setText(format.format(currency.value) + Constants.BTC);
            if (drawableRes > 0) {
                binding.ivBadge.setImageResource(drawableRes);
            } else {
                binding.ivBadge.setImageResource(R.drawable.ic_placeholder);
            }
        }
    }
}
