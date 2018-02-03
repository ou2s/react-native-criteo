import React, { Component } from "react";
import { Text, View, Platform } from "react-native";
import Criteo from "react-native-criteo";

export default class CriteoExample extends Component {
  componentDidMount() {
    Criteo.initCriteoEventService(
      "example.com",
      "FR",
      "fr",
      "test@example.com"
    );
    Criteo.criteoHomeEvent();
  }

  render() {
    return <View style={{ flex: 1, margin: 10, marginTop: 30 }} />;
  }
}
