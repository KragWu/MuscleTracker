import React, { Component } from "react";
import { Navigate } from "react-router-dom";

class HistorySessionLayout extends Component {
    constructor(props) {
        super(props)
        let connected = false;
        if (sessionStorage.getItem("userId") != undefined) {
            connected = true;
        }
        this.state = {
            userConnected: connected,
        }
    }
    render() {
        return <div>
            {!this.state.userConnected && <Navigate to="/login" />}
            <h2>History Sessions</h2>
        </div>
    }
}

export default HistorySessionLayout;
