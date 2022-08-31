package com.example.vungle_sdk_integration;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vungle.warren.BannerAdConfig;
import com.vungle.warren.Vungle;
import com.vungle.warren.AdConfig;              // Custom ad configurations
import com.vungle.warren.InitCallback;          // Initialization callback
import com.vungle.warren.LoadAdCallback;        // Load ad callback
import com.vungle.warren.PlayAdCallback;        // Play ad callback
//import com.vungle.warren.VungleNativeAd;        // MREC ad
import com.vungle.warren.Banners;               // Banner ad
import com.vungle.warren.VungleBanner;          // Banner ad
import com.vungle.warren.Vungle.Consent;        // GDPR consent
import com.vungle.warren.VungleSettings;        // Minimum disk space
import com.vungle.warren.error.VungleException;  // onError message

public class MainActivity extends AppCompatActivity {
    // Load and Show Button Declaration
    Button loadBannerAdButton, loadInterstitialAdButton, loadRewardedAdButton, loadMrecAdButton;
    Button showBannerAd, showInterstitialAd, showRewardedAd, showMrecAd;

    // ViewGroup Declaration
    ViewGroup bannerViewgroup, mrecViewgroup;

    // Banner Object Declaration
    VungleBanner vungleBanner;

    // Banner AdConfig Object Declaration
    BannerAdConfig bannerAdConfig;

    // Boolean Variables to Check the Ad Load Status
    boolean bannerLoaded, interStitialLoaded, rewardedLoaded, mrecLoaded;

    // String Variable to Assign AdType
    String AdType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting Up Custom Logo on the App Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.vungle); // Loading the Logo
        getSupportActionBar().setDisplayUseLogoEnabled(true); // Enabling Logo Display
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Disabling Title Display


        AdType = ""; //Declaring empty string variable to further check and assign the ad type

        //Initialising the BOOLEAN variables to check in the LOAD OnClickListeners
        bannerLoaded = false;
        interStitialLoaded = false;
        rewardedLoaded = false;
        mrecLoaded = false;

        //Initialising the Load Buttons
        loadBannerAdButton = findViewById(R.id.loadBannerAdButton);
        loadInterstitialAdButton = findViewById(R.id.loadInterstitialAdButton);
        loadRewardedAdButton = findViewById(R.id.loadRewardedAdButton);
        loadMrecAdButton = findViewById(R.id.loadMrecAdButton);

        //Initialising the Show Buttons
        showBannerAd = findViewById(R.id.showBannerAdButton);
        showInterstitialAd = findViewById(R.id.showInterstitialAdButton);
        showRewardedAd = findViewById(R.id.showRewardedAdButton);
        showMrecAd = findViewById(R.id.showMrecAd);

        // Initially Disabling the Load Buttons; if the SDK is not initialised successfully, the Load Buttons will remain Disabled
        loadButtonInit(false);

        //Initialising the View group
        bannerViewgroup = (LinearLayout) findViewById(R.id.bannerViewgroup);
        mrecViewgroup = (LinearLayout) findViewById(R.id.mrecViewgroup);

        //Setting the initial visibility of the View group
        bannerViewgroup.setVisibility(View.GONE);
        mrecViewgroup.setVisibility(View.GONE);



        /* *****************************
         *
         *  Vungle SDK Initialisation
         *
         ***************************** */

        Vungle.init("630771d86b7d588b436cfbed", getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                // Showing Toast on successful initialisation of the SDK
                Toast.makeText(MainActivity.this, "Vungle SDK Initialised Successfully", Toast.LENGTH_SHORT).show();
                loadButtonInit(true); // Enabling the Load Buttons by calling the loadButtonInit Method
            }

            @Override
            public void onError(VungleException exception) {
                // Showing Toast on failed initialisation of the SDK
                Toast.makeText(MainActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAutoCacheAdAvailable(String placementId) {
                // Ad has become available to play for a cache optimized placement
            }
        });

        /* **************
        *
        *  BANNER AD
        *
        ************** */

        //OnClickListener for Load Banner Ad Button
        loadBannerAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdType = "Banner"; //Assigning AdType as Banner
                bannerAdConfig = new BannerAdConfig(); //Defining object for Banner Ad Config
                bannerAdConfig.setAdSize(AdConfig.AdSize.BANNER); //Setting the Banner Ad Size
                Banners.loadBanner("BANNER_AD-5499430", bannerAdConfig, vungleLoadAdCallback); //Loading Banner Ad using the placement ID, defined size of the Banner Ad, calling the LoadAd Callback

            }
        });

        //OnClickListener for Show Banner Ad Button
        showBannerAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking if the Banner Ad was Loaded Successfully
                if(bannerLoaded) {
                    //Checking if the Banner Ad is Playable
                    if (Banners.canPlayAd("BANNER_AD-5499430", bannerAdConfig.getAdSize())) {
                        vungleBanner = Banners.getBanner("BANNER_AD-5499430", AdConfig.AdSize.BANNER, vunglePlayAdCallback); // Loading the Banner Ad into the Banner Ad Object
                        bannerViewgroup.removeAllViews(); //Removing all other views
                        bannerViewgroup.addView(vungleBanner); //Adding the Banner Object in the ViewGroup
                        bannerViewgroup.setVisibility(View.VISIBLE); //Setting the visibility of the ViewGroup
                    }
                }else{
                    // Showing Toast if the Banner Ad was not Loaded
                    Toast.makeText(MainActivity.this, "Banner Ad Was Not Loaded, Please Load a Banner Ad First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* ***********************
         *
         *  INTERSTITIAL AD
         *
         *********************** */

        // OnClickListener for Load Interstitial Ad Button
        loadInterstitialAdButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AdType = "Interstitial"; // Assigning AdType as Interstitial
               Vungle.loadAd("INT_AD-4652145", vungleLoadAdCallback); // Loading Interstitial Ad using the Placement ID, calling the LoadAdCallback
           }
        });

        // OnClickListener for Show Interstitial Ad Button
        showInterstitialAd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Checking if the Interstitial Ad was Loaded Successfully
               if(interStitialLoaded){
                   showAdvertisement("INT_AD-4652145"); // Showing Interstitial Ad by Calling the showAdvertisement Method
               }else{
                   // Showing Toast if the Interstitial Ad was not Loaded
                   Toast.makeText(MainActivity.this, "Interstitial Ad Was Not Loaded, Please Load a Interstitial Ad First!", Toast.LENGTH_SHORT).show();
               }
           }
        });

        /* ***********************
         *
         *  REWARDED AD
         *
         *********************** */

        // OnClickListener for Load Rewarded Ad Button
        loadRewardedAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdType = "Rewarded"; // Assigning AdType as Rewarded
                Vungle.loadAd("REWARD_AD-8794922", vungleLoadAdCallback); // Loading Rewarded Ad using the Placement ID, calling the LoadAdCallback
            }
        });

        // OnClickListener for Show Rewarded Ad Button
        showRewardedAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking if the Rewarded Ad was Loaded Successfully
                if(rewardedLoaded){
                    showAdvertisement("REWARD_AD-8794922"); // Showing Rewarded Ad by Calling the showAdvertisement Method
                }else{
                    // Showing Toast if the Rewarded Ad was not Loaded
                    Toast.makeText(MainActivity.this, "Rewarded Ad Was Not Loaded, Please Load a Rewarded Ad First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* **************
         *
         *  MREC AD
         *
         ************** */

        // OnClickListener for Load MREC Ad Button
        loadMrecAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdType = "MREC"; // Assigning AdType as MREC
                bannerAdConfig = new BannerAdConfig(); // Defining object for MREC Ad Config
                bannerAdConfig.setAdSize(AdConfig.AdSize.VUNGLE_MREC); // Setting the Size of MREC Ad
                Banners.loadBanner("MREC_AD-8292185", bannerAdConfig, vungleLoadAdCallback); // Loading MREC Ad using the Placement ID, calling the LoadAdCallback

            }
        });


        //OnClickListener for Show MREC Ad Button
        showMrecAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Checking if the MREC Ad was Loaded Successfully
                if(mrecLoaded) {
                    // Checking if the MREC Ad is Playable
                    if (Banners.canPlayAd("MREC_AD-8292185", bannerAdConfig.getAdSize())) {
                        vungleBanner = Banners.getBanner("MREC_AD-8292185", AdConfig.AdSize.VUNGLE_MREC, vunglePlayAdCallback); // // Loading the MREC Ad into the Banner Ad Object
                        mrecViewgroup.removeAllViews(); //Removing all other views
                        mrecViewgroup.addView(vungleBanner); //Adding the Banner Object in the ViewGroup
                        mrecViewgroup.setVisibility(View.VISIBLE); //Setting the visibility of the ViewGroup
                    }
                }else{
                    // Showing Toast if the MREC Ad was not Loaded
                    Toast.makeText(MainActivity.this, "MREC Ad Was Not Loaded, Please Load a MREC Ad First ;) ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Setting up the Status of the Load Buttons (enabled/disabled)
    private void loadButtonInit(boolean status){
        loadMrecAdButton.setEnabled(status);
        loadRewardedAdButton.setEnabled(status);
        loadBannerAdButton.setEnabled(status);
        loadInterstitialAdButton.setEnabled(status);
    }

    // showAdvertisement method Plays the Interstitial/Rewarded Ad
    private void showAdvertisement(String Placement_Id){
        // Checking if the Ad is Playable
        if(Vungle.canPlayAd(Placement_Id)){
            Vungle.playAd(Placement_Id, null, vunglePlayAdCallback); // Playing the Ad
        }
    }

    // Using this LoadAdCallback object to manipulate the Boolean Variables on Successful Ad Load Event
    private  LoadAdCallback vungleLoadAdCallback = new LoadAdCallback() {
        @Override
        public void onAdLoad(String id) {
            Toast.makeText(MainActivity.this, AdType+" Loaded Successfully", Toast.LENGTH_SHORT).show();
            if(AdType == "Banner")
                bannerLoaded = true;
            else if(AdType == "Interstitial")
                interStitialLoaded = true;
            else if(AdType == "Rewarded")
                rewardedLoaded = true;
            else if(AdType == "MREC")
                mrecLoaded = true;
        }

        @Override
        public void onError(String id, VungleException exception) {
            Toast.makeText(MainActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    };



    private final PlayAdCallback vunglePlayAdCallback = new PlayAdCallback() {
        @Override
        public void onAdStart(String id) {
            // Ad experience started
            Toast.makeText(MainActivity.this, AdType+" Started Successfully!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdEnd(String placementId, boolean completed, boolean isCTAClicked) {

        }

        @Override
        public void onAdViewed(String id) {
            // Ad has rendered
        }

        @Override
        public void onAdEnd(String id) {
            // Ad experience ended
        }

        @Override
        public void onAdClick(String id) {
            // User clicked on ad
        }

        @Override
        public void onAdRewarded(String placementId) {
            Toast.makeText(MainActivity.this, "Reward Faiso", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAdLeftApplication(String id) {
            // User has left app during an ad experience
        }

        @Override
        public void creativeId(String creativeId) {
            // Vungle creative ID to be displayed
        }

        @Override
        public void onError(String id, VungleException exception) {
            // Ad failed to play
        }
    };


    // Destroying Ad Object
    @Override
    protected void onDestroy() {
        vungleBanner.destroyAd();
        super.onDestroy();
    }
}