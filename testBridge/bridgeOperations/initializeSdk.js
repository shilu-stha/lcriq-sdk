const initializeSdk = (bridge, eventEmitter) => {
    bridge.initialize();
    return new Promise((resolve, reject) => {
      const listener = eventEmitter.addListener('initializeSdk', (response) => {
        resolve(response);
        listener.remove();
      });
      // Could add a listener for errors here too
    });
  };
  
  export default initializeSdk;
  
  export const terminate = (bridge) => bridge.terminate();