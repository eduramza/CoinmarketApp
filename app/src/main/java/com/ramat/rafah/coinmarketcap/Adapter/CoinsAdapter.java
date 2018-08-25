package com.ramat.rafah.coinmarketcap.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ramat.rafah.coinmarketcap.Model.Coins;
import com.ramat.rafah.coinmarketcap.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafah on 15/01/2018.
 */

public class CoinsAdapter extends ArrayAdapter<Coins> {
    //ATRIBUTOS
    private Context context;
    private ArrayList<Coins> arrayList;

    public CoinsAdapter(@NonNull Context c, ArrayList<Coins> objects) {
        super(c, 0,objects);
        this.context = c;
        this.arrayList = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        //VALIDANDO E CRIANDO A LISTA DE MOEDAS
        if (arrayList != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //montando a view a partir do xml
            view = inflater.inflate(R.layout.list_main, parent, false);

            //RECUPERANDO OS COMPONENTES PARA EXIBICAO
            TextView tvRank = view.findViewById(R.id.tv_rank);
            TextView tvCoin = view.findViewById(R.id.tv_coin);
            TextView tvPreco = view.findViewById(R.id.tv_preco);
            TextView tvMarket = view.findViewById(R.id.tv_market);
            TextView tvVol = view.findViewById(R.id.tv_vol);
            TextView tv1hr = view.findViewById(R.id.tv_1h);
            TextView tv24hr = view.findViewById(R.id.tv_24h);
            TextView tv7d = view.findViewById(R.id.tv_7d);

            //GRAVANDO OS DADOS DE ACORDOD COM A CLASSE MODELO
            Coins coins = arrayList.get(position);
            tvRank.setText(coins.getRank());
            tvCoin.setText(coins.getName() + " ("+coins.getSymbol()+")");
            tvPreco.setText(coins.getPrice_usd());
            tvMarket.setText(coins.getMarket_cap_usd());
            tvVol.setText(coins.getVolume_usd_24h());
            tv1hr.setText(coins.getPercent_change_1h()+"%");
            tv24hr.setText(coins.getPercent_change_24h()+"%");
            tv7d.setText(coins.getPercent_change_7d()+"%");

            //CONFIGURANDO AS CORES
            if (Float.parseFloat(coins.getPercent_change_1h()) < 0){
                tv1hr.setTextColor(Color.parseColor("#FFFF2327"));
            } else if (Float.parseFloat(coins.getPercent_change_24h()) < 0){
                tv24hr.setTextColor(Color.parseColor("#FFFF2327"));
            } else if (Float.parseFloat(coins.getPercent_change_7d()) < 0){
                tv7d.setTextColor(Color.parseColor("#FFFF2327"));
            }

        }
        return view;
    }
}
