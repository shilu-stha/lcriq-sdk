package com.reactlibrary;

import android.support.annotation.NonNull;

import com.facebook.react.bridge.ReadableMap;
import com.liquidcontrols.lcr.iq.sdk.BlueToothConnectionOptions;
import com.liquidcontrols.lcr.iq.sdk.DeviceInfo;
import com.liquidcontrols.lcr.iq.sdk.WiFiConnectionOptions;

public class ConnectionUtils {

  static ConnectionUtils connectionUtils;
  public static ConnectionUtils getInstance() {
    if (connectionUtils == null) {
      synchronized (ConnectionUtils.class) {
        connectionUtils = new ConnectionUtils();
      }
    }
    return connectionUtils;
  }

  /** LCR device LCP protocol address */
  private Integer lcpLCRAddress = 250;

  /** SDK LCP protocol address */
  private final Integer lcpSDKAddress = 20;

  /** Setup for WiFi connection */
  private String defaultWifiIpAddress = "192.168.1.30"; // WiFi Direct
  private Integer defaultWifiPort = 10001;

  /** Setup for Bluetooth connection */
  private String defaultBluetoothName = "IOGEAR GBC232A Serial Adapter";

  /**
   * Unique name for device (any name will do, as long its unique).
   * Sdk use this name to operating with device. Each device must have different name
   * TODO Test when multiple register is to be connected (do we need this to be dynamic?)
   */
  private final String deviceId = "LCR.12345";


  /**
   * Define connection parameters for using WIFI network.
   * NOTE!
   * Wifi must be connected to Liquid Control On The GO WiFi adapter and device where this app is running
   * IP Address and Port are defined inside On The Go Wifi adapter settings
   *
   * @return Connection options for WiFi connection
   * @param additionalDetails
   */
  public WiFiConnectionOptions getWifiConnectionOptions(ReadableMap additionalDetails) {

    String wifiIpAddress = additionalDetails.hasKey("ipAddress") ?
      additionalDetails.getString("ipAddress") : defaultWifiIpAddress;

    Integer wifiPort = additionalDetails.hasKey("port") ?
      additionalDetails.getInt("port") : defaultWifiPort;

    return new WiFiConnectionOptions(
      wifiIpAddress,
      wifiPort);
  }

  /**
   * Define connection parameters for Bluetooth connection.
   * NOTE!
   * Bluetooth pairing must be done with Liquid Control bluetooth adapter and device where this app is running
   * @return Connection options for Bluetooth connection
   * @param additionalDetails
   */
  public BlueToothConnectionOptions getBluetoothConnectionOptions(ReadableMap additionalDetails) {
    String bluetoothPairedName = additionalDetails.hasKey("name") ?
      additionalDetails.getString("name") : defaultBluetoothName;

    return new BlueToothConnectionOptions(
      bluetoothPairedName);
  }

  /**
   * Generate device info for making connection to LCR device.
   * @return	Device Info for connection
   */
  protected DeviceInfo getDeviceInfo() {
    // Making device info with device id
    DeviceInfo returnDeviceInfo = new DeviceInfo(getDeviceId());

    // Set Device LCP address
    returnDeviceInfo.setDeviceAddress(lcpLCRAddress);

    // Set SDK LCP address
    returnDeviceInfo.setSdkAddress(lcpSDKAddress);

    return returnDeviceInfo;
  }

  /**
   * Get device id information
   * @return	Device id as string
   */
  @NonNull
  protected String getDeviceId() {
    return this.deviceId;
  }

}
