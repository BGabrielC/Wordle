package cutas.gabriel.wordle.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cutas.gabriel.wordle.R;
import cutas.gabriel.wordle.adapter.OnItemClickListener;
import cutas.gabriel.wordle.adapter.TecladoAdapter;
import cutas.gabriel.wordle.app.Utils;
import cutas.gabriel.wordle.model.Letra;
import cutas.gabriel.wordle.model.Palabra;
import io.realm.Realm;
import io.realm.RealmResults;

public class Juego extends AppCompatActivity {

    RecyclerView recyclerView;
    Realm realm;
    RealmResults<Palabra> realmPalabras;
    LinkedHashMap<String, Letra> teclado;
    List<Letra> listaLetras;
    String palabraRandom="";
    int numCaracter =0;
    String InputUsuario ="";
    Long inicio;

    public void CreacionRecycler(){
        recyclerView = findViewById(R.id.recyclerViewTeclado);
        listaLetras = new ArrayList<Letra>(teclado.values());
        final TecladoAdapter tecladoAdapter = new TecladoAdapter(listaLetras, new OnItemClickListener() {
            @Override
            public void onItemClick(String letra) {
                if (letra.equals("⌫")){
                    if (Objects.requireNonNull(teclado.get(letra)).getColor().equals("#ba4b4b")){
                        numCaracter -= 1;
                        seleccionarLetra(numCaracter).setText(" ");
                        InputUsuario = InputUsuario.substring(0, InputUsuario.length() - 1);
                    }
                }else if(letra.equals("➥")){
                    if (Objects.requireNonNull(teclado.get(letra)).getColor().equals("#038b59")){
                        if(existePalabra()){
                            if(comprobarPalabra()){
                                long fin = System.currentTimeMillis();
                                double tiempo = (double) ((fin - inicio)/1000);
                                Intent victoria = new Intent(Juego.this,Victoria.class);
                                victoria.putExtra("tiempo", tiempo);
                                victoria.putExtra("numCaracteres",numCaracter);
                                startActivity(victoria);
                            }else{
                                if(numCaracter!=29){
                                    numCaracter += 1;
                                }else {
                                    Intent derrota = new Intent(Juego.this,Derrota.class);
                                    derrota.putExtra("palabra",palabraRandom);
                                    startActivity(derrota);

                                }
                                Toast toast2 = Toast.makeText(getApplicationContext(),palabraRandom, Toast.LENGTH_SHORT);
                                toast2.show();
                            }
                            InputUsuario ="";

                        }else{
                            Toast toast2 = Toast.makeText(getApplicationContext(),"Esta palabra no esta en nuestro diccionario", Toast.LENGTH_SHORT);
                            toast2.show();

                        }
                    }
                }else if(!letra.equals(" ") && Objects.requireNonNull(teclado.get("➥")).getColor().equals("#878787")){
                    if(numCaracter != 29){
                        seleccionarLetra(numCaracter).setText(letra);
                        InputUsuario += letra;
                        numCaracter += 1;
                    }
                }
                if (numCaracter==5 || numCaracter==11 || numCaracter==17 || numCaracter==23 || numCaracter==29){
                    Objects.requireNonNull(teclado.get("➥")).setColor("#038b59");
                }else{
                    Objects.requireNonNull(teclado.get("➥")).setColor("#878787");
                }
                if (numCaracter==0 || numCaracter==6 || numCaracter==12 || numCaracter==18 || numCaracter==24){
                    Objects.requireNonNull(teclado.get("⌫")).setColor("#878787");
                }else{
                    Objects.requireNonNull(teclado.get("⌫")).setColor("#ba4b4b");
                }
                CreacionRecycler();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(tecladoAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 7));

    }
    public boolean existePalabra(){
        return Utils.getWordsFirstPart().contains(InputUsuario);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public boolean comprobarPalabra(){
        if (InputUsuario.equals(palabraRandom)){
            return true;
        }else{

            for (int i = 0; i < 5; i++){
                for (int o = 0; o < 5; o++){
                    if(i==o){
                        if (InputUsuario.charAt(i) == palabraRandom.charAt(o)){
                            seleccionarLetra(numCaracter-5+i).setBackground(getResources().getDrawable(R.drawable.acertado));
                            Objects.requireNonNull(teclado.get(String.valueOf(InputUsuario.charAt(i)))).setColor("#044512");
                        }else {
                            if (palabraRandom.contains(String.valueOf(InputUsuario.charAt(i)))){
                                seleccionarLetra(numCaracter-5+i).setBackground(getResources().getDrawable(R.drawable.posicion_incorrecta));
                                if (!Objects.requireNonNull(teclado.get(String.valueOf(InputUsuario.charAt(i)))).getColor().equals("#044512")){
                                    Objects.requireNonNull(teclado.get(String.valueOf(InputUsuario.charAt(i)))).setColor("#b1a517");
                                }


                            }else {
                                seleccionarLetra(numCaracter-5+i).setBackground(getResources().getDrawable(R.drawable.erroneo));
                                if (!Objects.requireNonNull(teclado.get(String.valueOf(InputUsuario.charAt(i)))).getColor().equals("#044512") && !Objects.requireNonNull(teclado.get(String.valueOf(InputUsuario.charAt(i)))).getColor().equals("#b1a517")){
                                    Objects.requireNonNull(teclado.get(String.valueOf(InputUsuario.charAt(i)))).setColor("#B1B1B1");
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean compruebaSiSeRepiteLetra(String cadena) {
        cadena = cadena.toLowerCase();
        Map<Character, Integer> mapa = new HashMap<>();
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (mapa.containsKey(c)) {
                mapa.put(c, mapa.get(c) + 1);
            } else {
                mapa.put(c, 1);
            }
        }

        for (Map.Entry<Character, Integer> entrada : mapa.entrySet()) {
            if (entrada.getValue() > 1) {
                return true;
            }
        }
        return false;
    };


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance();
        realmPalabras = realm.where(Palabra.class).findAll();
        inicio = System.currentTimeMillis();
        Button btnReiniciar = (Button) findViewById(R.id.btnReiniciar);
        Intent Reiniciar = new Intent(Juego.this,Juego.class);

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Reiniciar);
            }
        });
        if (realmPalabras.size() == 0){
            for (int i = 0; i < Utils.getWordsFirstPart().size(); i++) {
                String temp = Utils.getWordsFirstPart().get(i);
                Palabra palabra = new Palabra(temp);
                realm.beginTransaction();
                realm.copyToRealm(palabra);
                realm.commitTransaction();
            }

            for (int i = 0; i < Utils.getWordsSecondPart().size(); i++) {
                String temp = Utils.getWordsSecondPart().get(i);
                Palabra palabra = new Palabra(temp);
                realm.beginTransaction();
                realm.copyToRealm(palabra);
                realm.commitTransaction();
            }

        }

        do {
            int numeroAleatorio = (int) (Math.random() * realmPalabras.size());
            palabraRandom = Objects.requireNonNull(realm.where(Palabra.class).equalTo("id", numeroAleatorio).findFirst()).getPalabra();
        }while (compruebaSiSeRepiteLetra(palabraRandom));


        teclado=new LinkedHashMap<String, Letra>();
        teclado.put("Q",new Letra("Q"));
        teclado.put("W",new Letra("W"));
        teclado.put("E",new Letra("E"));
        teclado.put("R",new Letra("R"));
        teclado.put("T",new Letra("T"));
        teclado.put("Y",new Letra("Y"));
        teclado.put("U",new Letra("U"));
        teclado.put("I",new Letra("I"));
        teclado.put("O",new Letra("O"));
        teclado.put("P",new Letra("P"));
        teclado.put("A",new Letra("A"));
        teclado.put("S",new Letra("S"));
        teclado.put("D",new Letra("D"));
        teclado.put("F",new Letra("F"));
        teclado.put("G",new Letra("G"));
        teclado.put("H",new Letra("H"));
        teclado.put("J",new Letra("J"));
        teclado.put("K",new Letra("K"));
        teclado.put("L",new Letra("L"));
        teclado.put("Ñ",new Letra("Ñ"));
        teclado.put("Z",new Letra("Z"));
        teclado.put("X",new Letra("X"));
        teclado.put("C",new Letra("C"));
        teclado.put("V",new Letra("V"));
        teclado.put("B",new Letra("B"));
        teclado.put("N",new Letra("N"));
        teclado.put("M",new Letra("M"));
        teclado.put(" ",new Letra(" "));
        teclado.put("⌫",new Letra("⌫","#878787"));
        teclado.put("➥",new Letra("➥","#878787"));
        CreacionRecycler();

    }

    public TextView seleccionarLetra(int num){
        switch (num) {
            case 0:
                return findViewById(R.id.Fila1Letra1);
            case 1:
                return findViewById(R.id.Fila1Letra2);
            case 2:
                return findViewById(R.id.Fila1Letra3);
            case 3:
                return findViewById(R.id.Fila1Letra4);
            case 4:
                return findViewById(R.id.Fila1Letra5);
            case 6:
                return findViewById(R.id.Fila2Letra1);
            case 7:
                return findViewById(R.id.Fila2Letra2);
            case 8:
                return findViewById(R.id.Fila2Letra3);
            case 9:
                return findViewById(R.id.Fila2Letra4);
            case 10:
                return findViewById(R.id.Fila2Letra5);
            case 12:
                return findViewById(R.id.Fila3Letra1);
            case 13:
                return findViewById(R.id.Fila3Letra2);
            case 14:
                return findViewById(R.id.Fila3Letra3);
            case 15:
                return findViewById(R.id.Fila3Letra4);
            case 16:
                return findViewById(R.id.Fila3Letra5);
            case 18:
                return findViewById(R.id.Fila4Letra1);
            case 19:
                return findViewById(R.id.Fila4Letra2);
            case 20:
                return findViewById(R.id.Fila4Letra3);
            case 21:
                return findViewById(R.id.Fila4Letra4);
            case 22:
                return findViewById(R.id.Fila4Letra5);
            case 24:
                return findViewById(R.id.Fila5Letra1);
            case 25:
                return findViewById(R.id.Fila5Letra2);
            case 26:
                return findViewById(R.id.Fila5Letra3);
            case 27:
                return findViewById(R.id.Fila5Letra4);
            case 28:
                return findViewById(R.id.Fila5Letra5);
            default:
                return null;
        }
    }

    }