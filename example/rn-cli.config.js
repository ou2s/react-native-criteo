const metroBundler = require('metro');

module.exports = {
  getBlacklistRE: function() {
    return blacklist([/[^e]\/node_modules\/react-native\/.*/]);
  }
};
