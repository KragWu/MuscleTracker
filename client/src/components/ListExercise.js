import React from "react";
import ExerciseChoice from "./ExerciseChoice";
import ExercisePresented from "./ExercisePresented";

class listExercise extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            listExo: props.listExo,
            listChoice: props.exoChoice,
            goal: props.goal
        }
    }

    _switchBalise(goal, listExo, listChoice) {
        var result = null
        switch (goal) {
            case 'Presentation': 
                result = listExo.map(item => {
                    return <div key={item.id_name}>
                        <ExercisePresented exo={item} listExercise={listExo} listChoice={listChoice} />
                    </div>
                })
                break;
            case 'Validation': 
                result = listChoice.map(item => {
                    return <div key={item.id_name}>
                        <ExerciseChoice exo={item} listExercise={listExo} listChoice={listChoice} />
                    </div>
                })
                break;
            default: 
                result = listExo.map(item => {
                    return <div key={item.id_name}>
                        <ExercisePresented exo={item} listExercise={listExo} listChoice={listChoice} />
                    </div>
                })
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