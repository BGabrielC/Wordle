package cutas.gabriel.wordle.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

    public void hacerRecyclerTeclado(){
        recyclerView = findViewById(R.id.recyclerViewTeclado);
        listaLetras = new ArrayList<Letra>(teclado.values());
        final TecladoAdapter tecladoAdapter = new TecladoAdapter(listaLetras, new OnItemClickListener() {
            @Override
            public void onItemClick(String letra) {
                if (letra.equals("⌫")){
                    if (Objects.requireNonNull(teclado.get(letra)).getColor().equals("#ba4b4b")){
                        numCaracter -= 1;
                        sacarTxtCaracter(numCaracter).setText(" ");
                        InputUsuario = InputUsuario.substring(0, InputUsuario.length() - 1);
                    }
                }else if(letra.equals("➥")){
                    if (Objects.requireNonNull(teclado.get(letra)).getColor().equals("#038b59")){
                        if(existePalabra()){
                            if(comprobarPalabra()){
                                Intent victoria = new Intent(Juego.this,Victoria.class);
                                startActivity(victoria);
                            }else{
                                if(numCaracter!=29){
                                    numCaracter += 1;
                                }else {
                                    Intent derrota = new Intent(Juego.this,Derrota.class);
                                    derrota.putExtra("palabra",palabraRandom);
                                    startActivity(derrota);

                                }
                                Toast toast2 = Toast.makeText(getApplicationContext(),"Ha perdido "+palabraRandom, Toast.LENGTH_SHORT);
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
                        sacarTxtCaracter(numCaracter).setText(letra);
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
                hacerRecyclerTeclado();
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
                            sacarTxtCaracter(numCaracter-5+i).setBackground(getResources().getDrawable(R.drawable.acertado));
                        }else {
                            sacarTxtCaracter(numCaracter-5+i).setBackground(getResources().getDrawable(R.drawable.casi));
                        }
                    }
                }


            }
        }
        return false;
    }
    public boolean compruebaSiSeRepiteLetra(String cadena) {
        // Convertimos la cadena a minúsculas
        cadena = cadena.toLowerCase();

        // Creamos un mapa vacío para guardar el número de veces que se repite cada letra
        Map<Character, Integer> mapa = new HashMap<>();

        // Iteramos a través de cada carácter en la cadena
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);

            // Si el mapa ya contiene la letra, aumentamos el contador para esa letra
            if (mapa.containsKey(c)) {
                mapa.put(c, mapa.get(c) + 1);
            } else {
                // Si no, añadimos la letra al mapa y le asignamos un contador de 1
                mapa.put(c, 1);
            }
        }

        // Iteramos a través de cada entrada en el mapa
        for (Map.Entry<Character, Integer> entrada : mapa.entrySet()) {
            // Si el contador es mayor a 1, devolvemos true ya que se ha repetido la letra
            if (entrada.getValue() > 1) {
                return true;
            }
        }

        // Si llegamos hasta aquí, significa que ninguna letra se ha repetido, por lo que devolvemos false
        return false;
    };


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        realm = Realm.getDefaultInstance();
        realmPalabras = realm.where(Palabra.class).findAll();
//        if (realmPalabras.size() != 0){
//            realm.beginTransaction();
//            realm.deleteAll();
//            realm.commitTransaction();
//        }
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
        teclado.put("⌫",new Letra("⌫","#ba4b4b"));//"#ba4b4b"));
        teclado.put("➥",new Letra("➥","#038b59")); //"#038b59"));
        hacerRecyclerTeclado();

    }
    public TextView sacarTxtCaracter(int num){
        switch (num) {
            case 0:
                return findViewById(R.id.txtCaracter00);
            case 1:
                return findViewById(R.id.txtCaracter01);
            case 2:
                return findViewById(R.id.txtCaracter02);
            case 3:
                return findViewById(R.id.txtCaracter03);
            case 4:
                return findViewById(R.id.txtCaracter04);
            case 6:
                return findViewById(R.id.txtCaracter10);
            case 7:
                return findViewById(R.id.txtCaracter11);
            case 8:
                return findViewById(R.id.txtCaracter12);
            case 9:
                return findViewById(R.id.txtCaracter13);
            case 10:
                return findViewById(R.id.txtCaracter14);
            case 12:
                return findViewById(R.id.txtCaracter20);
            case 13:
                return findViewById(R.id.txtCaracter21);
            case 14:
                return findViewById(R.id.txtCaracter22);
            case 15:
                return findViewById(R.id.txtCaracter23);
            case 16:
                return findViewById(R.id.txtCaracter24);
            case 18:
                return findViewById(R.id.txtCaracter30);
            case 19:
                return findViewById(R.id.txtCaracter31);
            case 20:
                return findViewById(R.id.txtCaracter32);
            case 21:
                return findViewById(R.id.txtCaracter33);
            case 22:
                return findViewById(R.id.txtCaracter34);
            case 24:
                return findViewById(R.id.txtCaracter40);
            case 25:
                return findViewById(R.id.txtCaracter41);
            case 26:
                return findViewById(R.id.txtCaracter42);
            case 27:
                return findViewById(R.id.txtCaracter43);
            case 28:
                return findViewById(R.id.txtCaracter44);
            default:
                return null;
        }
    }

    }