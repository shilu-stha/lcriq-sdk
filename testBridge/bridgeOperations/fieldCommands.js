const callback = (eventEmitter) => new Promise((resolve, reject) => {
    const listener = eventEmitter.addListener('commandRequest', (response) => {
        const {success} = response;
        resolve(response)
        listener.remove();
    })
})

export const start = (bridge, eventEmitter) => {
    bridge.start();
    return callback(eventEmitter);
};
  
export const stop = (bridge, eventEmitter) => {
    bridge.stop();
    return callback(eventEmitter);
}

export const pause = (bridge, eventEmitter) => {
    bridge.pause();
    return callback(eventEmitter);
}

export const requestData = bridge => bridge.readData();
  