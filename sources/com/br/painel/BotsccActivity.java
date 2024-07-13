package com.br.painel;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
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
public class BotsccActivity extends AppCompatActivity {
    private Button button10;
    private Button button11;
    private Button button12;
    private Button button15;
    private Button button16;
    private Button button17;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private AlertDialog.Builder d1;
    private AlertDialog.Builder hd;
    private HorizontalScrollView hscroll1;
    private Intent i1 = new Intent();
    private Intent i48 = new Intent();
    private ImageView imageview1;
    private ImageView imageview3;
    private ImageView imageview4;
    private ImageView imageview5;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear117;
    private LinearLayout linear119;
    private LinearLayout linear120;
    private LinearLayout linear121;
    private SharedPreferences save;
    private TextView textview1;
    private TextView textview10;
    private TextView textview6;
    private TextView textview7;
    private TextView textview8;
    private TextView textview9;
    private ScrollView vscroll1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.botscc);
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
        this.textview10 = (TextView) findViewById(R.id.textview10);
        this.hscroll1 = (HorizontalScrollView) findViewById(R.id.hscroll1);
        this.textview8 = (TextView) findViewById(R.id.textview8);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button5 = (Button) findViewById(R.id.button5);
        this.button6 = (Button) findViewById(R.id.button6);
        this.button7 = (Button) findViewById(R.id.button7);
        this.button8 = (Button) findViewById(R.id.button8);
        this.button9 = (Button) findViewById(R.id.button9);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button10 = (Button) findViewById(R.id.button10);
        this.button11 = (Button) findViewById(R.id.button11);
        this.button12 = (Button) findViewById(R.id.button12);
        this.button17 = (Button) findViewById(R.id.button17);
        this.button16 = (Button) findViewById(R.id.button16);
        this.button15 = (Button) findViewById(R.id.button15);
        this.linear117 = (LinearLayout) findViewById(R.id.linear117);
        this.linear121 = (LinearLayout) findViewById(R.id.linear121);
        this.linear119 = (LinearLayout) findViewById(R.id.linear119);
        this.linear120 = (LinearLayout) findViewById(R.id.linear120);
        this.imageview5 = (ImageView) findViewById(R.id.imageview5);
        this.textview9 = (TextView) findViewById(R.id.textview9);
        this.imageview3 = (ImageView) findViewById(R.id.imageview3);
        this.textview6 = (TextView) findViewById(R.id.textview6);
        this.imageview4 = (ImageView) findViewById(R.id.imageview4);
        this.textview7 = (TextView) findViewById(R.id.textview7);
        this.d1 = new AlertDialog.Builder(this);
        this.save = getSharedPreferences("save", 0);
        this.hd = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.finish();
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/SevenCcs_Bot?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/carteltx_bot?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i48.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i48.setData(Uri.parse("https://t.me/Cupcake_StoreBot"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i48);
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/efantasystore_bot"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("t.me/VALAK_STORE_BOT?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/VekawnStore_Bot?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button9.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/Savitarstoresbot?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/vonstorebot"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/JamaicaStore_Bot?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button11.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/ImperioDosLogins_Bot?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button12.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/GlobeStore_BOT?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button17.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/KazanskiStoreBOT?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button16.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/KGBSTORE_BOT?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.button15.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("t.me/ninjaccsBot?start=5331275295"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.linear121.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/SShield_bot?start=6131754749"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.linear119.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("t.me/CenterSmsBot?start=6131754749"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
        this.linear120.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.BotsccActivity.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BotsccActivity.this.i1.setAction("android.intent.action.VIEW");
                BotsccActivity.this.i1.setData(Uri.parse("https://t.me/aidraw_vip0_bot?start=6131754749"));
                BotsccActivity.this.startActivity(BotsccActivity.this.i1);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.BotsccActivity$19] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.BotsccActivity$24] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.BotsccActivity$25] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.BotsccActivity$26] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.BotsccActivity$27] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.br.painel.BotsccActivity$28] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.BotsccActivity$20] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.br.painel.BotsccActivity$29] */
    /* JADX WARN: Type inference failed for: r1v22, types: [com.br.painel.BotsccActivity$30] */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.br.painel.BotsccActivity$31] */
    /* JADX WARN: Type inference failed for: r1v26, types: [com.br.painel.BotsccActivity$32] */
    /* JADX WARN: Type inference failed for: r1v28, types: [com.br.painel.BotsccActivity$33] */
    /* JADX WARN: Type inference failed for: r1v30, types: [com.br.painel.BotsccActivity$34] */
    /* JADX WARN: Type inference failed for: r1v32, types: [com.br.painel.BotsccActivity$35] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.BotsccActivity$21] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.BotsccActivity$22] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.BotsccActivity$23] */
    private void initializeLogic() {
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.19
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.20
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.21
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.22
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.23
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button8.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.24
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button9.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.25
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.26
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button11.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.27
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button12.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.28
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.29
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button15.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.30
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button17.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.31
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button16.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.32
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear121.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.33
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear120.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.34
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear119.setBackground(new GradientDrawable() { // from class: com.br.painel.BotsccActivity.35
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
