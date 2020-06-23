export const requestFieldData = (fields, bridge, eventEmitter) => {
  bridge.requestFieldData(fields);
  return new Promise((resolve, reject) => {
      const listener = eventEmitter.addListener('requestFieldData', (response) => {
        resolve(response);
        listener.remove();
      });
      // Could add a listener for errors here too
    });
}

export const removeFieldData = (fields, bridge, eventEmitter) => {
  bridge.requestFieldData(fields);
  return new Promise((resolve, reject) => {
      const listener = eventEmitter.addListener('removeFieldRequestData', (response) => {
        resolve(response);
        listener.remove();
      });
      // Could add a listener for errors here too
    });
}

export const registerGallonEvent = (callback, eventEmitter) => eventEmitter.addListener('GROSS_QTY', callback);

export const registerFlowRateEvent = (callback, eventEmitter) => eventEmitter.addListener('FLOW_RATE', callback);
