package com.example.gouwuche;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<JavaBean.DataBean> data;
    int page=1;
    boolean isf;
    static ArrayList<JavaBean.DataBean>arrayList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.lv);
        new Mythe().execute("http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=3");
    }

    public class Mythe extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String path=strings[0];
            try {
                URL url=new URL(path);
                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(2000);
                httpURLConnection.setReadTimeout(2000);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode()==200){
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    String str="";
                    StringBuilder stringBuilder=new StringBuilder();
                    while ((str=bufferedReader.readLine())!=null){
                        stringBuilder.append(str);
                    }
                    String string = stringBuilder.toString();
                    return string;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null){
                Gson gson=new Gson();
                JavaBean javaBean = gson.fromJson(s,JavaBean.class);
                data = javaBean.getData();
                for (int i=0;i<data.size();i++){
                    JavaBean.DataBean dataBean = data.get(i);
                }
                MybaseApdater mybaseApdater = new MybaseApdater();
                listView.setAdapter(mybaseApdater);

                //添加商品
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                        intent.putExtra("name",data.get(i).getTitle());
                        intent.putExtra("pic",data.get(i).getPic());
                        intent.putExtra("num",data.get(i).getNum());
                        startActivity(intent);
                        Toast.makeText(MainActivity.this,data.get(i).getTitle(),Toast.LENGTH_SHORT).show();
                    }
                });


                //刷新页面
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {
                        if (isf&&i==SCROLL_STATE_IDLE){
                            page++;
                            new Mythe().execute("http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page"+page);

                        }
                    }

                    @Override
                    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                        isf=((i+i1)==i2);
                    }
                });

            }
        }
    }


    public class MybaseApdater extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = new ViewHolder();
            if (view==null){
                view=getLayoutInflater().inflate(R.layout.item,null);
                viewHolder.imageView=view.findViewById(R.id.img1);
                viewHolder.textView=view.findViewById(R.id.txt1);
                view.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) view.getTag();
            }
            viewHolder.textView.setText(data.get(i).getTitle()+"Y"+data.get(i).getNum());
            Picasso.with(MainActivity.this).load(data.get(i).getPic()).into(viewHolder.imageView);

            return view;
        }
    }

    public class ViewHolder{
        TextView textView;
        ImageView imageView;
    }
}
