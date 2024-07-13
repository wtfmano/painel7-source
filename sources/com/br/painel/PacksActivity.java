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
public class PacksActivity extends AppCompatActivity {
    private Button button1;
    private Button button10;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Intent i1 = new Intent();
    private AlertDialog.Builder i3;
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
        setContentView(R.layout.packs);
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
        this.button6 = (Button) findViewById(R.id.button6);
        this.button7 = (Button) findViewById(R.id.button7);
        this.button8 = (Button) findViewById(R.id.button8);
        this.button9 = (Button) findViewById(R.id.button9);
        this.button10 = (Button) findViewById(R.id.button10);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.i3 = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.finish();
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i3.setTitle("BINS SEM VBV");
                PacksActivity.this.i3.setIcon(R.drawable.j538969);
                PacksActivity.this.i3.setMessage(".vbv 514868\n\n.vbv 422011\n\n.vbv 420342\n\n.vbv 418053\n\n.vbv 423073\n\n.vbv 407843\n\n.vbv 418049\n\n.vbv 558763\n\n.vbv 422011\n\n.vbv 552937\n\n.vbv 418563\n\n.vbv 231038\n\n.vbv 409921\n\n.vbv 457674\n\n.vbv 457674\n\n.vbv 540772\n\n.vbv 448520\n\n.vbv 457291\n\n.vbv 412451\n\n.vbv 532655\n\n.vbv 496078\n\n.vbv 471511\n\n.vbv 526192\n\n.vbv 434769\n\n.vbv 403212\n\n.vbv 403117\n\n.vbv 494052\n\n.vbv 547086\n\n.vbv 559288\n\n.vbv 555736\n\n.vbv 540931\n\n.vbv 478200\n\n.vbv 546453\n\n.vbv 511588\n\n.vbv 523914\n\n.vbv 523914\n\n.vbv 547481\n\n.vbv 528223");
                PacksActivity.this.i3.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.br.painel.PacksActivity.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                PacksActivity.this.i3.create().show();
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://t.me/+gmVaEu0VdMs2YjMx"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://t.me/+K-iOjD2yp8FjZjEx"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://t.me/+aWltLsUUdmszY2I5"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://t.me/+VxTcUHiGa88zOWEx"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://t.me/+bdr6lCVPHTc5OGRh"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.button8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/v4v8h1bkr5fz5xd/score+MG.pdf/file"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.button9.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/gf97ddxszkhd0wa/MEGA_PACK_%25E1%25B6%259C%25E1%25B6%25A0%25E1%25B6%259B.zip/file"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.button10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i1.setAction("android.intent.action.VIEW");
                PacksActivity.this.i1.setData(Uri.parse("https://mega.nz/folder/0FR2gQ7C#B7TztowoJ7z7-MX_MFNmNw"));
                PacksActivity.this.startActivity(PacksActivity.this.i1);
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PacksActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PacksActivity.this.i3.setTitle("AQUI VOCÊ ENCONTRA VÁRIOS PACKS ALEATÓRIOS");
                PacksActivity.this.i3.setIcon(R.drawable.b5184592);
                PacksActivity.this.i3.setMessage("AQUI VOCÊ ACHA VARIOS PACKS PQRA TE AJUDAR EM VÁRIAS OCASIÕES ✅");
                PacksActivity.this.i3.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.br.painel.PacksActivity.11.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                PacksActivity.this.i3.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.PacksActivity$12] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.PacksActivity$17] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.PacksActivity$18] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.PacksActivity$19] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.PacksActivity$20] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.PacksActivity$13] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.PacksActivity$14] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.PacksActivity$15] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.PacksActivity$16] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.12
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.13
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.14
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.15
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.16
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.17
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button8.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.18
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button9.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.19
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.PacksActivity.20
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
