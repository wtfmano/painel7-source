package com.br.painel;

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
public class Hu7Activity extends AppCompatActivity {
    private Button button1;
    private Button button3;
    private Intent i1 = new Intent();
    private ImageView imageview1;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear118;
    private TextView textview1;
    private TextView textview5;
    private ScrollView vscroll1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.hu7);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.linear116 = (LinearLayout) findViewById(R.id.linear116);
        this.linear118 = (LinearLayout) findViewById(R.id.linear118);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button1 = (Button) findViewById(R.id.button1);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.Hu7Activity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Hu7Activity.this.finishAffinity();
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.Hu7Activity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Hu7Activity.this.i1.setAction("android.intent.action.VIEW");
                Hu7Activity.this.i1.setData(Uri.parse("https://www.instagram.com/eliax.visionario?igsh=MTA1b3BzbDBkMXZ3eg=="));
                Hu7Activity.this.startActivity(Hu7Activity.this.i1);
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.Hu7Activity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Hu7Activity.this.i1.setAction("android.intent.action.VIEW");
                Hu7Activity.this.i1.setData(Uri.parse("https://www.instagram.com/angel_annms?igsh=MXhic3JrbXU0bzg0Mw=="));
                Hu7Activity.this.startActivity(Hu7Activity.this.i1);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.Hu7Activity$4] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.Hu7Activity$5] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.Hu7Activity$6] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.Hu7Activity.4
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.Hu7Activity.5
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.textview5.setBackground(new GradientDrawable() { // from class: com.br.painel.Hu7Activity.6
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
