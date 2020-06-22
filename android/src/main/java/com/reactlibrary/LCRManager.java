package com.reactlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.liquidcontrols.lcr.iq.sdk.ConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.LcrSdk;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_COMMAND;
import com.liquidcontrols.lcr.iq.sdk.utils.AsyncCallback;

public class LCRManager {

  private static String TAG = "lcr-sdk: LCRManager";
  private static LCRManager instance;
  private LCR_COMMAND deliveryStatus = LCR_COMMAND.END_DELIVERY;

//  private LCRPrinterStatusListener printerStatusListener;
//  private LCRDeviceStatusListener deviceStatusListener;
//  private LCRSwitchStateListener switchStateListener;
//  private LCRFieldListener fieldListener;
  private LcrSdk lcrSdk;
  private Context context;

  private LCRManager(Context context){
    this.context = context;
  }

  public static LCRManager getInstance(Context context){
    if(instance == null) {
      instance = new LCRManager(context);
    }
    return instance;
  }

  public void initializeSdk() {
    lcrSdk = new LcrSdk(context);
    lcrSdk.init(new AsyncCallback() {
      @Override
      public void onAsyncReturn(@Nullable Throwable error) {
        if (error != null) {
          String strError = "ERROR INIT SDK : " + error.getLocalizedMessage();
          //          setTextViewLogger("SDK : " + strError);
          Log.e(TAG, strError);
        } else {
          Log.d(TAG, "SDK: SDK Init success");
          LCRManager.this.addSDKListeners();
          //          setTextViewLogger("SDK : SDK Init success");
        }
      }
    });
  }

  private void addSDKListeners() {
    if(lcrSdk == null) {
      return;
    }

//    deviceStatusListener = new LCRDeviceStatusListener(context);
//    switchStateListener = new LCRSwitchStateListener(context);
//    printerStatusListener = new LCRPrinterStatusListener(context);
//    fieldListener = new LCRFieldListener(context);
//
//    // Device connection listener
//    lcrSdk.addListener(new LCRDeviceConnectionListener(context));
//    // Field listener
//    lcrSdk.addListener(fieldListener);
//    // Command listener
//    lcrSdk.addListener(new LCRCommandListener(context));
//    // Device status / state
//    lcrSdk.addListener(deviceStatusListener);
//    // Switch state listener
//    lcrSdk.addListener(switchStateListener);
//    // Printer status listener
//    lcrSdk.addListener(printerStatusListener);
//    // Add device communication listener
//    lcrSdk.addListener(new LCRDeviceCommunicationListener(context));
//    // Device add/remove listener
//    lcrSdk.addListener(new LCRDeviceListener(context));
//    // Network status listener (for logging purposes)
//    lcrSdk.addListener(new LCRNetworkConnectionListener(context));

//    setTextViewLogger("SDK : Add listeners");
    Log.d(TAG, "SDK: Add Listeners");
  }

  /**
   * Request SDK to connect to device
   * NOTE: Make sure to request for permission and check bluetooth connect from the app before calling this method
   *
   * */
  public void connectDevice() {
    // Check SDK object (if call this method after close object)
    if(lcrSdk == null) {
      Log.e(TAG, "SDK: SDK not initialized");
      return;
    }

    // Call SDk to make connect
    lcrSdk.connect(ConnectionUtils.getInstance().getDeviceId());
    Log.d(TAG, "SDK: Device Connected");
  }

  /** Request SDK to disconnect from device */
  public void disconnectDevice() {
    // Check SDK object (if call this method after close object)
    if(lcrSdk == null) {
      return;
    }
    // Call SDK to make disconnect command for device
    lcrSdk.disconnect(ConnectionUtils.getInstance().getDeviceId());
    Log.d(TAG, "SDK: Device Disconnected");
  }

  protected void addDevice(String type, ReadableMap additionalDetails){
    if(lcrSdk == null) {
      return;
    }

    ConnectionOptions connectionOptions = null;

    if(type.equals("WIFI")){
      connectionOptions = ConnectionUtils.getInstance().getWifiConnectionOptions(additionalDetails);
      Log.d(TAG, "SDK: Using Wifi connection");
    } else {
      connectionOptions = ConnectionUtils.getInstance().getBluetoothConnectionOptions(additionalDetails);
      Log.d(TAG, "SDK: Using Bluetooth connection");
    }

//    switch (type) {
//      case WIFI:
//        connectionOptions = ConnectionUtils.getInstance().getWifiConnectionOptions(additionalDetails);
//        Log.d(TAG, "SDK: Using Wifi connection");
//        break;
//      case BLUETOOTH:
//        connectionOptions = ConnectionUtils.getInstance().getBluetoothConnectionOptions(additionalDetails);
//        Log.d(TAG, "SDK: Using Bluetooth connection");
//    }

    // Synchronize way to add device (no callback need), using try-catch for error detection
    try {

      lcrSdk.addDevice(
        ConnectionUtils.getInstance().getDeviceInfo(),
        connectionOptions);

      /* !! NOTE !! Device add will be confirmed in DeviceListener */

    } catch (Exception e) {
      // Device add request fail
      String strError = "Device add request failed : " + e.getLocalizedMessage();
      Log.e("Main",strError);
    }
  }

  public void start() {
    if(lcrSdk == null) {
      return;
    }
    deliveryStatus = LCR_COMMAND.RUN;
    // Put command
    lcrSdk.addDeviceCommand(
      ConnectionUtils.getInstance().getDeviceId(),
      deliveryStatus);
  }
  public void pause() {
    if(lcrSdk == null) {
      return;
    }
    deliveryStatus = LCR_COMMAND.PAUSE;
    // Put command
    lcrSdk.addDeviceCommand(
      ConnectionUtils.getInstance().getDeviceId(),
      deliveryStatus);
  }
  public void stop() {
    if(lcrSdk == null) {
      return;
    }
    deliveryStatus = LCR_COMMAND.END_DELIVERY;
    // Put command
    lcrSdk.addDeviceCommand(
      ConnectionUtils.getInstance().getDeviceId(),
      deliveryStatus);
  }
  public void readData() {

  }
  public void reset() {

  }
}
