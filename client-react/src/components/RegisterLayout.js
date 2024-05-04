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
        let usernameOK = true
        if (username == undefined || username == "") {
            usernameOK = false
            document.getElementById("identifiantObligatoire").hidden = false
            document.getElementById("erreurInscription").hidden = true
        } else {
            document.getElementById("identifiantObligatoire").hidden = true
            document.getElementById("erreurInscription").hidden = true
        }
        const password = document.getElementById("password").value
        let passwordOK = true
        if (password == undefined || password == "") {
            passwordOK = false
            document.getElementById("passwordObligatoire").hidden = false
            document.getElementById("erreurInscription").hidden = true
        } else {
            document.getElementById("passwordObligatoire").hidden = true
            document.getElementById("erreurInscription").hidden = true
        }
        usernameOK && passwordOK && register(username, password).then(() => {
            this.setState({register: true})
        }, (reason) => {
            console.log("register KO = " + reason)
            document.getElementById("erreurInscription").hidden = false
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
                <div id="identifiantObligatoire" className="messageErreur" hidden>
                    <label>Identifiant obligatoire</label>
                    <br />
                </div>
                <label htmlFor="password" className="alleger">
                    Mot de passe
                </label>
                <br />
                <input id="password" type="password" className="alleger"/>
                <br />
                <div id="passwordObligatoire" className="messageErreur" hidden>
                    <label>Mot de passe obligatoire</label>
                    <br />
                </div>
                <div id="erreurInscription" className="messageErreur" hidden>
                    <label>Inscription indisponible</label>
                    <br />
                </div>
                <button className="connexion buttonLogin alleger" onClick={() => this.handleInscription()} >Enregistrer</button>
            </div>
            {this.state.register && <Navigate to="/login" />}
        </div>
    }
}

export default RegisterLayout;
