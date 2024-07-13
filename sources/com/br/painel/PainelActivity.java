package com.br.painel;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/* loaded from: classes2.dex */
public class PainelActivity extends AppCompatActivity {
    private TextView apks;
    private AlertDialog.Builder atburro;
    private TextView bots;
    private TextView cursos;
    private AlertDialog.Builder d1;
    private AlertDialog.Builder di;
    private TextView editaveis;
    private SharedPreferences foto;
    private TextView gerarcc;
    private SharedPreferences hora;
    private AlertDialog.Builder i3e;
    private TextView ia;
    private ImageView imageview11;
    private ImageView imageview15;
    private ImageView imageview16;
    private ImageView imageview17;
    private ImageView imageview19;
    private ImageView imageview2;
    private ImageView imageview22;
    private ImageView imageview34;
    private ImageView imageview39;
    private ImageView imageview40;
    private ImageView imageview44;
    private ImageView imageview46;
    private ImageView imageview62;
    private ImageView imageview68;
    private ImageView imageview7;
    private ImageView imageview8;
    private ImageView imageview81;
    private ImageView imageview82;
    private Notification j;
    private TextView laras;
    private LinearLayout linear1;
    private LinearLayout linear162;
    private LinearLayout linear185;
    private LinearLayout linear1track;
    private LinearLayout linear200;
    private LinearLayout linear234;
    private LinearLayout linear235;
    private LinearLayout linear261;
    private LinearLayout linear2ia;
    private LinearLayout linear2maisopicoa;
    private LinearLayout linear2packs;
    private LinearLayout linear318;
    private LinearLayout linear320;
    private LinearLayout linear321;
    private LinearLayout linear322;
    private LinearLayout linear325;
    private LinearLayout linear326;
    private LinearLayout linear327;
    private LinearLayout linear329;
    private LinearLayout linear330;
    private LinearLayout linear331;
    private LinearLayout linear332;
    private LinearLayout linear333;
    private LinearLayout linear336;
    private LinearLayout linear339;
    private LinearLayout linear343;
    private LinearLayout linear350;
    private LinearLayout linear89;
    private LinearLayout linear96;
    private LinearLayout linear_apks;
    private LinearLayout linear_bots;
    private LinearLayout linear_cursos;
    private LinearLayout linear_editaveis;
    private LinearLayout linear_gerarcc;
    private LinearLayout linear_laras;
    private LinearLayout linear_metodos;
    private LinearLayout linear_plrs;
    private LinearLayout linear_rgfalso;
    private LinearLayout linear_virarsaldo;
    private LinearLayout linearnotas;
    private LinearLayout lineartelas;
    private TextView maisofcs;
    private SharedPreferences menu_adm;
    private TextView metodos_vip;
    private AlertDialog.Builder msk;
    private SharedPreferences navegador;
    private Notification no;
    private TextView notas;
    private TextView packssssssss;
    private TextView plrs;
    private SharedPreferences r;
    private TextView rgfalso;
    private SharedPreferences s;
    private SharedPreferences sai;
    private SharedPreferences salvarlogin;
    private SharedPreferences save;
    private TextView telasfk;
    private SharedPreferences tema;
    private TextView textview116;
    private TextView textview117;
    private TextView textview118;
    private TextView track;
    private TextView virar_sald;
    private ScrollView vscroll1;
    private AlertDialog.Builder wha;
    private String modell = "";
    private HashMap<String, Object> map = new HashMap<>();
    private Intent i1 = new Intent();
    private Intent i2 = new Intent();
    private Intent i3 = new Intent();
    private Intent i4 = new Intent();
    private Intent i5 = new Intent();
    private Intent i6 = new Intent();
    private Intent i7 = new Intent();
    private Intent i8 = new Intent();
    private Intent b6 = new Intent();
    private Intent b7 = new Intent();
    private Intent rg = new Intent();
    private Intent b = new Intent();
    private Intent t78 = new Intent();
    private Intent l77 = new Intent();
    private Intent b12 = new Intent();
    private Intent b14 = new Intent();
    private Intent b125 = new Intent();
    private Intent l78 = new Intent();
    private Intent l17 = new Intent();
    private Intent l9 = new Intent();
    private Intent l79 = new Intent();
    private Intent avisos = new Intent();
    private Intent url = new Intent();
    private Intent i27 = new Intent();
    private Intent ac = new Intent();
    private Intent blck = new Intent();
    private Intent inten = new Intent();
    private Intent l191 = new Intent();
    private Intent l192 = new Intent();
    private Intent l193 = new Intent();
    private Intent res = new Intent();
    private Intent e = new Intent();
    private Intent b29 = new Intent();
    private Intent l112 = new Intent();
    private Intent l111 = new Intent();
    private Intent gft = new Intent();
    private Intent b31 = new Intent();
    private Intent b202 = new Intent();
    private Intent l788 = new Intent();
    private Intent kd = new Intent();
    private Intent socio = new Intent();
    private Intent b34 = new Intent();
    private Intent i234 = new Intent();
    private Intent b36 = new Intent();
    private Intent dbs = new Intent();
    private Intent under = new Intent();
    private Intent iw = new Intent();
    private Intent aub = new Intent();
    private Intent packs = new Intent();
    private Intent die = new Intent();
    private Intent edita = new Intent();
    private Intent kts = new Intent();
    private Intent oi171 = new Intent();
    private Intent guns = new Intent();
    private Intent aoif = new Intent();
    private Intent http = new Intent();
    private Intent wtrp = new Intent();
    private Intent pp = new Intent();
    private Intent juc = new Intent();
    private Intent dowloa = new Intent();
    private Intent ou = new Intent();
    private Intent alv = new Intent();
    private Intent tr = new Intent();
    private Intent plr = new Intent();
    private Intent skr = new Intent();
    private Intent inst = new Intent();
    private Intent sql = new Intent();
    private Intent lotter = new Intent();
    private Intent rgg = new Intent();
    private Intent o1 = new Intent();
    private Intent jb = new Intent();
    private Intent trocarde = new Intent();
    private Intent i77 = new Intent();
    private Intent gc = new Intent();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.painel);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.linear1 = (LinearLayout) findViewById(R.id.linear1);
        this.linear89 = (LinearLayout) findViewById(R.id.linear89);
        this.linear162 = (LinearLayout) findViewById(R.id.linear162);
        this.linear185 = (LinearLayout) findViewById(R.id.linear185);
        this.linear96 = (LinearLayout) findViewById(R.id.linear96);
        this.linear234 = (LinearLayout) findViewById(R.id.linear234);
        this.linear261 = (LinearLayout) findViewById(R.id.linear261);
        this.linear235 = (LinearLayout) findViewById(R.id.linear235);
        this.imageview81 = (ImageView) findViewById(R.id.imageview81);
        this.textview116 = (TextView) findViewById(R.id.textview116);
        this.textview117 = (TextView) findViewById(R.id.textview117);
        this.linear200 = (LinearLayout) findViewById(R.id.linear200);
        this.linear_metodos = (LinearLayout) findViewById(R.id.linear_metodos);
        this.linear_virarsaldo = (LinearLayout) findViewById(R.id.linear_virarsaldo);
        this.linear_gerarcc = (LinearLayout) findViewById(R.id.linear_gerarcc);
        this.linear_laras = (LinearLayout) findViewById(R.id.linear_laras);
        this.linear_rgfalso = (LinearLayout) findViewById(R.id.linear_rgfalso);
        this.linear1track = (LinearLayout) findViewById(R.id.linear1track);
        this.linearnotas = (LinearLayout) findViewById(R.id.linearnotas);
        this.lineartelas = (LinearLayout) findViewById(R.id.lineartelas);
        this.linear_editaveis = (LinearLayout) findViewById(R.id.linear_editaveis);
        this.linear_plrs = (LinearLayout) findViewById(R.id.linear_plrs);
        this.linear_bots = (LinearLayout) findViewById(R.id.linear_bots);
        this.linear_apks = (LinearLayout) findViewById(R.id.linear_apks);
        this.linear_cursos = (LinearLayout) findViewById(R.id.linear_cursos);
        this.linear2ia = (LinearLayout) findViewById(R.id.linear2ia);
        this.linear2packs = (LinearLayout) findViewById(R.id.linear2packs);
        this.linear2maisopicoa = (LinearLayout) findViewById(R.id.linear2maisopicoa);
        this.textview118 = (TextView) findViewById(R.id.textview118);
        this.imageview7 = (ImageView) findViewById(R.id.imageview7);
        this.linear326 = (LinearLayout) findViewById(R.id.linear326);
        this.metodos_vip = (TextView) findViewById(R.id.metodos_vip);
        this.imageview8 = (ImageView) findViewById(R.id.imageview8);
        this.linear321 = (LinearLayout) findViewById(R.id.linear321);
        this.virar_sald = (TextView) findViewById(R.id.virar_sald);
        this.imageview19 = (ImageView) findViewById(R.id.imageview19);
        this.linear330 = (LinearLayout) findViewById(R.id.linear330);
        this.gerarcc = (TextView) findViewById(R.id.gerarcc);
        this.imageview17 = (ImageView) findViewById(R.id.imageview17);
        this.linear320 = (LinearLayout) findViewById(R.id.linear320);
        this.laras = (TextView) findViewById(R.id.laras);
        this.imageview22 = (ImageView) findViewById(R.id.imageview22);
        this.linear331 = (LinearLayout) findViewById(R.id.linear331);
        this.rgfalso = (TextView) findViewById(R.id.rgfalso);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.linear339 = (LinearLayout) findViewById(R.id.linear339);
        this.track = (TextView) findViewById(R.id.track);
        this.imageview11 = (ImageView) findViewById(R.id.imageview11);
        this.linear332 = (LinearLayout) findViewById(R.id.linear332);
        this.notas = (TextView) findViewById(R.id.notas);
        this.imageview15 = (ImageView) findViewById(R.id.imageview15);
        this.linear333 = (LinearLayout) findViewById(R.id.linear333);
        this.telasfk = (TextView) findViewById(R.id.telasfk);
        this.imageview46 = (ImageView) findViewById(R.id.imageview46);
        this.linear318 = (LinearLayout) findViewById(R.id.linear318);
        this.editaveis = (TextView) findViewById(R.id.editaveis);
        this.imageview62 = (ImageView) findViewById(R.id.imageview62);
        this.linear322 = (LinearLayout) findViewById(R.id.linear322);
        this.plrs = (TextView) findViewById(R.id.plrs);
        this.imageview16 = (ImageView) findViewById(R.id.imageview16);
        this.linear325 = (LinearLayout) findViewById(R.id.linear325);
        this.bots = (TextView) findViewById(R.id.bots);
        this.imageview34 = (ImageView) findViewById(R.id.imageview34);
        this.linear327 = (LinearLayout) findViewById(R.id.linear327);
        this.apks = (TextView) findViewById(R.id.apks);
        this.imageview39 = (ImageView) findViewById(R.id.imageview39);
        this.linear329 = (LinearLayout) findViewById(R.id.linear329);
        this.cursos = (TextView) findViewById(R.id.cursos);
        this.imageview40 = (ImageView) findViewById(R.id.imageview40);
        this.linear336 = (LinearLayout) findViewById(R.id.linear336);
        this.ia = (TextView) findViewById(R.id.ia);
        this.imageview44 = (ImageView) findViewById(R.id.imageview44);
        this.linear343 = (LinearLayout) findViewById(R.id.linear343);
        this.packssssssss = (TextView) findViewById(R.id.packssssssss);
        this.imageview68 = (ImageView) findViewById(R.id.imageview68);
        this.linear350 = (LinearLayout) findViewById(R.id.linear350);
        this.maisofcs = (TextView) findViewById(R.id.maisofcs);
        this.imageview82 = (ImageView) findViewById(R.id.imageview82);
        this.save = getSharedPreferences("save", 0);
        this.navegador = getSharedPreferences("navegador", 0);
        this.atburro = new AlertDialog.Builder(this);
        this.sai = getSharedPreferences("sai", 0);
        this.msk = new AlertDialog.Builder(this);
        this.d1 = new AlertDialog.Builder(this);
        this.salvarlogin = getSharedPreferences("salvarlogin", 0);
        this.r = getSharedPreferences("r", 0);
        this.hora = getSharedPreferences("hora", 0);
        this.i3e = new AlertDialog.Builder(this);
        this.wha = new AlertDialog.Builder(this);
        this.di = new AlertDialog.Builder(this);
        this.tema = getSharedPreferences("tema", 0);
        this.foto = getSharedPreferences("foto", 0);
        this.s = getSharedPreferences("s", 0);
        this.menu_adm = getSharedPreferences("menu_adm", 0);
        this.imageview81.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.i1.setClass(PainelActivity.this.getApplicationContext(), Hu7Activity.class);
                PainelActivity.this.startActivity(PainelActivity.this.i1);
            }
        });
        this.textview116.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.linear_metodos.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.l9.setClass(PainelActivity.this.getApplicationContext(), MetodosActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.l9);
            }
        });
        this.linear_virarsaldo.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.l17.setClass(PainelActivity.this.getApplicationContext(), VirarsaldoActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.l17);
            }
        });
        this.linear_gerarcc.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.b.setClass(PainelActivity.this.getApplicationContext(), GeaccActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.b);
            }
        });
        this.linear_laras.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.l111.setClass(PainelActivity.this.getApplicationContext(), LarasActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.l111);
            }
        });
        this.linear_rgfalso.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.rgg.setClass(PainelActivity.this.getApplicationContext(), RgfakeActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.rgg);
            }
        });
        this.linear1track.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.i4.setClass(PainelActivity.this.getApplicationContext(), TrcknfcActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.i4);
            }
        });
        this.linearnotas.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.i5.setClass(PainelActivity.this.getApplicationContext(), MatriznfActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.i5);
            }
        });
        this.lineartelas.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.b125.setClass(PainelActivity.this.getApplicationContext(), TelasfkActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.b125);
            }
        });
        this.linear_editaveis.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.edita.setClass(PainelActivity.this.getApplicationContext(), EditaveActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.edita);
            }
        });
        this.linear_plrs.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.plr.setClass(PainelActivity.this.getApplicationContext(), PlrsActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.plr);
            }
        });
        this.linear_bots.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.gft.setClass(PainelActivity.this.getApplicationContext(), BotsccActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.gft);
            }
        });
        this.linear_apks.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.l192.setClass(PainelActivity.this.getApplicationContext(), PosiboActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.l192);
            }
        });
        this.linear_cursos.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.under.setClass(PainelActivity.this.getApplicationContext(), UnderActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.under);
            }
        });
        this.linear2ia.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.iw.setClass(PainelActivity.this.getApplicationContext(), IaaActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.iw);
            }
        });
        this.linear2packs.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                PainelActivity.this.packs.setClass(PainelActivity.this.getApplicationContext(), PacksActivity.class);
                PainelActivity.this.startActivity(PainelActivity.this.packs);
            }
        });
        this.linear2maisopicoa.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.PainelActivity.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.bots.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.br.painel.PainelActivity.19
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.PainelActivity$20] */
    /* JADX WARN: Type inference failed for: r1v11, types: [com.br.painel.PainelActivity$25] */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.br.painel.PainelActivity$26] */
    /* JADX WARN: Type inference failed for: r1v15, types: [com.br.painel.PainelActivity$27] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.br.painel.PainelActivity$28] */
    /* JADX WARN: Type inference failed for: r1v19, types: [com.br.painel.PainelActivity$29] */
    /* JADX WARN: Type inference failed for: r1v21, types: [com.br.painel.PainelActivity$30] */
    /* JADX WARN: Type inference failed for: r1v23, types: [com.br.painel.PainelActivity$31] */
    /* JADX WARN: Type inference failed for: r1v25, types: [com.br.painel.PainelActivity$32] */
    /* JADX WARN: Type inference failed for: r1v27, types: [com.br.painel.PainelActivity$33] */
    /* JADX WARN: Type inference failed for: r1v29, types: [com.br.painel.PainelActivity$34] */
    /* JADX WARN: Type inference failed for: r1v31, types: [com.br.painel.PainelActivity$35] */
    /* JADX WARN: Type inference failed for: r1v33, types: [com.br.painel.PainelActivity$36] */
    /* JADX WARN: Type inference failed for: r1v35, types: [com.br.painel.PainelActivity$37] */
    /* JADX WARN: Type inference failed for: r1v37, types: [com.br.painel.PainelActivity$38] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.br.painel.PainelActivity$22] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.br.painel.PainelActivity$23] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.br.painel.PainelActivity$24] */
    private void initializeLogic() {
        this.textview116.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.20
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        SketchwareUtil.showMessage(getApplicationContext(), "BEM VINDO✅");
        if (this.save.getString("kk", "").equals("")) {
            this.i3e.setTitle("TROPA DO 7️⃣");
            this.i3e.setIcon(R.drawable.mone);
            this.i3e.setMessage("APÓS APLICAR ESSE CONTEÚDO, MUITO PROVAVELMENTE VOCÊ IRÁ FICAR RICO, OU PELO MENOS, TERA A MAIOR OPORTUNIDADE DA SUA VIDA. LISO MALDITO!");
            this.i3e.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.br.painel.PainelActivity.21
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    PainelActivity.this.save.edit().putString("kk", "kkk").commit();
                }
            });
        }
        this.linear162.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.22
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_metodos.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.23
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_virarsaldo.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.24
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear1track.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.25
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_gerarcc.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.26
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_laras.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.27
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_rgfalso.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.28
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linearnotas.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.29
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.lineartelas.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.30
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_plrs.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.31
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_editaveis.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.32
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_cursos.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.33
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear2packs.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.34
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear2maisopicoa.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.35
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear2ia.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.36
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_apks.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.37
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear_bots.setBackground(new GradientDrawable() { // from class: com.br.painel.PainelActivity.38
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
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
