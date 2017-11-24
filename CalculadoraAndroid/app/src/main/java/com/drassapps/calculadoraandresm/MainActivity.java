package com.drassapps.calculadoraandresm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.BoolRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Boolean finalizado = true;                              // Permite gestionar si se ha finalizado una operacion
    private Boolean primera_operacion = true;                       // Permite reconocer si es la primera operacion
    private Boolean menos_im = true;                                // Permite gestionar los numeros negativos
    private Boolean ans = false;                                    // Nos dice si ya hemos puesto el ans
    private Boolean sho_memoria = false;                            // Nos dice si ya hemos puesto el valor de la mmoria
    private Boolean mod_on = false;                                 // Nos dice si vamos a hacer la operacion módulo
    private Boolean activa_historial = true;                        // Permite gestionar la visualizacion del historial
    private ArrayList<String> operadores = new ArrayList<String>(); // Contiene los operadores
    private ArrayList<String> historial = new ArrayList<String>();  // Contiene las operaciones
    private String resultado = "0";                                 // String del resultado temp
    private int op_ac = 0;                                          // Numero de operacion para el hisitorial
    private int punto_add = 0;                                      // Permite gestionar si se han añaden varios puntos
    private int op_con_ans = 0;                                     // Permite gestionar el boton ans
    int config;                                                     // Sirve para determinar la orientacion del disp
    private Double memoria = 0.0;                                   // Contiene el contenido guardado en memoria

    // Mensajes del toast
    String Np1 = "No puedes añadir este operador.";
    String Np2 = "No puedes dividir entr 0.";
    String Np3 = "No puedes añadir el valor de ANS.";
    String Np4 = "No puedes calcular el valor en este momento.";
    String Np5 = "Fin de operacion.";
    String Np6 = "No puedes añadir el valor de la memoria.";

    private TextView text_calculando, text_historial;

    private Boolean buttonStateOpen = true;
    private DrawerLayout drawerLayout;

    private SharedPreferences prefs;
    private SharedPreferences pref2;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String text = "texto";
    public String aux2 = "";
    public String text_his = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(MyPREFERENCES, this.MODE_PRIVATE);
        config = getResources().getConfiguration().orientation;

        final String hay_texto = prefs.getString("texto", "");

        final Button bt_num0 = (Button) findViewById(R.id.bt_num0);
        final Button bt_num1 = (Button) findViewById(R.id.bt_num1);
        final Button bt_num2 = (Button) findViewById(R.id.bt_num2);
        final Button bt_num3 = (Button) findViewById(R.id.bt_num3);
        final Button bt_num4 = (Button) findViewById(R.id.bt_num4);
        final Button bt_num5 = (Button) findViewById(R.id.bt_num5);
        final Button bt_num6 = (Button) findViewById(R.id.bt_num6);
        final Button bt_num7 = (Button) findViewById(R.id.bt_num7);
        final Button bt_num8 = (Button) findViewById(R.id.bt_num8);
        final Button bt_num9 = (Button) findViewById(R.id.bt_num9);
        final Button bt_ans = (Button) findViewById(R.id.bt_ans);
        final Button bt_more = (Button) findViewById(R.id.bt_draw);
        final Button bt_coma = (Button) findViewById(R.id.bt_coma);
        final Button bt_sumar = (Button) findViewById(R.id.bt_sumar);
        final Button bt_restar = (Button) findViewById(R.id.bt_resta);
        final Button bt_multi = (Button) findViewById(R.id.bt_multiplicar);
        final Button bt_divi = (Button) findViewById(R.id.bt_dividir);
        final Button bt_igual = (Button) findViewById(R.id.bt_igual);
        final Button bt_borrar = (Button) findViewById(R.id.bt_del);
        final Button bt_limpiar = (Button) findViewById(R.id.bt_limpiar);
        Button bt_historial = (Button) findViewById(R.id.bt_historial);

        // Para landscape
        final Button bt_apagar = (Button) findViewById(R.id.bt_apagar);
        final Button bt_raiz = (Button) findViewById(R.id.bt_raiz);
        final Button bt_modulo = (Button) findViewById(R.id.bt_modulo);
        final Button bt_potencia = (Button) findViewById(R.id.bt_pot);
        final Button bt_signo = (Button) findViewById(R.id.bt_cambiarsigno);
        final Button bt_add_memoria = (Button) findViewById(R.id.bt_add_memoria);
        final Button bt_show_memoria = (Button) findViewById(R.id.bt_muestra_memoria);
        final Button bt_limpiar_memoria = (Button) findViewById(R.id.bt_limpia_memoria);
        final Button bt_elimina_memoria = (Button) findViewById(R.id.bt_elimina_memoria);
        final Button bt_log = (Button) findViewById(R.id.bt_log);
        final Button bt_factorial= (Button) findViewById(R.id.bt_factorial);
        final Button bt_ln = (Button) findViewById(R.id.bt_ln);
        final Button bt_cos = (Button) findViewById(R.id.bt_cos);
        final Button bt_tan = (Button) findViewById(R.id.bt_tang);
        final Button bt_sin = (Button) findViewById(R.id.bt_sin);


        Button menu = (Button) findViewById(R.id.bt_menu);
        Button menu_nav = (Button) findViewById(R.id.bt_menu_nav);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        text_calculando = (TextView) findViewById(R.id.text_calcuando);
        text_historial = (TextView) findViewById(R.id.text_historial);

        text_historial.setMovementMethod(new ScrollingMovementMethod());

        if(!hay_texto.equals("") || !hay_texto.equals("")){
            text_calculando.setText(hay_texto);
            finalizado = false;
            if(text_calculando.getText().toString().contains("+")){
                operadores.add("+");
                primera_operacion = false;
                menos_im = false;
            }else if (text_calculando.getText().toString().contains("-")){
                operadores.add("-");
                primera_operacion = false;
                menos_im = false;
            }else if(text_calculando.getText().toString().contains("+")){
                operadores.add("+");
                primera_operacion = false;
                menos_im = false;
            }else if (text_calculando.getText().toString().contains("×")){
                operadores.add("×");
                primera_operacion = false;
                menos_im = false;
            }
        }else{
            text_calculando.setText("0.0");
        }

        bt_more.setText("?");

        // Establecemos el nav
        NavigationView bundle;
        bundle = (NavigationView) findViewById(R.id.navview);

        // Creamos el nav
        if (bundle != null) { setupDrawerContent(bundle); }

        // El boton dentro del nav, cierra el nav
        menu_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            }
        });

        // El boton del lay abre el nav
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonStateOpen) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });


        // Añade un 0 al calculo
        bt_num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "0");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 0 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }
                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                            text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                    text_calculando.getTextSize() - 8);
                            text_calculando.setText(text_calculando.getText() + "0");

                    }else {

                        text_calculando.setText(text_calculando.getText() + "0");

                    }
                }
            }
        });

        // Añade un 1 al calculo
        bt_num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "1");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 1 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }
                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "1");

                    }else { text_calculando.setText(text_calculando.getText() + "1"); }
                }
            }
        });

        // Añade un 2 al calculo
        bt_num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "2");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 2 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }
                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "2");

                    }else { text_calculando.setText(text_calculando.getText() + "2"); }
                }
            }
        });

        // Añade un 3 al calculo
        bt_num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "3");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 3 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }

                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "3");

                    }else { text_calculando.setText(text_calculando.getText() + "3"); }
                }
            }
        });

        // Añade un 4 al calculo
        bt_num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "4");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 4 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }

                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "4");

                    }else { text_calculando.setText(text_calculando.getText() + "4"); }
                }
            }
        });

        // Añade un 5 al calculo
        bt_num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "5");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 5 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }
                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "5");

                    }else { text_calculando.setText(text_calculando.getText() + "5"); }
                }
            }
        });

        // Añade un 6 al calculo
        bt_num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "6");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 6 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }

                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "6");

                    }else { text_calculando.setText(text_calculando.getText() + "6"); }
                }
            }
        });

        // Añade un 7 al calculo
        bt_num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "7");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 7 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }

                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "7");

                    }else { text_calculando.setText(text_calculando.getText() + "7"); }
                }
            }
        });

        // Añade un 8 al calculo
        bt_num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "8");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();
                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 8 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }
                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "8");

                    }else { text_calculando.setText(text_calculando.getText() + "8"); }
                }
            }
        });

        // Añade un 9 al calculo
        bt_num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)  // Si termina de operar, quitamos el resultado y añadimos el num
                {
                    text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_SP,50);
                    text_calculando.setText("" + "9");
                    finalizado = false; // Cambiamos el bool pues ahroa esta operando
                }
                else{
                    // Añadimos el numero a la operacion
                    String aux = text_calculando.getText().toString();

                    if (mod_on){
                        String[] valor = aux.split("%");
                        Double res = Double.parseDouble(valor[0]);
                        Double result = res * 9 / 100;
                        text_calculando.setText(result.toString());
                        mod_on = false;
                        primera_operacion = true;
                    }

                    // Si el tamaño de la cadena es mayor que 5 vamos reduciendo el size del texto
                    else if (aux.length() > 5 && aux.length() < 14)
                    {
                        text_calculando.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                                text_calculando.getTextSize() - 8);
                        text_calculando.setText(text_calculando.getText() + "9");

                    }else { text_calculando.setText(text_calculando.getText() + "9"); }
                }
            }
        });

        // Operacion de suma
        bt_sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado) // Si no hay elementos damos un error, si ya hay podemos añadir el +
                {
                    Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                }
                else
                {   // Si estamos en la primera operacion, añadimos el operador
                    if (primera_operacion)
                    {
                        if (text_calculando.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                        }else{
                            String aux = text_calculando.getText().toString(); // Asignamos a una string
                            String ultimo_elemento = aux.substring(aux.length() - 1);

                            if(ultimo_elemento.equals("-") || ultimo_elemento.equals("+") ||
                                    ultimo_elemento.equals("÷") || ultimo_elemento.equals("×") ||
                                    ultimo_elemento.equals(".") || aux.contains("%")){
                                Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                                primera_operacion = false;
                            }else{
                                text_calculando.setText(text_calculando.getText() + "+");
                                operadores.clear();
                                operadores.add("+"); // Añadimos la operacion que tenemos que hacer
                                primera_operacion = false;
                                menos_im = false;
                            }
                        }

                    }
                    else // Operamos y añadimos el +
                    {
                        if(text_calculando.getText().equals(""))
                        {
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String aux = text_calculando.getText().toString(); // Asignamos a una string
                            String ultimo_elemento = aux.substring(aux.length() - 1);
                            String delimitadores = "\\+|\\-|\\÷|\\×"; // String de operadores

                            // No se puede concatenar operadores
                            if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                    ultimo_elemento.equals("÷") || ultimo_elemento.equals("×") ||
                                    ultimo_elemento.equals(".") || aux.contains("%"))
                            {
                                Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                sho_memoria = false;
                                ans = false;
                                menos_im = false;
                                punto_add = 1;
                                op_con_ans = 0;     // Restrablecemos el valor
                                text_calculando.setTextSize(40);
                                String numeros[] = aux.split(delimitadores); // Dividimos para obtener los numeros sueltos
                                Double numeros_double[] = {0.0 , 0.0};
                                Double result, result2;

                                // Cuando tenemos numeros negativos entramos
                                if (numeros.length == 3)
                                {
                                    Log.i("Numeros leng es 3: ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2]);

                                    // Operaciones del tipo x * (-y) o x / (-y)
                                    if(aux.contains("×-") || aux.contains("÷-")){

                                        Double r;
                                        numeros[2] = "-" + numeros[2];

                                        result = Double.parseDouble(numeros[2]);
                                        numeros_double[0] = result;

                                        r = Double.parseDouble(numeros[0]);
                                        numeros_double[1] = r;


                                    }
                                    // Operaciones del tipo x+-y
                                    else if (aux.contains("+-")) {
                                        operadores.clear();
                                        operadores.add("+");

                                        Double r;
                                        numeros[2] = "-" + numeros[2];

                                        result = Double.parseDouble(numeros[0]);
                                        numeros_double[0] = result;

                                        r = Double.parseDouble(numeros[2]);
                                        numeros_double[1] = r;
                                    }
                                    // Operaciones del tipo -x-y
                                    else{
                                        Double r;
                                        numeros[1] = "-" + numeros[1];

                                        result = Double.parseDouble(numeros[1]);
                                        numeros_double[0] = result;

                                        r = Double.parseDouble(numeros[2]);
                                        numeros_double[1] = r;
                                    }
                                }

                                // Operaciones del tipo -x * (-y) o -x / (-y)
                                else if (numeros.length == 4)
                                {
                                    // Log.i("Numeros leng es 4 ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2] + " numeros 3 " + numeros[3]);

                                    Double r;
                                    numeros[3] = "-" + numeros[3];
                                    numeros[1] = "-" + numeros[1];

                                    result = Double.parseDouble(numeros[1]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[3]);
                                    numeros_double[1] = r;
                                }

                                else
                                {
                                    for (int i = 0; i < numeros.length; i++)
                                    {
                                        if (numeros[i].contains("-")) { }
                                        else
                                        {
                                            result = Double.parseDouble(numeros[i]);
                                            numeros_double[i] = result;
                                        }
                                    }
                                }

                                // Si lo que hay es un suma, sumamos y añadimos el +
                                if (operadores.get(0).equals("+"))
                                {
                                    result2 = numeros_double[0] + numeros_double[1];
                                    operadores.clear();
                                    operadores.add("+");
                                    resultado = String.valueOf(result2);

                                    // Insertamos en el array del historial la operacion realizada

                                    historial.add("Operación número " + op_ac +" -> " +
                                            numeros_double[0] + " + " + numeros_double[1] + " = "
                                            + resultado);
                                    ++op_ac;
                                    
                                    // Para que no muestre numeros demasiado largos
                                    if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "+"); }
                                    else { text_calculando.setText(result2 + "+"); }
                                }
                                // Si lo que hay es un resta, restamos y añadimos el +
                                else if (operadores.get(0).equals("-"))
                                {
                                    result2 = numeros_double[0] - numeros_double[1];
                                    operadores.clear();
                                    operadores.add("+");
                                    resultado = String.valueOf(result2);

                                    historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " - " + numeros_double[1] + " = " + resultado);
                                    ++op_ac;

                                    if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "+"); }
                                    else { text_calculando.setText(result2 + "+"); }
                                }
                                // Si lo que hay es un division, dividimos y añadimos el +
                                else if (operadores.get(0).equals("÷"))
                                {
                                    if (numeros_double[1].equals(0.0))
                                    {
                                        // No puedo hacer esa operacion
                                        Toast.makeText(MainActivity.this,Np2,Toast.LENGTH_SHORT).show();
                                        limpiar();
                                        text_calculando.setTextSize(20);
                                        text_calculando.setText("No se puede dividir entre 0");

                                    }
                                    else
                                    {
                                        result2 = numeros_double[0] / numeros_double[1];
                                        operadores.clear();
                                        operadores.add("+");
                                        resultado = String.valueOf(result2);

                                        historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " ÷ " + numeros_double[1] + " = " + resultado);
                                        ++op_ac;

                                        if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "+"); }
                                        else { text_calculando.setText(result2 + "+"); }
                                    }

                                }
                                else if (operadores.get(0).equals("×")) // Si lo que hay es un division, dividimos y añadimos el +
                                {
                                    result2 = numeros_double[0] * numeros_double[1];
                                    operadores.clear();
                                    operadores.add("+");
                                    resultado = String.valueOf(result2);

                                    historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " × " + numeros_double[1] + " = " + resultado);
                                    ++op_ac;

                                    if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "+"); }
                                    else { text_calculando.setText(result2 + "+"); }
                                }

                            }
                        }


                    }

                }
            }
        });

        // Operacion de resta
        bt_restar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si ha finalizado la operacion podemos poner un menos delante para señalar que son numeros negativos
                if (finalizado)
                {
                    text_calculando.setText("-");
                    finalizado = false;
                    primera_operacion = true;
                    menos_im = false;
                }
                else
                {
                    // Si no es el primero operador y no hemos añadido ya dos menos
                    if (primera_operacion || menos_im)
                    {
                        if (text_calculando.getText().toString().equals("")){
                            text_calculando.setText("-");
                        }else{

                            String aux = text_calculando.getText().toString(); // Asignamos a una string
                            String ultimo_elemento = aux.substring(aux.length() - 1);

                            if (ultimo_elemento.equals("-") || aux.contains("%")) // Evitamos poner dos operadores - seguidos
                            {
                                Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                            }

                            // Si el ultimo elemento es un × o ÷ podemos añadir un numero negativo
                            // pero esto no cambia la operacion añadida en "operadores"
                            else if(ultimo_elemento.equals("×") || ultimo_elemento.equals("÷")){

                                text_calculando.setText(text_calculando.getText() + "-");
                                primera_operacion = false;
                                menos_im = false;

                            }else if (ultimo_elemento.equals("1")){
                                menos_im = false;
                                primera_operacion = false;
                            }
                            else // Para restar dos numeros aunque el primero sea negativo
                            {
                                text_calculando.setText(text_calculando.getText() + "-");
                                operadores.clear();
                                operadores.add("-");
                                primera_operacion = false;
                                menos_im = false;
                            }
                        }
                    }
                    else // Operamos y añadimos el -
                    {
                        String aux = text_calculando.getText().toString(); // Asignamos a una string
                        String ultimo_elemento = aux.substring(aux.length() - 1);
                        String delimitadores = "\\+|\\-|\\÷|\\×"; // String de operadores

                        if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                ultimo_elemento.equals("÷") || ultimo_elemento.equals("×")
                                || ultimo_elemento.equals(".") || aux.contains("%") ) // No se puede concatenar operadores
                        {
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            sho_memoria = false;
                            ans = false;
                            punto_add = 1;
                            op_con_ans = 0;
                            text_calculando.setTextSize(40);
                            String numeros[] = aux.split(delimitadores); // Dividimos para obtener los numeros sueltos
                            Double[] numeros_double = { 0.0, 0.0 };
                            Double result, result2;

                            // Cuando tenemos numeros negativos entramos
                            if (numeros.length == 3)
                            {
                                Log.i("Numeros leng es 3: ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2]);

                                // Operaciones del tipo x * (-y) o x / (-y)
                                if(aux.contains("×-") || aux.contains("÷-")){

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[2]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[0]);
                                    numeros_double[1] = r;


                                }
                                // Operaciones del tipo x+-y
                                else if (aux.contains("+-")) {
                                    operadores.clear();
                                    operadores.add("+");

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[0]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                                // Operaciones del tipo -x-y
                                else{
                                    Double r;
                                    numeros[1] = "-" + numeros[1];

                                    result = Double.parseDouble(numeros[1]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                            }

                            // Operaciones del tipo -x * (-y) o -x / (-y)
                            else if (numeros.length == 4)
                            {
                               // Log.i("Numeros leng es 4 ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2] + " numeros 3 " + numeros[3]);

                                Double r;
                                numeros[3] = "-" + numeros[3];
                                numeros[1] = "-" + numeros[1];

                                result = Double.parseDouble(numeros[1]);
                                numeros_double[0] = result;

                                r = Double.parseDouble(numeros[3]);
                                numeros_double[1] = r;
                            }

                            else
                            {
                                // Pasamos los numeros del EditText en formato string a Double para operar con ellos
                                for (int i = 0; i < numeros.length; i++)
                                {
                                    if (numeros[i].contains("-")) { }
                                    else
                                    {
                                        result = Double.parseDouble(numeros[i]);
                                        numeros_double[i] = result;
                                    }
                                }
                            }

                            if (operadores.get(0).equals("+"))
                            {
                                result2 = numeros_double[0] + numeros_double[1];
                                operadores.clear();
                                operadores.add("-");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " + " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "-"); }
                                else { text_calculando.setText(result2 + "-"); }
                            }
                            else if (operadores.get(0).equals("-"))
                            {
                                result2 = numeros_double[0] - numeros_double[1];
                                operadores.clear();
                                operadores.add("-");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " - " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "-"); }
                                else { text_calculando.setText(result2 + "-"); }
                            }
                            else if (operadores.get(0).equals("÷"))
                            {
                                if (numeros_double[1].equals(0.0))
                                {
                                    Toast.makeText(MainActivity.this,Np2,Toast.LENGTH_SHORT).show();
                                    limpiar();
                                    text_calculando.setTextSize(20);
                                    text_calculando.setText("No se puede dividir entre 0");
                                }
                                else
                                {
                                    result2 = numeros_double[0] / numeros_double[1];
                                    operadores.clear();
                                    operadores.add("-");
                                    resultado = String.valueOf(result2);

                                    historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " ÷ " + numeros_double[1] + " = " + resultado);
                                    ++op_ac;

                                    if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "-"); }
                                    else { text_calculando.setText(result2 + "-"); }
                                }
                            }
                            else if (operadores.get(0).equals("×"))
                            {
                                result2 = numeros_double[0] * numeros_double[1];
                                operadores.clear();
                                operadores.add("-");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " × " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "-"); }
                                else { text_calculando.setText(result2 + "-"); }

                            }
                        }
                    }
                }
            }
        });

        // Operacion de multiplicar
        bt_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado) // Si no hay elementos damos un error, si ya hay podemos añadir el *
                {
                    Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (primera_operacion) // Si estamos en la primera operacion, añadimos el operador
                    {
                        if(text_calculando.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();

                        }else{
                            String aux = text_calculando.getText().toString(); // Asignamos a una string
                            String ultimo_elemento = aux.substring(aux.length() - 1);

                            if(ultimo_elemento.equals("-") || aux.contains("%") ){
                                Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                            }else {
                                text_calculando.setText(text_calculando.getText() + "×");
                                operadores.clear();
                                operadores.add("×"); // Añadimos la operacion que tenemos que hacer
                                primera_operacion = false;
                                menos_im = false;
                            }
                        }
                    }
                    else // Operamos y añadimos el *
                    {
                        String aux = text_calculando.getText().toString(); // Asignamos a una string
                        String ultimo_elemento = aux.substring(aux.length() - 1);
                        String delimitadores = "\\+|\\-|\\÷|\\×"; // String de operadores

                        if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                ultimo_elemento.equals("÷") || ultimo_elemento.equals("×")
                                || ultimo_elemento.equals(".") || aux.contains("%")) // No se puede concatenar operadores
                        {
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            sho_memoria = false;
                            ans = false;
                            menos_im = false;
                            punto_add = 1;
                            op_con_ans = 0;
                            text_calculando.setTextSize(40); // Reajustamos el tamaño del texto
                            String[] numeros = aux.split(delimitadores); // Dividimos para obtener los numeros sueltos
                            Double[] numeros_double = { 0.0, 0.0 };
                            Double result, result2;

                            // Cuando tenemos numeros negativos entramos
                            if (numeros.length == 3)
                            {
                                Log.i("Numeros leng es 3: ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2]);

                                // Operaciones del tipo x * (-y) o x / (-y)
                                if(aux.contains("×-") || aux.contains("÷-")){

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[2]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[0]);
                                    numeros_double[1] = r;


                                }
                                // Operaciones del tipo x+-y
                                else if (aux.contains("+-")) {
                                    operadores.clear();
                                    operadores.add("+");

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[0]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                                // Operaciones del tipo -x-y
                                else{
                                    Double r;
                                    numeros[1] = "-" + numeros[1];

                                    result = Double.parseDouble(numeros[1]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                            }

                            // Operaciones del tipo -x * (-y) o -x / (-y)
                            else if (numeros.length == 4)
                            {
                                // Log.i("Numeros leng es 4 ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2] + " numeros 3 " + numeros[3]);

                                Double r;
                                numeros[3] = "-" + numeros[3];
                                numeros[1] = "-" + numeros[1];

                                result = Double.parseDouble(numeros[1]);
                                numeros_double[0] = result;

                                r = Double.parseDouble(numeros[3]);
                                numeros_double[1] = r;
                            }

                            else
                            {
                                for (int i = 0; i < numeros.length; i++)
                                {
                                    if (numeros[i].contains("-")) { }
                                    else
                                    {
                                        result = Double.parseDouble(numeros[i]);
                                        numeros_double[i] = result;
                                    }
                                }
                            }

                            if (operadores.get(0).equals("+"))
                            {
                                result2 = numeros_double[0] + numeros_double[1];
                                operadores.clear();
                                operadores.add("×");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " + " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "×"); }
                                else { text_calculando.setText(result2 + "×"); }
                            }
                            else if (operadores.get(0).equals("-"))
                            {
                                result2 = numeros_double[0] - numeros_double[1];
                                operadores.clear();
                                operadores.add("×");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " - " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "×"); }
                                else { text_calculando.setText(result2 + "×"); }
                            }
                            else if (operadores.get(0).equals("÷"))
                            {
                                if (numeros_double[1].equals(0.0))
                                {
                                    Toast.makeText(MainActivity.this,Np2,Toast.LENGTH_SHORT).show();

                                    limpiar();
                                    text_calculando.setTextSize(20);
                                    text_calculando.setText("No se puede dividir entre 0");

                                }
                                else
                                {
                                    result2 = numeros_double[0] / numeros_double[1];
                                    operadores.clear();
                                    operadores.add("×");
                                    resultado = String.valueOf(result2);

                                    historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " ÷ " + numeros_double[1] + " = " + resultado);
                                    ++op_ac;

                                    if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "×"); }
                                    else { text_calculando.setText(result2 + "×"); }
                                }
                            }
                            else if (operadores.get(0).equals("×"))
                            {
                                result2 = numeros_double[0] * numeros_double[1];
                                operadores.clear();
                                operadores.add("×");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " × " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "×"); }
                                else { text_calculando.setText(result2 + "×"); }
                            }

                        }
                    }
                }
            }
        });

        // Añade un ÷ al calculo
        bt_divi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)
                {
                    Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (primera_operacion)
                    {
                        if(text_calculando.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();

                        }else{
                            String aux = text_calculando.getText().toString(); // Asignamos a una string
                            String ultimo_elemento = aux.substring(aux.length() - 1);

                            if(ultimo_elemento.equals("-") || aux.contains("%") ){
                                Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                            }else {
                                text_calculando.setText(text_calculando.getText() + "÷");
                                operadores.clear();
                                operadores.add("÷"); // Añadimos la operacion que tenemos que hacer
                                primera_operacion = false;
                                menos_im = false;
                            }
                        }
                    }
                    else
                    {
                        String aux = text_calculando.getText().toString(); // Asignamos a una string
                        String ultimo_elemento = aux.substring(aux.length() - 1);
                        String delimitadores = "\\+|\\-|\\÷|\\×"; // String de operadores

                        if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                ultimo_elemento.equals("÷") || ultimo_elemento.equals("×")
                                || ultimo_elemento.equals(".") || aux.contains("%")) // No se puede concatenar operadores
                        {
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            sho_memoria = false;
                            ans = false;
                            menos_im = false;
                            punto_add = 1;
                            op_con_ans = 0;
                            text_calculando.setTextSize(40);
                            String[] numeros = aux.split(delimitadores); // Dividimos para obtener los numeros sueltos
                            Double[] numeros_double = { 0.0, 0.0 };
                            Double result, result2;

                            // Cuando tenemos numeros negativos entramos
                            if (numeros.length == 3)
                            {
                                Log.i("Numeros leng es 3: ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2]);

                                // Operaciones del tipo x * (-y) o x / (-y)
                                if(aux.contains("×-") || aux.contains("÷-")){

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[2]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[0]);
                                    numeros_double[1] = r;


                                }
                                // Operaciones del tipo x+-y
                                else if (aux.contains("+-")) {
                                    operadores.clear();
                                    operadores.add("+");

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[0]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                                // Operaciones del tipo -x-y
                                else{
                                    Double r;
                                    numeros[1] = "-" + numeros[1];

                                    result = Double.parseDouble(numeros[1]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                            }

                            // Operaciones del tipo -x * (-y) o -x / (-y)
                            else if (numeros.length == 4)
                            {
                                // Log.i("Numeros leng es 4 ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2] + " numeros 3 " + numeros[3]);

                                Double r;
                                numeros[3] = "-" + numeros[3];
                                numeros[1] = "-" + numeros[1];

                                result = Double.parseDouble(numeros[1]);
                                numeros_double[0] = result;

                                r = Double.parseDouble(numeros[3]);
                                numeros_double[1] = r;
                            }

                            else
                            {
                                for (int i = 0; i < numeros.length; i++)
                                {
                                    if (numeros[i].contains("-")) { }
                                    else
                                    {
                                        result = Double.parseDouble(numeros[i]);
                                        numeros_double[i] = result;
                                    }
                                }
                            }

                            if (operadores.get(0).equals("+"))
                            {
                                result2 = numeros_double[0] + numeros_double[1];
                                operadores.clear();
                                operadores.add("÷");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " + " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "÷"); }
                                else { text_calculando.setText(result2 + "÷"); }
                            }
                            else if (operadores.get(0).equals("-"))
                            {
                                result2 = numeros_double[0] - numeros_double[1];
                                operadores.clear();
                                operadores.add("÷");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " - " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "÷"); }
                                else { text_calculando.setText(result2 + "÷"); }
                            }
                            else if (operadores.get(0).equals("÷"))
                            {
                                if (numeros_double[1].equals(0.0))
                                {
                                    Toast.makeText(MainActivity.this,Np2,Toast.LENGTH_SHORT).show();
                                    limpiar();
                                    text_calculando.setTextSize(20);
                                    text_calculando.setText("No se puede dividir entre 0");

                                }
                                else
                                {
                                    result2 = numeros_double[0] / numeros_double[1];
                                    operadores.clear();
                                    operadores.add("÷");
                                    resultado = String.valueOf(result2);

                                    historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " ÷ " + numeros_double[1] + " = " + resultado);
                                    ++op_ac;

                                    if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "÷"); }
                                    else { text_calculando.setText(result2 + "÷"); }
                                }
                            }
                            else if (operadores.get(0).equals("×"))
                            {
                                result2 = numeros_double[0] * numeros_double[1];
                                operadores.clear();
                                operadores.add("÷");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " × " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado + "÷"); }
                                else { text_calculando.setText(result2 + "÷"); }
                            }

                        }
                    }

                }
            }
        });

        // Borra toda la ecuacion
        bt_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiar();
            }
        });

        // Añade el resultado anterior a la ecuacion
        bt_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado || primera_operacion)
                {
                    Toast.makeText(MainActivity.this,Np3,Toast.LENGTH_SHORT).show();
                }
                else if (operadores.contains("+") || operadores.contains("-") ||
                        operadores.contains("÷") || operadores.contains("×"))
                {
                    if (!ans){
                        text_calculando.setText(text_calculando.getText() + resultado);
                        ans = true;
                    }else{
                        Toast.makeText(MainActivity.this,Np3,Toast.LENGTH_SHORT).show();
                    }
                }
                else {Toast.makeText(MainActivity.this,Np3,Toast.LENGTH_SHORT).show();}
            }
        });

        // Elimina el ultimo elemento del a operacion
        bt_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado)
                {
                   limpiar();
                }
                else
                {
                    if(text_calculando.getText().toString().equals("")
                            || text_calculando.getText().toString().equals("0.0"))
                    {
                        limpiar();

                    }
                    else{
                        String aux = text_calculando.getText().toString();
                        String res = aux.substring(0, aux.length() - 1);
                        text_calculando.setText(res);
                        //operadores.clear();
                        //primera_operacion = true;
                    }
                }
            }
        });

        // Al pulsar el boton igual nos calcula el resultado de la operacion propuesta
        bt_igual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si esta acabado o es la primera operacion o el texto esta vacio
                if (finalizado || primera_operacion
                        || text_calculando.getText().toString().contains("%")
                        || text_calculando.getText().toString().equals("0.0"))
                {
                    Snackbar.make(v,Np4,Snackbar.LENGTH_SHORT).show();
                }
                else // Operamos
                {
                    if(text_calculando.getText().toString().equals("")){

                    }else{

                        String aux = text_calculando.getText().toString(); // Asignamos a una string
                        String ultimo_elemento = aux.substring(aux.length() - 1);
                        String delimitadores = "\\+|\\-|\\÷|\\×"; // String de operadores

                        if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                ultimo_elemento.equals("÷") || ultimo_elemento.equals("×")
                                || ultimo_elemento.equals(".") || aux.contains("%")) // No se puede concatenar operadores
                        {
                            Snackbar.make(v,Np4,Snackbar.LENGTH_SHORT).show();
                        }
                        else
                        {
                            sho_memoria = false;
                            ans = false;
                            menos_im = true; // Reactiva la posibilidad de poner un numero negativo
                            punto_add = 1;
                            op_con_ans = 0;
                            text_calculando.setTextSize(40);
                            String[] numeros = aux.split(delimitadores); // Dividimos para obtener los numeros sueltos
                            Double[] numeros_double = { 0.0, 0.0 };
                            Double result, result2;

                            // Cuando tenemos numeros negativos entramos
                            if (numeros.length == 3)
                            {
                                Log.i("Numeros leng es 3: ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2]);

                                // Operaciones del tipo x * (-y) o x / (-y)
                                if(aux.contains("×-") || aux.contains("÷-")){

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[2]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[0]);
                                    numeros_double[1] = r;


                                }
                                // Operaciones del tipo x+-y
                                else if (aux.contains("+-")) {
                                    operadores.clear();
                                    operadores.add("+");

                                    Double r;
                                    numeros[2] = "-" + numeros[2];

                                    result = Double.parseDouble(numeros[0]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                                // Operaciones del tipo -x-y
                                else{
                                    Double r;
                                    numeros[1] = "-" + numeros[1];

                                    result = Double.parseDouble(numeros[1]);
                                    numeros_double[0] = result;

                                    r = Double.parseDouble(numeros[2]);
                                    numeros_double[1] = r;
                                }
                            }

                            // Operaciones del tipo -x * (-y) o -x / (-y)
                            else if (numeros.length == 4)
                            {
                                // Log.i("Numeros leng es 4 ", "numeros 0 "+ numeros[0] +" numeros 1 " + numeros[1] + " numeros 2 " + numeros[2] + " numeros 3 " + numeros[3]);

                                Double r;
                                numeros[3] = "-" + numeros[3];
                                numeros[1] = "-" + numeros[1];

                                result = Double.parseDouble(numeros[1]);
                                numeros_double[0] = result;

                                r = Double.parseDouble(numeros[3]);
                                numeros_double[1] = r;
                            }

                            else
                            {
                                for (int i = 0; i < numeros.length; i++)
                                {
                                    if (numeros[i].contains("-")) { }
                                    else
                                    {
                                        result = Double.parseDouble(numeros[i]);
                                        numeros_double[i] = result;
                                    }
                                }
                            }

                            if (operadores.get(0).equals("+"))
                            {
                                result2 = numeros_double[0] + numeros_double[1];
                                operadores.clear();
                                operadores.add("÷");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " + " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                finalizado();

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado); }
                                else { text_calculando.setText(resultado); }

                                Snackbar.make(v,Np5,Snackbar.LENGTH_SHORT).show();

                            }
                            else if (operadores.get(0).equals("-"))
                            {
                                result2 = numeros_double[0] - numeros_double[1];
                                operadores.clear();
                                operadores.add("÷");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " - " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                finalizado();

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado); }
                                else { text_calculando.setText(resultado); }

                                Snackbar.make(v,Np5,Snackbar.LENGTH_SHORT).show();
                            }
                            else if (operadores.get(0).equals("÷"))
                            {
                                if (numeros_double[1].equals(0.0))
                                {
                                    Toast.makeText(MainActivity.this,Np2,Toast.LENGTH_SHORT).show();
                                    limpiar();
                                    text_calculando.setTextSize(20);
                                    text_calculando.setText("No se puede dividir entre 0");

                                }
                                else
                                {
                                    result2 = numeros_double[0] / numeros_double[1];
                                    operadores.clear();
                                    operadores.add("÷");
                                    resultado = String.valueOf(result2);

                                    historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " ÷ " + numeros_double[1] + " = " + resultado);
                                    ++op_ac;

                                    finalizado();

                                    if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado); }
                                    else { text_calculando.setText(resultado); }

                                    Snackbar.make(v,Np5,Snackbar.LENGTH_SHORT).show();
                                }
                            }
                            else if (operadores.get(0).equals("×"))
                            {
                                result2 = numeros_double[0] * numeros_double[1];
                                operadores.clear();
                                operadores.add("÷");
                                resultado = String.valueOf(result2);

                                historial.add("Operación número " + op_ac + " -> " + numeros_double[0] + " × " + numeros_double[1] + " = " + resultado);
                                ++op_ac;

                                finalizado();

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado); }
                                else { text_calculando.setText(resultado); }

                                Snackbar.make(v,Np5,Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });


        bt_historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(config == 1){
                    if (activa_historial) {
                        bt_ans.setVisibility(View.INVISIBLE);
                        bt_sumar.setVisibility(View.INVISIBLE);
                        bt_restar.setVisibility(View.INVISIBLE);
                        bt_divi.setVisibility(View.INVISIBLE);
                        bt_borrar.setVisibility(View.INVISIBLE);
                        bt_multi.setVisibility(View.INVISIBLE);
                        bt_limpiar.setVisibility(View.INVISIBLE);
                        bt_num0.setVisibility(View.INVISIBLE);
                        bt_num1.setVisibility(View.INVISIBLE);
                        bt_num2.setVisibility(View.INVISIBLE);
                        bt_num3.setVisibility(View.INVISIBLE);
                        bt_num4.setVisibility(View.INVISIBLE);
                        bt_num5.setVisibility(View.INVISIBLE);
                        bt_num6.setVisibility(View.INVISIBLE);
                        bt_num7.setVisibility(View.INVISIBLE);
                        bt_num8.setVisibility(View.INVISIBLE);
                        bt_num9.setVisibility(View.INVISIBLE);
                        bt_more.setVisibility(View.INVISIBLE);
                        bt_coma.setVisibility(View.INVISIBLE);
                        bt_igual.setVisibility(View.INVISIBLE);

                        text_historial.setVisibility(View.VISIBLE);

                        for (int i = 0; i< historial.size(); i++){
                            text_his = historial.get(i).toString();
                            aux2 += text_his + "\n" + "\n";
                        }
                        text_historial.setText(aux2);


                        activa_historial = false;
                    }else{

                        bt_ans.setVisibility(View.VISIBLE);
                        bt_sumar.setVisibility(View.VISIBLE);
                        bt_restar.setVisibility(View.VISIBLE);
                        bt_divi.setVisibility(View.VISIBLE);
                        bt_borrar.setVisibility(View.VISIBLE);
                        bt_multi.setVisibility(View.VISIBLE);
                        bt_limpiar.setVisibility(View.VISIBLE);
                        bt_num0.setVisibility(View.VISIBLE);
                        bt_num1.setVisibility(View.VISIBLE);
                        bt_num2.setVisibility(View.VISIBLE);
                        bt_num3.setVisibility(View.VISIBLE);
                        bt_num4.setVisibility(View.VISIBLE);
                        bt_num5.setVisibility(View.VISIBLE);
                        bt_num6.setVisibility(View.VISIBLE);
                        bt_num7.setVisibility(View.VISIBLE);
                        bt_num8.setVisibility(View.VISIBLE);
                        bt_num9.setVisibility(View.VISIBLE);
                        bt_more.setVisibility(View.VISIBLE);
                        bt_coma.setVisibility(View.VISIBLE);
                        bt_igual.setVisibility(View.VISIBLE);

                        text_historial.setVisibility(View.INVISIBLE);

                        activa_historial = true;
                    }
                }else if (config == 2){
                    if (activa_historial) {
                        bt_ans.setVisibility(View.INVISIBLE);
                        bt_sumar.setVisibility(View.INVISIBLE);
                        bt_restar.setVisibility(View.INVISIBLE);
                        bt_divi.setVisibility(View.INVISIBLE);
                        bt_borrar.setVisibility(View.INVISIBLE);
                        bt_multi.setVisibility(View.INVISIBLE);
                        bt_limpiar.setVisibility(View.INVISIBLE);
                        bt_num0.setVisibility(View.INVISIBLE);
                        bt_num1.setVisibility(View.INVISIBLE);
                        bt_num2.setVisibility(View.INVISIBLE);
                        bt_num3.setVisibility(View.INVISIBLE);
                        bt_num4.setVisibility(View.INVISIBLE);
                        bt_num5.setVisibility(View.INVISIBLE);
                        bt_num6.setVisibility(View.INVISIBLE);
                        bt_num7.setVisibility(View.INVISIBLE);
                        bt_num8.setVisibility(View.INVISIBLE);
                        bt_num9.setVisibility(View.INVISIBLE);
                        bt_more.setVisibility(View.INVISIBLE);
                        bt_coma.setVisibility(View.INVISIBLE);
                        bt_igual.setVisibility(View.INVISIBLE);

                        bt_raiz.setVisibility(View.INVISIBLE);
                        bt_apagar.setVisibility(View.INVISIBLE);
                        bt_modulo.setVisibility(View.INVISIBLE);
                        bt_add_memoria.setVisibility(View.INVISIBLE);
                        bt_show_memoria.setVisibility(View.INVISIBLE);
                        bt_signo.setVisibility(View.INVISIBLE);
                        bt_potencia.setVisibility(View.INVISIBLE);
                        bt_limpiar_memoria.setVisibility(View.INVISIBLE);
                        bt_elimina_memoria.setVisibility(View.INVISIBLE);
                        bt_log.setVisibility(View.INVISIBLE);
                        bt_factorial.setVisibility(View.INVISIBLE);
                        bt_ln.setVisibility(View.INVISIBLE);
                        bt_cos.setVisibility(View.INVISIBLE);
                        bt_sin.setVisibility(View.INVISIBLE);
                        bt_tan.setVisibility(View.INVISIBLE);

                        text_historial.setVisibility(View.VISIBLE);

                        for (int i = 0; i< historial.size(); i++){
                            text_his = historial.get(i).toString();
                            aux2 += text_his + "\n" + "\n";
                        }
                            text_historial.setText(aux2);

                        activa_historial = false;
                    }else{

                        bt_ans.setVisibility(View.VISIBLE);
                        bt_sumar.setVisibility(View.VISIBLE);
                        bt_restar.setVisibility(View.VISIBLE);
                        bt_divi.setVisibility(View.VISIBLE);
                        bt_borrar.setVisibility(View.VISIBLE);
                        bt_multi.setVisibility(View.VISIBLE);
                        bt_limpiar.setVisibility(View.VISIBLE);
                        bt_num0.setVisibility(View.VISIBLE);
                        bt_num1.setVisibility(View.VISIBLE);
                        bt_num2.setVisibility(View.VISIBLE);
                        bt_num3.setVisibility(View.VISIBLE);
                        bt_num4.setVisibility(View.VISIBLE);
                        bt_num5.setVisibility(View.VISIBLE);
                        bt_num6.setVisibility(View.VISIBLE);
                        bt_num7.setVisibility(View.VISIBLE);
                        bt_num8.setVisibility(View.VISIBLE);
                        bt_num9.setVisibility(View.VISIBLE);
                        bt_more.setVisibility(View.VISIBLE);
                        bt_coma.setVisibility(View.VISIBLE);
                        bt_igual.setVisibility(View.VISIBLE);

                        bt_raiz.setVisibility(View.VISIBLE);
                        bt_apagar.setVisibility(View.VISIBLE);
                        bt_modulo.setVisibility(View.VISIBLE);
                        bt_add_memoria.setVisibility(View.VISIBLE);
                        bt_show_memoria.setVisibility(View.VISIBLE);
                        bt_signo.setVisibility(View.VISIBLE);
                        bt_potencia.setVisibility(View.VISIBLE);
                        bt_limpiar_memoria.setVisibility(View.VISIBLE);
                        bt_elimina_memoria.setVisibility(View.VISIBLE);
                        bt_log.setVisibility(View.VISIBLE);
                        bt_factorial.setVisibility(View.VISIBLE);
                        bt_ln.setVisibility(View.VISIBLE);
                        bt_cos.setVisibility(View.VISIBLE);
                        bt_sin.setVisibility(View.VISIBLE);
                        bt_tan.setVisibility(View.VISIBLE);

                        text_historial.setVisibility(View.INVISIBLE);

                        activa_historial = true;
                    }
                }
            }
        });

        bt_coma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalizado) // Si no hay elementos damos un error, si ya hay podemos añadir el .
                {
                    Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (primera_operacion) // Si estamos en la primera operacion, añadimos el operador
                    {
                        String aux = text_calculando.getText().toString();

                        if(aux.contains("."))
                        {
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            text_calculando.setText(text_calculando.getText() + ".");
                            primera_operacion = true;
                            if (punto_add == 1){
                                punto_add = 2;
                            }else{punto_add = 1;}

                        }
                    }
                    else
                    {
                        String aux = text_calculando.getText().toString(); // Asignamos a una string
                        String ultimo_elemento = aux.substring(aux.length() - 1);
                        String pultimo_elemento = aux.substring(aux.length() - 2);

                        for (int i = 0; i < aux.length(); i++){
                            char c = aux.charAt(i);
                            char punt = '.';
                            if (c == punt){
                                op_con_ans++;
                            }
                        }

                        if (op_con_ans >= 2){

                        }
                        else{
                            if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                    ultimo_elemento.equals("÷") || ultimo_elemento.equals("×")
                                    || ultimo_elemento.equals(".") || aux.contains("%")) // No se puede concatenar operadores
                            {
                                if (pultimo_elemento.contains("0.")){
                                    Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    if (punto_add == 1){
                                        text_calculando.setText(text_calculando.getText() + "0.");
                                        punto_add = 2;
                                    }else{
                                        Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            else
                            {
                                if (punto_add == 2){
                                    Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    text_calculando.setText(text_calculando.getText() + ".");
                                    punto_add = 2;
                                }
                            }
                        }


                    }
                }
            }
        });

        if (config == 2){
            bt_apagar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setMessage("¿Realmente quiere salir de la Calculadora?")
                            .setTitle("Aviso")
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    System.exit(0);
                                }
                            });

                    builder.setCancelable(false);
                    AlertDialog dialog = builder.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            });

            bt_raiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalizado || text_calculando.getText().toString().equals("0.0")
                            || text_calculando.getText().toString().contains("-"))
                    {
                        Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(text_calculando.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();

                        }else{

                            String aux = text_calculando.getText().toString(); // Asignamos a una string
                            String ultimo_elemento = aux.substring(aux.length() - 1);
                            Double result, numero;
                            String resultado;

                            // No se puede concatenar operadores
                            if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                    ultimo_elemento.equals("÷") || ultimo_elemento.equals("×") ||
                                    ultimo_elemento.equals(".") || aux.contains("%")
                                    || text_calculando.getText().toString().contains("+")
                                    || text_calculando.getText().toString().contains("-")
                                    || text_calculando.getText().toString().contains("÷")
                                    || text_calculando.getText().toString().contains("×"))
                            {
                                Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                numero = Double.parseDouble(text_calculando.getText().toString());
                                result = Math.sqrt(numero);

                                resultado = String.valueOf(result);

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado); }
                                else { text_calculando.setText(resultado); }

                            }
                        }
                    }
                }
            });

            bt_modulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalizado) // Si no hay elementos damos un error, si ya hay podemos añadir el .
                    {
                        Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (primera_operacion) // Si estamos en la primera operacion, añadimos el operador
                        {
                            if (text_calculando.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, Np1, Toast.LENGTH_SHORT).show();

                            } else {
                                String aux = text_calculando.getText().toString(); // Asignamos a una string
                                String ultimo_elemento = aux.substring(aux.length() - 1);

                                if (aux.contains("+") || aux.contains("×") || aux.contains("÷")
                                        || ultimo_elemento.equals("-") || aux.contains("%")
                                        || ultimo_elemento.equals(".")) {
                                    Toast.makeText(MainActivity.this, Np1, Toast.LENGTH_SHORT).show();
                                } else {
                                    text_calculando.setText(text_calculando.getText() + "%");
                                    operadores.clear();
                                    primera_operacion = false;
                                    menos_im = false;
                                    mod_on = true;
                                }
                            }
                        }

                    }
                }
            });

            bt_add_memoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalizado)
                    {
                        Double m_p = memoria;

                        String aux = text_calculando.getText().toString();
                        memoria = Double.parseDouble(aux);

                        Toast.makeText(MainActivity.this,
                                "Has guardado el numero " + memoria + " en la memoria. Se ha " +
                                        "borrado " + m_p, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this,
                                "Finaliza la operacion para poder guardar en memoria",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            bt_show_memoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalizado || primera_operacion)
                    {
                        text_calculando.setText(memoria.toString());
                        finalizado = false;
                    }
                    else if (operadores.contains("+") || operadores.contains("-") ||
                            operadores.contains("÷") || operadores.contains("×"))
                    {
                        if (!sho_memoria){
                            text_calculando.setText(text_calculando.getText() + memoria.toString());
                            sho_memoria = true;
                            finalizado = false;
                        }else{
                            Toast.makeText(MainActivity.this,Np6,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {Toast.makeText(MainActivity.this,Np6,Toast.LENGTH_SHORT).show();}
                }
            });

            bt_potencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalizado || text_calculando.getText().toString().equals("0.0"))
                    {
                        Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(text_calculando.getText().toString().equals("")){
                            Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();

                        }else{

                            String aux = text_calculando.getText().toString(); // Asignamos a una string
                            String ultimo_elemento = aux.substring(aux.length() - 1);
                            Double result, numero;
                            String resultado;

                            // No se puede concatenar operadores
                            if (ultimo_elemento.equals("+") || ultimo_elemento.equals("-") ||
                                    ultimo_elemento.equals("÷") || ultimo_elemento.equals("×") ||
                                    ultimo_elemento.equals(".") || aux.contains("%")
                                    || text_calculando.getText().toString().contains("+")
                                    || text_calculando.getText().toString().contains("-")
                                    || text_calculando.getText().toString().contains("÷")
                                    || text_calculando.getText().toString().contains("×"))
                            {
                                Toast.makeText(MainActivity.this,Np1,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                numero = Double.parseDouble(text_calculando.getText().toString());
                                result = numero * numero;

                                resultado = String.valueOf(result);

                                if (resultado.length() > 4) { resultado = resultado.substring(0, 5); text_calculando.setText(resultado); }
                                else { text_calculando.setText(resultado); }

                            }
                        }
                    }
                }
            });

            bt_signo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalizado && !text_calculando.getText().toString().equals("0.0") &&
                            !text_calculando.getText().toString().equals(""))
                    {
                        String aux = text_calculando.getText().toString(); // Asignamos a una string
                        if(aux.contains("-")){
                            String a = aux.substring(aux.length(),aux.length()-1);
                            text_calculando.setText(a);
                            finalizado = false;
                        }else{
                            text_calculando.setText("-"+aux);
                            finalizado = false;
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Finaliza la operacion para poder " +
                                "cambiar de signo",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    // Funcion limpia el arraylist de operadores y reseta valores
    private void limpiar()
    {
        text_calculando.setText("0.0");
        operadores.clear();
        punto_add = 0;
        op_con_ans = 0;
        finalizado = true;
        menos_im = true;
        sho_memoria = false;
        mod_on = false;
        primera_operacion = true;
        ans = false;
        text_calculando.setTextSize(50);
    }

    private void finalizado(){
        operadores.clear();
        punto_add = 0;
        op_con_ans = 0;
        finalizado = true;
        mod_on = false;
        sho_memoria = false;
        primera_operacion = true;
        ans = false;
        text_calculando.setTextSize(50);
    }

    public void setPortrar(View v){

        config = getResources().getConfiguration().orientation;
        if(config == 1){
            Snackbar.make(v,"Ya estas mirando el modo básico",Snackbar.LENGTH_SHORT).show();
        }else if (config == 2){
            Snackbar.make(v,"Gira el dispositivo para ver modo científico",Snackbar.LENGTH_SHORT).show();
        }
    }

    public void setLands(View v){

        config = getResources().getConfiguration().orientation;
        if(config == 2){
            Snackbar.make(v,"Ya estas mirando el modo científico",Snackbar.LENGTH_SHORT).show();
        }else if (config == 1){
            Snackbar.make(v,"Gira el dispositivo para ver modo básico",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (!text_calculando.getText().toString().equals("")){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(text, text_calculando.getText().toString());
            editor.commit();

        }
    }

    public void setP(View v){

        Snackbar.make(v,"Disponible en la version 2",Snackbar.LENGTH_SHORT).show();
    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        menuItem.getItemId();
                        return true;
                    }
                });
    }
}