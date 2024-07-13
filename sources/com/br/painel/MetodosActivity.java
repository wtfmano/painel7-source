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
import android.widget.HorizontalScrollView;
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
public class MetodosActivity extends AppCompatActivity {
    private Button button1;
    private Button button10;
    private Button button11;
    private Button button14;
    private Button button15;
    private Button button16;
    private Button button17;
    private Button button18;
    private Button button19;
    private Button button2;
    private Button button20;
    private Button button21;
    private Button button22;
    private Button button23;
    private Button button24;
    private Button button25;
    private Button button26;
    private Button button27;
    private Button button28;
    private Button button29;
    private Button button3;
    private Button button30;
    private Button button31;
    private Button button32;
    private Button button35;
    private Button button37;
    private Button button39;
    private Button button4;
    private Button button40;
    private Button button41;
    private Button button42;
    private Button button43;
    private Button button44;
    private Button button46;
    private Button button47;
    private Button button48;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button9;
    private AlertDialog.Builder d1;
    private AlertDialog.Builder di;
    private HorizontalScrollView hscroll1;
    private Intent i1 = new Intent();
    private ImageView imageview1;
    private ImageView imageview3;
    private ImageView imageview4;
    private ImageView imageview5;
    private ImageView imageview54;
    private LinearLayout linear115;
    private LinearLayout linear116;
    private LinearLayout linear117;
    private LinearLayout linear119;
    private LinearLayout linear120;
    private LinearLayout linear121;
    private LinearLayout linear122;
    private LinearLayout linear215;
    private LinearLayout linear217;
    private TextView textview1;
    private TextView textview10;
    private TextView textview11;
    private TextView textview13;
    private TextView textview6;
    private TextView textview7;
    private TextView textview9;
    private ScrollView vscroll1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.metodos);
        initialize(bundle);
        FirebaseApp.initializeApp(this);
        initializeLogic();
    }

    private void initialize(Bundle bundle) {
        this.linear115 = (LinearLayout) findViewById(R.id.linear115);
        this.vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
        this.imageview1 = (ImageView) findViewById(R.id.imageview1);
        this.textview1 = (TextView) findViewById(R.id.textview1);
        this.linear215 = (LinearLayout) findViewById(R.id.linear215);
        this.linear116 = (LinearLayout) findViewById(R.id.linear116);
        this.textview10 = (TextView) findViewById(R.id.textview10);
        this.linear117 = (LinearLayout) findViewById(R.id.linear117);
        this.textview11 = (TextView) findViewById(R.id.textview11);
        this.button41 = (Button) findViewById(R.id.button41);
        this.button30 = (Button) findViewById(R.id.button30);
        this.button27 = (Button) findViewById(R.id.button27);
        this.button29 = (Button) findViewById(R.id.button29);
        this.button43 = (Button) findViewById(R.id.button43);
        this.button48 = (Button) findViewById(R.id.button48);
        this.button46 = (Button) findViewById(R.id.button46);
        this.button47 = (Button) findViewById(R.id.button47);
        this.button44 = (Button) findViewById(R.id.button44);
        this.button42 = (Button) findViewById(R.id.button42);
        this.button40 = (Button) findViewById(R.id.button40);
        this.button39 = (Button) findViewById(R.id.button39);
        this.button37 = (Button) findViewById(R.id.button37);
        this.button35 = (Button) findViewById(R.id.button35);
        this.button32 = (Button) findViewById(R.id.button32);
        this.button31 = (Button) findViewById(R.id.button31);
        this.button28 = (Button) findViewById(R.id.button28);
        this.button26 = (Button) findViewById(R.id.button26);
        this.button25 = (Button) findViewById(R.id.button25);
        this.button24 = (Button) findViewById(R.id.button24);
        this.button23 = (Button) findViewById(R.id.button23);
        this.button22 = (Button) findViewById(R.id.button22);
        this.button21 = (Button) findViewById(R.id.button21);
        this.button20 = (Button) findViewById(R.id.button20);
        this.button19 = (Button) findViewById(R.id.button19);
        this.button18 = (Button) findViewById(R.id.button18);
        this.button17 = (Button) findViewById(R.id.button17);
        this.button16 = (Button) findViewById(R.id.button16);
        this.button15 = (Button) findViewById(R.id.button15);
        this.button1 = (Button) findViewById(R.id.button1);
        this.button14 = (Button) findViewById(R.id.button14);
        this.button2 = (Button) findViewById(R.id.button2);
        this.button3 = (Button) findViewById(R.id.button3);
        this.button4 = (Button) findViewById(R.id.button4);
        this.button5 = (Button) findViewById(R.id.button5);
        this.button6 = (Button) findViewById(R.id.button6);
        this.button7 = (Button) findViewById(R.id.button7);
        this.button9 = (Button) findViewById(R.id.button9);
        this.button11 = (Button) findViewById(R.id.button11);
        this.button10 = (Button) findViewById(R.id.button10);
        this.hscroll1 = (HorizontalScrollView) findViewById(R.id.hscroll1);
        this.linear119 = (LinearLayout) findViewById(R.id.linear119);
        this.linear121 = (LinearLayout) findViewById(R.id.linear121);
        this.linear120 = (LinearLayout) findViewById(R.id.linear120);
        this.linear217 = (LinearLayout) findViewById(R.id.linear217);
        this.linear122 = (LinearLayout) findViewById(R.id.linear122);
        this.imageview5 = (ImageView) findViewById(R.id.imageview5);
        this.textview9 = (TextView) findViewById(R.id.textview9);
        this.imageview3 = (ImageView) findViewById(R.id.imageview3);
        this.textview6 = (TextView) findViewById(R.id.textview6);
        this.imageview54 = (ImageView) findViewById(R.id.imageview54);
        this.textview13 = (TextView) findViewById(R.id.textview13);
        this.imageview4 = (ImageView) findViewById(R.id.imageview4);
        this.textview7 = (TextView) findViewById(R.id.textview7);
        this.d1 = new AlertDialog.Builder(this);
        this.di = new AlertDialog.Builder(this);
        this.imageview1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.finish();
            }
        });
        this.button41.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("💧MÉTODO DESBANIR CONTA DE FF [2023] 💧\n\n💧1 - Abra o suporte da garena https://ffsuporte.garena.com/hc/pt-br\n\n💧2 - Clica nas 3 barrinha do lado em \"entrar\"\n\n💧3 - Clique em \"Abra um ticket\"\n\n💧4 - Na parte \"escolha um assunto\" coloque a seguinte opção \"Detectamos o uso de softwares/app/apk não oficiais da Garena que violam nossos Termos de Serviço. Esta conta foi suspensa permanentemente.\"\n\n💧5 - Coloque seu email, id da conta, Nick do jogo, tempo de suspensão e a data de quando você levou ban\n\n💧6 - Na descrição coloque o seguinte texto \"No dia XX/XX eu emprestei minha conta para um amigo, ele jogou por 3 semanas na conta, no dia XX/XX entrei na conta e estava banida perguntei para ele se ele tava usando alguma coisa de terceiros e respondeu que não e eu também não usei nada de errado que poderia resultar em banimento de minha conta, peço que tirem o ban de minha conta, preciso dela para fazer Live e ganhar o meu dinheiro para pagar as dividas de casa. \"\n\n💧7 - Tire um print da mensagem que aparece quando você clica para entrar na conta\n\n💧❗️ONDE EU COLOQUEI A DATA XX/XX E PARA VOCÊ PEGAR O DIA QUE LEVOU BAN E FAZER MENOS 21 DIAS EXEMPLO: levei ban dia 15/09 | -21 dias | meu amigo jogou dia 24/08\n\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button30.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("⚜️ MÉTODO SUBIR PUTAS ⚜️\n\nAGORA C VAI TRANSA P CARALHO\n\nSITE: www fatalmodel com\n\nescolha uma ninfetinha de seu interesse da sua cidade. 😎\u1f91d\n\nnão leve ela na sua casa, procure as que tem lugar certo, p caso o estorno vim ela não ir te incomodar na tua baia\n\nse for no zap procure usar numero fake, caso o estorno vim a puta não incomoda.\n\n💠 70% DAS GAROTAS DE PROGRAMA DA FATAL ACEITAM PAYPAL E SUMUP, PEÇA O LINK DE PAGAMENTO E PAGUE COM A CC, AS QUE ACEITAM PAYPAL DA PRA SUBIR COM GG! \u1f911\n\n⚠️ NÃO ME RESPONSABILIZO SE TU UPAR UM TRAVECO, ESTORNAR E TU APANHAR DELES!\n\nLEMBRANDO: garanto o esquema, não a transa 😎\u1f91d\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.3.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button27.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("A prática é ilegal e pode ter consequências graves, incluindo multas pesadas e até mesmo problemas legais.\n\nPrimeiramente, é importante separar sua renda e despesas para que possa manipulá-las de forma apropriada.\n\nIdentifique oportunidades de esconder parte da renda, como não declarar ganhos extras ou receber pagamentos \"por fora\".\nVocê também pode inflar suas despesas, criando recibos falsos ou registrando despesas inexistentes.\n\nUtilize contas bancárias no exterior para movimentar dinheiro e ocultar transações suspeitas.\n\nConsidere criar empresas fictícias ou \"laranjas\" para desviar recursos e evitar que a origem do dinheiro seja rastreada.\n\nNunca mencione essa prática a ninguém e seja cuidadoso para não levantar suspeitas.");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.4.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button29.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("1 - Usar Matriz on. \n\nMATRIZ DO MOMENTO SUBINDO 1K;\n4984069448XXXX 03/24 ✓\n\nNOVAS MATRIZES:\n\n6509079003547475|11|2024|000\n4984076128807429|09|2024|658\n\n2- preencha tudo normalmente, inspecione e delete usando 789\n(E a parte de inspecionar é apertando shift e i),  e quando preencher o nu\u200cmero da casa voce\u200c coloca sempre nessa base aqui\nEx: 56bs 56ab 56xd 56ls 56ps\nPode sentar o dedo.\n\n3- pronto aqui que ta o macete, quando voce\u200c efetuar a primeira compra de algo entre 50-100$\nPrint a parte de baixo com todos os dados da primeira compra (ela fica logo abaixo do pedido realizado)\n\n4- toda vez que for comprando fazer repetic\u200ca\u200co de todos os dados igualmente o primeiro pedido, na\u200co pode errar uma vi\u200crgula tem que ta\u200c tudo perfeito.\n\n5- pronto fac\u200ca os pedidos e copie o link de url na parte acima do edit web a cada pedido, fecha a aba do app e espere 5 mim\n\n6- pronto o esquema esta\u200c pronto para ser usado, so\u200c fazer como foi dito acima tudo certinho e fac\u200ca ate\u200c secar o saldo da GG (provavelmente ate\u200c 5 pedidos), na mesma GG.");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.5.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button43.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("\u1f3f4\u200d☠ ESQUEMA DE ESTOCÃO \u1f3f4\u200d☠\n\n⚠️ Atenção: Conteúdo pesado, tenho consciência do que vai fazer\n\n⚠️ O alvo vai ser esses tarados FDP que fica nas redes sociais olhando perfil de menina de menor, mandando nuds e etc \n\n\u1f977 1° + Importante, tenha uma Lara\n\n\u1f977 2° Dois Facebook antigo sem postagens, não use Instagram \n\n\u1f977 3° Se tiver tela fake do Facebook, o esquema vai ficar 100%\n\n\u1f977 4° Faça um perfil de uma garota de menor, tenha fatos pra postar no perfil e feed, modifique 100% o Facebook, seja criativo\n\n\u1f977 5° Entre em grupos de amizades da sua cidade, ou de outras, vai da sua escolha\n\n\u1f977 6° Tenha um grupo ou painel de puxada\n\n\u1f977 7° Vai convidando, e esperando eles convidar, e vai aceitando os convites, responde quem chamar e chame que não falar nada\n\n\u1f977 8° Use engenharia social pra desenrolar as conversas, entre nas ideias do indivíduo\n\n\u1f977 9° Se tiver tela fake do Facebook, use um 2° Facebook, diga a o indivíduo, exemplo: \"Me chama no outro face vida, esse o meu irmão tem a senha e entra às vezes\" Ai vocês manda o link da tela fake pra hackear o Facebook\n\n⚠️ Mais antes de tentar hackear o face, faça isso a baixo 👇\n\n- Vai conversando\n- Printis de todas as conversas\n- Peça nuds, vídeos e etc\n- Peça o whatsapp\n- Puxada de dados\n- Pegue todos os dados dele, CPF, nome completo e etc\n- Observe quem curte e comenta as publicações dele pra usar como ameaça de vazamento \n\n\u1f3f4\u200d☠ Após todos os requisitos, você vai atacar, ataque do jeito certo, pode pedir muito dinheiro \n\n⚠️ Quando hackear o Facebook, troque todos os dados , entre no face com a tela gravando as conversas, pra se caso a vítima recupera, você tenha gravações \n\n⚠️ NÃO USE SEUS DADOS BANCÁRIOS PRE RECEBER DINHEIRO SUJO\n\n⚠️ NÃO USE ESSE MÉTODO PRA FERIR PESSOAS INOCENTES, LEMBRE-SE QUE O ALVO É OS TERADOS FDPS\n\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.6.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button48.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("🔥 Método pra Assusta Qualquer Um\n(Via SMS)\n\nPra começar não vamos deixar nosso número né amigo.\n\nRequisitos:\n- Internet\n- Conta PayPal\n\n1- vá na play story e pesquisa por (2Number) e instala.\n\n2- abra o aplicativo e faça login via Google, preencha as formações e as coisas sobre o número\n\n3- na hora do pagamento vai na terceira opção que tem algo relacionado a free\" depois disso assina com a conta PayPal\n\n4- você já vai cair no inbox então é só clicar no \"+\" colocar o número e a mensagem\n\n(Não funciona como número fake pra receber sms!)\n\nBy: @Webzin116\nnão tira os créditos netfree");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.7.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button46.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("Este trampo é privado e próprio da anônimos comunity , Vaze com permissão!\nTrampo para Atrair clientes /Como vender bem \n\n1° Tenha uma boa divulgação em grupos grandes para se dar bem , Escolha um produto fixo para vender e atrair clientes que sempre vão comprar col você \n\n2° Crie um grupo de referências, E poste todo dia pelo menos uma referência para deixar salvo e verem , Coloca o grupo na sua bio e espere \n\n3° Divulgue sua página pelos grupos e vá fazendo promoções \n\nBy: Snowd3v \n\nO método será atualizado daqui um tempo!");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.8.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button47.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("Método colher dados completo para aprovação fácil , Criar contas em apps anonimamente,  Entre outros\nMasculino/Feminino \n\nIremos trabalhar com engenharia nova , Funciona da seguinte forma \nIrá criar uma conta em algum app que esteja lucruando \nApós isso vem os ensinamentos abaixo:\n\n\n1° Após criar a conta entre no app e se possível tente fazer um saque nem que seja de 15 \n\n2° Fale que vai criar uma conta no app no nome da pessoa e que irá depositar 10 para fazer o esquema (tem que censurar o app pra pessoa não desconfiar)\n\n3° Fale que o App pede \nNome (Primeiro e sobrenome)\nCPF \nData de nascimento \nE Após a pessoa mandar,  Consulte em algum Bot de dados ou em algum painel que soltarmos no grupo \n\n4° Após fazer tudo isso, Enrole a pessoa e faça algum jeito para sumir com ela e con todas print de sua conversa (recomendo bloquear e excluir o chat quando enviar Cpf e nome )");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.9.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button44.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("💝 ESQUEMA MAGALU 2023 💝\n\n1️⃣- Cell 100% Limpo, E IP Sem BlackList Na Magalu. \n\n2️⃣- Vai Pegar Uma Consul Magalu, Dados Full Do Seu Estado E Que Não Tenha Saldo No Site Da Magalu, Score Até 950 \n\n3️⃣- Com Dados Full Da Consul, Abre Um E-mail, E Sobe Um Chip No CPF Do Bico \n\n4️⃣- Após Fazer Isso, Vai No Magalu, E Cria Uma Conta Com Todos Os Dados Batendo \n\n5️⃣- Escolhe Um Pedido De Até 80$, E Paga Ele Normalmente Sem Ser Na CC, Paga Com Seu Dinheiro Normalmente Pra Movimentar A Conta \n\n6️⃣- Depois Pega A Consul, Bota Até 1K De Primeira No Carrinho, E Sobe Normal. Como Sempre Endereço De Cobrança Do Bico, Drop Seu \n\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.10.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button42.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("📕 | METODO CORRIDA 99\n\n📕 | Primeiro limpe Bem o Celular com ccleaner com Modo Avião Ligado. (ccleaner tem no grupo do canal)\n\n📕 | Após limpar ligue novamente o 4G.\n\n📕 | Entra na 99 Normal e cria um login novo ou sem rastros. Deixa Verificada bonitinha e marcha no próximo passo.\n\n📕 | Pega uma  CC Com essa bin que aprovei 407843.\n\n📕 | Adiciona a CC a Sua conta. Lembrando Sempre usando 4G");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.11.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button40.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("💧TRAMPO UPANDO RENNER \n\n💧 1- Pegue os dados de um bico da sua cidade e suba um chip no nome do bico. (vivo sobe fácil)\n\n💧 2- Cria e-mail batendo com nome do bico, ex:\nNome: João Batista Junior\nE-mail: Joaobatistajunior@gmail.com\n\n💧 3- Cria uma conta no site da Renner com dados do bico.\n\n💧4- Esquenta o login por 10 minutos e vai enchendo o carrinho até R$550 a R$700 \n\n💧 5- Vai neste Bot  e compre uma CC PLATINUM, BLACK ou INFINITE (SEMPRE NÍVEIS ALTOS)\n\n💧 6- Digite todos os dados pra pagamento e coloca o nome do bico, caso vá para análise rlx que aprovou mas caso demore muito desiste brecou.\n\n💧Adicionais: \n\nAs bins com início 651xxx e 650xxx estão subindo suave.\n\nPode ocorrer de mensagem no ZAP para confirmação de pedido.\n\nVocê também pode estar comprando um login com pedidos (mais garantido).\n\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.12.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button39.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("💧 ESQUEMA MAGALU 2023 💧\n\n💧Cell 100% Limpo, E IP Sem BlackList Na Magalu. 💧\n\n💧 Vai Pegar Uma Consul Magalu, Dados Full Do Seu Estado E Que Não Tenha Saldo No Site Da Magalu, Score Até 950 💧\n\n💧 Com Dados Full Da Consul, Abre Um E-mail, E Sobe Um Chip No CPF Do Bico 💧\n\n💧 Após Fazer Isso, Vai No Magalu, E Cria Uma Conta Com Todos Os Dados Batendo 💧\n\n💧 Escolhe Um Pedido De Até 80$, E Paga Ele Normalmente Sem Ser Na CC, Paga Com Seu Dinheiro Normalmente Pra Movimentar A Conta 💧\n\n💧Depois Pega A Consul, Bota Até 1K De Primeira No Carrinho, E Sobe Normal. Como Sempre Endereço De Cobrança Do Bico, Drop Seu\n\n ");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.13.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button37.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("📊 Golpe do Empréstimo 📊\n\n \n💲 - Vai precisar de um WhatsApp, nesse WhatsApp você vai montar o fake de alguma empresa de empréstimo, dá pra se passar por representante do Santander também e etc, recomendo se passar por uma empresa terceirizada, vocês vão entender no meio do trampo.\n\n 💲 - Vai subir um Facebook também, recomendo comprar antigo e vai postar um anuncio mais ou menos dizendo que você realiza empréstimos e que cobra 10% do valor liberado, ou então diga que você realiza saque FGTS rápido e 10% do valor liberado também, vai ter que montar um anúncio mais ou menos assim.\n\n💲 - Fez isso vai esperar as vítimas começarem a chamar, a plataforma que eu vou usar pra realizar os empréstimos não pede DOC e é a Losango. Enquanto conversa com o bico tu vai pedir os dados dele só pra fazer uma média, mas com o CPF dele tu sobe uma puxada boa e já faz o cadastro no Losango e vê se libera.\n\n 💲 - Se liberar você vai falar com o bico, que liberou o valor lá, e diga que o pagamento começa daqui 90 dias, convença o bico de pegar o empréstimo, pede a conta e agência dele, e quando cair pede pra ele pagar um boleto que você vai enviar com os 10% valor exato certinho, se vocês conseguirem alguma lábia pra pegar a grana toda é boa, mas a tática que eu subi foi essa, bons trampos gurizada\n\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.14.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button35.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage(" @BY_COMICK_2018 ------------- DOCUMENTO----------- EDITADO----------ATUALIZADO\n\n\n(DIGA!) puxe o maximo de dados pois no final tera algumas perguntas como:\nendereços\nplacas de carro modelo (puxe cnh)\nnomes de empresas\nsocios\nparentes\nendereços que ja foram das passoas \n\nPUXOU TUDO DPS SÓ ALEGRIA\n\n\n1- PEGUE SEU ESCORE ACIMA DE 900 SE NÃO NEM TENTA OK \n\n2- Faça um Email YHOO,TERRA, OU EMAIL CLOUD DO IPHONNE - EMIAL ICLOUD TEM MAIS CREDIBILIDADE\n\n3-USE UMA VPN DO SEU ESTADO OU UMA PROXY NO NAVEGADOR ,Limpe o computador com o Ccleaner , TROQUE O NOME DO SEU COMPUTADOR PARA O NOME DOS SEUS DADOS, E TROQUE SEU ENDEREÇO MAC (CASO DUVIDAS DE COMO FAZER ME CHAME)\nCASO FAÇA PELO CELULAR BAIXE O APP DU SPEED BOSTER E LIMPEO E PROSSIGA COM OS PASSOS A SEGUIR !\n\n\n4- Só escolha cartão com bin alta se o score for maior que 900 \n\n\nCom o pc limpo , acesse esse link: https://www.itau.com.br/cartoes/escolha/proposta/?tracker=0&step=0&card=2\n-------------------DICAS IMPORTANTES---------------------------------\n\n\nDICAS BOM NÃO USE GMAILS,HOTMAILS USE EMAILS DA YHOO TERRA OU CLOUD\nTENHA TODOS DADOS DA VITIMA COMO ENDEREÇOS CEP RUA ETC\nSE A VITIMA POSSUI UM CARRO O SITE DO ITAU CARD IRA PERGUNTAR QUAL O MODELO E MARCA (AI VC TBM TEM QUE PUXAR O CNH DA VITIMA)\nSEMPRE COLOQUE NUMEROS FIXOS E SEU PROPRIO NUMERO (CASO ELES LIGUEM FIQUE COM OS DADOS DA SUA VITIMA NAS MÃOS)\nSE JA TIVER UMA LARA CRIADA NO BANCO ITAU COM ESSES MESMO DADOS SERA MT MAIS FACIL\n\nESSES PASSOS NÃO MUDEI PQ ESSTA CORRETO AINDA \n\n5- preencha os dados, atenção quando voce estiver preenchendo os dados vai pedir endereço coloque o seu sem medo\n\n6- sempre colocar o orgao emissor SSP , Sempre colocar que e Casado ( mesmo se for solteiro ) , \n\n7- segunda parte do preenchimento do formulario e a parte que voce vai botar seu DROP e seu TELEFONE , fixo voce inventa e o Celular coloque um seu caso voce precise ligar para algo eles desconfia se ligar de outro numero , \n\n8- NA PARTE DA COLOCAR CARGO DE TRABALHO USE O QUE VC PUXOU CASO CONTRATIO COLOQUE (OUTROS)\n\n9 - sempre autorize todos os seguros TODOS\n\n10- ao finalizar NO outro dia mesmo vc vai saber se aprovou ou não o cartão chega em 15 dias \n\nSOBRE DESBLOQUIAR VC BAIXA O APP COLOCAR O NUMERO DO CARTÃO E SENHA E DESBLOQUEIA\nBOM A SENHA VEM POR EMIAL OU SMS SE NÃO VIR LIGUE LA SEJA CARA DE PAU MESMO KKKK\nBOM E ISSO.\n\n\nEDITADO @BY_COMICK_2018 ");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.15.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button32.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("📚 Esquema Saque de FGTS\n\n✏️ Créditos: @centraldo77 @jessereserva\n\n📚 Passo 1 - Você que tem aquele FGTS que não consegue sacar nunca vem comigo, separa um kit bico ou uma editável com os dados do FGTS que você vai sacar.\n\n📚 Passo 2 - Pesquisa na PlayStore Empresta, ou entra no site empresta.com.br vai da sua preferência, eu acho mais fácil abrir a conta pelo APP por que só vai pedir pra tirar uma selfie, se abrir pelo site vão enviar email pedindo DOCS, análise vai demorar mais e etc.\n\n📚 Passo 3 - Abre a conta com os dados do FGTS na empresta, e vai lá em solicitar antecipação do FGTS, vai pedir pra botar uma conta de banco, no @centraldo77 tem método pra você abrir neon/next, abre a Neon/Next e bota lá pra receber, não vai estar escrito Banco Neon e sim Banco Votorantim, bota lá e espera pra ver se vai cantar, se cantar cai no mesmo dia, bons trampos gurizada");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.16.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button31.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.17
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("📚 Trampo Habibs Reembolso em Cupons\n\n✏️ Créditos: @jessereserva @centraldo77\n\n📚 Passo 1 - No Ifood, você vai pedir qualquer coisa no Habibs, valor tanto faz, mas você vai precisar pagar obviamente, se conseguir pagar na CC mais lucro ainda pra você\n\n📚 Passo 2 - Depois de ter pedido, vai fazer aquele esquema do reembolso, chama lá no Ifood e etc, eles vão te enviar um WPP pra você chamar gurizada, chama esse WPP com o mesmo número que seu Ifood esta cadastrado\n\n📚 Passo 3 - Quando abrir o chat ja vai reconhecer sua conta, você vai seguindo as opções botando pra reembolso, lá eles vão perguntar qual item não veio no seu pedido, bota todos ou se quiser bota só metade, tanto faz\n\n📚 Passo 4 - Em seguida vão te enviar um cupom pra gastar no próprio site do habibs, pegou o cupom ativa ele e já era, não é cupom de porcentagem, é cupom de saldo equivalente ao que você reembolsou gurizada");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.17.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button28.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.18
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("1 - Usar Matriz on. \n\nMATRIZ DO MOMENTO SUBINDO 1K;\n4984069448XXXX 03/24 ✓\n\nNOVAS MATRIZES:\n\n6509079003547475|11|2024|000\n4984076128807429|09|2024|658\n\n2- preencha tudo normalmente, inspecione e delete usando 789\n(E a parte de inspecionar é apertando shift e i),  e quando preencher o nu\u200cmero da casa voce\u200c coloca sempre nessa base aqui\nEx: 56bs 56ab 56xd 56ls 56ps\nPode sentar o dedo.\n\n3- pronto aqui que ta o macete, quando voce\u200c efetuar a primeira compra de algo entre 50-100$\nPrint a parte de baixo com todos os dados da primeira compra (ela fica logo abaixo do pedido realizado)\n\n4- toda vez que for comprando fazer repetic\u200ca\u200co de todos os dados igualmente o primeiro pedido, na\u200co pode errar uma vi\u200crgula tem que ta\u200c tudo perfeito.\n\n5- pronto fac\u200ca os pedidos e copie o link de url na parte acima do edit web a cada pedido, fecha a aba do app e espere 5 mim\n\n6- pronto o esquema esta\u200c pronto para ser usado, so\u200c fazer como foi dito acima tudo certinho e fac\u200ca ate\u200c secar o saldo da GG (provavelmente ate\u200c 5 pedidos), na mesma GG.");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.18.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button26.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.19
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("1. Tenha uma vpn caso.\n\n2. Agora ligue a vpn nos EUA (New york ).\n\n3. Vá a hbo max cadastra se, é de sua preferência \n\n4. Pegue está bin\n\nBin:515462005503xxxx\n\nDATA:06/26\n\nCVV:RND\n\nIP: USA 🇺🇸\n\ntire 1 card desta Bin \n\n\n5. Coloque lá no pagamento este card \n\n6. 🔴DADOS🔴\n\nZIP CODE: 10080 \n\nESTADO: NEW YORK");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.19.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button25.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.20
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("Obtenha assinatura gratuita de 1 mês em seu e-mail ✉️\n\nPasso 1: Crie um novo Gmail\n\nEtapa 2: Vá para <Este site (https://shell.prinsh.com/Prinsley/)>\n e registre seu e-mail 📧\n\nEtapa 3: aguarde 2 horas após 2 horas, sua conta será ativada com sucesso 💯");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.20.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button24.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("🐝 Precisa De Uma Conta Amazon Seller Que Tenha Antecipação De Pagamento 🐝\n\n🐝 Pega A Conta E Anúncia Umas Coisas, E Só Espera Começarem A Comprar 🐝\n\n🐝 Quando Começar A Cair Os Pedidos, Vc Vai Ter Até 20 Dias Pra Continuar Fazendo O Trampo, Após Esses 20 Dias A Conta Bloqueia E Só Lamento. 🐝\n\n🐝 Vai Antecipando Tudo, Recebendo Tudo Da Pra Fazer Até 50k Fácil. 🐝\n\n🐝 Esquema Bem Simples,Porém Mt Gente Não Sabe...🐝");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.21.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button23.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("\u1fa781️⃣ - Você vai precisar logar no link oficial da shein Aqui embaixo\n\n\u1fa78LINK OFICIAL ➡️ https://shein.top\n\n\u1fa782️⃣ - após logar você vai ter que aperta em \" GANHE R$ 200 NO APLICATIVO SHEIN\" após isso vai abri o app da shein. \n\n\u1fa783️⃣ - Clique em jogar e na mesma hora você clica de novo pra fazer parar, fazendo isso vai aparace \"R$ 200 CRÉDITO EM CARTEIRA\" fez isso? Clique na carteira e vai está lá R$200 ai você gasta como quiser!!\n\n\u1fa78LINK OFICIAL ➡️ https://shein.top");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.22.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button22.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("ESQUEMA MAGAZINE LUIZA\n\n1° PASSO TER UM LOGIN ANTIGO DO SEU ESTADO PREFERENCIALMENTE PERTO DA SUA CIDADE NÃO TEM PROBLEMA TER PEDIDO CANCELADO\n\n2° PASSO TER LOGADO PELO APP MAGALU IR EM SUA CONTA \"DADOS PESSOAIS\" FORMA DE PAGAMENTO AI QUE TA A MAGICA A MAGALU MOSTRA A BIN DO CARTÃO NO CASO VOCÊ TERA Q USAR A MSM BIN!\n\n3º PASSO VOLTE A SUA CONTA VAI EM CONFIGURAR NOTIFICAÇÕES (DESATIVE TODAS AS NOTIFICAÇÕES CASO ESTEJA ATIVO) É SALVA!\n\n4° PASSO VAI EM DADOS PESSOAIS OBS: (ALTERE APENAS OS 2 ULTIMOS NUMEROS DO TELEFONE DO BICO COLOQUE A SENHA E SALVA\n\n5° PASSO BASTA ADICIONAR O PRODUTO QUE VOCÊ DESEJA NOS CARRINHOS, COLOCAR PARA RETIRADA (VOCE ESCOLHE O MELHOR LOCAL PARA RETIRADA), ADICIONE A CC, INFO OU CONSUL COM O NOME DO BICO DO LOGIN SEMPRE! SUBIU PEDIDO AGUARDE A CONFIRMAÇÃO PARA RETIRADA POR NOTIFICAÇÕES\n\n👉 CANAL OFC: @HARPYOFC 👈");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.23.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button21.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.24
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("\u1f7e5 MÉTODO DA OI - PLANO OI \u1f7e5\n\n\n🚨Bom, o método que irei ensinar, não vou gravar um vídeo pois vai ficar bem longo.. Mas é bem simples. Recomendo que tenha no máximo 3 dados Full com score acima de 800, com nome limpo no Serasa..🚨\n\n\n🚨O método é como se vc estivesse ativando o plano controle da vivo, só que ao invés de você ativar por chat, você vai ligar pra lá.🚨\n\n\n🚨Vc liga para esse número 08000716504 para falar diretamente com eles e diz que quer assinar o plano pós pago da oi de 100gb.🚨\n\n\n🚨Eles vão pedir tudo que o chat da vivo pede como: Nome completo, data de nascimento, nome da mãe, email, endereço🚨 \n\n🚨O endereço você coloca o seu, da sua casa, para chegar o chip.. Porém o chip irá chegar com o nome que passou no chip, mas qualquer um pode assinar.. 🚨\n\n\n🚨Eles vão dizer que a oi em até 48 horas irá ligar para seu numero para confirmar e você atende, o CPF que passou lá vc confirma, diz tudo direitinho para que nada saia errado.🚨\n\n\n🚨Em até 5 dias úteis, seu chip chegará em sua casa e no ponto de usar.. com 100gb de internet🚨\n\n\n\n\n\n\u1f7e3Trampo Vivo CHIP + Plano\u1f7e3 \n\nPasso 1 - Vai pegar dados full com score bom em qualquer grupo de puxada, se nao sabe algum procure por puxadas na pesquisa do telegram gurizada \n\nPasso 2 - Abre o site da vivo, vai em aderir plano, procura o plano pra celular normal, e inicia o cadastro, bota todos dados que voce pegou batendo, menos o endereço pra receber o chip, endereço no caso o seu gurizada \n\nPasso 3 - Preencheu tudo certinho? Vai pra analise, espera até uns 2 dias o email da vivo confirmando a adesão do plano \n\nPasso 4 - Espera dropar o chip e só usar galera, se não pagar o plano dura no maximo 3 meses só pra avisar\n\n\n\n\n\n\n\n\n🔴Trampo pra aprovar chip claro + plano de internet🔴 \n\n\u1fa78Passo 1 - Dados full com score bom da sua regiao\nNao lerda gurizada nao usa dados da mesma cidade \n\n\u1fa78Passo 2 - Abre site da claro com os dados full que pegou vai lá em planos, bota os dados batendo so nao bota o drop da pessoa que voce ta usando o nome dai usa o seu ne gurizada pra receber o chip \n\n\u1fa78Passo 3 - Vai finalizar e ja vai te dizer se ta aprovado, se tiver aprovado eh gg, so esperar dropar o chip e usufruir \n\nRpzd dura no maximo 3 meses cada chip se nao pagar a conta\n\n\n\n\n🔵APROVAR CHIP TIM🔵 \n\nPara esquema Está dando certo, Vocês Vão precisar de Dados com score acima de 800, Recomendo usar Dados com 900 de score ou mais. \n\nFeito isso, Vocês vão no site https://www.meuplanotim.com.br \n\nEscolham o plano de R$54,99 para o esquema está dando certo. \n\nBeleza , Feito isso vocês vão Criar email no nome do bico. \n\nPreencha os dados no site: \n\nEmail: \"COLOQUE EMAIL QUE VOCÊ CRIOU NO NOME DO BICO\" \n\nCPF: \"COLOQUE CPF DO BICO\" \n\nNumero: \"COLOQUE SEU NUMERO REAL DO WHATSAPP\" \n\nData nacismento: \"Data de nacismento do bico\"\n\n\nFeito isso, No site tera 3 OPÇÕES \n\nVocês escolheram a ultima opção, que é:  \"Quero Uma nova linha com um número Tim\" \n\nBeleza, Escolham um DD de acordo com seu Estado ok? (Rocomendo por o estado do bico)\n\n\nApós isso, basta preencher o seu endereço tudo certinho para entrega do chip.\n\n\nLembre-se Use sempre 4G , e tenha Ip limpos. \n\nObs: Escolha forma de pagamento por (fatura) caso não apareça escolha (boleto) \n\nCreditos: @riquinho_o_golpista\n\nChannel:\n\n@HarpyOfc x @HarpyOfc");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.24.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button20.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.25
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("💎Esquema Gift Card💎\n\n> 1°・Primeiro, você precisará do valor do giftcard que você quer comprar. Mas calma! Não se desespera ou me xingue, você não vai perder NADA.\n\n> 2°・ Vá no site do https://submarino.com.br, crie uma conta e compra o giftcard que você quer VIA PIX,\n\n> 3°・Quando eles te enviarem o código do giftcard, você utiliza ele, sai da aba de onde você usou, entra nela de novo e coloca o giftcard novamente mas com o ultimo digito errado!\n> 4°・Quando aparecer a mensagem que você colocou um gift incorreto, printa ela,\n\n> 5°・Vai lá no SubMarino e solicita devolução por produto errado. Se pedirem provas ou algo relacionado, é só enviar o print que você tirou. Se você fizer tudo certo vai estornar no pix ou vão te oferecer outro giftcard.\n\n➡️Faça em contas diferentes pode ser novas ou antigas\n\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.25.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button19.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.26
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("➡️𝙈𝙀𝙏𝙊𝘿𝙊 𝙄𝙁𝙊𝙊𝘿\n\n1️⃣° - Primeiro você deve criar uma conta virgem no IFOOD. \nVocê Vai Escolher o restaurante Grande, MCDONALD'S, HABIBS, OUTBACK, PIZZAHUT, E ETC... Coloca o produto na sacola e faz o pedido no pix. O lanche chegou você vai comer e esperar 30 minutos, PRECISA SER 30MIN NO MÍNINO, NÃO PODE SER 29MIN, NEM 28M.... Você vai entrar no Ifood e clica no seu perfil, daí você desce até embaixo, vai em ajuda, seleciona problema com o pedido >> \"meu pedido veio errado\" >> \"todos os produtos vieram errado\"!! \n\n2️⃣° -  Agora você vai mandar o seguinte texto pro Ifood(seja o mais educado e formal possível) #TEXTO ABAIXO DO ULTIMO PASSO# \nNa hora que for fazer a reclamação edite o texto abaixo e escreva o nome do restaurante que você pediu, os detalhes do pedido!!! \n\n3️⃣° -  E reclame do que você comeu ( exemplo macarrão tu reclama do macarrão e tals) e por assim vai, não se esquema de dizer, que o produto estava com um gosto muito estranho, e diga que tem problemas intestinais, diga que toma remédio para a flora intestinal. E que irá tomar as medidas cabíveis caso o Ifood não resolva o seu problema! \n\n➖ texto: Olá atendimento Ifood, boa noite, demorei para fazer a reclamação porque não estava encontrando o botão de pedir ajuda. Enfim, fiz o pedido tudo certo, chegou o pedido, e o hamburguer estava com um gosto estranho demais, com gosto de azedo, batata bem murcha, bebida sem sabor aguada e quente, e meu sorvete veio todo derretido, tudo pessimo! O pedido veio lacrado, mais veio um lixo. Estava com diarreia e estou com problemas no intestino até agora, acabei de tomar o meu remedio para a flora intestinal, paguei caro em remedios e eles me vem com essa porcaria. Realmente eu não entendo como uma empresa grande como o Ifood permite que uma porcaria dessas esteja vendendo pordutos ruins ou estragados. Enfim quero reembolso imediato do meu pedido TODO! Caso contrario entrarei com as medidas cabiveis. Eu ia ate tirar foto mais minha camera traseira esta com problema entao deixei para lá. Aguardo solucao imediata obrigado. E por sinal o motoboy super atencioso e legal, incrivel ter motoboys tao incriveis como ele na plataforma!!! aguardo meu dinheiro. \n\n4️⃣° - Após enviar a mensagem aguarde que o valor do seu pedido será estornado no pix.  \n\n🎃By: @perryzin\n\n💻canal: @HarpyOfc");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.26.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button18.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.27
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("💙 MÉTODO UBER CASH\n\n👑Surpresa no final👑\n\nAVISOS\n\nTenha uma conta com pelo menos três corridas pagas em dinheiro.\nDá para fazer o método mais de uma vez, porém, com um intervalo de uma semana.\n\nMETODO\n\nVá em conta >>> viagens e escolha uma viagem de R$5,00 até R$20,00 (paga em dinheiro).\n\nSolicitar ajuda com viagens >>> mais opções >>> problemas de pagamento em dinheiro.\n\nAgora você vai procurar a opção \"paguei a mais porque meu motorista não tinha troco suficiente\".\n\nVai pedir para você colocar o valor que você supostamente pagou. Daí você coloca R$50,00 e aperta em \"Avançar\".\n\nVão perguntar se esse é o valor que você pagou em dinheiro e você aperta na opção \"Isso mesmo.\"\n\nPronto, método feito.\n\nO truque é o seguinte, se você escolher uma viagem de R$10,00 e colocar que pagou com R$50,00 vai voltar pra você R$40,00 de Uber Cash. é só saber um pouco de matemática\n\nTalvez funcione com 99, mas é mais demorado para voltar o valor.\n\nOBS: esqueci de avisar que dá para gastar dentro do próprio aplicativo, lá tem supermercado e farmácia\n\n\nCREDITOS: @freshzada2k & @TrashzinIg \n\n© @HarpyOfc • @HarpyOfc");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.27.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button17.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.28
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("💜MÉTODO INTERNET ILIMITADA VIVO💜 \n\n❗Entre para mais metodos:\n\n💬 -By: @HarpyOfc • @HarpyOfc - ✅\n  \n♎Eu uso esse método, sem proxy e sem programa e grátis só precisa ter créditos validos: \n\n\n♎Desligue sua internet de dados. \n\n♎Entre em contato com a vivo através: \n\n♎Telefone 1058 e escolha a opção que fale com a atendente \n(Opção 9(outros) e aguarde ser atendido) \n\n♎Envie um SMS: 1058 com a palavra vivo  \n\nOu \n\n♎Entre no site da vivo e tenta um chat online \n(Esse chat é uma bosta XD).E esse procedimento é o que mais demora sei porque já trabalhei em chat kkk. \n\n  \n♎Após ser atendido fale para a atendente que você deseja que o seu chip seja habilitado no PLANO BASE INTERNET PRÉ  \n(não é pacote de internet) diga a atendente que você quer usar o chip no modem e insista, pois alguns atendentes desconhecem o plano. \n\n♎Lembre-se não é pacote de internet é um plano para usar a internet.  \nNo modem isso é importante!!!!! \n\n  \n♎ após isso você deve confirmar se realmente o plano foi Habilitado, para isso siga os passos abaixo: \nMande uma mensagem de texto para o número 1058 com a palavra vivo. \n\n\n♎Após chegar a mensagem você escolha a opção  \n5-plano vigente. \nDeverá está escrito a seguinte mensagem - o seu plano vigente e PLANO BASE INTERNET PRÉ. \n\n♎Caso não esteja significa que a atendente não habilitou o plano correto, e você deve entrar em contato novamente, e só partir para os passos seguintes após isso se confirmar. \n\n  \n♎Observação importante os passos seguintes você terá que ter pelo menos R$ 7,00 Reais em créditos. \nPorém aconselho que você insira R$ 20,00 assim a validade dos créditos será por 90 dias e o método funciona enquanto os créditos estão valendo.   \nDepois de 90 dias basta inserir mais R$ 20,00 que você ganhará mais 90 dias e assim sucessivamente \n\n  \n♎Ligue no *9003 agora o atendimento é automático. Escolha a OPÇÃO 1 VIVO TUDO e contrate-o. Serão descontados R$ 6,90 dos seus créditos. Após a confirmação da contratação do plano vivo Tudo passe para o item abaixo. \n\n♎Ligue sua internet de dados e navegue em três páginas espere carregar por completa, uma depois a outra. Assista a um vídeo de 1 a 2 minutos, finalize tudo e em seguida e desligue sua internet de dados. \n\n♎Agora ligue novamente no *9003 e escolha a opção 1 Vivo Tudo e Espere até chegar na opção de cancelar, e confirme o Cancelamento. \n\n  \n♎Aguarde até chegar uma mensagem de texto confirmando o Cancelamento. \n\n\n♎Desligue o seu celular e após 2 minutos ligue-o novamente. \n\n\n♎Ligue sua internet de dados. Se conectar é porque o método funcionou e sua internet está ilimitada e de graça. \n\n♎Outra informação importante você não poderá adquirir nenhuma promoção ou pacotes. \nVocê poderá receber e ligar normalmente, só não poderá adquirir promoções ou pacotes. \n\n\n💜MÉTODO ALTERNATIVO💜: \n\n♎Usando a internet diário R$0,99 centavos/15mb/dia \n\n♎Use ela do jeito que quiser até aparecer a mensagem que acabo o  \nLimite \n\n♎Depois que aparecer a mensagem, (No Android) vá em Configurações/Redes Moveis/Pontos de acessos e lá você verá os APN’s e aperta em `restaurar ajustes padrão´ . \n\n♎Veja a velocidade pra ver se deu certo  \nTem que ser feito os dias que você quiser usar ela rápida. \n  \n💬 -By: @HarpyOfc • @HarpyOfc - ✅");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.28.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button16.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.29
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("METODO DOS 100$ ASTROPAY\n\nVOCE VAI BAIXAR O APK DO ASTROPAY (TEM NA PLAYSTORE) VAI CE CADASTRAR COM SEU NÚMERO, LOGO EM SEGUIDA VAI PEDIR PRA VC COLOCAR O CÓDIGO Q VOCE CHEGOU NO SEU SMS\n\nAO COLOCAR O CODIGO DE VERIFICAÇÃO VAI PERGUNTAR CE VC TEM UM CUPOM BÔNUS\nVC COLOCA: PAIXAO100\n\nE APERTA PRA CONTINUAR COM O CUPOM ( CE VC CONTINUAR SEM O CUPOM VAI DA RUIM) \n\nEM SEGUIDA VC ENTRA NA SUA CONTA ASTROPAY E SOLICITA O CARTÃO MASTERCARD, ENVIA FOTO DE IDENTIDADE E TIRA SELFIE \u1f933 (NORMAL DE TODO CARTAO) \n\nEM SEGUIDA APOS ALGUMS MINUTOS O DINHEIRO VAI TA NA SUA CONTRA ASTROPAY\n\n2- VA EM ALGUMA BET QUE ACEITE ASTROPAY (RECOMENDO PAGBET) EM SEGUIDA TRANSFIRA SEU SALDO PARA LA , DPS E GG SO SACAR \n\nBY: @OEFIPE");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.29.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button15.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.30
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("Bom Paypal MÉTODO Primeiro, crie alguns endereços de e-mail (deve ser depois de 10 endereços do mesmo provedor de mudança de provedor, ex. Yahoo, web, gmail etc.) (Dependendo de quanto dinheiro você quiser) Eles devem ser construídos da seguinte forma: Example123 @ web.de : password12 Example1123@web.de : password12 Com o primeiro que você criar um perfil no Facebook onde você confirmar e-mail E HANDYNUMBER (ambos !!!) Então você entra em seus arquivos de registro do Facebook em Paidlikes Após o login no Paidlikes você excluir o handynummer do seu perfil do Facebook. E novamente o processo com o seu segundo e-mail e o mesmo número E você quer o processo de cima para baixo até que você tenha terminado todos os e-mails. No Paidlikes você ganha pro Acc 10 gosta de um dia. Para isso você ganha pontos e dinheiro. Após cerca de 4 dias você tem 250 pontos. Então você pode pagar. Pro Acc você ganha 5 €, se você tiver 10 acc feito você ganha 50 € na sua conta Paypal. Divirta-se com o método");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.30.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button1.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.31
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("METODO NOVO PLATAFORMA 26BET\n\n*METODO NÃO CAI FACIL*\n\nSEGUINTE , POR CADA CONVITE NA PLATAFORMA , VOCÊ GANHA 20$ , VOCE PRECISA DE 4 CONTAS PRA FAZER O METODO , A PRIMEIRA CONTA VOCE VAI CRIAR POR ESSE LINK CE QUISER : https://26bet.com/?id=33932718\n\nDPS DE TER CRIADA A CONTA EM GUIA NORMAL DO GOOGLE , VC VAI PEGAR O LINK DE INDICAÇÃO DELA , E COPIAR , VAI ABRIR UMA GUIA ANONIMA , CRIAR OUTRA CONTA , DEPOSITAR MAIS 30$ POR ESSE LINK DE CONVITE Q VC COPIOU (USANDO 3G , OU OUTRO WIFI) , E VC VAI FICAR COM 50$ NA PRIMEIRA CONTA , E 30$ NESSA \n\nDPS VOCE VAI PEGAR OUTRO CELULAR OU PC , FZ A MSM COISA POR 3G TBM , NA GUIA NORMAL , VC PEGA O LINK DE CONVITE DA SUA PRIMEIRA CONTA , DEPOSITA MAIS 30$ NESSA 3° CONTA , COM ISSO A PRIMEIRA CONTA JA VAI TER 80$ , E OUTRAS 2 CONTAS COM 30$ , TOTALIZANDO JA 140$ , AGORA A ULTIMA CONTA E A MSM COISA , VOCE CRIAR PELO LINK DA SUA PRIMEIRA CONTA , DEPOSITA OS 30$ TBM POR 3G/4G , E A SUA PRIMEIRA CONTA VAI TER 100$ , E OUTRAS 3 CONTAS COM 30$ , TOTALIZANDO 190$ TOTAL , COM DEPOSITO DE 120$ TOTAL , \n\n\nAPOS AS 4 CONTAS ESTAR NO JEITO , VOCE ENTRAR EM 2 CONTAS QUE TEM 30$ , VAI ENTRAR EM QUALQUER ROLETA AO VIVO , DE PRETO E VERMELHO ETC , UMA CONTA VAI POR 30$ NO VERMELHO , OUTRA CONTA 30$ NO PRETO , E SE QSER POE UM VALOR A MENOS NAS CORES E COBRE O 0 , AI VAI DE VC\n\nCOM ISSO , A CONTA QUE GANHAR NA ROLETA VAI FICAR UMA SÓ COM 60 , AI VOCE ENTRA NA OUTRA CONTA QUE TEM 30$ , E ENTRA EM OUTRO CLL NA DE 60$ , VAI POR A MSM COISA , 30 VERMELHO , E NA OUTRA CONTA 30 NO PRETO , SE UMA FICAR COM 90 JA SACA , OU SE A COM 30 GANHAR , JOGA ATE FICAR O SALDO EM 1 SÓ, DPS JUNTA DO MSM JEITO COM O SALDO DA OUTRA CONTA DE 100$ , E SACA TUDO 👍\u1f3fb");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.31.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button14.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.32
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("🐇 MÉTODO DO YOUTUBE PREMIUM 1 MÊS GRÁTIS\n\n1- Crie um email novo, e certifique-se de adicionar seu cartão virtual nele, crie o perfil de pagamento com dados de puxada\n\n2- Vá até o YouTube, e clica em assinar teste Grátis, selecione seu cartao virtual (NÃO PRECISA TER SALDO PODE TÁ ZERADO).\n\n3- Vá até a PlayStore vá no email e vá em Pagamentos/Assinaturas, vá em assinaturas selecione a do YouTube Premium e Cancele.\n\n\n\u1f6e1️O método é gratuito, sem cobranças. A assinatura expira em um mês, portanto, adquira acesso ao canal VIP agora mesmo e aprenda a renová-la sem gastar dinheiro. Além disso, você terá acesso a materiais pagos e métodos de streaming gratuitos em várias plataformas.\n\n⭐️ CANAL VIP ⭐️");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.32.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button2.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.33
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("\u1fa78ESQUEMA BET 365\u1fa78\n\n\u1fa781. Criar a conta ou usar uma antiga!\n\n\u1fa782. Espere cerca de 20 minutos navegando pela BET 365\n\n\u1fa783. Pegue uma cc da Bin 636*** ( COMPRE A CC JUNTO DA BIN COM O @acrilex7 tá tendo de montão!!)\n\n\u1fa784. Adicione saldo na Bet. (Exemplos: 4.500, 4550, Sempre assim sendo valores diferentes.\n\n\u1fa785. Com essa Bin o reembolso demora 20 dias para chegar. Sendo assim até lá hoje já sacou bastante!\n\n\u1fa786. Bom uso do esquema, Use com sabedoria.\n\n💳Caso queira adquirir CCs No preço💳\nhttps://t.me/TheMetodosFree/2276\n\n\u1fa78CANAL VIP THE METODOS FREE\u1fa78\nhttps://t.me/TheMetodosFree/1996\n\n\u1f911Método grátis pra ta fazendo +100R$\u1f911\nhttps://t.me/TheMetodosFree/2986\n\n\u1fa78CANAL: @TheMetodosFree\n\u1fa78CHAT: @TheMetodosFree2");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.33.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button3.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.34
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("🔄 ESQUEMA AMAZON via PIX (estorno) \n\n1° Passo  \nBaixa e faz o cadastro no AMAZON\n\n2° Passo \nProcurar produto que seja VENDIDO E ENTREGUE PELA AMAZON, somente se for assim.\n\n3° Passo \nEscolhe produto ate 1k, escolheu, ok. Agora faça o pagamento pelo pix, finalize  \npara entrega de 2 a 3 dias e aguarde chegar.\n\n4° Passo \nQuando o produto chegar no drop coloque uma assinatura diferente do nome da pessoa que comprou, pois quando for pedir o estorno terá no sistema deles a assinatura de quem recebeu a mercadoria.\n\n5° Passo  \nChegou, espere 2 dias para fazer o estorno, passou dois dias, só fazer procedimento  \nabaixo.\n\nAPÓS CHEGAR O PRODUTO e PASSAR O PRAZO DE 24/48 HORAS SÓ FAZER O PROCEDIMENTO ABAIXO!\n\nLogo após vai aparecer o pedido. Clica no produto e pede pra falar com atendente via ligação. Eles vão te ligar de imediato, ai você vai alegar isso abaixo:\n\n\"Fiz a compra, porém nao chegou nada na minha mão, precisava da mercadoria no dia citado na entrega, e não é viável receber novamente, preciso do reembolso do meu pix ou tomarei as medidas cabíveis com a plataforma\"\n\nEscolha receber via pix, não aceite vale-presente!");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.34.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button4.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.35
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("📚 Esquema Eudora 2023\n\n✏️ Créditos: @centraldo77 @jessereserva\n\n📚 Passo 1 - Dados femea full do seu estado, score 900+, renda tem que ser boa mas não pode ser muito alta se não não vai liberar nada pra você\n\n📚 Passo 2 - Vai no site da Eudora Representante, bota Quero ser uma Representante, e vai lá cadastra tudo certo, e espera ir pra análise\n\n📚 Passo 3 - Agora pode acontecer 2 coisas, ou pode aprovar e já liberar pra você fazer o pedido de primeira mesmo, ou vai liberar o limite mas não vai aparecer pra pagar com o limite, se não aparecer o limite liga nesse número 0800 727 4535 ela vai pedir umas informações a mais, pedir pra confirmar alguns dados e vai liberar pra você fazer as compras normal lá com o limite liberado pela eudora, bons trampos gurizada, trampo ta on desde 2021");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.35.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button5.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.36
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("\u1fa78ESQUEMA SHOPEE\u1fa78\n\n\u1fa78Já que tem nego vendendo esquema Shopee preço absurdo e pior que conseguem derrubar trampo vamos lá.\n\n\u1fa781° Primeiramente, CPF da mesma cidade (isso dificulta o antifraude).\n\n\u1fa782° Criar um gmail com nome do bico exemplo: joaopaulodasilva@gmail.com \n\n\u1fa783° Cadastrar no shopee (chip virgem não necessariamente no CPF do bico). \n\n\u1fa784° Cadastrou? Primeira coisa entra no perfil altera nome vendedor / titular e dados completo do bico (Obs: joao.paulo.silva ambos vendedor / titular). \n\n\u1fa785° Agora entra a parte do e-mail, coloque no perfil também.\nObs: Eu coloco porém nem verifico nada, vai por conta de vocês. \n\n\u1fa786° Coloque o drop que desejar no número da residência coloque \"00\" e o número, sempre isso, até mesmo na hora de cadastrar a cc. \n\n\u1fa787° IMPORTANTE: Produto inicial até 150 conto achou o produto? Beleza, coloque um coração nele e vai em carrinho. \n\n\u1fa788° No carrinho vai pra parte de pagamento vai abrir a tela pra preencher dados do titular, endereço, telefone etc.. (lembrando, sempre coloque número de residência \"00\" + número), fez isso vai aparecer cartão vinculado, vinculou na próxima etapa coloca o CPF titular e parcelas sempre x3 (eu prefiro). \n\n\u1fa789° Segredo que poucos sabem aí.. Fez pedido aprovou na hora, vai em perfil, cartões e retira o cartão de lá. Quando você fizer isso espere mais uns minutos antes da próxima compra alta (lembrando material top pra subir). Deu uns minutinhos vai no produto até 1.5k coloca coração e mete o pau de novo ! 👍👍👍 \n\nantes de colocar coração no produto vai em \"cartões\" vincula a cc top e deixa vinculado, fez pagamento exclui a cc e recomeça.\n\n💳Caso queira adquirir CCs No preço💳\nhttps://t.me/TheMetodosFree/2276\n\n\u1fa78CANAL VIP THE METODOS FREE\u1fa78\nhttps://t.me/TheMetodosFree/1996\n\n\u1f911Método grátis pra ta fazendo +100R$\u1f911\nhttps://t.me/TheMetodosFree/2986\n\n\u1fa78CANAL: @TheMetodosFree\n\u1fa78CHAT: @TheMetodosFree2");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.36.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button6.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.37
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("🐇ESQUEMA KABUM PELO APP 2023🐇\n\n🐇PRIMEIRO LIMPAR O IP E CELULAR TODO\n\n  🐇SEGUNDO BAIXA O APP DA KABUM E FAZ O CADASTRO PELO APP COM 4G LIGADO\n\n🐇DEPOIS OLHE VÁRIOS PRODUTOS, MAIS NÃO COMPRE NADA\n\n🐇ENTÃO FECHE O APP E ABRA NO OUTRO DIA\n\n🐇 ENTÃO ESCOLHA UMA COISA DE ATÉ 100 REAIS\n\n🐇 FEITO ISSO USE UMA CC PLATINUM OU CC Signature E FINALIZE A COMPRA\n\n🐇 DEMORA UM POUCO PARA APROVAR\n\n🐇 DEPOIS DE APROVADO PODE FAZER PEDIDO DE ATÉ 1.500 REAIS QUE VAI QUASE NA HORA, MAIS DEPOIS QUE FIZER O SEGUNDO PEDIDO ELE A PROVA MAIS A CONTA QUEIMA ENTÃO NÃO VAI DAR PARA FAZER MAIS PEDIDOS NESSA CONTA .\n\nOBS : USE OS DADOS DO DONO DA SUA CC, POR ISSO PEGUE CC FULL DADOS BATENDO.");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.37.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button7.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.38
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("ESQUEMA TROCAFONE \n\nPRIMEIRO DE TUDO VAI PEGAR UMA CONSULTAVEL S/T S/A S/P \n\nVAI SUBIR E-MAIL PARECIDO COM DO BICO DA CONSULTAVEL E SUBIR CHIP NO CPF DELE \n\nCRIA A CONTA COM OS DADOS BATENDO TUDO 100% USA O E-MAIL QUE VOCÊ CRIO E NUMERO QUE SUBIU NO CPF DO BICO \n\nVAI ESCOLHER UM APARELHO E COLOCAR PRA COMPRAR \n\nENDEREÇO DE COBRANÇA USA O DO BICO E ENDEREÇO DE ENTREGA PÕE O QUE VAI RECEBER O APARELHO \n\nCOLOCA OS DADOS DA CONSULTAVEL E FINALIZA A COMPRA \n\nELES VÃO TE CHAMAR NO WHATSAPP OU NO E-MAIL FIQUE ATENTO QUE É RAPIDO \n\nVÃO PEDIR UM PRINT DA FATURA DO CARTÃO PRA APROVAR A COMPRA, VOCÊS TIRA RAPIDÃO E ENCAMINHA PRA ELES \n\nFEITO ISSO JA ERA APROVA NA HORA");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.38.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button9.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.39
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("📚 Esquema Saque de FGTS\n\n✏️ Créditos: @centraldo77 @jessereserva\n\n📚 Passo 1 - Você que tem aquele FGTS que não consegue sacar nunca vem comigo, separa um kit bico ou uma editável com os dados do FGTS que você vai sacar.\n\n📚 Passo 2 - Pesquisa na PlayStore Empresta, ou entra no site empresta.com.br vai da sua preferência, eu acho mais fácil abrir a conta pelo APP por que só vai pedir pra tirar uma selfie, se abrir pelo site vão enviar email pedindo DOCS, análise vai demorar mais e etc.\n\n📚 Passo 3 - Abre a conta com os dados do FGTS na empresta, e vai lá em solicitar antecipação do FGTS, vai pedir pra botar uma conta de banco, no @centraldo77 tem método pra você abrir neon/next, abre a Neon/Next e bota lá pra receber, não vai estar escrito Banco Neon e sim Banco Votorantim, bota lá e espera pra ver se vai cantar, se cantar cai no mesmo dia, bons trampos gurizada");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.39.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button11.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.40
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("ESQUEMA NETFLIX 2023\u1f39e\u1f37f\n\n1 - PEGUE SUA CC'S VOCÊ PODE USAR O QUALQUER CC SE ESTIVER EM LIVE.\n\n\n2 - VOCÊ PRECISA DE UM NAVEGADOR SECUNDÁRIO, PORTANTO, SE SUA CONTA FOR BLOQUEADA DEVIDO A MUITAS TENTATIVAS, VOCÊ PODERÁ LIMPAR FACILMENTE OS DADOS DO NAVEGADOR SEM SE PREOCUPAR. \n\n3 - VOCÊ PRECISA DE UM E-MAIL ATIVO, CRIE UM SE AINDA NÃO TIVER UM. \n\n4 - USE VPN CONECTANDO-SE AO PAÍS VINDO DO SEU CC\n\n5 - REGISTRE O NETFLIX, PREENCHA O E-MAIL COM E-MAIL ALEATÓRIO USE PRIMEIRO E-MAIL OCIOSO ALEATÓRIO (E-MAIL NÃO ATIVO). PORTANTO, SE SUA CONTA ESTIVER BLOQUEADA, VOCÊ NÃO PRECISARÁ CRIAR UM NOVO E-MAIL. \n\n6 - ESCOLHA O SERVIÇO DESEJADO É PREENCHA AS INFORMAÇÕES DO DADOS DA CC DO BICO USE O NOME VERDADEIRO, NÃO USE O NOME COMO \"ALEATÓRIO\"  OU QUALQUER NOME ESTRANHO. \n\n7 - DIGITE O NÚMERO DO CARTÃO QUE VOCÊ POSSUI. SE FOR RECUSADO, USE OUTRO NÚMERO DE CARTÃO. SE A CONTA ESTIVER BLOQUEADA: LIMPE OS DADOS DO NAVEGADOR, ALTERE O ENDEREÇO MAC É REPITA A ETAPA. \n\n8 - DEU BOM NÃO SE ESQUEÇA DE ALTERAR O E-MAIL COM UM E-MAIL ATIVO (VOCÊ PRECISA DE UM E-MAIL ATIVO PORQUE A NETFLIX EM ALGUM MOMENTO REDEFINIRÁ A SENHA SE MUITAS PESSOAS FIZEREM O LOGIN, \nPARA QUE VOCÊ NÃO POSSA RECUPERAR SUA CONTA FACILMENTE VOCÊ PERDERÁ SUA CONTA SE VOCÊ AINDA USA O E-MAIL INATIVO)\n\nAPROVEITEM E FAÇAM BOM USO🔥\n\n\nhttps://www.netflix.com/br/");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.40.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.button10.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.41
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("☯️ ESQUEMA MERCADO LIVRE ☯️\n\n☯️ SUBINDO EM CONTA LEVEL 1\n\n✡️ CONTAS NOVAS LEVEL 1 VOCÊ SOBE ATÉ 2.200 TESTADO!\n\n✡️ SERVE PRA QUALQUER LEVEL: SE USAR O MATERIAL CONSULTADO FICA MELHOR PRA SUBIR VALOR ALTO\n\n☯️ [MÉTODO] ☯️\n\n✡️ Pesquise o produto que quer e siga as instruções: \n\n✡️ Clique em \"filtrar\" e maque as opções \"ENTREGA FULL\" e \"PARCELAMENTO SEM JUROS\"\n\n✡️Clique no produto que deseja e vá para o pagamento\n\n✡️ Não altere nada do login, endereço nome, nada!\n\n✡️Coloca a CC com NOME REAL DO TITULAR, caso tenha o CPF da info coloque também, caso não tenha coloque o CPF do login desde que ambos sejam do mesmo sexo!\n\n☯️ CCS UTILIZADAS 👇\n\n☯️ NUBANK FULL, GOLD FULL E PLATINUM FULL\n\n☯️ PARA VALORES ALTO USE CONSUL");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.41.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.linear121.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.42
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("MÉTODO/ESQUEMA");
                MetodosActivity.this.di.setMessage("♛ NOVO METODO ALIEXPRESS NA GG 2023♛\n\n● Nada complicado, basta um login antigo ou um novo, com antigo sobe até 2k, com novo sobe só até 300 nos primeiros pedidos.\n\n● Abra o Aliexpress, clique em \"Entrar\", depois nos três pontinhos, cadastre ou entre em um login.\n\n● Agora você procura um produto internacional, repito, internacional, não vai meter coisa do Brasil aí, se não vai pedir vbv na hora do pagamento ou vai falhar.\n\n● Escolheu? Agora clique em \"Compre agora\", vai ter que clicar duas vezes, agora adicione um endereço, se for login antigo, apenas edite o endereço, coloca o nome do destinatário, telefone, cpf, tudo bonitinho. (E batendo dados né, pelo amor, coisa óbvia), mas se for login antigo, só troca o nome e o endereço, (telefone e cpf) você deixa como tá.\n\n● Vai clicar em \"Gravar endereço\", voltar, depois clique em \"pagamento\", e adicione a GG, Deixe a GG adicionada la e navega no app por uns 10 a 15 minutos.\n\n● Após isso, va no seu carrinho de compras q voçe adicionou seu produtos clique em \"Pagar agora\" e pronto, pagamento concluído, só esperar enviar.\n\nTire live dessa matriz: 4854641253xxxxxx|07|28|\n\nObs: Se der erro de apelação, seu drop tá queimado (ou ip), por isso use 4g sempre, ou wifi limpo.\n\n-Se tiver com o endereço queimado, basta colocar assim, exemplo:\n\nRua Antônio 722 >>>\nrua antonioo 722\nou\nrua antonio 0722\nou\nrua ant0nio 722\n\nExiste diversas formas...");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.42.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.linear120.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.43
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("MÉTODO/ESQUEMA");
                MetodosActivity.this.di.setMessage("COMO CAPTURAR INFOCC\n\nAtravés do Programa Acunetix, é possível scanearmos qualquer tipo de site (e-commerce), e suas “vulnerabilidades”, partindo desse principio\n\nPrimeiramente busquei algumas strings para localizar “e-commerce” segue algumas delas..\n\nallinurl:”.php?pag=”\nallinurl:”.php?p=”\nallinurl:”.php?content=”\nallinurl:”.php?cont=”\nallinurl:”.php?c=”\n\nDepois de colocar essas strings (DORK) no google, filtrei para localizar “e-commerce” com a palavra, “cart” “carrinho” “buy” “compras” entre outros, após a string.\n\nApós localizar “bons alvos” que sem dúvida trariam algumaINFOcc por se tratarem de e-commerce, filtrei eles para buscar sites que não faziam pagamento através do pagseguro, paypal, entre outros, no caso empresas que assumem o risco de compra e fazem tudo por conta própria..\n\nDepois disso foi fácil, coloquei muitas dessas “urls” noacunetix, e fui em busca de Vulnerabilidades em E-commerces com grande potencial de conversão para infoCC.\n\nApós ter também uma boa lista desses com vulnerabilidade fui para o programa “Havij” que facilita muito para quem não tem conhecimento em SQL-inJection e não saberia aplicar os comandos para localizar o banco de dados, tabelas, colunas, dessas informações.\n\nPS: Se atentem as informações fornecidas pelo Acunetix, que se apontar vulnerabilidade para SQL-INJECTION, dará a vocês o tipo de injeção (POST, GET) etc..\n\nNão me considero um usuário avançado, porem para estimular alguns que estão lendo o tópico, posso deixar somente uma dica, se você tem interesse em seguir em frente, estude, procure e faça, pois somente a prática te trará resultados reais e com os erros que você melhora, como falei no começo do tópico, foram 3 longas semanas, madrugadas e madrugadas, para conseguir aprender alguma coisa.\n\n**No Acunetix, programa que citei anteriormente o apontamento do método segue do lado direito da tela do software\n\nAttack detailsURL encoded \n\nGET input codprod was set to 1′”\n\nEstá me informando que nesse caso “CodProd” está setado em “1”\n\ne que eu posso setar qualquer variável para “CodProd” em Sql-Injection pois está vulnerável..\n\nou seja posso enviar qualquer comando para o banco de dados e esperar a resposta.. no case de sucesso o comando utilizado foi \n\nUNION SELECT name, uname, 1, pass, 2, 3, email, 4, cc, 5, 6 FROM users\n\nSelect – Selecionar\n\nName, uname – Nome, sobrenome\n\n1, 2, 3, 4 – Colunas\n\nEmail, cc, cvv – E-mail do cara, cartão de crédito, cvv, etc. (poderia setar CPF, RG, entre outros)\n\nE para quem realmente acha que é rapidinho só ir e fazer.. não obterá sucesso, assim como tudo nessa vida, a persistência é o que leva a perfeição...\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.43.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.linear217.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.44
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("✅ ENCHE SALDO NO IFOOD\n\n📝 1-PASOO: vai criar 2 conta no Ifood gurizada, baixa uma vpn que preste, agora vai pegar cc gringa, pode ser qualquer uma, se for de qualidade e tiver saldo vai funcionar\n\n📝 2-PASOO: agora vai colocar no IP dos Estados Unidos, vai entrar no ifood e vai compra vale presente\n\n📝 3-PASOO: colocar para como presente, pagar os  gifts bonitinho, sem erro quando aprovar vai resgatar os gifts na sua conta segundaria, e depois apaga a conta que você comprou os gifts limpar o celular e pronto ");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.44.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.linear122.setOnClickListener(new View.OnClickListener() { // from class: com.br.painel.MetodosActivity.45
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MetodosActivity.this.di.setTitle("ESQUEMAS/MÉTODOS");
                MetodosActivity.this.di.setMessage("🔥 Shein indo na GG 🔥\n\n💰 MÉTODO BY @REIZINTRAMPS\n\n✨Primeiro passo criar a conta. siga abaixo \n\n✨1-cria um Gmail novo.\n\n✨2-Após criar Gmail vai no Google aba anônima e escreve Gpay apareceu Mec já  faz login com a conta Gmail que criou ✨\n\n✨3-Lá mesmo no gpay já add qualquer drop/(endereço )que nunca foi usado na Shein e que seja valido.✨\n\n✨4-Feito isso feche o navegador.\n\n✨5-Baixe o canary preferência versão premiun vou soltar no grupo também tudo na mão pra vocês só se não gostarem mm.✨\nBaixou Mec.\n\n✨6 - vincular a gg \nNESSA MATRIZ METE MARCHA.\n👑 BIN: 483313005878xxxx\n\n✨7-Abre o canary e prossiga a instalação como pede e tal feito a instalação tu vai em uma cruz que fica na tela inicial do canary e clica nela vai aparecer uma caixa de pesquisa tu coloca via vai aparecer o navegador e tu seleciona ele ok \nFeito isso tu já ativa o canary liga ele fica em baixo essa parte de ativar ativou ok .✨\n\n8-✨Agora tu vai no navegador via e digita gpay\nLoga com a conta .✨\n\n✨9-Apos isso vai em add método de pagamento \nDigita lá a gg toda quando chegar no cvv tu coloca 000 mais não clica em salvar .✨\n\n✨10-Volta no canary e bem em cima te um sinal de lixeira tu deixa o canary ativado e só clica no lixo apagar pronto volta no navegador via e clica em salvar .✨\n\n\n✨Vai no canary e vai aparecer essa tela aqui \n\n\n✨Onde tá com início/efe/   tu clica e segura ok vai aparecer isso aqui ✨\n\n✨Tu clica em Rewrite  aí desse lá na última opção que tem um lápis clica no lápis e vai em edit online \nFeito isso vai aparecer assim ✨\n\n\n✨Tu procura onde está 000 e clica em apagar os 000 só apaga eles apagou tu coloca -00 e clica naquela setinha em cima Feito isso tu só confirma o que pedir e pronto volta no gpay e clica em salvar a gg e já era✨\n\n\n\n✨Vinculou a gg  no gpay só criar conta na shein com o e-mail que usou  escolhe o que quer depende do valor que sua matriz sobe outra coisa só compre de lojas internacionais lojas  nacionais não dá certo ok escolheu o que quer vai em comprar e vai na opção gpay escolhe o e-mail que tu vinculou a gg e já era man se tiver saldo sobe liso✨\n\n");
                MetodosActivity.this.di.setPositiveButton("ok", new DialogInterface.OnClickListener() { // from class: com.br.painel.MetodosActivity.45.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                MetodosActivity.this.di.create().show();
            }
        });
        this.imageview5.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.br.painel.MetodosActivity.46
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                return true;
            }
        });
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.br.painel.MetodosActivity$47] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.br.painel.MetodosActivity$52] */
    /* JADX WARN: Type inference failed for: r1v12, types: [com.br.painel.MetodosActivity$53] */
    /* JADX WARN: Type inference failed for: r1v14, types: [com.br.painel.MetodosActivity$54] */
    /* JADX WARN: Type inference failed for: r1v16, types: [com.br.painel.MetodosActivity$55] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.br.painel.MetodosActivity$56] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.br.painel.MetodosActivity$48] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.br.painel.MetodosActivity$57] */
    /* JADX WARN: Type inference failed for: r1v22, types: [com.br.painel.MetodosActivity$58] */
    /* JADX WARN: Type inference failed for: r1v24, types: [com.br.painel.MetodosActivity$59] */
    /* JADX WARN: Type inference failed for: r1v26, types: [com.br.painel.MetodosActivity$60] */
    /* JADX WARN: Type inference failed for: r1v28, types: [com.br.painel.MetodosActivity$61] */
    /* JADX WARN: Type inference failed for: r1v30, types: [com.br.painel.MetodosActivity$62] */
    /* JADX WARN: Type inference failed for: r1v32, types: [com.br.painel.MetodosActivity$63] */
    /* JADX WARN: Type inference failed for: r1v34, types: [com.br.painel.MetodosActivity$64] */
    /* JADX WARN: Type inference failed for: r1v36, types: [com.br.painel.MetodosActivity$65] */
    /* JADX WARN: Type inference failed for: r1v38, types: [com.br.painel.MetodosActivity$66] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.br.painel.MetodosActivity$49] */
    /* JADX WARN: Type inference failed for: r1v40, types: [com.br.painel.MetodosActivity$67] */
    /* JADX WARN: Type inference failed for: r1v42, types: [com.br.painel.MetodosActivity$68] */
    /* JADX WARN: Type inference failed for: r1v44, types: [com.br.painel.MetodosActivity$69] */
    /* JADX WARN: Type inference failed for: r1v46, types: [com.br.painel.MetodosActivity$70] */
    /* JADX WARN: Type inference failed for: r1v48, types: [com.br.painel.MetodosActivity$71] */
    /* JADX WARN: Type inference failed for: r1v50, types: [com.br.painel.MetodosActivity$72] */
    /* JADX WARN: Type inference failed for: r1v52, types: [com.br.painel.MetodosActivity$73] */
    /* JADX WARN: Type inference failed for: r1v54, types: [com.br.painel.MetodosActivity$74] */
    /* JADX WARN: Type inference failed for: r1v56, types: [com.br.painel.MetodosActivity$75] */
    /* JADX WARN: Type inference failed for: r1v58, types: [com.br.painel.MetodosActivity$76] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.br.painel.MetodosActivity$50] */
    /* JADX WARN: Type inference failed for: r1v60, types: [com.br.painel.MetodosActivity$77] */
    /* JADX WARN: Type inference failed for: r1v62, types: [com.br.painel.MetodosActivity$78] */
    /* JADX WARN: Type inference failed for: r1v64, types: [com.br.painel.MetodosActivity$79] */
    /* JADX WARN: Type inference failed for: r1v66, types: [com.br.painel.MetodosActivity$80] */
    /* JADX WARN: Type inference failed for: r1v68, types: [com.br.painel.MetodosActivity$81] */
    /* JADX WARN: Type inference failed for: r1v70, types: [com.br.painel.MetodosActivity$82] */
    /* JADX WARN: Type inference failed for: r1v72, types: [com.br.painel.MetodosActivity$83] */
    /* JADX WARN: Type inference failed for: r1v74, types: [com.br.painel.MetodosActivity$84] */
    /* JADX WARN: Type inference failed for: r1v76, types: [com.br.painel.MetodosActivity$85] */
    /* JADX WARN: Type inference failed for: r1v78, types: [com.br.painel.MetodosActivity$86] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.br.painel.MetodosActivity$51] */
    /* JADX WARN: Type inference failed for: r1v80, types: [com.br.painel.MetodosActivity$87] */
    /* JADX WARN: Type inference failed for: r1v82, types: [com.br.painel.MetodosActivity$88] */
    /* JADX WARN: Type inference failed for: r1v84, types: [com.br.painel.MetodosActivity$89] */
    /* JADX WARN: Type inference failed for: r1v86, types: [com.br.painel.MetodosActivity$90] */
    /* JADX WARN: Type inference failed for: r1v88, types: [com.br.painel.MetodosActivity$91] */
    private void initializeLogic() {
        this.button48.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.47
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button23.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.48
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button1.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.49
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button2.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.50
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button46.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.51
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button47.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.52
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button3.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.53
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button43.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.54
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button37.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.55
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button44.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.56
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button4.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.57
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button5.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.58
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button6.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.59
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button35.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.60
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button7.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.61
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button9.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.62
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.63
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button11.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.64
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button10.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.65
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button19.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.66
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button20.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.67
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button21.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.68
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button22.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.69
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button14.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.70
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear121.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.71
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear122.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.72
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear120.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.73
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.linear217.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.74
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button15.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.75
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button16.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.76
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button18.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.77
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button17.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.78
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button24.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.79
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button25.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.80
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button26.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.81
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button27.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.82
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button28.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.83
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button29.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.84
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button30.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.85
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button31.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.86
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button32.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.87
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button39.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.88
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button40.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.89
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button41.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.90
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius(i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns(25, 4, -14606047, -14606047));
        this.button42.setBackground(new GradientDrawable() { // from class: com.br.painel.MetodosActivity.91
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
