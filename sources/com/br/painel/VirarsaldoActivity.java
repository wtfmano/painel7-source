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
public class VirarsaldoActivity extends AppCompatActivity {
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
    private AlertDialog.Builder di;
    private Intent i1 = new Intent();
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
        setContentView(R.layout.virarsaldo);
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
        this.button9 = (Button) findViewById(R.id.button9);
        this.button11 = (Button) findViewById(R.id.button11);
        this.button10 = (Button) findViewById(R.id.button10);
        this.button1 = (Button) findViewById(R.id.button1);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button5 = (Button) findViewById(R.id.button5);
        this.button6 = (Button) findViewById(R.id.button6);
        this.button7 = (Button) findViewById(R.id.button7);
        this.button8 = (Button) findViewById(R.id.button8);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.d1 = new AlertDialog.Builder(this);
        this.di = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.finish();
            }
        });
        this.button9.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage(" Faça uma conta na eduzz no seu nome ou no nome de alguma lara\n\n2️⃣ Cadastro feito, Agora va no Canva e crie um e-book qualquer ( vou deixar abaixo um pack de plr's )\n\n3️⃣ Depois do ebook criado,  coloque pra vender na eduzz por 110 Reais.\n\n4️⃣ Caso não saiba criar os e-book e tals, pesquise na internet, pois estou ensinando apenas  a fraude.\n\n5️⃣ Bote o e-book a venda! Agr você vai pegar outro celular e comprar o e-book normal com seu dinheiro\n\n6️⃣ Após isso espere 30 dias para poder fazer o seu saque \n\n\u1f6e1 Após o 1° saque você já esquentou seu login, e liberou o saque pra 2 dias❗\n\n7️⃣ Pegue uma cc fuzil, (Recomendo as da @Akum4_store_bot) ou uma consul de 1k, aumente o preço do seu e-book pra 700R$ e compre com o cc\n\n8️⃣ Em 2 dias você já consegue fazer o saque, Vá repetindo o processo até receber block na conta\n\n\n\u1f6e1 Use sempre Ccleaner com modo avião ligado antes de comprar, e ative o 4g pra não queimar a consul na hora da compra❗\n\n\u1f6e1 Não faça a compra pelo mesmo celular do cadastro❗");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button11.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("💎PRIMEIRO VAI PRECISAR DE UM CELULAR QUE TENHA SUPORTE NFC (PAGAMENTOS POR APROXIMAÇÃO) ACONSELHO IPHONE ✅\n\n💎DEPOIS CRIA E-MAIL NO NOME DO BICO DA CC, ISSO MSM CC 💎\n\n💎ABRE APP CARTEIRA E SELECIONA A CONTA QUE VOCE CRIOU💎\n\n💎CLICA EM ADD CARD E ADICIONA A CC BATENDO DADOS REAIS DO DONO DA CC!!💎\n\n💎VÍNCULO  MARCHA SO PEGAR E USAR NA SUA MAQUINHA OU ONDE FOR USAR, POR APROXIMAÇÃO💎\n\nBins subindo✅\n2306 GOLD E\nCLASSIC");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("💎Pega 2 contas na uber, uma passageira sem dever nada e uma motorista. \n\n💎Pela conta passageiro, com a conta motorista ligada e perto, pede viagem pra um lugar bem longe que dê bem caro. Cidade de SP é perfeita pra isso. Pague na CC ou quando finalizar a viagem bota pra pagar na próxima. (Saldo vai pro motorista normal e conta passageiro queima). \n\n💎No celular que a conta motorista tiver instala fake gps. Configura ele certinho. \n\n💎Pediu a viagem aceita pela conta motorista. \n\n💎inicia ela e espera uns 4 minutos, depois abre o Fake GPS, bota a localização do destino e fecha e abre o app da uber. \n\n💎Quando abrir de novo vai aparecer no local de destino e vai aparecer pra encerrar. \n\n💎Encerra, se não tiver passado CC bota que o cliente paga na próxima e já era, saldo vai pra motorista, só fazer de novo e de novo até a uber blockar a conta.");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("🐇PASSO A PASSO PARA VIRAR O SALDO CORRETO NO ITI🐇.\n\n1 - pegou a consultavel Santa especifica, limpa o cel, cache e liga e desliga 4g.\n\n2 - gere um link de pagamento valor máximo 200. (GERE SEMPRE COM PORCENTAGEM) no iti \n\n3 - copia o link de pagamento e cole no Google para realizar .\n\n4 - não copie o cartão coloque número por número.  \n\n5 - use nome é cpf da consultavel na hora do pagamento. \n\n6 - pagou limpe o Google e pronto \n\nPRONTO TUD0 CERTO PAGAMENTO REALIZADO 🐇");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.5.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("Você cria uma conta em um gateway de \npagamento como pagpop, dinheiro mail, \nfb2, akatus, ou pagseguro. E La você \nterá que fazer todas as opções para \nverificar uma conta, mandar documentos \ncomo RG CPF comprovante de endereço \nisso pode ser contratado com qualquer \npessoal que mecha com documentos \neditáveis. \nOs documentos podem ser forjados \nnormalmente como alterar os dados do RG \nCPF mais tem que ser CPF valido e \nendereço com qualquer editavel de \ncomprovante de endereço \nApós fazer o cadastro no gateway de \npagamento e verificar a conta enviando \ndocumentação. Você terá que forja a \ncompra, no caso va em criar botão de \ncompra no próprio site e crie um botão \ncom as descrições das compra e o valor \nva no FrontPage selecione inserir \nhyperlink e copie e cole o código que o \ngateway de pagamentos fornecer após \nisso so salvar e abri com qualquer \nnavegador o arquivo salvo, clique no \nbotão e faça o pagamento por cartão, no \ncaso dos gateways para que seja \nautorizado a receber o pagamento por \ncartão será \nnescessario verirficar os cadastros não \ntem jeito. Isso tudo enviando as \ndocumentações que eles pedem. Após um \ndeterminado tempo você tem a opção de \npedir o saque do dinheiro na sua conta.");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("MÉTODO/ESQUEMA VIRAR SALDO OU APROVAR PRODUTO NA OLX!\n\nPrimeiramente esse método ensina a voce virar saldo na olx: ex comprar seu propio produto na olx com cc e depois sacar o dinheiro pra sua conta bancaria. e fazer isso sem parar gera uma grana boa até queimar a cc. a cc varia entre 15-80 das mais top voce pode aprovar seu propio produto com valor mais alto.\n\n1- Primeiro ativa a olxpay em 2 contas da olx. (a olxpay voce vai receber pagamentos online cartao etc e depois pode sacar pra sua conta)\n\n2- com uma das contas crie um anuncio e o valor do item de quanto voce quer virar ex: 100R$\n\n3- com sua outra conta da olxpay adicione uma cc e compre o item que voce anunciou na sua outra conta.\n\n4- Recomendo pegar cc: Classic,Gold, Standard pra cima.\n\n5- depois de pagar voce vai receber o saldo na sua conta em que anunciou depois vai e confirma o recebimento na outra e vai virando saldo e sacando pra sua conta bancaria. \n\nO saldo vai ficar disponivel na sua carteira dai quando liberar voce saca.");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.7.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("METODO DE PAGAR BOLETO NA CC\n\nCOMPRA UMA CC BOA NÃO VAI COM MATERIAL LIXO QUE NÃO VAI DAR!!\n\nPREFERÊNCIA MASTER OU VISA\n\nENTRE NO SITE PARCELE AQUI\n\nCRIA UMA CONTA COM OS DADOS FULL DA CC\n\nPROCURA PAGAR BOLETO, JOGA SEU BOLETO LA E PAGA NA CC VAI APROVAR NAORA.\n\nDA PRA VIRAR SALDO ASSIM TAMBÉM COMO É NA CC GERA BOLETO BAIXO 20 30 OU 40\n\nVAI CAIR NA SUA CONTA PRONTO JAERA \n\nNAO RESPONDO DUVIDAS LE DIREITO E FAZ\n\n• CRIADOR: @supremacorte77\n\n©️ @ClownsCanal");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.8.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button5.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.br.painel.VirarsaldoActivity.9
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("COMO VIRAR NA PAGBANK\n\nExiste um método simples e fácil, que uso e já virei lá.\n1 - Simplesmente você só vai precisar de uma Lara pagbank, faça/compre uma Lara se não tiver, você vai precisar verificar ela, tudo certinho, e depois criar links de pagamento com valor até 200 reais.\n\n2 - Feito isso, vai pegar cc da bin 606282, ou 636368, e upar em um ip novo, aba anônima e tudo, como se fosse uma pessoal real, a dica do pagseguro é upar no mesmo e-mail da puxada, desde que o bico não tenha cadastro lá (eu sempre upei assim lá).\n\n3 - Depois de upar, e der aprovado, basta esperar 15 dias, elo e Hipercard demoram muito tempo pra estornar, então vai dar certo, só confia na minha call! (É de grande importância não queimar a cc, a cc tem que estar live durante 10 dias no mínimo), eu já vi elo/Hipercard durar até 3 meses live, mas se você arrancar o saldo todo, impossível o dono não perceber.\n\n4 - Basta correr pra alegria e pro abraço depois de 15 dias, faça o pix pra outra Lara sua e já era, virada com sucesso!!! ✅\n\nErros do pagseguro:\nPagamento negado pela administradora do cartão: cc sem saldo\n\nCancelado: dados não estão batendo, ou a conta do vendedor queimou.\n\nLEMBRANDO!!!\nMuitas transações recusadas queima a conta de vendedor, então só use cc boa!\n\n@TheMetodosFree");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.10.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("\u1f3f4\u200d☠ Esquema Ton Virada de Saldo \u1f3f4\u200d☠\n\n\u1f977 1° Passo: Na lara aberta no Ton, gera link de pagamento, vai fazer o seguinte, separa uma CC full dados também, link de pagamento até 500R$\n\n\u1f977 2° Passo: Agora vai abrir o link em outro celular, ou PC, modo anônimo do navegador, vai botar os dados full da CC, e parcelar no máximo que der e mandar, aprova na hora,\n\n\u1f977 3° Passo: Recomendo se for pagar mais vezes, cria outros links, não fica pagando o mesmo link toda hora, da pra virar mais de 1k fácil, trampo ta liso, bons trampos");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.11.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("\u1f3f4\u200d☠ VIRAR SALDO ONLYFANS \u1f3f4\u200d☠\n\n\u1f977 É bom você conhecer uma dessas vadias de OnlyFans, ou vai precisar de uma mina de cúmplice.\n\n\u1f977 Se a mina ja tiver o perfil é perfeito, vai fazer o seguinte, pegar umas amex e umas platinum.\n\n\u1f977 Pelo PC no Opera, liga a VPN e abre Aba Anonima com o PC limpo, pode fazer pelo celular mas queima mais fácil. \n\n\u1f977 Não tem bin certa e não tem segredo, se a CC tiver saldo vai aprovar, depois de aprovar reinicia o pc, limpa de novo e repete o processo. \n\n\u1f977 O saque demora alguns dias e o trampo é liso, só correr certinho que esse trampo é lucrativo.");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.12.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.button8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.di.setTitle("VIRAR SALDO");
                VirarsaldoActivity.this.di.setMessage("Virada de Saldo Stone ▫️\n\n▫️Vai precisar de uma lara com link de pagamento na Stone, se for virar saldo de PVC não precisa de antecipação, mas se for cc, consultavel precisa de antecipação de 1 dia.▫️\n\n▫️Com o link de pagamento em mãos pega dados full de alguém, pega uma cc nubank ou se for consultável hipercard. ▫️\n\n▫️Vai chegar lá e pagar normalmente o link de pagamento, em até 3 pagamentos.▫️\n\n▫️Se não foi PVC espera umas 2h pra pedir antecipação e marcha.▫️");
                VirarsaldoActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.13.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.di.create().show();
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VirarsaldoActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA VIRADA DE SALDO");
                VirarsaldoActivity.this.d1.setIcon(R.drawable.b5184592);
                VirarsaldoActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\n1-APERTE NO ESQUEMA QUE DESEJAR \n\n2-SIGA OS PASSOS\n\n3-AGR É SO VER O DINHERO CAIR NA CONTA\n\nRISCO DE BO, USE COM MODERAÇÃO  ");
                VirarsaldoActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.VirarsaldoActivity.14.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                VirarsaldoActivity.this.d1.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.VirarsaldoActivity$15] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.VirarsaldoActivity$20] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.VirarsaldoActivity$21] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.VirarsaldoActivity$22] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.VirarsaldoActivity$23] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.br.painel.VirarsaldoActivity$24] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.VirarsaldoActivity$16] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.br.painel.VirarsaldoActivity$25] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.VirarsaldoActivity$17] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.VirarsaldoActivity$18] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.VirarsaldoActivity$19] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.15
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.16
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.17
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.18
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.19
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.20
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.21
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button8.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.22
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button9.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.23
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.24
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button11.setBackground(new GradientDrawable() { // from class: com.br.painel.VirarsaldoActivity.25
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
