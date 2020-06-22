package com.reactlibrary.listeners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.FieldItem;
import com.liquidcontrols.lcr.iq.sdk.RequestField;
import com.liquidcontrols.lcr.iq.sdk.ResponseField;
import com.liquidcontrols.lcr.iq.sdk.interfaces.FieldListener;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.FIELDS.FIELD_REQUEST_STATES;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.FIELDS.UNITS;
import com.liquidcontrols.lcr.iq.sdk.lc.api.constants.LCR.FIELD_WRITE_STATE;
import com.reactlibrary.LCRManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LCRFieldListener implements FieldListener {

  private static String TAG = "lcr-sdk: LCRFieldListener";
  private final Context context;

  /** List for available fields */
  private final List<FieldItem> availableLCRFields = new ArrayList<>();

  /** LCR User fields to get data */
  public static FieldItem grossQty = null;
  public FieldItem flowRate = null;


  public LCRFieldListener(Context context){
    this.context = context;
  }


  /** Helper to look available field by name */
  @Nullable
  private FieldItem findUserFieldByName(@NonNull String name) {
    for(FieldItem item : availableLCRFields) {
      if(item.getFieldName().equals(name)) {
        return item;
      }
    }
    return null;
  }

  /**
   * onFieldInfoChanged event is activated when device field list is available or
   * field list status is changed.
   * Field Data read request can be done only for items what is in field list.
   *
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Device info
   * @param fields		List of available fields
   */
  @Override
  public void onFieldInfoChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @Nullable List<FieldItem> fields) {

    // Clear local list
     availableLCRFields.clear();

    if(fields != null) {
      String logText = "Field info arrived : " + fields.size() + " fields";
      Log.d(TAG, "onFieldInfoChanged: "+logText);

      // Add all list items to local list
      availableLCRFields.addAll(fields);

      // Get field items by name (using helper)
      grossQty = findUserFieldByName("GROSSQTY");
      flowRate = findUserFieldByName("FLOWRATE");
    }
  }

  /**
   * onFieldReadDataChanged event is activated when new data arrived in requested field
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Device info
   * @param responseField	Reply/response field with data
   * @param requestField 	Requested field info
   */
  @Override
  public void onFieldReadDataChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull ResponseField responseField,
    @NonNull RequestField requestField) {

    // Temporary variables for unit information
    String strMeasureUnit = "unknown";
    String strRateBaseUnit = "unknown";

    // For not log items what is displayed in separated fields
    Boolean showInLog = true;

    // Get measure unit
    if(responseField.getUnits().get(UNITS.MEASURE_UNIT) != null) {
      strMeasureUnit = responseField.getUnits().get(UNITS.MEASURE_UNIT).toLowerCase(Locale.getDefault());
    }
    // Get rate base unit (used in flow rate)
    if(responseField.getUnits().get(UNITS.RATE_BASE) != null) {
      strRateBaseUnit = responseField.getUnits().get(UNITS.RATE_BASE).toLowerCase(Locale.getDefault());
    }

    if(requestField.getItemToRequest().equals(grossQty)) {
      // Set logger off for this field
      showInLog = false;
      String grossQty = String.format(
                  Locale.getDefault(),
                  "%s   %s",
                  responseField.getNewValue(),
                  strMeasureUnit);
      LCRManager.getInstance(context).resolvePromise(true, grossQty, "GROSS_QTY");
    }

    if(requestField.getItemToRequest().equals(flowRate)) {
      // Set logger off for this field
      showInLog = false;
      // Format setText string
      String flowRate = String.format(
        Locale.getDefault(),
        "%s   %s/%s",
        responseField.getNewValue(),
        strMeasureUnit,
        strRateBaseUnit);
      LCRManager.getInstance(context).resolvePromise(true, flowRate, "FLOW_RATE");

    }

    if(showInLog) {
      String logText = "Field data arrive : "
        + responseField.getFieldItem().getFieldName()
        + " - " + responseField.getOldValue()
        + " -> "
        + responseField.getNewValue();

      // Logging field data change event
      Log.d(TAG, "onFieldReadDataChanged: "+logText);
    }
  }


  /**
   * Called when field data request run is success (data has received from LCR device)
   * (activated every time when data request has success, even received data values are same)
   * @param deviceId			Device Id
   * @param deviceInfo		Device info
   * @param responseField		Reply field with data
   * @param requestField		Requested field info
   */
  @Override
  public void onFieldDataRequestSuccess(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull ResponseField responseField,
    @NonNull RequestField requestField) {

    // Logging this event with timestamp
    String logString;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    String ts = sdf.format(new Date());


    logString = String.format(Locale.getDefault(),
      "Field data request success %s time : %s" ,requestField.getItemToRequest().getFieldName(), ts);
    Log.d(TAG, "onFieldDataRequestSuccess: "+logString);

    /*
     * NOTE!
     * This event return field data request values (ResponseField), even data has not change
     */

  }

  /**
   * Called when field item data request is failed (Request data from LCR device is failed)
   * @param deviceId		Device Id
   * @param deviceInfo	Device info
   * @param requestField	Requested field info
   * @param cause			Error cause / message
   */
  @Override
  public void onFieldDataRequestFailed(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull RequestField requestField,
    @NonNull Throwable cause) {

    String logString;

    logString = String.format(Locale.getDefault(),
      "Field data request failed %s\nCause : %s"
      ,requestField.getItemToRequest().getFieldName()
      ,cause.getLocalizedMessage());

    // Write log text
    Log.e(TAG, "onFieldDataRequestFailed: "+logString, cause);
  }

  /**
   * Called when field item data request {@link FIELD_REQUEST_STATES state} has changed
   * @param deviceId		Device Id
   * @param deviceInfo		Device info
   * @param requestField	Requested field info
   * @param newValue		New value of {@link FIELD_REQUEST_STATES}
   * @param oldValue		Old value of {@link FIELD_REQUEST_STATES}
   */
  @Override
  public void onFieldDataRequestStateChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull RequestField requestField,
    @NonNull FIELD_REQUEST_STATES newValue,
    @NonNull FIELD_REQUEST_STATES oldValue) {

    // Logging this event with timestamp
    String logString;
    String frs_old="";
    String frs_new="";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.SSS");
    String ts = sdf.format(new Date());

    switch (oldValue) {
      case NONE : frs_old = "NONE"; break;
      case ADD: frs_old = "ADD"; break;
      case ADD_SUCCESS: frs_old = "ADD_SUCCESS"; break;
      case ADD_FAILED: frs_old = "ADD_FAILED"; break;
      case QUEUED: frs_old = "QUEUED"; break;
      case PAUSED : frs_old = "PAUSED"; break;
      case CHECK_RELEVANT_FIELDS : frs_old = "CHECK_RELEVANT_FIELDS"; break;
      case RUNNING : frs_old = "RUNNING"; break;
      case RUN_SUCCESS : frs_old = "RUN_SUCCESS"; break;
      case RUN_FAILED : frs_old = "RUN_FAILED"; break;
      case REMOVED : frs_old = "REMOVED"; break;
      default : frs_old = "UNKNOWN";
    }

    switch (newValue) {
      case NONE : frs_new = "NONE"; break;
      case ADD: frs_new = "ADD"; break;
      case ADD_SUCCESS: frs_new = "ADD_SUCCESS"; break;
      case ADD_FAILED: frs_new = "ADD_FAILED"; break;
      case QUEUED: frs_new = "QUEUED"; break;
      case PAUSED : frs_new = "PAUSED"; break;
      case CHECK_RELEVANT_FIELDS : frs_new = "CHECK_RELEVANT_FIELDS"; break;
      case RUNNING : frs_new = "RUNNING"; break;
      case RUN_SUCCESS : frs_new = "RUN_SUCCESS"; break;
      case RUN_FAILED : frs_new = "RUN_FAILED"; break;
      case REMOVED : frs_new = "REMOVED"; break;
      default : frs_new = "UNKNOWN";
    }

    logString = String.format(Locale.getDefault(),
      "Field data request state change %s %s -> %s time : %s" ,requestField.getItemToRequest().getFieldName(), frs_old, frs_new, ts);

    // Write log text
    Log.d(TAG, "onFieldDataRequestStateChanged: "+logString);
  }

  /**
   * onFieldDataRequestAddSuccess event activated when new field data request add success
   * (activated only one time for each new field data request)
   * @param deviceId			Device Id
   * @param deviceInfo			Device info
   * @param requestField		Original requested field information
   * @param overWriteRequest	<code>true</code> if previous request from same field was replaced
   */
  @Override
  public void onFieldDataRequestAddSuccess(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull RequestField requestField,
    @NonNull Boolean overWriteRequest) {

    Log.d(TAG, "onFieldDataRequestAddSuccess: " + requestField.getItemToRequest().getFieldName());
  }

  /**
   * Called when new field data request task add failed
   * (activated only one time for each new field data request)
   * @param deviceId		Device Id
   * @param deviceInfo		Device info
   * @param requestField	Original requested field information
   * @param cause			Cause of error as throwable
   */
  @Override
  public void onFieldDataRequestAddFailed(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull RequestField requestField,
    @NonNull Throwable cause) {

    Log.e(TAG, "onFieldDataRequestAddFailed: "+ requestField.getItemToRequest().getFieldName(), cause);
  }

  /**
   * onFieldDataRequestRemoved event activated when field data request is removed
   * (activate only one time)
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Device info
   * @param requestField	Original requested field information
   * @param info			Information of remove (why removed)
   */
  @Override
  public void onFieldDataRequestRemoved(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull RequestField requestField,
    @NonNull String info) {

    Log.d(TAG, "onFieldDataRequestRemoved: "+ requestField.getItemToRequest().getFieldName() + " - " + info);
  }

  /**
   * onFieldWriteStatusChanged event is activated when write process status is changed
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Device info
   * @param fieldItem		Field information
   * @param data			Data to write into field
   * @param newValue		New Status
   * @param oldValue		Old Status
   */
  @Override
  public void onFieldWriteStatusChanged(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull FieldItem fieldItem,
    @NonNull String data,
    @Nullable FIELD_WRITE_STATE newValue,
    @Nullable FIELD_WRITE_STATE oldValue) {

    Log.d(TAG, "onFieldWriteStatusChanged: "+ oldValue + " -> " + newValue);
  }

  /**
   * onFieldWriteSuccess activated when write process is success to LCR device.
   * (activate only one time for each write process)
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Device Info
   * @param fieldItem		Field information
   * @param data			Data what is written to field
   */
  @Override
  public void onFieldWriteSuccess(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull FieldItem fieldItem,
    @NonNull String data) {

    Log.d(TAG, "onFieldWriteSuccess: "+ fieldItem.getFieldName());
  }

  /**
   * onFieldWriteFailed activated when field write process has failed
   * (activate only one time for each write process)
   * @param deviceId		Device Id for using multiple devices same time
   * @param deviceInfo	Device Info
   * @param fieldItem		Field info
   * @param data			Data what tried to write to field
   * @param cause			Cause of fail
   */
  @Override
  public void onFieldWriteFailed(
    @NonNull String deviceId,
    @NonNull DeviceInfo deviceInfo,
    @NonNull FieldItem fieldItem,
    @NonNull String data,
    @Nullable Throwable cause) {

    String errorMsg = "(unknown)";
    if(cause != null) {
      errorMsg = cause.getLocalizedMessage();
    }
    if(fieldItem != null) {
      Log.e(TAG, "onFieldWriteFailed: "+ fieldItem.getFieldName() + " Cause : " + errorMsg, cause);
    } else {
      Log.e(TAG, "onFieldWriteFailed: "+ "null fieldItem" + " Cause : " + errorMsg, cause);
    }
  }
}
