// defines bridge operations

import { NativeModules, NativeEventEmitter } from 'react-native';
import getValue from './getValue';
import {requestFieldData, removeFieldData} from './requestFieldData';
import initializeSdk, { terminate } from './initializeSdk';
import connectDevice from './connectDevice';
import { start, stop, pause } from './fieldCommands';

const bridge = NativeModules.RNLibLcrModule;
const eventEmitter = new NativeEventEmitter(bridge);

export default {
  getValue: () => getValue(bridge),
  initializeSdk: () => initializeSdk(bridge, eventEmitter),
  terminate: () => terminate(bridge),
  connectDevice: (type, details) => connectDevice(type, details, bridge, eventEmitter),
  start:() => start(bridge, eventEmitter),
  stop:() => stop(bridge, eventEmitter),
  pause:() => pause(bridge, eventEmitter),
  requestFieldData: (fields) => requestFieldData(fields, bridge, eventEmitter),
  removeFieldData: (fields) => removeFieldData(fields, bridge, eventEmitter)
};
