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
public class LarasActivity extends AppCompatActivity {
    private Button button1;
    private Button button10;
    private Button button11;
    private Button button12;
    private Button button13;
    private Button button14;
    private Button button15;
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
    private AlertDialog.Builder dia;
    private ImageView imageview1;
    private ImageView imageview2;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear117;
    private LinearLayout linear118;
    private TextView textview1;
    private TextView textview5;
    private AlertDialog.Builder toba;
    private AlertDialog.Builder trocado;
    private ScrollView vscroll1;
    private Intent itii = new Intent();
    private Intent trocados = new Intent();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.laras);
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
        this.button15 = (Button) findViewById(R.id.button15);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button14 = (Button) findViewById(R.id.button14);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button5 = (Button) findViewById(R.id.button5);
        this.button6 = (Button) findViewById(R.id.button6);
        this.button7 = (Button) findViewById(R.id.button7);
        this.button8 = (Button) findViewById(R.id.button8);
        this.button9 = (Button) findViewById(R.id.button9);
        this.button11 = (Button) findViewById(R.id.button11);
        this.button13 = (Button) findViewById(R.id.button13);
        this.button12 = (Button) findViewById(R.id.button12);
        this.button10 = (Button) findViewById(R.id.button10);
        this.textview5 = (TextView) findViewById(R.id.textview5);
        this.imageview2 = (ImageView) findViewById(R.id.imageview2);
        this.toba = new AlertDialog.Builder(this);
        this.trocado = new AlertDialog.Builder(this);
        this.dia = new AlertDialog.Builder(this);
        this.di = new AlertDialog.Builder(this);
        this.d1 = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.finish();
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.toba.setTitle("ABRIR LARA ITI");
                LarasActivity.this.toba.setMessage("Método Lara iti atualizado 26/03/23 Sem dá block na mesma hora\n\nMesmo procedimento\n\nCOMO CRIAR LARA SEM DOC\n\nBanco: iti\n\n\n1- tenha dados de menores, tipo com a data de nascimento de 2006 ou 2007\n\n2- abaixem o app da iti\n\n3- na parte do número você coloca um que nunca usou na iti\n\n4- eles vai pedi uma foto mais você pode coloca a sua mesma não vai dá nada\n\n5- ser for cria pra vende, sinto muito pois você não vai conseguir fazer mais Lara seu IP vai d block,\n\n6- essa conta e só pra lava dinheiro nada mais\n\nQuando for loga na Lara sempre  usa o app\n");
                LarasActivity.this.toba.setPositiveButton("baixar iti", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LarasActivity.this.itii.setAction("android.intent.action.VIEW");
                        LarasActivity.this.itii.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.excelliance.multiaccount \n"));
                        LarasActivity.this.startActivity(LarasActivity.this.itii);
                    }
                });
                LarasActivity.this.toba.setNegativeButton("cancelar ", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.toba.create().show();
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.trocado.setTitle("ABRIR LARA TROCADOS");
                LarasActivity.this.trocado.setMessage("\u1fa78Esquema abrir lara trocados \u1fa78\n\n\u1fa781° VAI ENTRAR NA PLAY STORE OU NO APP STORE E VAI PROCURAR POR TROCADOS UM QUE TEM O SIMBOLO DO R \n\n\u1fa78 2° VAI CRIAR CONTA COM NÚMERO QUE VOCE PODE RECEBER SMS\n\n\u1fa783° PEGAR DADOS DE ALGUM GRUPO DE PUXADAS NOME CPF DATA DE NASCIMENTO TUDO CERTINHO\n\n\u1fa784° VAI COLOCAR PARA RECEBER SMS LOGO EM SEGUIDA VAI PEDIR NOME AI VOCE COLOCA O DO BICO\n\n\u1fa785° FAÇA TUDO QUE SE PEDE, DEPOIS DE FEITA SUA LARA, PARA ATIVAR CHAVE PIX VÁ EM ADICIONAR DINHEIRO E TERMINA CADASTRO\n\n\u1fa787° ASSIM QUE TERMINAR VOCÊ VAI EM ADICIONAR DINHEIRO, O VALOR MÍNIMO PARA TRANSFERÊNCIA É 100R$, DEPÓSITO DE QUALQUER VALOR\n");
                LarasActivity.this.trocado.setPositiveButton("BAIXAR APP TRCADOS", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LarasActivity.this.trocados.setAction("android.intent.action.VIEW");
                        LarasActivity.this.trocados.setData(Uri.parse("https://play.google.com/store/apps/details?id=br.com.trocados.trocadosapps"));
                        LarasActivity.this.startActivity(LarasActivity.this.trocados);
                    }
                });
                LarasActivity.this.trocado.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.3.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.trocado.create().show();
            }
        });
        this.button15.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("LARA");
                LarasActivity.this.di.setMessage("💧| TRAMPOS CRIAR MP VIA SUPORTE💧\n\n🚧 - Na minha humilde opinião esse e o jeito mais fácil de criar lara na mp\n\n💧| Passo 1 - Crie uma conta virgem no Mercado Livre pelo navegador, com o celular no modo computador ou se vc for fazer no pc melhor ainda, faça com e-mail e dos dados batendo tudo com o bico, o número pode ser o seu normal mesmo\n\n💧| Passo 2 - Agora entre no site do Mercado Pago e e faça login na conta do Mercado Livre que você acabou de criar, do mesmo jeito d passo anterior, não faça a verificação de identidade, o pulo do gato vem agora\n\n⬇️ | Passo 3 - Vá em ajuda e logo em seguida inicie uma conversa com o suporte, logo após você começara a falar com um bot, aí você fala para o bot que quer falar com algum atendente\n\n💧 | Passo 4 - Após o atendente ter entrado na conversa, você espera ele ter perguntado qual e o problema aí você cópia o texto abaixo e espera uns 30/40 segundos para enviar ele, para o atendente não desconfiar que você colou\n\n💧Desculpe pelo inconveniente, mas infelizmente a câmera do meu dispositivo está quebrada no momento, impossibilitando a realização da verificação de identidade. Estou ciente da importância dessa etapa e estou tomando as medidas necessárias para resolver o problema o mais rápido possível. Peço sua compreensão e solicito que considerem alternativas para concluir o processo de verificação temporariamente até que eu possa resolver a questão da câmera, eu tenho registros e fotos dos meus documentos no meu dispositivo, eu poderia enviar para você fazer a verificação. Agradeço sua compreensão.\n\n💧| Passo 5 - Logo após isso o atendente vai cair e vai mandar pra análise, em 3 dias aprova, e muito importante que você não entre no app até sua conta for liberada, para não blockear a lara\n\n━━━━━━━━━━━━━━━━━━━━━━━━━━\u200c\u200c\n");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.dia.setTitle("ABRIR LARA picpay");
                LarasActivity.this.dia.setMessage("TRAMPO PICPAY LARA PJ\n\n1 - Kit bico PJ, não importa renda nem nada, só precisar ser PJ\n\n2 - Baixa o PicPay Empresas\n\n3 - Pega todos os dados do CNPJ , titular do CNPJ batendo certinho, renda tudo certo, email recomendo que crie um com o nome da empresa, chip não precisar ser no CNPJ não gurizada\n\n4 - Se pedir a selfie primeiro é gol, só tira a selfie e vai ir pra análise, recomendo que a idade do rosto que for tirar a selfie bata com a idade do titular do CNPJ\n\n5 - Se pedir os docs é só espelhar, mas nem toda vez vai, mas na maioria das vezes tá pedindo só selfie gurizada bons");
                LarasActivity.this.dia.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.5.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.dia.create().show();
            }
        });
        this.button14.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.dia.setTitle("ABRIR LARA ASTROPAY ");
                LarasActivity.this.dia.setMessage("✅ LARA ASTROPAY\n\n📝 1-PASSO: vai pegar dadosl full, pegar um editável velha, ou usa seu RG mesmo, um chip qualquer pro sms, email tbm\n\n📝 2-PASSO: colocar as informações tudo certinho, colocar o número pro sms, pode ser qualquer um, até mesmo de bot\n\n📝 3-PASSO: não verifica o CPF, deixa para depois, tira foto da editável ou RG, ai depois você colocar o cpf que deseja, e vapo gurizada, só aproveitar,\n\n📝 OBS: depois de criar umas 3 a 4 contas vai começar a bloquear, recomendo formatar o celular.....\n\n");
                LarasActivity.this.dia.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.dia.create().show();
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA infinity ");
                LarasActivity.this.di.setMessage("📲 ABRINDO LARA INFINITYPAY  SO COM SELFIE \u1f933\n\nPRIMEIRO PEGA OS DADOS FULL DO BICO QUE VOCE PRETENDO CRIAR A LARA\n\nENTRA PELO APLICATIVO NORMAL DA INFINITYPAY \n\nVAI EM CRIAR CONTA .\n\nBOTA CPF,TUDO BATENDO.\n\nFAZ UM GMAIL BATENDO COM O NOME DO BICO\n\nNO CHIP PODE SER O SEU MESMO\n\nFAZENDO ISSO VAI PEDIR\n\nSELFIE, PODE BOTAR O SEU ROSTO.\n\nVAI PRA ANALISE\n\nEM 20 MINUTOS LARA TA PRONTA.\n\nOBS: REPITO BOTA TUDO EM DADOS REAIS DO BICO.\n\nESSE E O REAL ESQUEMA DADOS FULL");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.7.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA OROBANCO");
                LarasActivity.this.di.setMessage("\u1f3f4\u200d☠ ABRINDO LARA OROBANCO SEM DOCS E SELFIE \u1f3f4\u200d☠\n\n\u1f977 \ufeff\ufeff1° Pega os dados full que é seu alvo, faça a puxada completa do dados do bico escolhido.\n\n\u1f977 2° Baixa o APP OroBanco em um celular limpo e via 4G, baixou abre o APP e inicia cadastro.\n\n\u1f977 \ufeff\ufeff3° Vai por todos os dados do bico batendo 100% certo, endereço dele, email e número você pode usar qualquer um que você tenha acesso pra pegar os códigos.\n\n\u1f977 4° Chegando nos DOCS, vai pegar uma editável velha, ou até um documento velho seu ai, do seu pai tanto faz, e tira foto, na selfie tira foto do seu rosto e já era vai aprovar em até 1 hora.\n\n\u1f977\ufeff\ufeff 5° Se ficar em análise infinita o DOC pode estar rodado, o celular pode estar sujo, são inúmeras possibilidades, só abre 1 por rosto.");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.8.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA Modalmais");
                LarasActivity.this.di.setMessage("\u1f575\u200d♂MÉTODO LARA MODALMAIS 2023\u1f575\u200d♂\n\n1:BAIXE O APP NA PLAY STORE NORMALMENTE.\n\n2:DEPOIS VA A UM BOT DE PUXADAS É PEGUE UMA CONSULTA FULL QUE TEJA TUDO.\n\n3:DEPOIS ENTRE NO BANCO É COLOQUE OS DADOS QUE PEDIR.\n\n4:COLOQUE OS DADOS CERTINHO, PARA QUE TUDO DER CERTO.\n\n5: O BANCO VAI PEDIR UMA FOTO É VC PODE COLOCAR A SUA NORMALMENTE.\n\n6:DEPOIS DISSO VAI APARECER A PARTE ONDE VC VAI COLOCAR OS DADOS COMO,RG,DATA DE EMISSÃO,ETC... OU VC PODE ESCOLHER OS OUTROS MODOLOS.\n\n7:O BANCO NÃO PEDIRA FOTO DE RG NEM NADA DISSO, É SO COLOCAR OS DADOS FULL QUE VAI PASSAR NUMA BOA.\n\n💎BONS TRAMPOS💎\n\nCRÉDITOS: @DREAMDX1995");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.9.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA MP");
                LarasActivity.this.di.setMessage("ESQUEMA ABRINDO LARA MP 2023 \n\nVai abrir o MercadoLivre por modo computador, num celular limpo.\n\nCria a conta no MercadoLivre normal, vai ficar em pendencia os documentos e etc.\n\nVai pegar um documento e editar com uma 3x4 sua ou de algum amigo em cima da do bico.\n\nFeito isso, abre o WPP, chama a atendente e diz que não ta conseguindo verificar a conta pelo APP, diz que a câmera fica toda preta.\n\nA assistente vai pedir os docs e 3 selfies e 1 vídeo, envia tudo pra ela pelo WPP que ela vai mandar pra análise.\n\nMandou pra análise já era, só espera aprovar.\n\nCaso queira adquirir CCs No preço\nhttps://t.me/TheMetodosFree/2276\n\ncanal vip no preço \nhttps://t.me/TheMetodosFree/1996\n\nMétodo grátis pra fazendo +100R$\nhttps://t.me/TheMetodosFree/2986\n\n\u1fa78CANAL: @TheMetodosFree\n\u1fa78CHAT: @TheMetodosFree2");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.10.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button8.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA SANTANDER ");
                LarasActivity.this.di.setMessage("📖 Trampo Lara PJ Santander\n\n✏️ Créditos: @centraldo77 @jessereserva\n\n📖 Passo 1 - Vai precisar de um kit bico pj top, faz a puxada completa, separa tudo certinho, sobe um CHIP e um Email, nesse trampo é obrigatório subir chip no nome do bico, não tem outra opção\n\n📖 Passo 2 - Entra nesse site aqui https://www.santander.com.br/conta-empresarial/cnpj?gad=1&gclid=CjwKCAjw9pGjBhB-EiwAa5jl3LckDJBo0hbW7i8X9h4PE0i6x6OhGoOnOYqWPy8ULtiE-nil7_HjURoCHjAQAvD_BwE&gclsrc=aw.ds\n\n📖 Passo 3 - Entrou no site, vai preenchendo todos os dados do bico batendo 100% certo, email e número que você subiu, vai enviar pra uma análise, espera de 1 a 2 dias que entram em contato de novo no Email\n\n📖 Passo 4 - No e-mail vão te enviar um site pra você enviar os documentos, os documentos você consegue enviar pela galeria, mas a selfie você vai ter que tirar, então você faz o seguinte, troca a 3x4 do doc por uma sua, e tira a selfie e envia o doc com sua 3x4\n\n📖 Passo 5 - Feito tudo isso, agora vai só esperar a outra análise dos documentos, se aceitarem loga na conta e vai pedir pra ativar token, você faz o seguinte, tira a sua selfie mesmo mas se for vender, invez de sua 3x4 e sua selfie você pede pro cliente criar e tirar lá, só libera 1 conta por rosto, então se for abrir mais vai precisar de outros rostos, bons trampos gurizada");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.11.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button9.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA ZROBANK");
                LarasActivity.this.di.setMessage("📖 Trampo Lara ZroBank\n\n✏️ Créditos: @centraldo77 @jessereserva\n\n📖 Passo 1 - Kit bico sem muitas consultas e sem estar rodado, baixa qualquer emulador que tenha suporte pra câmera virtual e baixa o ManyCame e crackeia pra tirar a marca d'agua do ManyCam\n\n📖 Passo 2 - Vai fazer o seguinte, abre o emulador e vincula um conta Google nova recém criada no emulador pra baixar APPs na PlayStore\n\n📖 Passo 3 - Agora baixa o APP ZroBank, nele começa o cadastro certinho bota todos dados do bico batendo, pode botar endereço dele também por que não libera crédito\n\n📖 Passo 4 - Chegou nos DOCS vai enviar os DOCS pelo ManyCam, mesma coisa com a selfie e vai enviar, aprova na mesma hora, bons trampos gurizada libera PIX CPF, Email, Telefone tudo que quiser");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.12.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button11.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA MOZPER");
                LarasActivity.this.di.setMessage("LARA MOZPER MENOR DE IDADE\n➗➗➗➗➗➗➗➗➗➗➗\n📔 Com dados de uma pessoa do distrito federal e que tenha filhos entre 15 e 16 anos.\n\n📔 Faz uma editável com os dados do menor de idade e põe sua foto, recomendo por no FaceApp deixa você mais jovem.\n\n📔 Agora baixa o aplicativo Mozper e faz um Gmail com o nome da mãe.\n\n📔 Gmail criado, entra no aplicativo Mozper e vai à opção criar conta para o meu filho, colocar os dados se quiser pôr telefone da mãe ou o seu tanto faz.\n\n📔 Depois de colocar todos os dados, vamos para Foto você vai tirar do seu rosto, recomendo lavar a cara e tira a barba e corta o cabelo.\n\n📔 Por último envia a editável de RG e pronto se ela tiver boa vai abrir na hora.\n\n✏️BÔNUS - vai em um grupo de puxada e pesquisa 2007 ou 2008,aí fica fácil de achar os dados.\nNa Editável põe qualquer data de emissão e qualquer número de RG.\n\n📣CRIADOR - @coderkioll");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.13.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button13.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA BANQI ");
                LarasActivity.this.di.setMessage("📖 Trampo Invasão de Lara BANQI\n\n📖 Passo 1 - Tem algum kit bico com cadastro no BANQI e não sabe o que fazer? Vem comigo, vai entrar no APP BANQI, botar esqueci a senha, e enviar o CPF, vai apertar em reenviar 2 ou 3 vezes e já vai aparecer em baixo \"entre em contato conosco via whatsapp\", clica lá e vai pro Whats\n\n📖 Passo 2 - Enviou a mensagem vai escolher a opção \"Sim já tenho conta BANQI\", depois irão perguntar se você está conseguindo acessar sua conta BANQI você bota a opção 2. Não.\n\n📖 Passo 3 - Vai aparecer várias opções, escolha a opção 3. Alterar E-mail. E em seguida escolha a opção 2. Quero trocar e-mail por que não tenho mais acesso a esse. 1. Não lembro o e-mail cadastrado.\n\n📖 Passo 4 - Em seguida, digite o email o qual você vai usar pra invadir a conta, no caso qualquer um seu mesmo, em seguida vão enviar um SMS pro seu número de telefone (o que você esta usando no WhatsApp), e em seguida irão perguntar se você quer seguir com esse número mesmo,\n\n📖 Passo 5 - Agora vão ter 2 caminhos, as vezes buga e irão te encaminhar direto pro especialista, se for pro especialista vai ter que dar a lábia boa nele, trocar uma ideia maneira e em seguida ele vai pedir os DOCS e a selfie, e vai trocar o e-mail pro seu.\n\n📖 Passo 6 - Agora se não for pro especialista, vai direto e vai respondendo as perguntas de acordo com os dados e ai vai pedir os DOCS, envia 1 de cada vez conforme o robô pedir, e manda que ai vai entrar em análise, e em até 2h útil você vai receber um email confirmando a mudança de e-mail\n\n📖 Passo 7 - Email trocado vai acessar a lara pelo aplicativo, vai pedir PIN, erra 3 vezes o PIN que vão enviar um código pro email pra trocar o PIN, só trocar e seja feliz com sua nova lara ou com a grana que tiver lá haha tamo junto gurizada");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.14.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button12.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA DIMO ");
                LarasActivity.this.di.setMessage("\u1fa78METODO CRIA LARA DIMO \u1fa78\n\n\u1fa78 VAI PRECISAR DE UM CELULAR DA MOTOROLA QUE ESTEJA COM IP LIMPO\n\n\u1fa78 LIGA 4G SE POSSÍVEL  FORMATA O CELULAR\n\n\u1fa78 VAI PRECISAR DE UM KIT FE BICO FULL VIRGEM OU QUE NÃO TENHA NENHUMA CONTA NA DIMO\n\n\u1fa78 PRENCHE TUDO COM OS DADOS DO BICO\n\n\u1fa78 DEPOIS NA HORA DE PEDIR OS DOCS USAR O BURLADO, E CLICKA NA NOTIFICAÇÃO QUE VAI APARECE E VAI REDIRECIONAR PRA SUA GALERIA SELECIONA A FOTOS\n\n\u1fa78 DEPOIS CLICKA EM CONTINUAR E ESPERA SE APROVADO, VAI CHEGA NO SEU EMAIL");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.15.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.button10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.di.setTitle("ABRIR LARA CACTVS ");
                LarasActivity.this.di.setMessage("💸 ESQUEMA ABRIR LARA CACTVS SEM DOCS/SELFIE 💸\n\n🔢 - Dados full de qualquer pessoa, pega e deixa separadinho e vai baixar o APP Cactvs\n\n🔢 - Baixou o APP, vai pegar e iniciar o cadastro, email e telefone tanto faz, bota lá os dados full\n\n🔢 - Chegou nos DOCS e na selfie tira foto com tudo preto, isso mesmo, bota o dedo na câmera ou um pano tanto faz e tira, aprova na hora e libera PIX mas o PIX tá em manutenção a uns 2 dias, bons trampos gurizada\n\n➡️ Créditos: jessereserva");
                LarasActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.16.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.di.create().show();
            }
        });
        this.imageview2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.LarasActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LarasActivity.this.d1.setTitle("AQUI VOCÊ ENCONTRA CONTAS LARA");
                LarasActivity.this.d1.setIcon(R.drawable.b5184592);
                LarasActivity.this.d1.setMessage("⚠️ATENÇÃO⚠️\n\n1-APERTE NO BANCO QUE DESEJAR CRIA A LARA\n\n2-SIGA AS INSTRUÇÕES DADAS\n\nRISCO DE B0, USE COM CUIDADO \n\n(LARA=CONTA BANCÁRIA EM NOME DE TERCEIROS)\n\n📚  Como Usar Lara Do Jeito Certo\n\n📚 Passo 1 - Sempre mande chave aleatória pro seu cliente \n\n📚 Passo 2 - uma chave aleatória pra cada 4 clientes, depois você apagar e criar outra\n\n📚 Passo 3 - Não use lara pra dar lotter, ou vai perder ela rapidinho \n\n📚 Passo 4 -  Não acumule dinheiro em uma lara, se ela por algum acaso der block você perde tudo\n\n📚 Passo 5 - Se você usar sua conta pessoal pra dar golpe, pode esperar uma bala se dirigindo a sua cabeça, ou Xadrez");
                LarasActivity.this.d1.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.LarasActivity.17.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                LarasActivity.this.d1.create().show();
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.LarasActivity$18] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.LarasActivity$23] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.LarasActivity$24] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.LarasActivity$25] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.LarasActivity$26] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.br.painel.LarasActivity$27] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.LarasActivity$19] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.br.painel.LarasActivity$28] */
    /* JADX WARN: Type inference failed for: r1v22, types: [com.br.painel.LarasActivity$29] */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.br.painel.LarasActivity$30] */
    /* JADX WARN: Type inference failed for: r1v26, types: [com.br.painel.LarasActivity$31] */
    /* JADX WARN: Type inference failed for: r1v28, types: [com.br.painel.LarasActivity$32] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.LarasActivity$20] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.LarasActivity$21] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.LarasActivity$22] */
    private void initializeLogic() {
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.18
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.19
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.20
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.21
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.22
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.23
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.24
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button15.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.25
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button14.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.26
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button8.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.27
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button9.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.28
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.29
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button11.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.30
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button12.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.31
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button13.setBackground(new GradientDrawable() { // from class: com.br.painel.LarasActivity.32
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
