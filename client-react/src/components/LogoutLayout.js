import React from "react";
import "../Global.css"
import { Navigate } from "react-router-dom";
import { logout } from "../services/UserService";

class LogoutLayout extends React.Component {
    constructor(props) {
        super(props)
    }

    handleDeconnexion() {
        const sessionId = sessionStorage.getItem("sessionId")
        const sessionToken = sessionStorage.getItem("sessionToken")
        logout(sessionId, sessionToken).then(result => {
            console.log("logout = " + result)
        }, (reason) => {
            console.log("logout KO = " + reason)
        })
        sessionStorage.removeItem("userId")
        sessionStorage.removeItem("sessionId")
        sessionStorage.removeItem("sessionToken")
        return true
    }

    render() {
        return <div className="centrage">
            {this.handleDeconnexion() && <Navigate to="/login" />}
        </div>
    }
}

export default LogoutLayout;
