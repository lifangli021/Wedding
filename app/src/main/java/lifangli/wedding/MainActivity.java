package lifangli.wedding;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wilddog.client.DataSnapshot;
import com.wilddog.client.SyncError;
import com.wilddog.client.SyncReference;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.WilddogSync;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //private String data="";
    private Map<String,Object>  data;
   private List<String> name=new ArrayList<String>();
    private EditText user,tel;
    private Button save;
    private TextView word;
    Runnable run=new Runnable() {
        @Override
        public void run() {
            //Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            // new Thread(run).start();
            WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://1104509763.wilddogio.com").build();
            WilddogApp.initializeApp(MainActivity.this, options);
// 获取 SyncReference 实例
            SyncReference ref = WilddogSync.getInstance().getReference("/wedding/person");
// 创建 Map 对象
            HashMap<String, Object> jone = new HashMap<>();
            jone.put("time", new Date());
            jone.put("name", "lifangli");
            jone.put("tel", "15296802651");
// child() 用来定位到某个节点。
            ref.child("lifangli").setValue(jone);
        }
    };
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://1104509763.wilddogio.com").build();
            WilddogApp.initializeApp(MainActivity.this, options);
// 获取 SyncReference 实例
            SyncReference ref = WilddogSync.getInstance().getReference("/wedding/person");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    data=(Map<String,Object>)dataSnapshot.getValue();
                    for (Map.Entry MapString : data.entrySet()) {
                        name.add(MapString.getKey().toString());
                    }
                }
                @Override
                public void onCancelled(SyncError syncError) {
                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_main);
        user=(EditText)findViewById(R.id.username) ;
        save=(Button)findViewById(R.id.save);
        word=(TextView)findViewById(R.id.textView);
        tel=(EditText)findViewById(R.id.tel);
        //word.getBackground().setAlpha(80);
      //  TelephonyManager phoneMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        //txtPhoneNumber.setText(phoneMgr.getLine1Number()); //txtPhoneNumber是一个EditText 用于显示手机号
        user.setText("李方力");
        new Thread(runnable).start();;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<name.size();i++) {
                    if (user.getText().toString().trim().equals(name.get(i))) {
                        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl("https://1104509763.wilddogio.com").build();
                        WilddogApp.initializeApp(MainActivity.this, options);

                        SyncReference ref = WilddogSync.getInstance().getReference("/wedding/person");

                        HashMap<String, Object> jone = new HashMap<>();
                        jone.put("time", new Date());
                        jone.put("name", user.getText().toString().trim());
                        jone.put("tel", tel.getText().toString());
                        ref.child(user.getText().toString().trim()).setValue(jone);
                        Intent intent=new Intent();
                        intent.setClass(MainActivity.this,Main2Activity.class);
                        intent.putExtra("username",user.getText().toString().trim());
                        startActivity(intent);
                    }
                }

               // Toast.makeText(MainActivity.this,name.get(0)+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
