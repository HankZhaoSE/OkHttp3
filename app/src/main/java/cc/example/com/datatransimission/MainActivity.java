package cc.example.com.datatransimission;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;


import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cc.example.com.utils.CutPicture;
import cc.example.com.utils.OkManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private Button testButton,getJsonButton,button3 ;
    private ImageView testImageView;
    private final static int SUCCESS_SATUS = 1;
    private final static int FAILURE = 0;
    private final static String Tag = MainActivity.class.getSimpleName();



    private OkManager manager;

  private OkHttpClient clients;
    //图片下载的路径
    private String img_path = "http://10.11.177.245:8080/DownLoadUpLoad/UploadDownloadServlet?method=download";
    private String jsonpath= "http://10.11.177.245:8080/HelloJson/ServletJson";

    private Handler handler=new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS_SATUS: byte[] result=(byte[])msg.obj;
 //                   Bitmap bitmap= BitmapFactory.decodeByteArray(result,0,result.length);  //使用裁剪功能将此注销
               // --------------------------------------------------------------------------------
              Bitmap bitmap=new CutPicture().transform(BitmapFactory.decodeByteArray(result,0,result.length));
//               // --------------------------------------------------------------------------
                    testImageView.setImageBitmap(bitmap);
                    System.out.println(result);
                    break;         //得到一个字节数组
                case FAILURE: break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testButton = (Button) findViewById(R.id.test);
        getJsonButton=(Button)findViewById(R.id.getjson);



        testImageView = (ImageView) findViewById(R.id.testImageView);
        button3=(Button)findViewById(R.id.button3);

        final OkHttpClient clients=new OkHttpClient();
        //先构建一个Request的请求,使用的是get请求
        final Request request=new Request.Builder().url(img_path).build();
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            clients.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Message message=handler.obtainMessage();
                    if (response.isSuccessful()){
                        message.what=SUCCESS_SATUS;
                        message.obj=response.body().bytes();
                        handler.sendMessage(message);
                    }else {
                        handler.sendEmptyMessage(FAILURE);
                    }
                }
            });

            }

        });
        //-----------------------------------------------------------------------------------------
        manager=OkManager.getInstance();
        getJsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.asyncJsonStringByURL(jsonpath, new OkManager.Fun1() {
                    @Override
                    public void onResponse(String result) {
                        Log.i(Tag, result);   //获取JSON字符串
                    }
                });
            }
        });
        //-------------------------------------------------------------------------
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map =new HashMap<String, String>();
                map.put("username","123");
                map.put("password","123");
                manager.sendComplexForm("http://192.168.191.1:8080/HelloJson/OkHttpLoginServlet",map, new OkManager.Fun4() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(Tag,jsonObject.toString());
                    }
                });
            }
        });

    }
}
