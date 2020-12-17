package com.pgmaru.common;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import static com.google.android.gms.ads.MobileAds.initialize;

public class MainActivity extends AppCompatActivity {
  public WebView webView;
  WebAppInterface webAppInterface;

  private static final int RC_UNUSED = 5001;

  public InterstitialAd mInterstitialAd;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_main);
    if (getString(R.string.setting_keep_screen_on).equals("1")) {
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //
    // Admob
    //
    initialize(this);
    mInterstitialAd = new InterstitialAd(this);
    mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_unit_id));
    AdView adView = (AdView)findViewById(R.id.adView);
    AdRequest adRequest = new AdRequest.Builder().build();
    adView.loadAd(adRequest);

    //
    // Webview
    //
    webView = (WebView)findViewById(R.id.webView);
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);

    webAppInterface = new WebAppInterface(this);
    webView.addJavascriptInterface(webAppInterface, "Android");

    // url
    webView.loadUrl(getString(R.string.http_url));
    webView.setWebViewClient(new WebViewClient());
  }

  // Back button activity
  public void onBackPressed() {
    try {
      webAppInterface.showSubMenu();
    } catch(Exception e) {
      super.onBackPressed();
    }

  }

}
