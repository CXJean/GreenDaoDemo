package xuejian.bwie.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import xuejian.bwie.com.gen.UserDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private UserDao userDao;
    private EditText mid;
    private EditText editName;
    private TextView text;

    private Button btn_add;
    private Button btn_delete;
    private Button btn_deleteAll;
    private Button btn_update;
    private Button btn_select;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = MyApplication.getInstances().getDaoSession().getUserDao();

        initView();
    }


    private void initView() {

        mid = (EditText) findViewById(R.id.mId);
        editName = (EditText) findViewById(R.id.name);
        btn_add = (Button) findViewById(R.id.add);
        text = (TextView) findViewById(R.id.text);
        btn_delete = (Button) findViewById(R.id.delete);
        btn_deleteAll = (Button) findViewById(R.id.deleteAll);
        btn_update = (Button) findViewById(R.id.update);
        btn_select = (Button) findViewById(R.id.select);

        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_select.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String midStr = mid.getText().toString().trim();
        String nameStr = editName.getText().toString().trim();
        switch (v.getId()){
            case R.id.add:
                mUser = new User(Long.parseLong(midStr), nameStr,1);
                userDao.insert(mUser);//添加一个
                text.setText(mUser.getName());
                break;
            case R.id.delete:
                //根据id删除
                userDao.deleteByKey(Long.parseLong(midStr));
                break;
            case R.id.deleteAll:
//                删除所有数据
                userDao.deleteAll();
                Toast.makeText(this,"deleteAll",Toast.LENGTH_SHORT).show();
                break;
            case R.id.update:
                mUser = new User(Long.parseLong(midStr),nameStr,1);
                userDao.update(mUser);
                break;

            case R.id.select:
                List<User> users = userDao.loadAll();
                String userName = "";
                for (int i = 0; i < users.size(); i++) {
                    userName += users.get(i).getId()+","+users.get(i).getName()+"------/";
                }

                text.setText("查询全部数据==>"+userName);
                break;
        }

    }
}
