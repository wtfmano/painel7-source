package com.br.painel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes2.dex */
public class RgfakeActivity extends AppCompatActivity {
    private Button button3;
    private Button button73;
    private Button button78;
    private Button button79;
    private Button button80;
    private Button button81;
    private AlertDialog.Builder d1;
    private ImageView imageview1;
    private ImageView imageview2;
    private ImageView imageview3;
    private LinearLayout linear111;
    private LinearLayout linear113;
    private LinearLayout linear115;
    private LinearLayout linear117;
    private LinearLayout linear118;
    private LinearLayout linear2;
    private SharedPreferences navegador;
    private TextView textview1;
    private TextView textview5;
    private ScrollView vscroll1;
    private ScrollView vscroll3;
    private Intent i1 = new Intent();
    private Intent l77 = new Intent();

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.rgfake);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.imageview3 = (ImageView) findViewById(R.id.imageview3);
        this.linear2 = (LinearLayout) findViewById(R.id.linear2);
        this.vscroll3 = (ScrollView) findViewById(R.id.vscroll3);
        this.linear111 = (LinearLayout) findViewById(R.id.linear111);
        this.linear113 = (LinearLayout) findViewById(R.id.linear113);
        this.linear118 = (LinearLayout) findViewById(R.id.linear118);
        this.button81 = (Button) findViewById(R.id.button81);
        this.button80 = (Button) findViewById(R.id.button80);
        this.button79 = (Button) findViewById(R.id.button79);
        this.button78 = (Button) findViewById(R.id.button78);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button73 = (Button) findViewById(R.id.button73);
        this.linear117 = (LinearLayout) findViewById(R.id.linear117);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.d1 = new AlertDialog.Builder(this);
        this.navegador = getSharedPreferences("navegador", 0);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.finish();
            }
        });
        this.imageview3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.i1.setAction("android.intent.action.VIEW");
                RgfakeActivity.this.i1.setData(Uri.parse("https://t.me/+T5Lgx0AAT8o1NTM5"));
                RgfakeActivity.this.startActivity(RgfakeActivity.this.i1);
            }
        });
        this.button81.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.i1.setAction("android.intent.action.VIEW");
                RgfakeActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/pcdmg3o8dws2yey/RG_-_PIAUI.pdf/file"));
                RgfakeActivity.this.startActivity(RgfakeActivity.this.i1);
            }
        });
        this.button80.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.i1.setAction("android.intent.action.VIEW");
                RgfakeActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/kjgpzkcer0e4wk7/RG_-_RIO_DE_JANEIRO_2-Copiar.pdf/file"));
                RgfakeActivity.this.startActivity(RgfakeActivity.this.i1);
            }
        });
        this.button79.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.i1.setAction("android.intent.action.VIEW");
                RgfakeActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/n4zs497k50dhd3z/RG_-_RIO_GRANDE_DO_NORTE.pdf/file"));
                RgfakeActivity.this.startActivity(RgfakeActivity.this.i1);
            }
        });
        this.button78.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.i1.setAction("android.intent.action.VIEW");
                RgfakeActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/q6htk92k9htn5nz/RG_-_RIO_GRANDE_DO_SUL.pdf/file"));
                RgfakeActivity.this.startActivity(RgfakeActivity.this.i1);
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.i1.setAction("android.intent.action.VIEW");
                RgfakeActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/6rmgqjhd1lg2b37/RG_-_SANTA_CATARINA.pdf/file"));
                RgfakeActivity.this.startActivity(RgfakeActivity.this.i1);
            }
        });
        this.button73.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.i1.setAction("android.intent.action.VIEW");
                RgfakeActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/ydht87r0avn4m71/RG_-_PARA.pdf/file"));
                RgfakeActivity.this.startActivity(RgfakeActivity.this.i1);
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.RgfakeActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RgfakeActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA RG FALSO");
                RgfakeActivity.this.d1.setIcon(R.drawable.b5184592);
                RgfakeActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\n1-BAIXE O MODELO\n\n2- ASSISTA O TUTORIAL \n\nfala ai rapaziada vou ta falando pra vc assistir o video sem pular nada\ne preste muita atenção e eu o responsavel pelo esse material não é responsavel pelo oque vc faz com ele");
                RgfakeActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.RgfakeActivity.9.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                RgfakeActivity.this.d1.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.RgfakeActivity$10] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.RgfakeActivity$15] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.RgfakeActivity$11] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.RgfakeActivity$12] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.RgfakeActivity$13] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.RgfakeActivity$14] */
    private void initializeLogic() {
        this.button73.setBackground(new GradientDrawable() { // from class: com.br.painel.RgfakeActivity.10
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button81.setBackground(new GradientDrawable() { // from class: com.br.painel.RgfakeActivity.11
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button80.setBackground(new GradientDrawable() { // from class: com.br.painel.RgfakeActivity.12
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button79.setBackground(new GradientDrawable() { // from class: com.br.painel.RgfakeActivity.13
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button78.setBackground(new GradientDrawable() { // from class: com.br.painel.RgfakeActivity.14
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.RgfakeActivity.15
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
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
