import React from "react";
import ListExercise from "./ListExercise";

const listExo = require("../listExercise.json")

class PrepareSessionLayout extends React.Component {
    constructor(props) {
        super(props)
        this.handleChoiceExercises = this.handleChoiceExercises.bind(this)
        this.state = {
            listChoice: []
        }
    }

    handleChoiceExercises(listSelectedExercise) {
        this.setState({listChoice: listSelectedExercise})
    }

    render() {
        const listChoice = this.state.listChoice
        return <>
            <div>
                <h2>Liste d&apos;exercices</h2>
                <ListExercise listExo={listExo} goal="Presentation" exoChoice={listChoice} onSelectExo={this.handleChoiceExercises} />
            </div>
            <div>
                <h2>Choix d&apos;exercices pour la session</h2>
                <ListExercise listExo={listChoice} goal="Validation" exoChoice={listChoice}/>
            </div>
        </>
    }
}

export default PrepareSessionLayout;
