import React from "react";
import ListExercise from "./ListExercise";

const listExo = require("../listExercise.json")
const listChoice = []

const PrepareSessionLayout = () => {
    return <>
        <div>
            <h2>Liste d'exercices</h2>
            <ListExercise listExo={listExo} goal="Presentation" exoChoice={listChoice}/>
        </div>
        <div>
            <h2>Choix d'exercices pour la session</h2>
            <ListExercise listExo={listChoice} goal="Validation" exoChoice={[]}/>
        </div>
    </>
};

export default PrepareSessionLayout;
