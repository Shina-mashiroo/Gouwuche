package com.example.gouwuche;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    static  int mc=0;
    ListView listView;
    TextView textView;
    Button button1,button2;
    CheckBox checkBox;
    ArrayList<JavaBean.DataBean>dataBeans=MainActivity.arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        chushihua();
        Mybase mybase = new Mybase();
        listView.setAdapter(mybase);
    }

    public class Mybase extends BaseAdapter{

        @Override
        public int getCount() {
            return dataBeans.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            chushihua2 csh = new chushihua2();
            if (view==null){
            view=getLayoutInflater().inflate(R.layout.item2,null);
            csh.dx1=view.findViewById(R.id.dx1);
            csh.txt3=view.findViewById(R.id.txt3);
            csh.img3=view.findViewById(R.id.img3);
            csh.jage=view.findViewById(R.id.jage);
            csh.jian=view.findViewById(R.id.jian);
            csh.jia=view.findViewById(R.id.jia);
            csh.sl=view.findViewById(R.id.sl);
            view.setTag(csh);
            }else{
                csh= (chushihua2) view.getTag();
            }
//            csh.txt3.setText(dataBeans.get(i).getTitle()+"");

            csh.jage.setText(dataBeans.get(i).getNum()+"");
            csh.sl.setText(dataBeans.get(i).getCount() +"");
            Picasso.with(Main3Activity.this).load(dataBeans.get(i).getPic()).into(csh.img3);
            jisuanjiage();
            if (dataBeans.get(i).getIsche()==1){
                csh.dx1.setChecked(true);
            }else {
                csh.dx1.setChecked(false);
            }
            csh.dx1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CheckBox)view).isChecked()){
                    dataBeans.get(i).setIsche(1);
                    notifyDataSetChanged();
                    }else {
                    dataBeans.get(i).setIsche(0);
                    notifyDataSetChanged();
                    }
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i=0;i<dataBeans.size();i++){
                        if (((CheckBox)view).isChecked()){
                            dataBeans.get(i).setIsche(1);
                            notifyDataSetChanged();
                        }else {
                            dataBeans.get(i).setIsche(0);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
            button1.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i=dataBeans.size()-1;i>=0;i--){
                        if (dataBeans.get(i).getIsche()==1){
                            dataBeans.remove(i);
                            notifyDataSetChanged();
                            jisuanjiage();
                        }


                    }
                }
            });
            csh.jia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBeans.get(i).setCount(dataBeans.get(i).getCount()+1);
                    notifyDataSetChanged();
                }
            });
            csh.jian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dataBeans.get(i).getCount() < 1) {
                        Toast.makeText(Main3Activity.this,"数量不能小于1",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        dataBeans.get(i).setCount(dataBeans.get(i).getCount()-1);
                    }
                    notifyDataSetChanged();
                    jisuanjiage();

                }
            });





            return view;
        }
    }

    public void jisuanjiage(){
        mc=0;
        for (int i=0;i<dataBeans.size();i++){
            int num = dataBeans.get(i).getNum();
            int count=dataBeans.get(i).getCount();
            mc+= (num*count);

        }
        textView.setText(mc+"");
    }


    public class chushihua2{
        CheckBox dx1;
         TextView txt3;
         ImageView img3;
         TextView jage;
        Button jian;
        TextView sl;
        Button jia;
    }
    private void chushihua(){
        listView=findViewById(R.id.lv1);
        button1=findViewById(R.id.re);
        button2=findViewById(R.id.js);
        checkBox=findViewById(R.id.dx);
        textView=findViewById(R.id.jg);
    }



}
