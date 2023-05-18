import React from "react";
import PropTypes from 'prop-types'

export default class ExerciseSession extends React.Component {
    static propTypes = {
        exo: PropTypes.string.isRequired
    }
    constructor(props) {
        super(props)
        this.state = {
            exo: props.exo,
        }
    }

    render() {
        const exercise = this.props.exo
        return <div>
            <div className="name">{exercise.name}</div>
            <div className="weight"><input></input></div>
            <div className="repetition"><input></input></div>
        </div>
    }
}