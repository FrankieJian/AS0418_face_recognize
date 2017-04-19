package com.example.g572_528r.as0418_face_recognize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.baidu.aip.face.AipFace;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String APP_ID = "9533086";
    public static final String API_KEY = "scy0t2WavPAhjhfFtgQNU0fs";
    public static final String SECRET_KEY = "6sSiGP0z0xOR5et4KzYlB5WBkQO7t2Bk";
    private Button btnRecognize;
    private FaceView mImageView;
    private AipFace client;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initClient();
        initView();
        initHandler();
    }

    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Rect rect = (Rect) msg.obj;
                mImageView.drawFace(rect);
                return true;
            }
        });
    }

    private void initView() {
        btnRecognize = (Button) findViewById(R.id.Recognize);
        mImageView = (FaceView) findViewById(R.id.tv_pic);

        btnRecognize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceRecognize();
            }
        });
    }

    private void initClient() {
        // 初始化一个FaceClient
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    private void faceRecognize() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.raw.red);
        final byte[] imgByte = getByte(bmp);
        final HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("face_fields", "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities");

        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject res = client.detect(imgByte,paraMap );
                Log.e("MainAcitity", res.toString());
                // left":117,"top":127,"width":207,"height":194
                // 差json解析
                Rect r = new Rect((int)(329/2.5f),(int)(268/3.0f),(int)((329+325)/2.8f),(int)((268+312)/3.0f));
                Message msg = Message.obtain();
                msg.obj = r;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    private byte[] getByte(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }
}
