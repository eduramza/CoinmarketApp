package com.ramat.rafah.coinmarketcap.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ramat.rafah.coinmarketcap.Model.Coins;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rafah on 12/01/2018.
 */

public class HTTPService extends AsyncTask<Void, Void, Coins> {
    //ATRIBUTOS
    private ArrayAdapter adapter;
    private ListView listView;
    private Context context;
    private List<Coins>  lCoins;
    private ArrayList<Coins> arrayList;
    private ProgressBar load;

    public HTTPService(ArrayList<Coins> coins, Context c) {
        this.arrayList = coins;
        this.context = c;
    }


    @Override
    protected Coins doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        try{
            //URL QUE SERÁ CONSUMIDA
            URL url = new URL("https://api.coinmarketcap.com/v1/ticker/?limit=20");

            //---- ABRINDO A CONEXÃO ---
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Content-type","application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);
            conexao.setConnectTimeout(5000);
            conexao.connect();

            //----- LENDO AS INFORMAÇÕES -------
            Scanner scanner = new Scanner(url.openStream());
            arrayList.clear();

            while (scanner.hasNext()){
                resposta.append(scanner.next());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //CONVERTENDO O RETORNO PARA A CLASSE MODELO
        lCoins = new Gson().fromJson(resposta.toString(), new TypeToken<List<Coins>>(){}.getType() );
        //arrayList.add((Coins) lCoins);

        return lCoins.size() > 0 ? lCoins.get(0) : null;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Coins coins) {
        super.onPostExecute(coins);

        for (int i = 0; i < lCoins.size(); i++){
            arrayList.add(lCoins.get(i));
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
