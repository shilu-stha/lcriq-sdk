const coolFeature = async (bridgeOperations) => {
  try {
    const value = 'test'; //await bridgeOperations.getValue();
    // Our emitter sends an object by creating a writable map
    // Below we just destructure that object
    const { id } = await bridgeOperations.requestDeviceId();
    return `Device: ${id}, says you are seeing ${value} `;
  } catch (e) {
    throw (new Error(e));
  }
};

export default coolFeature;
