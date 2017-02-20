package cc.example.com.okhttp;







import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/14 0014.
 */
public class PostString  {


    public void  post(){

        OkHttpClient client =new OkHttpClient();
        MediaType MEDIA_TYPE_TEXT=MediaType.parse("xml/plain");
        String postBody="Hello World";
        Request request=new Request.Builder().url("http://www.baidu.com").get().build();
        Call call=client.newCall(request);
        try{
            Response response=call.execute();
            String json=response.body().string();
            System.out.println(json);
        }catch (IOException e){
            e.printStackTrace();
        }

    }


//    if (!response.isSuccessful()) {
//        throw new IOException("服务器端错误: " + response);
//    }
//
//    System.out.println(response.body().string());
}
