package com.br.painel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes2.dex */
public class NavegadoranoActivity extends AppCompatActivity {
    private AlertDialog.Builder b1;
    private AlertDialog.Builder d1;
    private AlertDialog.Builder di;
    private HorizontalScrollView hscroll1;
    private Intent i1 = new Intent();
    private ImageView imageview1;
    private ImageView imageview2;
    private ImageView imageview3;
    private ImageView imageview4;
    private ImageView imageview5;
    private ImageView imageview6;
    private ImageView imageview7;
    private ImageView imageview8;
    private LinearLayout linear115;
    private LinearLayout linear215;
    private LinearLayout linear216;
    private LinearLayout linear217;
    private LinearLayout linear218;
    private LinearLayout linear317;
    private LinearLayout linear318;
    private LinearLayout linear319;
    private LinearLayout linear320;
    private LinearLayout linear321;
    private LinearLayout linear322;
    private LinearLayout linear323;
    private SharedPreferences navegador;
    private TextView textview1;
    private WebView webview1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.navegadorano);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.linear217 = (LinearLayout) findViewById(R.id.linear217);
        this.webview1 = (WebView) findViewById(R.id.webview1);
        this.webview1.getSettings().setJavaScriptEnabled(true);
        this.webview1.getSettings().setSupportZoom(true);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.linear216 = (LinearLayout) findViewById(R.id.linear216);
        this.linear215 = (LinearLayout) findViewById(R.id.linear215);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.hscroll1 = (HorizontalScrollView) findViewById(R.id.hscroll1);
        this.linear218 = (LinearLayout) findViewById(R.id.linear218);
        this.linear323 = (LinearLayout) findViewById(R.id.linear323);
        this.imageview3 = (ImageView) findViewById(R.id.imageview3);
        this.linear317 = (LinearLayout) findViewById(R.id.linear317);
        this.imageview4 = (ImageView) findViewById(R.id.imageview4);
        this.linear318 = (LinearLayout) findViewById(R.id.linear318);
        this.imageview5 = (ImageView) findViewById(R.id.imageview5);
        this.linear319 = (LinearLayout) findViewById(R.id.linear319);
        this.imageview6 = (ImageView) findViewById(R.id.imageview6);
        this.linear320 = (LinearLayout) findViewById(R.id.linear320);
        this.imageview8 = (ImageView) findViewById(R.id.imageview8);
        this.linear322 = (LinearLayout) findViewById(R.id.linear322);
        this.imageview7 = (ImageView) findViewById(R.id.imageview7);
        this.linear321 = (LinearLayout) findViewById(R.id.linear321);
        this.navegador = getSharedPreferences("navegador", 0);
        this.b1 = new AlertDialog.Builder(this);
        this.d1 = new AlertDialog.Builder(this);
        this.di = new AlertDialog.Builder(this);
        this.webview1.setWebViewClient(new WebViewClient() { // from class: com.br.painel.NavegadoranoActivity.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                super.onPageStarted(webView, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
            }
        });
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.finish();
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA O NAVEGADOR INTERNO DO ANONYMOUS ");
                NavegadoranoActivity.this.d1.setIcon(R.drawable.b5184592);
                NavegadoranoActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\nNAVEGADOR INTERNO");
                NavegadoranoActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                NavegadoranoActivity.this.d1.create().show();
            }
        });
        this.imageview3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.webview1.loadUrl("https://www.google.com.br/?hl=pt-BR");
            }
        });
        this.imageview4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.webview1.loadUrl("https://www.xvideos.com/");
            }
        });
        this.imageview5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.webview1.loadUrl("https://www.4devs.com.br/home");
            }
        });
        this.imageview6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.webview1.loadUrl("https://chat.openai.com/");
            }
        });
        this.imageview8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.webview1.loadUrl("https://namso-gen.com/?tab=basic");
            }
        });
        this.imageview7.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.br.painel.NavegadoranoActivity.9
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.imageview7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.NavegadoranoActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                NavegadoranoActivity.this.webview1.loadUrl("https://hackersec.com/melhores-ferramentas-hacker/");
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.NavegadoranoActivity$11] */
    private void initializeLogic() {
        this.hscroll1.setBackground(new GradientDrawable() { // from class: com.br.painel.NavegadoranoActivity.11
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.webview1.loadUrl(this.navegador.getString(ImagesContract.URL, ""));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        this.webview1.goBack();
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
