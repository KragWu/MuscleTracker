import React from "react";
import "./Exercise.css"

export default class ExercisePresented extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            exo: props.exo,
            listExercise: props.listExercise,
            listChoice: props.listChoice,
        }
    }

    muscleIcon(muscle) {
        return <>
        {
            muscle.map(elem => {
                return <label key={elem} className="muscle-icon">{elem}</label>
            })
        }
        </>
    }

    toolsIcon(tools) {
        return <>
        {
            tools.map(elem => {
                return <label key={elem} className="tools-icon">{elem}</label>
            })
        }
        </>
    }

    selectExercise(listExercice, exoSelected, listChoice, event) {
        const element = event.currentTarget
        if (element.checked) {
            listExercice.forEach(elem => {
                if (elem.name == exoSelected) {
                    listChoice.push(elem)
                    let index = listChoice.indexOf(elem)
                    let test = listChoice.at(index)
                    console.log("push : elem : " + elem.name + " - index : " + index + " - test : " + test.name)
                }
            });
        } else {
            listChoice.forEach(elem => {
                if(elem.name == exoSelected) {
                    let index = listChoice.indexOf(elem)
                    let test = listChoice.at(index)
                    console.log("pop: elem : " + elem.name + " - index : " + index + " - test : " + test.name)
                    listChoice.splice(index, 1)
                }
            });
        }
        console.log(listChoice)
    }

    render() {
        const exercise = this.props.exo
        const listExercise = this.props.listExercise
        const listChoice = this.props.listChoice
        return <div className="exercise">
            <div className="selection"><input id={exercise.id_name} type="checkbox" onClick={(e) => this.selectExercise(listExercise, exercise.name, listChoice, e)}/></div>
            <div className="name">{exercise.name}</div>
            <div className="muscle">{this.muscleIcon(exercise.muscle)}</div>
            <div className="tools">{this.toolsIcon(exercise.tools)}</div>
        </div>
    }
}