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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes2.dex */
public class AlboutActivity extends AppCompatActivity {
    private CircleImageView circleimageview1;
    private CircleImageView circleimageview2;
    private CircleImageView circleimageview3;
    private CircleImageView circleimageview4;
    private CircleImageView circleimageview5;
    private AlertDialog.Builder d2;
    private AlertDialog.Builder hv;
    private Intent i1 = new Intent();
    private AlertDialog.Builder i3e;
    private ImageView imageview10;
    private ImageView imageview11;
    private ImageView imageview12;
    private ImageView imageview13;
    private ImageView imageview2;
    private ImageView imageview57;
    private ImageView imageview8;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear117;
    private LinearLayout linear118;
    private LinearLayout linear119;
    private LinearLayout linear120;
    private LinearLayout linear121;
    private LinearLayout linear122;
    private LinearLayout linear123;
    private LinearLayout linear124;
    private LinearLayout linear125;
    private LinearLayout linear126;
    private LinearLayout linear127;
    private LinearLayout linear128;
    private LinearLayout linear129;
    private LinearLayout linear130;
    private LinearLayout linear131;
    private LinearLayout linear132;
    private LinearLayout linear133;
    private LinearLayout linear135;
    private LinearLayout linear136;
    private LinearLayout linear137;
    private TextView textview10;
    private TextView textview11;
    private TextView textview12;
    private TextView textview13;
    private TextView textview14;
    private TextView textview15;
    private TextView textview16;
    private TextView textview17;
    private TextView textview18;
    private TextView textview19;
    private TextView textview20;
    private ScrollView vscroll1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.albout);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.imageview8 = (ImageView) findViewById(R.id.imageview8);
        this.textview10 = (TextView) findViewById(R.id.textview10);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.linear116 = (LinearLayout) findViewById(R.id.linear116);
        this.linear118 = (LinearLayout) findViewById(R.id.linear118);
        this.linear117 = (LinearLayout) findViewById(R.id.linear117);
        this.linear120 = (LinearLayout) findViewById(R.id.linear120);
        this.linear122 = (LinearLayout) findViewById(R.id.linear122);
        this.linear125 = (LinearLayout) findViewById(R.id.linear125);
        this.linear126 = (LinearLayout) findViewById(R.id.linear126);
        this.linear129 = (LinearLayout) findViewById(R.id.linear129);
        this.linear130 = (LinearLayout) findViewById(R.id.linear130);
        this.linear133 = (LinearLayout) findViewById(R.id.linear133);
        this.linear135 = (LinearLayout) findViewById(R.id.linear135);
        this.circleimageview1 = (CircleImageView) findViewById(R.id.circleimageview1);
        this.linear121 = (LinearLayout) findViewById(R.id.linear121);
        this.linear119 = (LinearLayout) findViewById(R.id.linear119);
        this.imageview10 = (ImageView) findViewById(R.id.imageview10);
        this.textview11 = (TextView) findViewById(R.id.textview11);
        this.textview12 = (TextView) findViewById(R.id.textview12);
        this.circleimageview2 = (CircleImageView) findViewById(R.id.circleimageview2);
        this.linear123 = (LinearLayout) findViewById(R.id.linear123);
        this.linear124 = (LinearLayout) findViewById(R.id.linear124);
        this.imageview11 = (ImageView) findViewById(R.id.imageview11);
        this.textview13 = (TextView) findViewById(R.id.textview13);
        this.textview14 = (TextView) findViewById(R.id.textview14);
        this.circleimageview3 = (CircleImageView) findViewById(R.id.circleimageview3);
        this.linear127 = (LinearLayout) findViewById(R.id.linear127);
        this.linear128 = (LinearLayout) findViewById(R.id.linear128);
        this.imageview12 = (ImageView) findViewById(R.id.imageview12);
        this.textview15 = (TextView) findViewById(R.id.textview15);
        this.textview16 = (TextView) findViewById(R.id.textview16);
        this.circleimageview4 = (CircleImageView) findViewById(R.id.circleimageview4);
        this.linear131 = (LinearLayout) findViewById(R.id.linear131);
        this.linear132 = (LinearLayout) findViewById(R.id.linear132);
        this.imageview13 = (ImageView) findViewById(R.id.imageview13);
        this.textview17 = (TextView) findViewById(R.id.textview17);
        this.textview18 = (TextView) findViewById(R.id.textview18);
        this.circleimageview5 = (CircleImageView) findViewById(R.id.circleimageview5);
        this.linear136 = (LinearLayout) findViewById(R.id.linear136);
        this.linear137 = (LinearLayout) findViewById(R.id.linear137);
        this.imageview57 = (ImageView) findViewById(R.id.imageview57);
        this.textview19 = (TextView) findViewById(R.id.textview19);
        this.textview20 = (TextView) findViewById(R.id.textview20);
        this.i3e = new AlertDialog.Builder(this);
        this.d2 = new AlertDialog.Builder(this);
        this.hv = new AlertDialog.Builder(this);
        this.imageview8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.finish();
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.i3e.setTitle("DESENVOLVIMENTO ");
                AlboutActivity.this.i3e.setIcon(R.drawable.onymous_emblem);
                AlboutActivity.this.i3e.setMessage("TODOS QUE ESTÃO AQUI CONTRIBUÍRAM PARA O ANONYMOUS APK COM OS SEUS SERVIÇOS \n\nCASO TENHA INTERESSE EM ADIRIR LARAS BANNERS E ETC PEDEM CHAMALOS SÃO TODOS DE CONFIANÇA DOU A MINHA PALAVRA");
                AlboutActivity.this.i3e.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlboutActivity.this.i3e.create().show();
            }
        });
        this.linear122.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.i3e.setTitle("RAPOZO");
                AlboutActivity.this.i3e.setIcon(R.drawable.j0240108_212743_862);
                AlboutActivity.this.i3e.setMessage("SUB DONO DA COMUNIDADE ANONYMOUS E DESIGNER \n\n\u1f7e9 BANNERS BOLHAS\n\n\u1f7e9 LOGOS");
                AlboutActivity.this.i3e.setPositiveButton("CHAMALO", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://t.me/Raposodo7"));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
                AlboutActivity.this.i3e.setNegativeButton("SAIR", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.3.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlboutActivity.this.i3e.setNeutralButton("PORTFOLIO ", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.3.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://t.me/portifolioraposo"));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
            }
        });
        this.linear130.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.d2.setTitle("PH DO 7");
                AlboutActivity.this.d2.setIcon(R.drawable.u0240109_124553_143);
                AlboutActivity.this.d2.setMessage("O PH DO 7 É O FORNECEDOR DE CONTAS BANCÁRIAS LARANJAS DO ANONYMOUS APK CASO TENHA INTERESSE EM SABER MAIS APERTE EM ADQUIRIR LARA");
                AlboutActivity.this.d2.setPositiveButton("ADQUIRIR LARA", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://t.me/phzn_8"));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
                AlboutActivity.this.d2.setNegativeButton("Sair", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.4.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlboutActivity.this.d2.create().show();
            }
        });
        this.linear135.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.hv.setTitle("snowd3v");
                AlboutActivity.this.hv.setIcon(R.drawable.j0109_131455_168);
                AlboutActivity.this.hv.setMessage("DESENVOLVEDOR DE METODOS DO APLICATIVO CASO QUEIRA CONTRATAR OS SERVIÇOS DELE FALA QUE VEIO PELO KNOX ");
                AlboutActivity.this.hv.setPositiveButton("CHAMAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.5.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://t.me/snowd3v"));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
                AlboutActivity.this.hv.create().show();
            }
        });
        this.linear119.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.i3e.setTitle("KNOX");
                AlboutActivity.this.i3e.setIcon(R.drawable.f20240108_152604_641);
                AlboutActivity.this.i3e.setMessage("KNOX É O ATUAL DONO DE TODA A COMUNIDADE ANONYMOUS VISITE O INSTAGRAM DELE ");
                AlboutActivity.this.i3e.setPositiveButton("VISITAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://www.instagram.com/bob_bont_?igsh=ODA1NTc5OTg5Nw=="));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
                AlboutActivity.this.i3e.setNegativeButton("Sair", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.6.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlboutActivity.this.i3e.create().show();
            }
        });
        this.imageview10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.i3e.setTitle("KNOX");
                AlboutActivity.this.i3e.setIcon(R.drawable.f20240108_152604_641);
                AlboutActivity.this.i3e.setMessage("KNOX É O ATUAL DONO DE TODA A COMUNIDADE ANONYMOUS VISITE O INSTAGRAM DELE ");
                AlboutActivity.this.i3e.setPositiveButton("VISITAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.7.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://www.instagram.com/bob_bont_?igsh=ODA1NTc5OTg5Nw=="));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
                AlboutActivity.this.i3e.setNegativeButton("Sair", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.7.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlboutActivity.this.i3e.create().show();
            }
        });
        this.linear123.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.i3e.setTitle("RAPOSO");
                AlboutActivity.this.i3e.setIcon(R.drawable.j0240108_212743_862);
                AlboutActivity.this.i3e.setMessage("CASO QUEIRA CONTRAR OS SERVIÇOS DELE FALA QUE VEIO PELO KNOX\n\nELE FAZ:\n\n\u1f7e9 BANNER BOLHAS\n\n\u1f7e9 FOTOS PARA STORE DE CC\n\n\u1f7e9 E VARIOS SERVIÇO RELACIONADOS A DESIGNER");
                AlboutActivity.this.i3e.setPositiveButton("CHAMAR ", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.8.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://t.me/Raposodo7"));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
                AlboutActivity.this.i3e.setNeutralButton("PORTFOLIO ", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.8.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlboutActivity.this.i1.setAction("android.intent.action.VIEW");
                        AlboutActivity.this.i1.setData(Uri.parse("https://t.me/portifolioraposo"));
                        AlboutActivity.this.startActivity(AlboutActivity.this.i1);
                    }
                });
                AlboutActivity.this.i3e.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.8.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlboutActivity.this.i3e.create().show();
            }
        });
        this.linear127.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.AlboutActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlboutActivity.this.i3e.setTitle("BOB");
                AlboutActivity.this.i3e.setIcon(R.drawable.cvvc);
                AlboutActivity.this.i3e.setMessage("INFELIZMENTE O BOB ESTA NO MOMENTO KITADO DA WEB O RESPONSÁVEL PELO APLICATIVO NO MOMENTO É O KNOX");
                AlboutActivity.this.i3e.setPositiveButton("Sair", new DialogInterface.OnClickListener() { // from class: com.br.painel.AlboutActivity.9.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlboutActivity.this.i3e.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.br.painel.AlboutActivity$10] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.br.painel.AlboutActivity$11] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.br.painel.AlboutActivity$12] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.br.painel.AlboutActivity$13] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.br.painel.AlboutActivity$14] */
    private void initializeLogic() {
        SketchwareUtil.showMessage(getApplicationContext(), "DEV BOB");
        this.linear117.setBackground(new GradientDrawable() { // from class: com.br.painel.AlboutActivity.10
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear126.setBackground(new GradientDrawable() { // from class: com.br.painel.AlboutActivity.11
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear122.setBackground(new GradientDrawable() { // from class: com.br.painel.AlboutActivity.12
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear130.setBackground(new GradientDrawable() { // from class: com.br.painel.AlboutActivity.13
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear135.setBackground(new GradientDrawable() { // from class: com.br.painel.AlboutActivity.14
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
