import React from "react";
import ListExercise from "./ListExercise";

const listExo = require("../listExercise.json")

class SessionLayout extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            listChoice: []
        }
    }

    strExoToList(strListExo) {
        let resultListExo = []
        const arrayStrListExo = strListExo.split("/")
        arrayStrListExo.forEach(element => {
            const exoSplit = element.split("-")
            if (exoSplit[0] != "") {
                listExo.forEach(
                    exo => {
                        if(exo.id_name == exoSplit[1]) {
                            resultListExo.push(exo)
                        }
                    }
                )
            }
        });
        return resultListExo;
    }

    render() {
        const listChoice = this.strExoToList(sessionStorage.getItem("listExoChoice"))
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
