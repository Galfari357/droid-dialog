package com.dialogdroid;

import android.app.Dialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dialog.AnimUtils;
import com.dialog.DroidDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openDialog1(View view) {
        new DroidDialog.Builder(MainActivity.this)
                .icon(R.drawable.ic_tinder)
                .title("All Well!")
                .content("test")
                .cancelable(true, true)
                .positiveButton("OK", new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog droidDialog) {
                        droidDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .typeface("raleway_regular.ttf")
                .animation(AnimUtils.AnimFadeInOut)
                .color(ContextCompat.getColor(MainActivity.this, R.color.indigo), ContextCompat.getColor(MainActivity.this, R.color.white),
                        ContextCompat.getColor(MainActivity.this, R.color.dark_indigo))
                .divider(true, ContextCompat.getColor(MainActivity.this, R.color.orange))
                .show();
    }
}
