package com.br.painel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
public class GunsActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private AlertDialog.Builder d1;
    private Intent i1 = new Intent();
    private ImageView imageview1;
    private ImageView imageview2;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear117;
    private LinearLayout linear118;
    private TextView textview1;
    private TextView textview5;
    private ScrollView vscroll1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.guns);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.linear116 = (LinearLayout) findViewById(R.id.linear116);
        this.linear117 = (LinearLayout) findViewById(R.id.linear117);
        this.linear118 = (LinearLayout) findViewById(R.id.linear118);
        this.button1 = (Button) findViewById(R.id.button1);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button5 = (Button) findViewById(R.id.button5);
        this.button6 = (Button) findViewById(R.id.button6);
        this.button7 = (Button) findViewById(R.id.button7);
        this.button8 = (Button) findViewById(R.id.button8);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.d1 = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.finish();
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/138WnR1Qh8eAledQCfGcvzW-SFhH3DiPV/view"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1bOIp-edok-w-vNBp385jsHYKh2Vnu8zC/view"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1g-myP2X_VZut6pXrFMSxf1GhXWFNKSmZ/view"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1FHOApcW2AV4S8QxMlDhF1ixKedMacsGQ/view"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/19UPnbJxJbDB18NNhK6kadyZ3S_WmkAO7/view"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/17W06RbSm90f8zMeGeUDsQh2pQW5PtcqG/view"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1Owhifx9UreUmkjWKlRR86sZeqNHcKzAT/view"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.button8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.i1.setAction("android.intent.action.VIEW");
                GunsActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/9ym95srr59rt093/APOSTILA_TEORICA_ETAPA_PRATICA_BERETTA_APX.pdf/file"));
                GunsActivity.this.startActivity(GunsActivity.this.i1);
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GunsActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GunsActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA GUNS");
                GunsActivity.this.d1.setIcon(R.drawable.b5184592);
                GunsActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\nVARIOS METODOS DE MANUSEIO DE ARMA DE FOGO E ATE MESMO PASSO A PASSO DE COMO FAZELAS ");
                GunsActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.GunsActivity.10.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                GunsActivity.this.d1.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.GunsActivity$11] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.GunsActivity$16] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.GunsActivity$17] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.GunsActivity$18] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.GunsActivity$12] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.GunsActivity$13] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.GunsActivity$14] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.GunsActivity$15] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.11
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.12
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.13
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.14
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.15
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.16
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.17
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button8.setBackground(new GradientDrawable() { // from class: com.br.painel.GunsActivity.18
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
