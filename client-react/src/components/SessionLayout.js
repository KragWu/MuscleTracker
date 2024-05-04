import React from "react";
import ListExercise from "./ListExercise";
import { parseExerciseStringToList } from "./helpers/ExerciseParser";
import "../Global.css"

class SessionLayout extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            listChoice: []
        }
    }

    render() {
        const listChoice = parseExerciseStringToList(sessionStorage.getItem("listExoChoice"))
        return <>
            <div>
                <h2>Liste d&apos;exercices pour la session</h2>
                <ListExercise listExo={listChoice} goal="Session" exoChoice={listChoice}/>
                <h2>Liste d&apos;enregistrement de mouvements</h2>
            </div>
        </>
    }
}

export default SessionLayout;
