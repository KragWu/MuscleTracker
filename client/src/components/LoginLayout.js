import React from "react";
import { Divider } from 'primereact/divider';
import "../Global.css"
import { Navigate } from "react-router-dom";
import { authorize, login } from "../services/UserService";

class LoginLayout extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            connected: false,
            inscription: false,
        }
    }

    handleConnexion() {
        const username = document.getElementById("username").value
        const password = document.getElementById("password").value
        login(username, password).then((sessionResult) => {
            console.log("login OK = " + sessionResult)
            authorize(sessionResult.id, sessionResult.token).then(() => {
                console.log("authorize OK")
                sessionStorage.setItem("sessionId", sessionResult.id);
                sessionStorage.setItem("sessionToken", sessionResult.token);
                sessionStorage.setItem("userId", sessionResult.idUser);
                this.setState({connected: true})
            }, (reason) => {
                console.log("authorize KO = " + reason)
                this.setState({connected: false})
            })
        }, (reason) => {
            console.log("login KO = " + reason)
            this.setState({connected: false})
        })
    }

    handleInscription() {
        this.setState({inscription: true})
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
            <div>
                <Divider layout="horizontal" align="center">
                    <b>OU</b>
                </Divider>
            </div>
            <div>
                <button className="inscription buttonLogin alleger" onClick={() => this.handleInscription()} >Inscription</button>
            </div>
            {this.state.connected && <Navigate to="/prepare" />}
            {this.state.inscription && <Navigate to="/register" />}
        </div>
    }
}

export default LoginLayout;
