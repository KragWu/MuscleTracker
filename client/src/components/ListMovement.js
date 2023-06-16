import React from "react";
import PropTypes from 'prop-types'
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

class ListMovement extends React.Component {
    static propTypes = {
        listMovement: PropTypes.array.isRequired,
    }
    constructor(props) {
        super(props)
        this.state = {
            listMovement: this.props.listMovement
        }
    }

    render() {
        const listMovement = this.state.listMovement
        return <>
            {console.log(listMovement)}
            <DataTable value={listMovement} tableStyle={{ minWidth: '10rem' }}>
            <Column field="name" header="Nom" ></Column>
            <Column field="weight" header="Poids" ></Column>
            <Column field="repetition" header="Repetition" ></Column>
            <Column field="saved" header="Sauvegardé ?" ></Column>
        </DataTable>
            {/*listMovement.map((movement) => {
                return <div key="" className="block">
                <div className="inline nameMove">Nom: {movement.name}</div>
                <div className="inline weightMove">Poids: {movement.weight}</div>
                <div className="inline repetitionMove">Repetition: {movement.repetition}</div>
                {movement.saved == true &&<div className="inline savedMove">&#128427;</div>}
                {movement.saved == false &&<div className="inline notSavedMove" onClick={() => {}}>&#128427;</div>}
                </div>
            })*/}
        </>
    }
}

export default ListMovement;
