import React from "react";

export default class ExerciseChoice extends React.Component {
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
            <div className="mediumWeight">{exercise.mediumWeight}</div>
            <div className="maxWeight">{exercise.maxWeight}</div>
            <div className="averageRepetition">{exercise.averageRepetition}</div>
        </div>
    }
}