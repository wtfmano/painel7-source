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
public class UnderActivity extends AppCompatActivity {
    private Button button1;
    private Button button10;
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.under);
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
        this.button17 = (Button) findViewById(R.id.button17);
        this.button16 = (Button) findViewById(R.id.button16);
        this.button15 = (Button) findViewById(R.id.button15);
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
        this.button13 = (Button) findViewById(R.id.button13);
        this.button14 = (Button) findViewById(R.id.button14);
        this.button12 = (Button) findViewById(R.id.button12);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.i3 = new AlertDialog.Builder(this);
        this.d1 = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.finish();
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://drive.google.com/file/d/1aZixunwC4HtV3XCehuQBJb526Bibn-E0/view"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button17.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/v6dsq9wvnah06jy/TUDO_SOBRE_ENDERE%25C3%2587OS_IP.pdf/file"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button16.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://seulink.net/GoogleAdsParaAfliados"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button15.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://4br.me/NomadeMilionarioCurse"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i3.setTitle("SKECHEWARE");
                UnderActivity.this.i3.setIcon(R.drawable.b5184592);
                UnderActivity.this.i3.setMessage("Sketchware é um software de desenvolvimento de aplicativos móveis que permite criar aplicativos Android sem a necessidade de conhecimentos avançados de programação. Ele oferece uma interface gráfica intuitiva e baseia-se em blocos de construção em lógica ( conhecidos como blocos de código) que podem ser arrastados e soltos para criar a lógica do aplicativo.Use o Zarchiver (app) para extração do arquivo, logo após, ative nas configurações a opção \"mostrar ficheiros/ arquivos e pastas ocultas\". Ativando essa opção, mova a pasta \"block\" para a pasta oculta chamada \".sketchware\", dentro dela existe a pasta \"resources\", mova para dentro desta. (obs: caso você iniciar o sketchware e perceber que demora pra entrar no sistema de idioma, desligue a internet)");
                UnderActivity.this.i3.setPositiveButton("download ", new DialogInterface.OnClickListener() { // from class: com.br.painel.UnderActivity.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                        UnderActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/3saq10cbhzer9cc/sketchwarepro.zip/file"));
                        UnderActivity.this.startActivity(UnderActivity.this.i1);
                    }
                });
                UnderActivity.this.i3.setNegativeButton("curso", new DialogInterface.OnClickListener() { // from class: com.br.painel.UnderActivity.6.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                        UnderActivity.this.i1.setData(Uri.parse("https://drive.google.com/drive/mobile/folders/1A32zCemu2HWqH1Quu5icLiP7fThUvxqD?usp=drive_link"));
                        UnderActivity.this.startActivity(UnderActivity.this.i1);
                    }
                });
                UnderActivity.this.i3.create().show();
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/otak8u7ttfzjvdl/%25F0%259F%2585%259F%25F0%259F%2585%25A2%25F0%259F%2585%2598%25F0%259F%2585%2592%25F0%259F%2585%259E%25F0%259F%2585%259B%25F0%259F%2585%259E%25F0%259F%2585%2596%25F0%259F%2585%2598%25F0%259F%2585%2590.rar/file"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/folder/ih93ike8autj8/Banco+de+Dados"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://t.me/+XKbea2hEsaE1MGNh"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://t.me/+JEnRHS75kOszZTYx"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://www.mediafire.com/file/ti4goovymv0gjjd/cabeamento_estruturado.rar/file"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://t.me/+GhTm2EjeNdA0ZGRh"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button9.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://t.me/+8JNJL1U-5KNjOWVh"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://4br.me/RhbLV9R"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button11.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.br.painel.UnderActivity.15
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.button11.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://4br.me/CKhYK8L"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button13.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://4br.me/NkkV2ZJF2"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button14.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://4br.me/x7dj48U"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.button12.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i1.setAction("android.intent.action.VIEW");
                UnderActivity.this.i1.setData(Uri.parse("https://4br.me/qmKyZQKS"));
                UnderActivity.this.startActivity(UnderActivity.this.i1);
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.UnderActivity.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                UnderActivity.this.i3.setTitle("AQUI VOCÊ ENCONTRA COMO AMPLIAR O SEU CONHECIMENTO ");
                UnderActivity.this.i3.setIcon(R.drawable.b5184592);
                UnderActivity.this.i3.setMessage("AQUI VOCÊ CONSEGUE VARIOS CURSOS/AULAS PARA APRIMORAR O SEU CONHECIMENTO DA WEB");
                UnderActivity.this.i3.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.br.painel.UnderActivity.20.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                UnderActivity.this.i3.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.UnderActivity$21] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.UnderActivity$26] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.UnderActivity$27] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.UnderActivity$28] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.UnderActivity$29] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.br.painel.UnderActivity$30] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.UnderActivity$22] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.br.painel.UnderActivity$31] */
    /* JADX WARN: Type inference failed for: r1v22, types: [com.br.painel.UnderActivity$32] */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.br.painel.UnderActivity$33] */
    /* JADX WARN: Type inference failed for: r1v26, types: [com.br.painel.UnderActivity$34] */
    /* JADX WARN: Type inference failed for: r1v28, types: [com.br.painel.UnderActivity$35] */
    /* JADX WARN: Type inference failed for: r1v30, types: [com.br.painel.UnderActivity$36] */
    /* JADX WARN: Type inference failed for: r1v32, types: [com.br.painel.UnderActivity$37] */
    /* JADX WARN: Type inference failed for: r1v34, types: [com.br.painel.UnderActivity$38] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.UnderActivity$23] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.UnderActivity$24] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.UnderActivity$25] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.21
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.22
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button8.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.23
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.24
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.25
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.26
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.27
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.28
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.29
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button9.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.30
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.31
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button11.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.32
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button13.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.33
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button12.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.34
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button14.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.35
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button15.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.36
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button16.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.37
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button17.setBackground(new GradientDrawable() { // from class: com.br.painel.UnderActivity.38
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
