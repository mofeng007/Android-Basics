package com.mofeng.studentdatabasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
    SimpleAdapter adapter;
    StudentDao dao;
    EditText txtStuNo,txtName,txtClazz;
    Button addBtn,updateBtn,queryBtn;
    ListView dataList;


    private void initComponents(){
        txtStuNo = (EditText)findViewById(R.id.txtStuNo);
        txtName = (EditText)findViewById(R.id.txtName);
        txtClazz = (EditText)findViewById(R.id.txtClazz);
        addBtn = (Button)findViewById(R.id.btnAdd);
        updateBtn = (Button)findViewById(R.id.btnUpdate);
        queryBtn = (Button)findViewById(R.id.btnQuery);
        dataList =(ListView)findViewById(R.id.dataList);
    }

    private void initListView(){
        //初始状态默认查询全部
        String sql = " select * from student order by publish desc";
        dao.execQuery(list,sql);
        adapter = new SimpleAdapter(
                this,
                list,
                R.layout.item,
                new String[]{"stu_no","name","clazz","publish"},
                new int[]{R.id.stuNo,R.id.stuName, R.id.clazz, R.id.publish}
        );
        dataList.setAdapter(adapter);
        //为listView列表注册数据项监听器
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int pos,
                                    long arg3) {
                String stuNo = ((TextView)view.findViewById(R.id.stuNo)).getText().toString();
                String msg ="您确定要删除学号为"+stuNo+"的学生吗？";
                createAlertDialog(msg, stuNo).show();
            }
        });
        //根据header布局，生成view，加入列表
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view =layoutInflater.inflate(R.layout.header, null);
        dataList.addHeaderView(view);
    }

    /**生成信息交互对话框！    */
    private AlertDialog createAlertDialog(String msg, final String stuNo){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除确认"); //设置对话框的标题
        builder.setMessage(msg); //设置对话框的内容
        //设置对话框的按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteStu(stuNo);
            }
        });
        return builder.create();
    }
    /**根据sid删除一个学生记录*/
    private void deleteStu(String stuNo){
        String sql = "delete from student where stu_no="+stuNo;
        dao.execSQL(sql);
        queryStudent();
    }


    //根据查询条件刷新列表数据
    private void queryStudent(){
        String stuNo = txtStuNo.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String clazz = txtClazz.getText().toString().trim();
        String strSQL = "select * from student where 1=1 " ;
        if(!"".equals(stuNo)){
            strSQL+=" and stu_no like '%"+stuNo+"%'";
        }
        if(!"".equals(name)){
            strSQL+=" and name like '%"+name+"%'";
        }
        if(!"".equals(clazz)){
            strSQL+=" and clazz like '%"+clazz+"%'";
        }
        strSQL+=" order by publish desc";
        dao.execQuery(list,strSQL);
        adapter.notifyDataSetChanged();//只有当适配器数据容器中的值发生变化时，它才会触发uI的刷新；
    }
    //添加学生
    private void addStudent(){
        String stuNo = txtStuNo.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String clazz = txtClazz.getText().toString().trim();
        String publish = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if("".equals(stuNo)||"".equals(name)||"".equals(clazz)){
            Toast.makeText(getApplicationContext(), "请填写完整数据！", Toast.LENGTH_SHORT).show();
            return ;
        }
        // 动态组件SQL语句
        String strSQL = "insert into student values(null,'" + stuNo + "','"
                + name + "','" + clazz + "','" + publish + "')";
        boolean flag = dao.execSQL(strSQL);
        txtStuNo.setText("");
        txtName.setText("");
        txtClazz.setText("");
        //返回信息
        String message = flag?"添加成功":"添加失败";
        queryStudent();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**定义适用三个按钮的监听器类*/
    class ViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAdd:
                    addStudent();
                    break;
                case R.id.btnQuery:
                    queryStudent();
                    break;
                case R.id.btnUpdate:
                    break;
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new StudentDao(new StudentDBHelper(this));
        initComponents();
        initListView();
        View.OnClickListener oc =new ViewOnClickListener();
        addBtn.setOnClickListener(oc);
        updateBtn.setOnClickListener(oc);
        queryBtn.setOnClickListener(oc);

    }
}
