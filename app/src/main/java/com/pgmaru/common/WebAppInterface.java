package com.pgmaru.common;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;

public class WebAppInterface {
    Context mContext;
    MainActivity mMain;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
        mMain = (MainActivity)c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /** AdMob Load InterstitialAd */
    @JavascriptInterface
    public void adMobInterstitialLoad() {
        try {
            mMain.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMain.mInterstitialAd.loadAd(new AdRequest.Builder().build());
                }
            });
        } catch(Exception e) {
            showToast(e.toString());
        }
    }

    /** AdMob Show InterstitialAd */
    @JavascriptInterface
    public void adMobInterstitialShow() {
        try {
            mMain.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                if (mMain.mInterstitialAd.isLoaded()) {
                    mMain.mInterstitialAd.show();
                } else {
                    //showToast("The interstitial wasn't loaded yet.");
                }
                }
            });
        } catch(Exception e) {
            showToast(e.toString());
        }
    }

    /** Exit app from the web page */
    @JavascriptInterface
    public void exitApp() {
        try {
            mMain.finish();
        } catch (Exception e) {
            showToast(e.toString());
        }
    }

    public void showSubMenu() {
        try {
            mMain.webView.loadUrl("javascript:showSubMenu();");
        } catch (Exception e) {
            showToast(e.toString());
        }
    }
}
