package com.ramat.rafah.coinmarketcap.Util;

import android.content.Context;
import android.widget.Toast;

import com.ramat.rafah.coinmarketcap.Activity.MainActivity;

import java.util.TimerTask;

/**
 * Created by rafah on 20/01/2018.
 */

public class Inbackground extends TimerTask {
    private Context context;

    public Inbackground(Context c){
        this.context = c;
    }

    @Override
    public void run() {
        MainActivity main = new MainActivity();
        main.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Rodando a thread", Toast.LENGTH_SHORT).show();
            }
        });
        //Toast.makeText(context, "Rodando a thread", Toast.LENGTH_SHORT).show();
    }
}
