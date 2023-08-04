import React from "react";
import "../Global.css"
import { Navigate } from "react-router-dom";
import { register } from "../services/UserService";

class RegisterLayout extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            register: false,
        }
    }

    handleInscription() {
        const username = document.getElementById("username").value
        const password = document.getElementById("password").value
        register(username, password).then(() => {
            this.setState({register: true})
        }, (reason) => {
            console.log("register KO = " + reason)
            this.setState({register: false})
        })
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
                <button className="connexion buttonLogin alleger" onClick={() => this.handleInscription()} >Enregistrer</button>
            </div>
            {this.state.register && <Navigate to="/login" />}
        </div>
    }
}

export default RegisterLayout;
