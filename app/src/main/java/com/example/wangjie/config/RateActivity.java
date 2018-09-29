package com.example.wangjie.config;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Formatter;

public class RateActivity extends Activity {
    private float dollarRate ;
    private float euroRate;
    private float wonRate ;

    private Button btn_doller;
    private Button btn_won;
    private Button btn_euor;
    private Button btn_config;
    private EditText et_rmb;
    private TextView tv_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        btn_doller=findViewById(R.id.btn_doller);
        btn_won=findViewById(R.id.btn_won);
        btn_euor=findViewById(R.id.btn_euor);
        btn_config=findViewById(R.id.btn_config);
        et_rmb=findViewById(R.id.et_rmb);
        tv_money=findViewById(R.id.tv_money);

        SharedPreferences sp=getSharedPreferences("myRate",this.MODE_PRIVATE);

        dollarRate=sp.getFloat("dollar",0.0f);
        euroRate=sp.getFloat("euor",0.0f);
        wonRate=sp.getFloat("won",0.0f);

    }


    public void calculate(View v){
        double rmb=0.0;
        try{
            rmb=Double.parseDouble(et_rmb.getText().toString());
            if(v==btn_doller){
                tv_money.setText(String.format("%.2f",rmb*dollarRate));
            }else if(v==btn_euor){
                tv_money.setText(String.format("%.2f",rmb*euroRate));
            }else{
                tv_money.setText(String.format("%.2f",rmb*wonRate));
            }
        }catch (Exception e){
            Toast.makeText(this,"请输入合法数值！",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
           config();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);
            euroRate = bundle.getFloat("key_euro",0.1f);
            wonRate = bundle.getFloat("key_won",0.1f);

            SharedPreferences sp=getSharedPreferences("myRate",this.MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();

            editor.putFloat("dollar",dollarRate);
            editor.putFloat("euor",euroRate);
            editor.putFloat("won",wonRate);

            editor.commit();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    public void openOne(View btn){
       config();
    }
    private void config(){
        Intent config = new Intent(this,ConfigActivity.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);
        startActivityForResult(config,1);
    }


}
