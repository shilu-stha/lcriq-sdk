package com.reactlibrary.listeners;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.interfaces.DeviceStatusListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_DELIVERY_CODE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_DELIVERY_STATUS;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_DEVICE_STATE;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_SECURITY_LEVEL;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LCRDeviceStatusListener implements DeviceStatusListener {

  private final Context context;
  private TextView tvLogger;
  private Button btnStart;
  private Button btnPause;
  private Button btnStop;

  public LCRDeviceStatusListener(Context context){
    this.context = context;
  }

  /**
   * Event is activated when one of {@link LCR_DELIVERY_CODE LCR_DELIVERY_CODE} status is changed.
   * Each delivery code has values of <code>null</code>, <code>true</code> or <code>false</code>
   * @param deviceId	Device identification
   * @param deviceInfo	Device info
   * @param code		Delivery code {@link LCR_DELIVERY_CODE}
   * @param newValue	New value
   * @param oldValue	Old value
   */
  @Override
  public void onDeliveryCodeChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull LCR_DELIVERY_CODE code,
    @Nullable Boolean newValue,
    @Nullable Boolean oldValue) {

    // Check out FLOW_ACTIVE event
//    if(code.equals(LCR_DELIVERY_CODE.FLOW_ACTIVE)) {
//      if(newValue == null) {
//        textViewFlowStateData.setText(R.string.text_state_unknown);
//      } else if(newValue) {
//        textViewFlowStateData.setText(R.string.text_flow_state_on);
//      } else {
//        textViewFlowStateData.setText(R.string.text_flow_state_off);
//      }
//    }
    // Logging delivery code status
//    setTextViewLogger("Delivery code changed : " + code + " " + oldValue + " -> " + newValue);
  }

  /**
   * Event is activated when one of {@link LCR_DELIVERY_STATUS LCR_DELIVERY_STATUS} code is changed.
   * Each delivery status code has values of <code>null</code>, <code>true</code> or <code>false</code>
   * @param deviceId		Device identification
   * @param deviceInfo	Device info
   * @param code			{@link LCR_DELIVERY_STATUS LCR_DELIVERY_STATUS} code
   * @param newValue		New Value
   * @param oldValue		Old Value
   */
  public void onDeliveryStatusChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull LCR_DELIVERY_STATUS code,
    @Nullable Boolean newValue,
    @Nullable Boolean oldValue) {

    // Logging delivery status codes
//    setTextViewLogger("Delivery Status changed : " + code + " " + oldValue + " -> " + newValue);
  }

  /**
   * Event is activated when delivery active state is changed
   * @param deviceId				Device identification
   * @param deviceInfo			Device information
   * @param deliveryActiveState	Delivery active <code>true</code> delivery is active
   */
  @Override
  public void onDeliveryActiveStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull Boolean deliveryActiveState) {

    // Logging delivery active state
//    setTextViewLogger("Delivery active state changed : " + deliveryActiveState);
  }

  /**
   * Event is activated when LCR Device {@link LCR_DEVICE_STATE state} is changed
   * @param deviceId		Device identification
   * @param deviceInfo	Device info
   * @param newValue		New device {@link LCR_DEVICE_STATE state}
   * @param oldValue		Old device {@link LCR_DEVICE_STATE state}
   */
  @Override
  public void onDeviceStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_DEVICE_STATE newValue,
    @Nullable LCR_DEVICE_STATE oldValue) {

//    Log.d("Panda LCRDeviceStatusListener", "onDeviceStateChanged: " + newValue);

    // Set start/pause button by state
//    if (btnStart != null && newValue != null) {
//      // Any of button state change enable it
//      btnStart.setEnabled(true);
//      switch (newValue) {
//        case STATE_RUN:
//          btnPause.setEnabled(true);
//          btnStop.setEnabled(true);
//          btnStart.setEnabled(false);
//          break;
//        default:
//          btnPause.setEnabled(false);
//          btnStop.setEnabled(false);
//          btnStart.setEnabled(true);
//          break;
//      }
//    }
//    // Logging device state
//    setTextViewLogger("Device state changed : " + oldValue + " -> " + newValue);
  }

  /**
   * Event is activated when one of security level {@link LCR_SECURITY_LEVEL code} value is changed.
   * @param deviceId		Device identification
   * @param deviceInfo	Device info
   * @param newValue		New value of current security level code
   * @param oldValue		Old value of current security level code
   */
  public void onSecurityLevelChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_SECURITY_LEVEL newValue,
    @Nullable LCR_SECURITY_LEVEL oldValue) {

    // Logging security level (old security level -> new security level)
//    setTextViewLogger("Security level changed : " + " " + oldValue + " -> " + newValue);
  }

}
