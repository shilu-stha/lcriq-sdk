// Crafts library api, combining into features


import bridgeOperations from '../bridgeOperations';

export default {
  coolFeature: () => coolFeature(bridgeOperations),
  ...bridgeOperations,
};

