import React from "react";
import PropTypes from 'prop-types'
import { replaceTextByIcon } from './helpers/ImageDecorator.js'
import callSessionService from "../services/SessionService.js";

export default class ExerciseSession extends React.Component {
    static propTypes = {
        listExo: PropTypes.array.isRequired
    }
    constructor(props) {
        super(props)
        this.state = {
            listExo: props.listExo,
        }
    }

    addFive(element, exercise) {
        const value = document.getElementById(element+"-"+exercise.id_name).getElementsByClassName("inputExercise")[0].value
        console.log(value)
        const result = value == "" ? 0 : Number(value)
        document.getElementById(element+"-"+exercise.id_name).getElementsByClassName("inputExercise")[0].value = result + 5
    }

    handleSubmit(element, idSession) {
        const valueWeight = document.getElementById("weight-"+element.id_name).getElementsByClassName("inputExercise")[0].value
        console.log(valueWeight)
        const weight = valueWeight == "" ? 0 : Number(valueWeight)
        const valueRepetition = document.getElementById("repetition-"+element.id_name).getElementsByClassName("inputExercise")[0].value
        console.log(valueRepetition)
        console.log(idSession)
        const repetition = valueRepetition == "" ? 0 : Number(valueRepetition)
        if (repetition == 0 || idSession == undefined) {
            console.log("Not call Session API because weight and repetition not validated")
        } else {
            callSessionService(element.name, weight, repetition, element.id_name, "")
        }
    }

    render() {
        const listExo = replaceTextByIcon(this.state.listExo)
        return <div className="exercise">
            {
                listExo.map(element => { 
                    return <div key={element.name} className="blockExo" >
                        <div className="titleExoSession">
                            <h4 className="nameExoSession">{element.name}</h4>
                            {element.tools}
                            </div>
                            <div id={"weight-"+element.id_name} className="champsExoSession">
                                <label className="labelExercise">Poids: </label><input className="inputExercise" type="number" /><button className="buttonPlus5" onClick={() => this.addFive("weight", element)}>+5</button>
                            </div>
                            <div id={"repetition-"+element.id_name} className="champsExoSession">
                                <label className="labelExercise">Répétition: </label><input className="inputExercise" type="number" /><button className="buttonPlus5" onClick={() => this.addFive("repetition", element)}>+5</button>
                            </div>
                            <div className="validationExercise">
                                <button id={"buttonValidateMove"+element.id_name} className="buttonValidateMove" onClick={() => this.handleSubmit(element)}>Add</button>
                            </div>
                        </div>
                    })
                }
            </div>
    }
}
