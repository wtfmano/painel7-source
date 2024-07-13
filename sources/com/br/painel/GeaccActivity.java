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
import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.FirebaseApp;
import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes2.dex */
public class GeaccActivity extends AppCompatActivity {
    private Button button5;
    private Button button6;
    private AlertDialog.Builder d1;
    private AlertDialog.Builder i3;
    private ImageView imageview1;
    private ImageView imageview11;
    private ImageView imageview12;
    private ImageView imageview14;
    private ImageView imageview2;
    private ImageView imageview3;
    private ImageView imageview6;
    private ImageView imageview8;
    private ImageView imageview9;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear119;
    private LinearLayout linear121;
    private LinearLayout linear123;
    private LinearLayout linear124;
    private LinearLayout linear125;
    private LinearLayout linear127;
    private LinearLayout linear128;
    private LinearLayout linear129;
    private LinearLayout linear130;
    private LinearLayout linear132;
    private LinearLayout linear215;
    private LinearLayout linear216;
    private LinearLayout linear217;
    private SharedPreferences navegador;
    private TextView textview1;
    private TextView textview11;
    private TextView textview12;
    private TextView textview13;
    private TextView textview19;
    private TextView textview20;
    private TextView textview21;
    private TextView textview6;
    private TextView textview9;
    private ScrollView vscroll1;
    private Intent f = new Intent();
    private Intent i1 = new Intent();
    private Intent l77 = new Intent();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.geacc);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.linear217 = (LinearLayout) findViewById(R.id.linear217);
        this.imageview14 = (ImageView) findViewById(R.id.imageview14);
        this.linear216 = (LinearLayout) findViewById(R.id.linear216);
        this.imageview3 = (ImageView) findViewById(R.id.imageview3);
        this.linear215 = (LinearLayout) findViewById(R.id.linear215);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.linear116 = (LinearLayout) findViewById(R.id.linear116);
        this.textview6 = (TextView) findViewById(R.id.textview6);
        this.linear119 = (LinearLayout) findViewById(R.id.linear119);
        this.textview11 = (TextView) findViewById(R.id.textview11);
        this.linear128 = (LinearLayout) findViewById(R.id.linear128);
        this.linear123 = (LinearLayout) findViewById(R.id.linear123);
        this.textview19 = (TextView) findViewById(R.id.textview19);
        this.linear127 = (LinearLayout) findViewById(R.id.linear127);
        this.button6 = (Button) findViewById(R.id.button6);
        this.linear132 = (LinearLayout) findViewById(R.id.linear132);
        this.linear121 = (LinearLayout) findViewById(R.id.linear121);
        this.imageview6 = (ImageView) findViewById(R.id.imageview6);
        this.textview9 = (TextView) findViewById(R.id.textview9);
        this.linear129 = (LinearLayout) findViewById(R.id.linear129);
        this.linear130 = (LinearLayout) findViewById(R.id.linear130);
        this.linear125 = (LinearLayout) findViewById(R.id.linear125);
        this.imageview11 = (ImageView) findViewById(R.id.imageview11);
        this.textview20 = (TextView) findViewById(R.id.textview20);
        this.imageview12 = (ImageView) findViewById(R.id.imageview12);
        this.textview21 = (TextView) findViewById(R.id.textview21);
        this.imageview9 = (ImageView) findViewById(R.id.imageview9);
        this.textview13 = (TextView) findViewById(R.id.textview13);
        this.linear124 = (LinearLayout) findViewById(R.id.linear124);
        this.imageview8 = (ImageView) findViewById(R.id.imageview8);
        this.textview12 = (TextView) findViewById(R.id.textview12);
        this.button5 = (Button) findViewById(R.id.button5);
        this.d1 = new AlertDialog.Builder(this);
        this.navegador = getSharedPreferences("navegador", 0);
        this.i3 = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.finish();
            }
        });
        this.imageview14.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.i1.setAction("android.intent.action.VIEW");
                GeaccActivity.this.i1.setData(Uri.parse("https://t.me/SShield_bot?start=5331275295"));
                GeaccActivity.this.startActivity(GeaccActivity.this.i1);
            }
        });
        this.imageview3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.i3.setTitle("TUTORIAL BÁSICO ");
                GeaccActivity.this.i3.setIcon(R.drawable.b5184592);
                GeaccActivity.this.i3.setMessage("Curso Sobre Geradas\n\nIndice:\n- O que e Geradas?\n- Onde Consigo?\n- Como Usar?\n\nO que e Geradas?\n- Geradas e um termo / adjetivo , usado para uma criacao de um novo cartao funcional.\n- Ou seja voce descubrira um novo cartao de credito, porem nao tera o nome do titular ou cpf ou o CVV de 3 digitos.\n- A abreviacao \"GG\" significa = Geradas.\n- Como assim Geradas? a GG ela e criada aparti de um cartao de alguem ja existente, e atraves dele acharemos um novo :)\n\nOnde Consigo?\n\n- Para criarmos nossa propria geradas, vamos precisar de um cartao ja existente. No caso vou ultilizar esse:\n\nCARTAO: 4984 5351 4152 2021\nBANCO: BANCO BRASIL\nNIVEL: CLASSIC\nVALIDADE: 02 / 2021\nCVV: 853\n- Agora vamos no site: https://namso-gen.com/\n\n- Agora apague os Ultimos 5 ou 4 Digitos do cartao e subistitua por \"xxxx\"\n\n- Agora Click em Generate.\n\nPronto voce \"gerou\" novos cartoes aparti de um cartao ja existente.\nComo Usar?\n- Bom, primeiramente os cartoes que voce gerou nem todos estao funcionando.\n- Voce precisara de um \"CHECK DE GG\" do Banco do cartao que voce gerou. meu caso foi Banco do Brasil\n- Voce pode alugar um \"CHECK DE GG\"com algum programador dono de Central\n- Ou em vez de ter um \"CHECK DE GG\" Voce pode testar manualmente no site: \"https://www.amazon.com.br/prime\" a que passar, ta boa.\n- A que tiver Boa, voce nao tera o (CVV) Codigo de 3 digitos xxx.\n- Entao voce pode testar essa sua GG BOA em sites vulneraveis como \"IFOOD, CACAUSHOW, ADIDAS, REBOOK ELLUS e etc..\".\n- Lembrando: Voce pode fazer novas \"GG\" como ELO, ITAU, CREDICARD, SANTANDER e etc\n- Basta Seguir o Curso.");
                GeaccActivity.this.i3.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.br.painel.GeaccActivity.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                GeaccActivity.this.i3.create().show();
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA GERADOR DE CC");
                GeaccActivity.this.d1.setIcon(R.drawable.b5184592);
                GeaccActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\n1- APERTE EM GERAR A CC\n\n2-PEGUE A CC E APERTE EM VERIFICAR CC\n\n3-A QUE SAIR LIVE É SO USAR \n\nO QUE É UM CC.? \n\n(CC = CARTÃO DE CRÉDITO) \n\nPrimeiro devemos deixar claro que um CC não é um BIN, um CC é feito de informações reais. \n\nOs CCS são compostos basicamente por: \n\n°Nome\n° Número do cartão\n°CCV\n°Data\n°Endereço\n° CEP\n° País");
                GeaccActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.GeaccActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                GeaccActivity.this.d1.create().show();
            }
        });
        this.linear128.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.i3.setTitle("AQUI VOCÊ ENCONTRA AS MELHORES BINS");
                GeaccActivity.this.i3.setIcon(R.drawable.b5184592);
                GeaccActivity.this.i3.setMessage("💳𝗟𝗜𝗩𝗘 𝗕𝗜𝗡𝗦 𝗖𝗖💳:\nBins comuns sem vbv: \n417401\n\n407843\n\n230888\n\n498423\n\n435087\n\n423662\n\n498442\n\n546479\n\n498442\n\n376528\n\n444456\n\n650486\n\n498401\n\n498442\n\n485464\n\n423064\n\n407765\n\n560685\n\n447428\n\n630605\n\n471821\n\n600608\n\n615004\n\n535283\n\n");
                GeaccActivity.this.i3.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.br.painel.GeaccActivity.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                GeaccActivity.this.i3.create().show();
            }
        });
        this.linear121.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.navegador.edit().putString(ImagesContract.URL, "https://namso-gen.co/").commit();
                GeaccActivity.this.l77.setClass(GeaccActivity.this.getApplicationContext(), NavegadoranoActivity.class);
                GeaccActivity.this.startActivity(GeaccActivity.this.l77);
            }
        });
        this.linear129.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.i1.setAction("android.intent.action.VIEW");
                GeaccActivity.this.i1.setData(Uri.parse("https://checker2.visatk.com/card/ccn1/"));
                GeaccActivity.this.startActivity(GeaccActivity.this.i1);
            }
        });
        this.linear130.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.i1.setAction("android.intent.action.VIEW");
                GeaccActivity.this.i1.setData(Uri.parse("http://sunxinechk.fzndtt.shop/"));
                GeaccActivity.this.startActivity(GeaccActivity.this.i1);
            }
        });
        this.textview20.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.linear124.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.i1.setAction("android.intent.action.VIEW");
                GeaccActivity.this.i1.setData(Uri.parse("https://t.me/SDBB_Bot"));
                GeaccActivity.this.startActivity(GeaccActivity.this.i1);
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.GeaccActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                GeaccActivity.this.f.setClass(GeaccActivity.this.getApplicationContext(), MatrixActivity.class);
                GeaccActivity.this.startActivity(GeaccActivity.this.f);
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.GeaccActivity$13] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.GeaccActivity$18] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.GeaccActivity$19] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.GeaccActivity$14] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.GeaccActivity$15] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.GeaccActivity$16] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.GeaccActivity$17] */
    private void initializeLogic() {
        this.linear121.setBackground(new GradientDrawable() { // from class: com.br.painel.GeaccActivity.13
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear130.setBackground(new GradientDrawable() { // from class: com.br.painel.GeaccActivity.14
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.GeaccActivity.15
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.GeaccActivity.16
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear125.setBackground(new GradientDrawable() { // from class: com.br.painel.GeaccActivity.17
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear124.setBackground(new GradientDrawable() { // from class: com.br.painel.GeaccActivity.18
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear129.setBackground(new GradientDrawable() { // from class: com.br.painel.GeaccActivity.19
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
