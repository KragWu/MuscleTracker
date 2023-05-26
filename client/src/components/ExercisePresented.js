import React from "react";
import "./Exercise.css";
import { DataTable } from 'primereact/datatable';
import { replaceTextByIcon } from './helpers/ImageDecorator.js'
import { Column } from 'primereact/column';
import PropTypes from 'prop-types'

export default class ExercisePresented extends React.Component {
    static propTypes = {
        listExercise: PropTypes.array.isRequired,
        listChoice: PropTypes.array.isRequired,
        onChoiceExo: PropTypes.func
    }
    constructor(props) {
        super(props)
        this.state = {
            listExercise: props.listExercise,
            listChoice: props.listChoice,
        }
        this.handleChoice = this.handleChoice.bind(this)
    }

    handleChoice(e) {
        this.setState({listChoice: e.value}, () => {
            this.props.onChoiceExo(this.state.listChoice)
        })
    } 

    render() {
        const listChoiceWithIcon = replaceTextByIcon(this.state.listExercise)
        return <DataTable value={listChoiceWithIcon} selectionMode={'multiple'} selection={this.state.listChoice} 
        onSelectionChange={(e) => this.handleChoice(e)} dataKey="id_name" tableStyle={{ minWidth: '10rem' }}>
            <Column selectionMode="multiple" className="p-selection-column" ></Column>
            <Column field="name" header="Nom" className="p-selection-column" ></Column>
            <Column field="muscle" header="Muscle" className="p-selection-column" ></Column>
            <Column field="tools" header="Equipement" className="p-selection-column" ></Column>
        </DataTable>
    }
}
