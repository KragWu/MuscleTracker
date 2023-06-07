import React from "react";
import ExerciseChoice from "./ExerciseChoice";
import ExercisePresented from "./ExercisePresented";
import PropTypes from 'prop-types'
import ExerciseSession from "./ExerciseSession";

class listExercise extends React.Component {
    static propTypes = {
        listExo: PropTypes.array.isRequired,
        exoChoice: PropTypes.array.isRequired,
        goal: PropTypes.string.isRequired,
        onSelectExo: PropTypes.func
    }
    constructor(props) {
        super(props)
        this.state = {
            listExo: props.listExo,
            listChoice: props.exoChoice,
            goal: props.goal
        }
        this._switchBalise = this._switchBalise.bind(this)
        this.handleSelectExercise = this.handleSelectExercise.bind(this)
    }

    handleSelectExercise(listSelectExo) {
        this.setState({listChoice: listSelectExo}, () => {
            this.props.onSelectExo(listSelectExo)
        })
    }

    _switchBalise(goal, listExo, listChoice) {
        var result = null
        switch (goal) {
            case "Presentation": 
                result = <ExercisePresented listExercise={listExo} listChoice={listChoice} onChoiceExo={this.handleSelectExercise} />
                break;
            case "Validation": 
                result = <ExerciseChoice listChoice={listChoice} />
                break;
            case "Session": 
                result = <ExerciseSession listExo={listExo} />
                break;
            default: 
                result = <ExercisePresented listExercise={listExo} listChoice={listChoice} onChoiceExo={this.handleSelectExercise} />
                break;
        }
        return result

    }

    render() {
        const listExo = this.props.listExo
        const listChoice = this.props.exoChoice
        const goal = this.props.goal
        return <>
            {
                this._switchBalise(goal, listExo, listChoice)  
            }
        </>
    }
}

export default listExercise
