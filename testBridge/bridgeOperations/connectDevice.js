const connectDevice = (type, details, bridge, eventEmitter) => {
    bridge.connectDevice(type, details);
    return new Promise((resolve, reject) => {
      const listener = eventEmitter.addListener('connectDevice', (response) => {
        resolve(response);
        listener.remove();
      });
      // Could add a listener for errors here too
    });
  };
  
export default connectDevice;