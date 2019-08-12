package com.vbcreations.vbcalculator;


import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity{

    private Button zero,one,two,three,four,five,six,seven,eight,nine,add,sub,mul,div,clear,equal;
    private TextView screen;
    private String display="",currentOperator="";
    private String result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        updateScreen();
    }

    private void updateScreen(){
        screen.setText(display);
    }

    private void Clear() {
        display = "";
        currentOperator = "";
        result = "";
    }

    private void init(){
        zero=(Button)findViewById(R.id.btn0);
        one=(Button)findViewById(R.id.btn1);
        two=(Button)findViewById(R.id.btn2);
        three=(Button)findViewById(R.id.btn3);
        four=(Button)findViewById(R.id.btn4);
        five=(Button)findViewById(R.id.btn5);
        six=(Button)findViewById(R.id.btn6);
        seven=(Button)findViewById(R.id.btn7);
        eight=(Button)findViewById(R.id.btn8);
        nine=(Button)findViewById(R.id.btn9);
        add=(Button)findViewById(R.id.btnPlus);
        sub=(Button)findViewById(R.id.btnMinus);
        mul=(Button)findViewById(R.id.btnMul);
        div=(Button)findViewById(R.id.btnDiv);
        clear=(Button)findViewById(R.id.btnClear);
        equal=(Button)findViewById(R.id.btnEqual);
        screen=(TextView)findViewById(R.id.tvResult);
    }

    public void onClickNumber(View v){
        if(result!=""){
            Clear();
            updateScreen();
        }
        Button b=(Button) v;
        display+=b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch(op){
            case '+':
            case '-':
            case 'x':
            case '/':return true;
            default:return  false;
        }
    }

    public void onClickOperator(View v){
        Button b=(Button) v;
        if(display=="")return;
        if(result!=""){
            String _display=result;
            Clear();
            display=_display;
        }
        if(currentOperator!=""){
            Log.d("CalcX",""+display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display=display.replace(display.charAt(display.length()-1),b.getText().charAt(0));
                updateScreen();
                return;
            }
            else{
                getResult();
                display=result;
                result="";
            }
            currentOperator=b.getText().toString();
        }
        display+=b.getText();
        currentOperator=b.getText().toString();
        updateScreen();
    }

    public void onClickClear(View v){
        Clear();
        updateScreen();
    }

    private double operate(String a,String b,String op){
        switch (op){
            case "+":return Double.valueOf(a)+Double.valueOf(b);
            case "-":return Double.valueOf(a)-Double.valueOf(b);
            case "x":return Double.valueOf(a)*Double.valueOf(b);
            case "/":try{
                return Double.valueOf(a)/Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc",e.getMessage());
            }
            default:return -1;
        }

    }

    private boolean getResult(){
        if(currentOperator=="")return false;
        String[] operation=display.split(Pattern.quote(currentOperator));
        if(operation.length<2)return false;
        result=String.valueOf(operate(operation[0],operation[1],currentOperator));
        return true;
    }

    public void onClickEqual(View v){
        if(display=="")return;
        if(!getResult())return;
        screen.setText(display+"\n"+String.valueOf(result));
    }
}
