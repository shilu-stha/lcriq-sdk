package com.reactlibrary.listeners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.LCRCommunicationException;
import com.liquidcontrols.lcr.iq.sdk.interfaces.DeviceCommunicationListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_COMMUNICATION_STATUS;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_MESSAGE_STATE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.device.InternalEvent;

public class LCRDeviceCommunicationListener implements DeviceCommunicationListener {

  private final Context context;
  private TextView tvLogger;

  public LCRDeviceCommunicationListener(Context context){
    this.context = context;
//    tvLogger = (TextView) ((MainActivity)context).findViewById(R.id.tv_data_logger);
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

  /**
   * Notify when message state is changed between SDK and LCR device
   * NOTE! This listener update very high frequency
   * @param deviceId		Device identification string
   * @param deviceInfo	Device info
   * @param newValue		New value
   * @param oldValue		Old value
   */
  @Override
  public void onMessageStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_MESSAGE_STATE newValue,
    @Nullable LCR_MESSAGE_STATE oldValue) {

  }

  /**
   * Notify when SDK has detect communication error with LCR device.
   *
   * Also notify some important internal SDK errors (example. generated output message size errors).
   *
   * About event trace :
   * - Event trace start when device object get run turn from SDK DeviceRunner Thread
   * - Event list actions is add in key points of SDK and LCR device communicating
   * - Event list is cleared when device object finnish run or onCommunicationStatus error listeners is notified
   *
   * Event trace is not yet fully implemented inside SDK. More events will add recording later on.
   *
   * @param deviceId		Device identification string
   * @param deviceInfo	Device Info
   * @param cause			Special type of exception
   */
  @Override
  public void onCommunicationStatusError(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull LCRCommunicationException cause) {

    Log.e("ERROR_EVENT", "---------------------------------");
    Log.e("ERROR_EVENT", "Error event from : " + deviceId);
    Log.e("ERROR_EVENT", "-------- event data start--------");
    // Print events trace to lead up in error (not complete trace yet)
    Integer lineNumber = 1;
    for(InternalEvent event : cause.getEvents()) {
      // Print events (all type of events)
      Log.e("ERROR_EVENT", String.valueOf(lineNumber++) + " - " + (event.toShortFormat()));
    }
    Log.e("ERROR_EVENT", "--------- event data end ---------");
//    setTextViewLogger("ERROR_EVENT--------- event data end ---------");
  }

  /**
   * Notify when SDK communication status with LCR device is changed
   * !! NOTE !!
   * Not full implemented inside SDK
   * @param deviceId		Device identification string
   * @param deviceInfo	Device info
   * @param newValue		New Value
   * @param oldValue		Old Value
   */
  @Override
  public void onCommunicationStatusChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_COMMUNICATION_STATUS newValue,
    @Nullable LCR_COMMUNICATION_STATUS oldValue) {
  }
}
