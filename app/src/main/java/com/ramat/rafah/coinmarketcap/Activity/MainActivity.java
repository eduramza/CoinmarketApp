package com.ramat.rafah.coinmarketcap.Activity;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ramat.rafah.coinmarketcap.Adapter.CoinsAdapter;
import com.ramat.rafah.coinmarketcap.Model.Coins;
import com.ramat.rafah.coinmarketcap.R;
import com.ramat.rafah.coinmarketcap.Util.HTTPService;
import com.ramat.rafah.coinmarketcap.Util.Inbackground;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static java.lang.Thread.interrupted;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    //ATRIBUTOS
    private ArrayAdapter adapter;
    private ListView listView;
    private ArrayList<Coins> arrayList;
    private Toolbar toolbar;

    private final long intervalo = 5000;
    private int email = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CONFIGURANDO A TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("CoinmarketCap");
        setSupportActionBar(toolbar); //indispensavel para o funcionamento da toolbar

        //********* CONFIGURANDO A LISTAGEM DAS MOEDAS **********/
        listView = (ListView) findViewById(R.id.lv_coins);

        arrayList = new ArrayList<>();
        adapter = new CoinsAdapter(MainActivity.this, arrayList);
        listView.setAdapter(adapter);

        //********* FIM CONFIGURANDO A LISTAGEM DAS MOEDAS **********/
        recarregar();

        //EXECUTANDO A TAREFA DE CALCULO
        Timer timer = new Timer();
        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                try{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (email == 1){
                                enviarEmail(new String[]{"ramattec@gmail.com", "eduardo.rmb@outlook.com"}
                                            , "Testando o envio do App");
                                Toast.makeText(MainActivity.this, "Caiu Aqui! Email", Toast.LENGTH_SHORT).show();
                                email += 1;
                            }

                            //Toast.makeText(MainActivity.this, "Testando toast UI", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //Inbackground inbackground = new Inbackground(MainActivity.this);
                    //inbackground.run();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(tarefa, intervalo, intervalo);
    }

    /********************* METODOS RELACIONADOS AOS MENUS ***********************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //instanciando o menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_search:
                return true;
            case R.id.item_refresh:
                Toast.makeText(this, "Clicado em recarregar", Toast.LENGTH_SHORT).show();
                recarregar();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void recarregar(){
        try{
            Coins coins = new HTTPService(arrayList, MainActivity.this)
                    .execute()
                    .get();

            adapter.notifyDataSetChanged();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void enviarEmail(String[] address, String subject){
        Intent envio = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+address));
        envio.putExtra(Intent.EXTRA_SUBJECT, subject);
        envio.putExtra(Intent.EXTRA_TEXT, "Enviei direto do app CoinmarketCap");

        //if (envio.resolveActivity(getPackageManager()) != null){
            startActivity(Intent.createChooser(envio, "Chooser Tittle"));
        //}
    }

}
