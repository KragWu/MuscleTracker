import React from "react";
import "./Exercise.css"
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

export default class ExerciseChoice extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return <DataTable value={this.props.listChoice} dataKey="id_name" tableStyle={{ minWidth: '10rem' }}>
            <Column field="name" header="Nom" className="p-selection-column" ></Column>
            <Column field="muscle" header="Muscle" className="p-selection-column" ></Column>
            <Column field="tools" header="Equipement" className="p-selection-column" ></Column>
        </DataTable>
    }
}