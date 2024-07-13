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
public class JogosActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
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
        setContentView(R.layout.jogos);
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
        this.button3 = (Button) findViewById(R.id.button3);
        this.button1 = (Button) findViewById(R.id.button1);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button5 = (Button) findViewById(R.id.button5);
        this.button6 = (Button) findViewById(R.id.button6);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.d1 = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.finish();
            }
        });
        this.linear117.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.br.painel.JogosActivity.2
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.i1.setAction("android.intent.action.VIEW");
                JogosActivity.this.i1.setData(Uri.parse("https://terabox.com/s/1TX5OK8NyOj8N_JLB3AnWVA"));
                JogosActivity.this.startActivity(JogosActivity.this.i1);
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.i1.setAction("android.intent.action.VIEW");
                JogosActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/wevy3n523gbw17w/Minecraft-1.20.51.01-Mod-APKGara.Com+signed.apk/file"));
                JogosActivity.this.startActivity(JogosActivity.this.i1);
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.i1.setAction("android.intent.action.VIEW");
                JogosActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/j80ok8kwheizbds/GTASA-v2.00%2528APK%252BOBB%2529Android11%252C12.zip/file"));
                JogosActivity.this.startActivity(JogosActivity.this.i1);
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.i1.setAction("android.intent.action.VIEW");
                JogosActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/coycjc31fp5d49s/BAD_TEAM_V18.apk/file"));
                JogosActivity.this.startActivity(JogosActivity.this.i1);
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.i1.setAction("android.intent.action.VIEW");
                JogosActivity.this.i1.setData(Uri.parse("https://www.terabox.com/wap/share/filelist?surl=1TDLzALGj4kFsFZ43HrRlg"));
                JogosActivity.this.startActivity(JogosActivity.this.i1);
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.i1.setAction("android.intent.action.VIEW");
                JogosActivity.this.i1.setData(Uri.parse("https://terabox.com/s/1VRaNT9sGt6LEqDTWQFuNHQ"));
                JogosActivity.this.startActivity(JogosActivity.this.i1);
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.JogosActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                JogosActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA JOGOS");
                JogosActivity.this.d1.setIcon(R.drawable.b5184592);
                JogosActivity.this.d1.setMessage("AQUI VOCÊ ACHA VARIOS JOGOS PAGOS DE GRAÇA ");
                JogosActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.JogosActivity.9.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                JogosActivity.this.d1.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.JogosActivity$10] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.JogosActivity$15] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.JogosActivity$11] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.JogosActivity$12] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.JogosActivity$13] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.JogosActivity$14] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.JogosActivity.10
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.JogosActivity.11
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.JogosActivity.12
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.JogosActivity.13
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.JogosActivity.14
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.JogosActivity.15
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
