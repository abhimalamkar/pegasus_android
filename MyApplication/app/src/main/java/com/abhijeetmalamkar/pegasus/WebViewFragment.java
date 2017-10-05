package com.abhijeetmalamkar.pegasus;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewFragment extends android.webkit.WebViewFragment {

    private String url;
    private WebView webView;
    private Context _context;
    private Close close;
    public static String Tag = "WebViewFragment";

    interface Close{
        void close();
    }

    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance() {
        WebViewFragment fragment = new WebViewFragment();
        fragment.url = "http://www.pegasusbookkeeping.com/";
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        webView = (WebView) super.onCreateView(inflater, container, savedInstanceState);
        return webView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context = context;
        if (context instanceof Close) {
            close = (Close) context;
        }
    }
}