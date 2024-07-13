package com.br.painel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
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
public class MatrixActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button29;
    private Button button3;
    private Button button30;
    private Button button31;
    private Button button32;
    private Button button33;
    private Button button34;
    private Button button35;
    private Button button36;
    private Button button4;
    private AlertDialog.Builder d1;
    private AlertDialog.Builder di;
    private Intent i1 = new Intent();
    private ImageView imageview1;
    private ImageView imageview2;
    private ImageView imageview3;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear117;
    private LinearLayout linear118;
    private TextView textview1;
    private TextView textview3;
    private TextView textview5;
    private ScrollView vscroll1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.matrix);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.imageview3 = (ImageView) findViewById(R.id.imageview3);
        this.linear116 = (LinearLayout) findViewById(R.id.linear116);
        this.linear117 = (LinearLayout) findViewById(R.id.linear117);
        this.linear118 = (LinearLayout) findViewById(R.id.linear118);
        this.button32 = (Button) findViewById(R.id.button32);
        this.button31 = (Button) findViewById(R.id.button31);
        this.button30 = (Button) findViewById(R.id.button30);
        this.button29 = (Button) findViewById(R.id.button29);
        this.button1 = (Button) findViewById(R.id.button1);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button33 = (Button) findViewById(R.id.button33);
        this.button34 = (Button) findViewById(R.id.button34);
        this.button35 = (Button) findViewById(R.id.button35);
        this.button36 = (Button) findViewById(R.id.button36);
        this.textview3 = (TextView) findViewById(R.id.textview3);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.d1 = new AlertDialog.Builder(this);
        this.di = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.finish();
            }
        });
        this.imageview3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.i1.setClass(MatrixActivity.this.getApplicationContext(), GeaccActivity.class);
                MatrixActivity.this.startActivity(MatrixActivity.this.i1);
            }
        });
        this.linear118.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
        this.button32.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("Matriz vinculando Aliexpress \n\n41740100654xxxxx 01/26\nCvv: Use o bug do 000 ou 789... etc\n\nQuem puder testar, bin sem vbv \u1f913👍");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button31.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("\u1f5fa Pack Matrizes Gringas\n\nEUA\n\n(Purchasing)\n448559000716xxxx 08/28\n448559000734xxxx 07/29\n448559000381xxxx 12/31\n448559000743xxxx 05/31\n\n(Prepaid)\n44039344855xxxxx 12/27 ou xx/27\n44039319335xxxxx 11/26\n44039336331xxxxx 04/27\n\"Antiga upando RecargaPay\"\n\n(Classic)\n440066425665xxxx 04/27\n440066425067xxxx 11/27\n\nEUA sem VBV\n\n(Classic)\n406042557722xxxx 04/27\n406042557706xxxx 10/27\n\n(Classic)\n47820020659xxxxx 06/27\n47820020649xxxxx 10/27\n\nMalásia\n\n532678000220xxxx 08/26\n532678000651xxxx 11/30\n532678000505xxxx 03/25\n\nTrinidade e Tobago \n\n(Gold)\n49154300444xxxxx 08/27\n49154300447xxxxx 10/27\n\nUcrânia\n\n(Standart)\n54570822736xxxxx 11/25\n\nUganda\n\n(Gold)\n45924301010xxxxx 05/27\n\nPalestina\n\n(Titanium)\n52373301323xxxxx xx/28\n\n");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.5.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button30.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("🇧🇷 Matriz sem VBV (Enhanced Business)\n\n407833003140xxxx 07/26\n4078330031xxx11x 07/26\n\n");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button29.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("🇧🇷 Matriz sem VBV (Classic)\n\n40644100125xxxxx 10/25\n\n");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.7.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("Matriz Elo\n\n650507001xxxxxxx 10/24\n\nErro comum: Security Violation (51 ou 63)\nSai esse retorno igual água 👍\u1f913\n\n");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.8.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("45924301010xxxxx 05/27\n49154300444xxxxx 08/27\n49154300447xxxxx 10/27");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.9.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("52373301323xxxxx xx/28");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.10.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("MATRIZ DE CC ");
                MatrixActivity.this.di.setMessage("47820020659xxxxx 06/27\n47820020649xxxxx 10/27\n406042557722xxxx 04/27\n406042557706xxxx 10/27");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.11.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button33.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("BIN/MATRIZ");
                MatrixActivity.this.di.setMessage("⚜️ SPOTIFY ⚜️\n\n💳 BIN : 515942017969xxxx  02  /  27\n\n📡 IP : Estados Unidos 🇺🇸\n\nObservação : Crie uma nova conta no Spotify sem usar VPN. Após criar a conta, vá para a parte inferior do site e mude para o Estados Unidos. Em seguida, selecione o plano de teste grátis para uma pessoa, insira os detalhes do cartão e um endereço do Estados Unidos. Depois disso, conecte-se à VPN no Estados Unidos e finalize a assinatura.\n\n⚠️ Requisitos para criar a conta\n\n1º Criar uma conta nova no Spotify.\n\n2º Tire Live da BIN para alcançar maior sucesso.\n\n3º Forneça um endereço válido do Estados Unidos.\n\n4º Utilizar uma VPN de qualidade conectada na Estados Unidos.\n\n");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.12.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button34.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("BIN/MATRIZ");
                MatrixActivity.this.di.setMessage("✈️ BIN TELEGRAM PREMIUM ✈️\n\n\n483313006342xxxx\n|09|2028\n\nIp próprio\n\nNota: (remova ao vivo e tente por um mês)\n");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.13.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button35.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("BIN/MATRIZ");
                MatrixActivity.this.di.setMessage("𝐔𝐍𝐃𝐄𝐑 𝐂𝐇𝐀𝐍𝐍𝐄𝐋:\n📹 BIN YOUTUBE PREMIUM 📹\n\n📌| BIN¹: 519471000110xxxx\n⏰| FECHA: 03/25\n♾| CVV: RND\n🆘| IP  USA 🇺🇸\n  \n🌐| CC GEN: namso-gen.com\n\n⏳|Check cc : https://www.mrchecker.net/card-checker/ccn2/\n\n📧ADDRESS 1 :- street234 \n📧 ADDRESS  221\n📍CITY :- NEW YORK \n🏠|PROVINCE :- NEW YORK \n📝|ZIP :- 10080\n\nHBO MAX 12 MESSES VIA RAPPI\n\nPrimeiro tire live nessas 2 bins\n\nBIN¹: 48151430025xxxxx|03|2025|xxx\nBIN²: 48151430034xxxxx|08|2025|xxx\nCVV: Gerado\nIP: México (Rappi)\n\n1- Cria uma conta no rappi no país mexico\n\n2- Coloque qualquer endereço do México\n\n3- Coloque esse cupom para ganhar 6 messes de rappi prime  6primexbbva23\n\n4- Para o bin ir, fique clicando sem parar com os dois dedo (ou autoclick)\n\n5- No navegador acesse esse link para pegar mais 6 messes de rappi prime\n\nhttps://rappi.onelink.me/2373836517/upyx91k1?lid=gj5rqbc5d97o\n\n6- O mesmo esquema de clicar rápido com os dois dedos ou autoclick\n\n7- Se tu fez tudo certo você terá 9 meses de rappi prime mais 3 meses que demora pra eles cancelar\n\n8- Agora só entra na hbo max, entrar pelo provedor rappi e ser feliz\n\n");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.14.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.button36.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.di.setTitle("BIN/MATRIZ");
                MatrixActivity.this.di.setMessage("BIN Canva PRO \n\n| BIN: 54677556808xxxxx\n| BIN: 54677556808xxxxx\n| DATE: 09/27\n| CVV: RND\n\nlink : canva\n\n BIN TIDAL MAYBE AUTOPAY\nBIN:546775142534xxxx|\nDate:09/25\nCVV:RND\nIP: YOURS OR MX");
                MatrixActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.15.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.di.create().show();
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MatrixActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MatrixActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA MATRIZ DE CC");
                MatrixActivity.this.d1.setIcon(R.drawable.b5184592);
                MatrixActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\nMATRIZ DE CC SÃO USADAS PARA GERAR CARTÕES DE CRÉDITO CONADOS");
                MatrixActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MatrixActivity.16.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MatrixActivity.this.d1.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.MatrixActivity$17] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.MatrixActivity$22] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.MatrixActivity$23] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.MatrixActivity$24] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.MatrixActivity$25] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.br.painel.MatrixActivity$26] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.MatrixActivity$18] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.br.painel.MatrixActivity$27] */
    /* JADX WARN: Type inference failed for: r1v22, types: [com.br.painel.MatrixActivity$28] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.MatrixActivity$19] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.MatrixActivity$20] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.MatrixActivity$21] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.17
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.18
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.19
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button32.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.20
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button29.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.21
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button30.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.22
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button31.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.23
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.24
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button34.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.25
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button35.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.26
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button36.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.27
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button33.setBackground(new GradientDrawable() { // from class: com.br.painel.MatrixActivity.28
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
