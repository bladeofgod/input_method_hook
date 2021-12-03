package com.example.myinputmethodhook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;

import pw.qlm.inputmethodholder.InputMethodHolder;
import pw.qlm.inputmethodholder.OnInputMethodListener;
import pw.qlm.inputmethodholder.util.ReflectUtil;

public class MainActivity extends AppCompatActivity {

    OnInputMethodListener onInputMethodListener;
    EditText et;
    Button btnHook;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.loadUrl("https://baidu.com");


//        et = (EditText) findViewById(R.id.et);
        btnHook = (Button) findViewById(R.id.btn_init);
//        btnClose = (Button) findViewById(R.id.btn_close_input);
        btnHook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //android.view.inputmethod.InputMethodManager
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                try {
//                    SparseArray<InputMethodManager> aa =(SparseArray<InputMethodManager>) ReflectUtil
//                            .getStaticFiled(imm.getClass(),"sInstanceMap");
//                    Log.e("mainactivity","size is : " + aa.size() );

                    ReflectUtil.invokeStaticMethod(imm.getClass(),"forContextInternal",
                            new Class[]{int.class, Looper.class}, new Object[]{23, MainActivity.this.getMainLooper()});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        onInputMethodListener = new OnInputMethodListener() {
            @Override
            public void onShow(boolean result) {
                Toast.makeText(MainActivity.this, "Show input method! " + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onHide(boolean result) {
                Toast.makeText(MainActivity.this, "Hide input method! " + result, Toast.LENGTH_SHORT).show();
            }
        };

        //InputMethodHolder.registerListener(onInputMethodListener);


//        btnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        });
    }
}