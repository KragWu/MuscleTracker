import React from "react";
import ListExercise from "./ListExercise";
import { Navigate } from "react-router-dom";
import { parseExerciseListToString } from "./helpers/ExerciseParser";
import "./PrepareSessionLayout.css"

const listExo = require("../listExercise.json")

class PrepareSessionLayout extends React.Component {
    constructor(props) {
        super(props)
        sessionStorage.removeItem("listExoChoice")
        this.handleChoiceExercises = this.handleChoiceExercises.bind(this)
        this.state = {
            listChoice: [],
            submitted: false,
        }
    }

    handleChoiceExercises(listSelectedExercise) {
        this.setState({listChoice: listSelectedExercise})
    }

    handleSubmit = () => {
        const listChoice = this.state.listChoice
        console.log("Submit session : Size of listChoice = " + listChoice.length)
        if (listChoice.length != 0) {
            const strListChoise = parseExerciseListToString(listChoice)
            sessionStorage.setItem("listExoChoice", strListChoise)
            this.setState({submitted: true});
        }
    }

    render() {
        const listChoice = this.state.listChoice
        const submitted = this.state.submitted
        return <>
            <div>
                <h2>Liste d&apos;exercices</h2>
                <ListExercise listExo={listExo} goal="Presentation" exoChoice={listChoice} onSelectExo={this.handleChoiceExercises} />
            </div>
            <div>
                <h2>Choix d&apos;exercices pour la session</h2>
                <ListExercise listExo={listChoice} goal="Validation" exoChoice={listChoice}/>
                <div className="centrage">
                    <button className="validateExo" type="submit" value="Valider" onClick={this.handleSubmit}>Valider</button>
                </div>
            </div>
            {submitted && <Navigate to="/session" />}
        </>
    }
}

export default PrepareSessionLayout;
