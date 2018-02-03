const metroBundler = require('metro');

module.exports = {
  getBlacklistRE: function() {
    return metroBundler.createBlacklist([/[^e]\/node_modules\/react-native\/.*/]);
  }
};