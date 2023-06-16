import React from "react";
import { Navigate } from "react-router-dom";
import ListExercise from "./ListExercise";
import { parseExerciseStringToList } from "./helpers/ExerciseParser";
import "../Global.css"
import { startSession } from "../services/SessionService";

class SessionLayout extends React.Component {
    constructor(props) {
        super(props)
        sessionStorage.removeItem("idSession")
        this.state = {
            listChoice: [],
            statusSession: "NOT_START",
            idSession: undefined,
            canceled: false,
            errorCallService: false
        }
    }

    startSession(idSession) {
        console.log("Start Session : " + idSession)
        if (this.state.idSession == undefined) {
            startSession().then((idSession) => {
                this.setState({idSession: idSession})
                sessionStorage.setItem("idSession", idSession)
                this.setState({statusSession: "STARTED"})
            }, (reason) => {
                console.log(reason)
                this.setState({errorCallService: true})
            })
        } else {
            this.setState({statusSession: "STARTED"})
        }
    }

    pauseSession() {
        this.setState({statusSession: "PAUSE"})
    }

    stopSession() {
        this.setState({canceled: true})
    }

    cancelSession() {
        this.setState({canceled: true})
    }

    render() {
        const listChoice = parseExerciseStringToList(sessionStorage.getItem("listExoChoice"))
        const idSession = this.state.idSession
        return <>
            <div>
                <h2>Liste d&apos;exercices pour la session</h2>
                <div className="centrage">
                    {("NOT_START" == this.state.statusSession || "PAUSE" == this.state.statusSession) && <button id="buttonStartSession" className="buttonSession buttonGreen" onClick={() => this.startSession(idSession)}>&#9658;</button>}
                    {this.state.statusSession == "STARTED" && <button id="buttonPauseSession" className="buttonSession" onClick={() => this.pauseSession()}>&#9208;</button>}
                    {("STARTED" == this.state.statusSession || "PAUSE" == this.state.statusSession) && <button id="buttonStopSession" className="buttonSession buttonRed" onClick={() => this.stopSession()}>&#11036;</button>}
                    {this.state.statusSession == "NOT_START" && <button id="buttonCancelSession" className="buttonSession buttonRed" onClick={() => this.cancelSession()}>&#10006;</button>}
                    {this.state.canceled && <Navigate to="/"/>}
                </div>
                <ListExercise listExo={listChoice} goal="Session" exoChoice={listChoice} statusSession={this.state.statusSession} />
                <h2>Liste d&apos;enregistrement de mouvements</h2>
            </div>
        </>
    }
}

export default SessionLayout;
