# Vungle-SDK-Integration-Task


**Version 6.12.0**

Welcome to Vungle SDK Integration Task. This app is a smaple Adnroid App built for the sole purpose of the SDK integration task given by Vungle.

**Features of the App:**
-------------------------

This App is using the Vungle SDK to LOAD and SHOW the following Ad Types:
1. Banner Ad
2. Interstitial Ad
3. Rewarded Ad
4. MREC Ad

To LOAD and SHOW different types of Ad, two different types of BUTTONs are being used - **Load**, **Show**

**Load Button:**

Load Button looks for the available ad using the placement IDs, configure the ad size (if required) and make the ad available to show. Initially the Load buttons are disabled utill the SDK is successfully initialised. Upon the the successful SDK initialisation, the Load buttons are getting enabled. A success message is displayed once an ad is loaded.

**Show Button:**
 
Show Buttons are used to display the ads. The functionality of the Show buttons are configured in such way that they will only work if the ad was loaded successfully. In case of failed load ad events, clicking the Show buttons will instruct to load the ad beforehand.

**Integration Steps**
--------------------------

* **Adding Repositories and Dependencies:** Required repositories and the dependecies were added to the Project Level and App Level **Gradle** Files.
* **Providing Necessary Permissions:** Required Usage permissions (e.g., Internet Usage) were given in the **AndroidManifest** file.
* **Desiging the Layout:** The Homescreen of the App uses a Linear Layout as a Parent Layout in the **activity_main** file. Within the Parent Layout, three groups of layout is used:
  * Table Layout: This layout holds all the textViews and the buttons in the Homescreen.
  * Linear Layout: The first Linear Layout is used to contain the ViewGroup for the Banner Ad.
  * Linear Layout: The second Linear Layout is used to contain the ViewGroup for the MREC Ad.
* **Initialising the SDK:** In the **MainActivity** file adding the codes to initialise the SDK.
* **Adding OnClickListener for Load and Show Buttons:** The OnClickListener are added in the **MainActivity** file to enable the buttons to load and show ads. 
