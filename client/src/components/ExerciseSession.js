import React from "react";
import PropTypes from 'prop-types'
import { replaceTextByIcon } from './helpers/ImageDecorator.js'

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
        return <div>
            {
                listExo.map(element => { 
                    return <>
                        <h4 className="name">{element.name}</h4>
                        {element.tools}
                        <div className="weight"><label>Poids: </label><input type="number" /></div>
                        <div className="repetition"><label>Répétition: </label><input type="number" /></div>
                        </>
                    })
                }
            </div>
    }
}
