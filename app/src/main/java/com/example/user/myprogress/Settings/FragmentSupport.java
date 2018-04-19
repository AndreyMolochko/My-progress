package com.example.user.myprogress.Settings;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.example.user.myprogress.R;

/**
 * Created by User on 17.03.2018.
 */

public class FragmentSupport extends Fragment implements View.OnClickListener {
    Button buttonSupport;
    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_support,null);
        buttonSupport = (Button)view.findViewById(R.id.buttonContactSupport);
        buttonSupport.setOnClickListener(this);
        mWebView = (WebView) view.findViewById(R.id.webView);
        // включаем поддержку JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки

        return view;
    }

    @Override
    public void onClick(View view) {
        mWebView.loadUrl(getString(R.string.link_support));
    }
}
