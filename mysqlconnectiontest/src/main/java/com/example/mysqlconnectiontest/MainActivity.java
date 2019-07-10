package com.example.mysqlconnectiontest;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button,button_delete,button_insert,button_update;
    private TextView textView;
    private static final int TEST_USER_SELECT = 1;
    int i =0,d=0,z=0;
    private EditText editText,editText_update;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String user;
            switch (msg.what){
                case TEST_USER_SELECT:
                    Test test = (Test) msg.obj;
                    user = test.getUser();
                    System.out.println("***********");
                    System.out.println("***********");
                    System.out.println("user:"+user);
                    textView.setText(user);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.bt_send);
        textView = (TextView) findViewById(R.id.tv_response);
        button_delete = (Button) findViewById(R.id.bt_delete);
        button_insert = (Button) findViewById(R.id.bt_insert);
        button_update = (Button) findViewById(R.id.bt_update);
        editText_update = (EditText) findViewById(R.id.ed_update);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_insert.setOnClickListener(this);
        button_update.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_send:
                //执行查询操作
                //通多点击buttoni自增长查询对应id的name
                if (i<=3){
                    i++;
                }else {
                    i=1;
                }
                //连接数据库进行操作需要在主线程操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        conn =(Connection) DBOpenHelper.getConn();
                        String sql = "select name from test_one where id='"+i+"'";
                        Statement st;
                        try {
                            st = (Statement) conn.createStatement();
                            ResultSet rs = st.executeQuery(sql);
                            while (rs.next()){
                                //因为查出来的数据试剂盒的形式，所以我们新建一个javabean存储
                                Test test = new Test();
                                test.setUser(rs.getString(1));
                                Message msg = new Message();
                                msg.what =TEST_USER_SELECT;
                                msg.obj = test;
                                handler.sendMessage(msg);
                            }
                            st.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.bt_delete:
                //new一个线程执行删除数据库数据
                d++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        int u = 0;
                        conn =(Connection) DBOpenHelper.getConn();
                        String sql = "delete from test_one where id='"+d+"'";
                        PreparedStatement pst;
                        try {
                                pst = (PreparedStatement) conn.prepareStatement(sql);
                                u = pst.executeUpdate();
                            pst.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.bt_insert:
                //执行插入操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        int u = 0;
                        conn =(Connection) DBOpenHelper.getConn();
                        String sql = "insert into test_one (name) values(?)";
                        PreparedStatement pst;
                        try {
                            pst = (PreparedStatement) conn.prepareStatement(sql);
                            //将输入的edit框的值获取并插入到数据库中
                            pst.setString(1,editText.getText().toString());
                            u = pst.executeUpdate();
                            pst.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.bt_update:
                //new一个线程执行删除数据库数据
                z++;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Connection conn = null;
                        int u = 0;
                        conn =(Connection) DBOpenHelper.getConn();
                        String sql = "update test_one set name='"+editText_update.getText().toString()+"' where id='"+z+"'";
                        PreparedStatement pst;
                        try {
                            pst = (PreparedStatement) conn.prepareStatement(sql);
                            u = pst.executeUpdate();
                            pst.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
                default:
        }
    }
}
