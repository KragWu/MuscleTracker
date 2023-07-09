import React from "react";
import "../Global.css"
import { Navigate } from "react-router-dom";

class RegisterLayout extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            connected: false,
        }
    }

    handleConnexion() {
        sessionStorage.setItem("userId", "test");
        this.setState({connected: true})
    }

    render() {
        return <div className="centrage">
            <div>
                <label htmlFor="username" className="alleger">
                    Identifiant
                </label>
                <br />
                <input id="username" type="text" className="alleger"/>
                <br />
                <label htmlFor="password" className="alleger">
                    Mot de passe
                </label>
                <br />
                <input id="password" type="password" className="alleger"/>
                <br />
                <button className="connexion buttonLogin alleger" onClick={() => this.handleConnexion()} >Connexion</button>
            </div>
            {this.state.connected && <Navigate to="/prepare" />}
        </div>
    }
}

export default RegisterLayout;
