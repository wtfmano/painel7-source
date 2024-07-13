package com.br.painel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.br.painel.RequestNetwork;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes2.dex */
public class Main1Activity extends AppCompatActivity {
    private ChildEventListener _not1_child_listener;
    private RequestNetwork.RequestListener _requerst_request_listener;
    private ImageView imageview1;
    private LinearLayout linear3;
    private LinearLayout linear4;
    private LinearLayout linear5;
    private LinearLayout linear6;
    private LinearLayout linear7;
    private LinearLayout linear8;
    private LinearLayout linear9;
    private ProgressBar progressbar1;
    private RequestNetwork requerst;
    private SharedPreferences sai;
    private TextView textview1;
    private TextView textview4;
    private TimerTask timer;
    private Timer _timer = new Timer();
    private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
    private HashMap<String, Object> map = new HashMap<>();
    private Intent i1 = new Intent();
    private DatabaseReference not1 = this._firebase.getReference("not1");
    private Intent i6 = new Intent();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main1);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear3 = (LinearLayout) findViewById(R.id.linear3);
        this.linear4 = (LinearLayout) findViewById(R.id.linear4);
        this.linear5 = (LinearLayout) findViewById(R.id.linear5);
        this.linear6 = (LinearLayout) findViewById(R.id.linear6);
        this.linear8 = (LinearLayout) findViewById(R.id.linear8);
        this.linear7 = (LinearLayout) findViewById(R.id.linear7);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.textview4 = (TextView) findViewById(R.id.textview4);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.linear9 = (LinearLayout) findViewById(R.id.linear9);
        this.progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
        this.requerst = new RequestNetwork(this);
        this.sai = getSharedPreferences("sai", 0);
        this.linear3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.Main1Activity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this._requerst_request_listener = new RequestNetwork.RequestListener() { // from class: com.br.painel.Main1Activity.2
            @Override // com.br.painel.RequestNetwork.RequestListener
            public void onResponse(String str, String str2, HashMap<String, Object> hashMap) {
            }

            @Override // com.br.painel.RequestNetwork.RequestListener
            public void onErrorResponse(String str, String str2) {
            }
        };
        this._not1_child_listener = new ChildEventListener() { // from class: com.br.painel.Main1Activity.3
            @Override // com.google.firebase.database.ChildEventListener
            public void onChildAdded(DataSnapshot dataSnapshot, String str) {
                GenericTypeIndicator<HashMap<String, Object>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, Object>>() { // from class: com.br.painel.Main1Activity.3.1
                };
                dataSnapshot.getKey();
                if (!((HashMap) dataSnapshot.getValue(genericTypeIndicator)).get("titulo").toString().equals("on")) {
                    SketchwareUtil.showMessage(Main1Activity.this.getApplicationContext(), "OFF🔴");
                    Main1Activity.this.startActivity(Main1Activity.this.i6);
                    Main1Activity.this.finish();
                }
            }

            @Override // com.google.firebase.database.ChildEventListener
            public void onChildChanged(DataSnapshot dataSnapshot, String str) {
                GenericTypeIndicator<HashMap<String, Object>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, Object>>() { // from class: com.br.painel.Main1Activity.3.2
                };
                dataSnapshot.getKey();
                if (!((HashMap) dataSnapshot.getValue(genericTypeIndicator)).get("titulo").toString().equals("on")) {
                    SketchwareUtil.showMessage(Main1Activity.this.getApplicationContext(), "OFF🔴");
                    Main1Activity.this.startActivity(Main1Activity.this.i6);
                    Main1Activity.this.finish();
                }
            }

            @Override // com.google.firebase.database.ChildEventListener
            public void onChildMoved(DataSnapshot dataSnapshot, String str) {
            }

            @Override // com.google.firebase.database.ChildEventListener
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Object>> genericTypeIndicator = new GenericTypeIndicator<HashMap<String, Object>>() { // from class: com.br.painel.Main1Activity.3.3
                };
                dataSnapshot.getKey();
                HashMap hashMap = (HashMap) dataSnapshot.getValue(genericTypeIndicator);
            }

            @Override // com.google.firebase.database.ChildEventListener
            public void onCancelled(DatabaseError databaseError) {
                databaseError.getCode();
                databaseError.getMessage();
            }
        };
        this.not1.addChildEventListener(this._not1_child_listener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.br.painel.Main1Activity$4  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass4 extends TimerTask {
        AnonymousClass4() {
        }

        /* renamed from: com.br.painel.Main1Activity$4$1  reason: invalid class name */
        /* loaded from: classes2.dex */
        class AnonymousClass1 implements Runnable {
            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public void run() {
                Main1Activity.this.progressbar1.setProgress(SketchwareUtil.getRandom(50, 100));
                Main1Activity.this.timer = new TimerTask() { // from class: com.br.painel.Main1Activity.4.1.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        Main1Activity.this.runOnUiThread(new Runnable() { // from class: com.br.painel.Main1Activity.4.1.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                SketchwareUtil.showMessage(Main1Activity.this.getApplicationContext(), "Carregando...");
                                Main1Activity.this.i1.setClass(Main1Activity.this.getApplicationContext(), PainelActivity.class);
                                Main1Activity.this.startActivity(Main1Activity.this.i1);
                                Main1Activity.this.finish();
                            }
                        });
                    }
                };
                Main1Activity.this._timer.schedule(Main1Activity.this.timer, 800L);
            }
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Main1Activity.this.runOnUiThread(new AnonymousClass1());
        }
    }

    private void initializeLogic() {
        this.progressbar1.setProgress(SketchwareUtil.getRandom(10, 50));
        this.timer = new AnonymousClass4();
        this._timer.schedule(this.timer, SketchwareUtil.getRandom(600, 2000));
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
