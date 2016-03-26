package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private PublisherInterstitialAd mInterstitialAd;
    private Button tellJokeButton;
    private boolean mAdIsLoading;
    private Context context;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        context = this.getActivity();

        // Create the InterstitialAd
        mInterstitialAd = new PublisherInterstitialAd(context);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                ((MainActivity)context).tellJoke();
            }

            @Override
            public void onAdLoaded() {
                mAdIsLoading = false;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mAdIsLoading = false;
            }
        });
        if (!mAdIsLoading && !mInterstitialAd.isLoaded()) {
            mAdIsLoading = true;
            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

        tellJokeButton = (Button) root.findViewById(R.id.tellJokeButton);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
