package com.reactlibrary.listeners;

import android.content.Context;
import android.widget.TextView;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.interfaces.SwitchStateListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.LCR_SWITCH_STATE;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LCRSwitchStateListener implements SwitchStateListener {
  private final Context context;
  private TextView tvLogger;

  public LCRSwitchStateListener(Context context){
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
   * Event of Switch State changed
   * @param deviceId		Device identification
   * @param deviceInfo	Device info
   * @param newValue		New switch state
   * @param oldValue		Old switch state
   */
  @Override
  public void onSwitchStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable LCR_SWITCH_STATE newValue,
    @Nullable LCR_SWITCH_STATE oldValue) {

    // Make variables for show status (getString formatting don't allow null)
    String newValueText = "(null)";
    if(newValue != null) {
      newValueText = newValue.toString();
    }
//    setTextViewLogger("Switch state : " + oldValue + " -> " + newValue);
  }

}
