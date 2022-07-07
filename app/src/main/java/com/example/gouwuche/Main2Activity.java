package com.example.gouwuche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    ImageView imageView;

    Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initialize();
        cas();
    }

    private void cas(){
        Intent intent=getIntent();
        final String name = intent.getStringExtra("name");
        textView1.setText(name);
        final int num = intent.getIntExtra("num", 0);
        textView2.setText("价格:"+num);
        final String pic = intent.getStringExtra("pic");
        Picasso.with(Main2Activity.this).load(pic).into(imageView);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JavaBean.DataBean dataBean = new JavaBean.DataBean();
                dataBean.setTitle(name);
                dataBean.setNum(num);
                dataBean.setPic(pic);
                MainActivity.arrayList.add(dataBean);
                button2.setText("数量"+MainActivity.arrayList.size());
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,Main3Activity.class);
                startActivity(intent);
            }
        });

    }

    private void  initialize(){
        textView1 =  findViewById(R.id.txt2);
        imageView =  findViewById(R.id.img2);
        textView2 =  findViewById(R.id.txt3);
        button1 = findViewById(R.id.add);
        button2= findViewById(R.id.bu1);

    }
}
