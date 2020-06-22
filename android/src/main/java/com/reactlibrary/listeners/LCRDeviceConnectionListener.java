package com.reactlibrary.listeners;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.liquidcontrols.lcr.iq.sdk.BlueToothConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.ConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.WiFiConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.interfaces.DeviceConnectionListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_DEVICE_CONNECTION_STATE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_THREAD_CONNECTION_STATE;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LCRDeviceConnectionListener implements DeviceConnectionListener {

  private final Context context;
  private TextView tvLogger;
  private Button btnConnect;

  private final String deviceId = "LCR.iQ";

  public LCRDeviceConnectionListener(Context context){
    this.context = context;
//    tvLogger = (TextView) ((MainActivity)context).findViewById(R.id.tv_data_logger);
//    btnConnect = (Button) ((MainActivity)context).findViewById(R.id.btnConnect);

    Log.d("Panda", "LCRDeviceConnectionListener"+ context);
  }

//  private void setTextViewLogger(String text) {
//    Logs logs = Logs.getInstance();
//    List<String> dataList = logs.setTextViewLogger(text);
//
//    String textBuffer = "";
//    // Make data to print
//    for (String str : dataList) {
//      textBuffer = textBuffer + str + "\n";
//    }
//    // Print data
//    tvLogger.setText(textBuffer);
//    Log.d("Panda", textBuffer);
//  }

//  /**
//   * Set application TITLE and connection change button status
//   * @param action enum value for set text (in current action)
//   */
//  private void setAppTitle(MainActivity.DEVICE_ACTION action) {
//    switch (action) {
//      case CONNECT:
//        ((Activity)context).setTitle("RNSampleLCRApp : Connected to LCR.iQ");
//        break;
//      case DISCONNECT:
//        ((Activity)context).setTitle("RNSampleLCRApp : Disconnected from LCR.iQ");
//        break;
//      case CONNECTING:
//        ((Activity)context).setTitle("RNSampleLCRApp : Connecting to LCR.iQ");
//        break;
//      case DISCONNECTING:
//        ((Activity)context).setTitle("RNSampleLCRApp : Disconnecting from LCR.iQ");
//        break;
//    }
//  }

//  /** Set User Interface objects in disconnected mode */
//  public void doUIActionsForDeviceDisconnected() {
//    // Set title
//    setAppTitle(MainActivity.DEVICE_ACTION.DISCONNECT);
//    // Enabled connect/disconnect button
//    btnConnect.setEnabled(true);
//    btnConnect.setText("Connect");
//    // Save next command state to button
//    btnConnect.setTag(MainActivity.DEVICE_ACTION.CONNECT);
//    // Reset status text
//    // Disable buttons who needs connection
//    ((MainActivity)context).setEnableStateOfConnectionRequestedButtons(false);
//    // Save next command state to button
//  }

//  /** Set User Interface objects in Connected mode */
//  private void doUIActionsForDeviceConnected() {
//    // Set title
//    setAppTitle(MainActivity.DEVICE_ACTION.CONNECT);
//    // Enabled connect/disconnect button
//    btnConnect.setEnabled(true);
//    btnConnect.setText("Disconnect");
//    btnConnect.setTag(MainActivity.DEVICE_ACTION.DISCONNECT);
//    // Enabled buttons who needs connection
//    ((MainActivity)context).setEnableStateOfConnectionRequestedButtons(true);
//    // Re-set listeners to refresh status data from SDK service
//    ((MainActivity)context).refreshStatusListeners();
//
//  }

//  /** Set User Interface objects in device connection error mode */
//  private void doUIActionsForDeviceError() {
//    // Set title
//    setAppTitle(MainActivity.DEVICE_ACTION.DISCONNECT);
//    // Enabled connect/disconnect button
//    btnConnect.setEnabled(true);
//    btnConnect.setText("Connect");
//    btnConnect.setTag(MainActivity.DEVICE_ACTION.CONNECT);
//    // Disable buttons who needs connection
//    ((MainActivity)context).setEnableStateOfConnectionRequestedButtons(false);
//    // Save next command state to button
//
//  }


  /**
   * Called when connection to LCR device is made with all relevant information.
   * @param deviceId 		Device identification string
   * @param deviceInfo	Device information
   */
  @Override
  public void deviceOnConnect(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo) {

    // Set user interface for connected state
//    doUIActionsForDeviceConnected();

    String logText = "Device on CONNECTED : "
      + deviceId
      + " LCP SDK Address : "
      + deviceInfo.getSdkAddress().toString()
      + " LCP Device Address : "
      + deviceInfo.getDeviceAddress().toString();

//    setTextViewLogger(logText);
    Log.d("DEVICE", logText);
  }
  /**
   * Called when device lost connection
   * @param deviceId		Device identification string
   * @param deviceInfo	Device information
   * @param cause			Reason for connection lost
   */
  @Override
  public void deviceOnDisconnect(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable Throwable cause) {

    // Set user interface for disconnected state
//    doUIActionsForDeviceDisconnected();

    // Log text about disconnecting
    String causeString = "unknown";
    if(cause != null) {
      causeString = (cause.getLocalizedMessage());
    }
//    setTextViewLogger("Device on DISCONNECTED : " + deviceId + " Cause : " + causeString);
  }

  /**
   * Called when device connection enter in error state
   * @param deviceId		Device identification string
   * @param deviceInfo	Device information
   * @param cause			Cause of error
   */
  @Override
  public void deviceOnError(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable Throwable cause) {

    // Set user interface for error connected state
//    doUIActionsForDeviceError();

    String errorMsg = "";
    if(cause != null) {
      errorMsg = (cause.getLocalizedMessage());
    }
//    setTextViewLogger("Device on ERROR : " + deviceId + " Cause : " + errorMsg);
  }


  /**
   * Notify any status change events
   * @param deviceId		Device identification string
   * @param deviceInfo	Device information
   * @param newValue		New State
   * @param oldValue		Old State
   */
  @Override
  public void deviceConnectionStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_DEVICE_CONNECTION_STATE newValue,
    @Nullable LCR_DEVICE_CONNECTION_STATE oldValue) {

//    setTextViewLogger("Device connection state changed : " + oldValue + " -> " + newValue);
  }

  /**
   * Device network status changed
   * @param deviceId			Device identification string
   * @param deviceInfo		Device info
   * @param connectionOptions	Connection info
   * @param newValue			Network new State
   * @param oldValue			Network old state
   */
  @Override
  public void deviceNetworkStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable ConnectionOptions connectionOptions,
    @NonNull LCR_THREAD_CONNECTION_STATE newValue,
    @NonNull LCR_THREAD_CONNECTION_STATE oldValue) {
    String networkAdressData;


    if(connectionOptions != null) {
      if(connectionOptions instanceof BlueToothConnectionOptions) {
        Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: Bluetooth");
      } else if(connectionOptions instanceof WiFiConnectionOptions) {

        Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: WIFI");
      } else {
        Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: Unknown");
      }
    } else {
      Log.d("Panda LCRDeviceConnectionListener", "deviceNetworkStateChanged: Unknown");
    }

//    setTextViewLogger("Network connection state changed : " + oldValue + " -> " + newValue);
  }
}
