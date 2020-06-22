package com.reactlibrary.listeners;

import android.content.Context;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.interfaces.PrinterStatusListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_PRINTER_STATUS;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.PRINTING_STATE;

import java.util.Locale;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class LCRPrinterStatusListener implements PrinterStatusListener {

  private static String TAG = "lcr-sdk: LCRPrinterStatusListener";
  private final Context context;

  public LCRPrinterStatusListener(Context context){
    this.context = context;
  }

  /**
   * Event is activated when printer status is changed
   * @param DeviceID		Device Id
   * @param deviceInfo	Device info
   * @param statusCode	Printer status code
   * @param newValue		New state for current status code
   * @param oldValue		Old state for current status code
   */
  @Override
  public void onPrinterStatusChanged(
    @NonNull String DeviceID,
    @NonNull DeviceInfo deviceInfo,
    @NonNull LCR_PRINTER_STATUS statusCode,
    @Nullable Boolean newValue,
    @Nullable Boolean oldValue) {

    Log.d(TAG, "onPrinterStatusChanged: "+ statusCode + " " + oldValue + " -> " + newValue);
  }

  /**
   * onPrintStatusChanged Listener is activated when print status for print item is changed.
   * Sta
   * @param DeviceId		Device identification
   * @param deviceInfo	Device information
   * @param workId		Print work identification
   * @param newValue		Print item new status
   * @param oldValue		Print item old status
   */
  @Override
  public void onPrintStatusChanged(
    @NonNull String DeviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull String workId,
    @Nullable PRINTING_STATE newValue,
    @Nullable PRINTING_STATE oldValue) {

    Log.d(TAG, "onPrintStatusChanged: " + oldValue + " -> " + newValue);
  }

  /**
   * onPrintSuccess event is activated when print data is successfully send to LCR device.
   * @param deviceId		Device identification
   * @param deviceInfo	Device information
   * @param workId		Print work identification
   */
  @Override
  public void onPrintSuccess(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull String workId) {

    // Logging print success
    Log.d(TAG, "onPrintSuccess: ");
  }

  /**
   * onPrintFailed event is activated when sending print data to LCR device has failed
   * @param deviceId		Device identification
   * @param deviceInfo	Device information
   * @param workId		Print work identification
   * @param cause			Error cause (note! can be <code>null</code>)
   */
  @Override
  public void onPrintFailed(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull String workId,
    @Nullable Throwable cause) {

    String strCause = "(null)";
    if(cause != null && cause.getMessage() != null) {
      strCause = String.format(
        Locale.getDefault(),
        "LCP Error : %s",
        cause.getMessage());
    }

    Log.e(TAG, "onPrintFailed: "+strCause, cause);
  }
}
