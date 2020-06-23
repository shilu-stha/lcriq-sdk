// defines bridge operations

import { NativeModules, NativeEventEmitter } from 'react-native';
import {requestFieldData, removeFieldData, grossQty, flowRate} from './requestFieldData';
import initializeSdk, { terminate } from './initializeSdk';
import connectDevice from './connectDevice';
import { start, stop, pause } from './fieldCommands';

const bridge = NativeModules.RNLibLcrModule;
const eventEmitter = new NativeEventEmitter(bridge);

export default {
  // Initialize and remove sdk
  initializeSdk: () => initializeSdk(bridge, eventEmitter),
  terminate: () => terminate(bridge),

  // Add meter
  connectDevice: (type, details) => connectDevice(type, details, bridge, eventEmitter),

  // Send commands to meter
  start:() => start(bridge, eventEmitter),
  stop:() => stop(bridge, eventEmitter),
  pause:() => pause(bridge, eventEmitter),

  // Request for field values from the meter
  requestFieldData: (fields) => requestFieldData(fields, bridge, eventEmitter),
  removeFieldData: (fields) => removeFieldData(fields, bridge, eventEmitter),
  getGallons: () => grossQty(eventEmitter),
  getFlowRate: () => flowRate(eventEmitter)
};
