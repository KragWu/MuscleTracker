import React from "react";
import PropTypes from 'prop-types'
import { replaceTextByIcon } from './helpers/ImageDecorator.js'
import "./ExerciseSession.css"

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

    render() {
        const listExo = replaceTextByIcon(this.state.listExo)
        return <div className="exercise">
            {
                listExo.map(element => { 
                    return <div key={element.name} >
                        <div className="titleExoSession">
                            <h4 className="nameExoSession">{element.name}</h4>
                            {element.tools}
                            </div>
                            <div className="weight">
                                <label className="labelExercise">Poids: </label><input className="inputExercise" type="number" /><button className="Plus5" >+5</button>
                            </div>
                            <div className="repetition">
                                <label className="labelExercise">Répétition: </label><input className="inputExercise" type="number" /><button className="Plus5" >+5</button>
                            </div>
                            <button className="validateMove">Add</button>
                        </div>
                    })
                }
            </div>
    }
}
