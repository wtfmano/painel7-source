package com.br.painel;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.br.painel.RequestNetwork;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class MainActivity extends AppCompatActivity {
    private SharedPreferences Salvar;
    private RequestNetwork.RequestListener _wifi_request_listener;
    private AlertDialog.Builder d1;
    private AlertDialog.Builder di;
    private AlertDialog.Builder dialog;
    private AlertDialog.Builder hd;
    private SharedPreferences hora;
    private AlertDialog.Builder i3;
    private MediaPlayer m;
    private SharedPreferences menu_adm;
    private SharedPreferences sai;
    private SharedPreferences salvarlogin;
    private SharedPreferences save;
    private SoundPool ss;
    private SharedPreferences test;
    private TimerTask timer;
    private RequestNetwork wifi;
    private Timer _timer = new Timer();
    private double dias = 0.0d;
    private double meses = 0.0d;
    private double anos = 0.0d;
    private double pos = 0.0d;
    private double days_num = 0.0d;
    private HashMap<String, Object> mapa = new HashMap<>();
    private double id_num = 0.0d;
    private String id = "";
    private double meses_num = 0.0d;
    private double ano_num = 0.0d;
    private String Path = "";
    private HashMap<String, Object> map = new HashMap<>();
    private ArrayList<HashMap<String, Object>> lista = new ArrayList<>();
    private ArrayList<String> str = new ArrayList<>();
    private Calendar c1 = Calendar.getInstance();
    private Calendar c2 = Calendar.getInstance();
    private Intent Biel_MODz = new Intent();
    private Intent i1 = new Intent();
    private Intent i6 = new Intent();
    private Intent i48 = new Intent();
    private Intent canal = new Intent();
    private ObjectAnimator anim = new ObjectAnimator();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.Salvar = getSharedPreferences("Salvar", 0);
        this.dialog = new AlertDialog.Builder(this);
        this.salvarlogin = getSharedPreferences("salvarlogin", 0);
        this.save = getSharedPreferences("save", 0);
        this.sai = getSharedPreferences("sai", 0);
        this.d1 = new AlertDialog.Builder(this);
        this.wifi = new RequestNetwork(this);
        this.hd = new AlertDialog.Builder(this);
        this.hora = getSharedPreferences("hora", 0);
        this.test = getSharedPreferences("test", 0);
        this.i3 = new AlertDialog.Builder(this);
        this.di = new AlertDialog.Builder(this);
        this.menu_adm = getSharedPreferences("menu_adm", 0);
        this._wifi_request_listener = new RequestNetwork.RequestListener() { // from class: com.br.painel.MainActivity.1
            @Override // com.br.painel.RequestNetwork.RequestListener
            public void onResponse(String str, String str2, HashMap<String, Object> hashMap) {
            }

            @Override // com.br.painel.RequestNetwork.RequestListener
            public void onErrorResponse(String str, String str2) {
            }
        };
    }

    private void initializeLogic() {
    }

    @Deprecated
    public void showMessage(String str) {
        Toast.makeText(getApplicationContext(), str, 0).show();
    }

    @Deprecated
    public int getLocationX(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return iArr[0];
    }

    @Deprecated
    public int getLocationY(View view) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        return iArr[1];
    }

    @Deprecated
    public int getRandom(int i, int i2) {
        return new Random().nextInt((i2 - i) + 1) + i;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView listView) {
        ArrayList<Double> arrayList = new ArrayList<>();
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        for (int i = 0; i < checkedItemPositions.size(); i++) {
            if (checkedItemPositions.valueAt(i)) {
                arrayList.add(Double.valueOf(checkedItemPositions.keyAt(i)));
            }
        }
        return arrayList;
    }

    @Deprecated
    public float getDip(int i) {
        return TypedValue.applyDimension(1, i, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels() {
        return getResources().getDisplayMetrics().heightPixels;
    }
}
