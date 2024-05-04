import React from "react";
import imgPecs from "./assets/pecs.png"
import imgShoulders from "./assets/shoulders.png"
import imgArms from "./assets/arms.png"
import imgLegs from "./assets/legs.png"
import imgBack from "./assets/back.png"
import imgBody from "./assets/body.png"
import imgBanc from "./assets/banc.png"
import imgBarre from "./assets/barre.png"
import imgHaltere from "./assets/haltere.png"
import imgElastic from "./assets/elastic.png"

export function replaceTextByIcon(listChoice) {
    const listResult = []
    listChoice.forEach(element => {
        var newMuscle = <>
            {
            element.muscle.map(musc => {
                switch (musc) {
                    case "Pectoraux": 
                    case "Abdos":
                        return <img key={element.id_name + musc} src={imgPecs} alt={musc} title={musc} />;
                    case "Epaules":
                        return <img key={element.id_name + musc} src={imgShoulders} alt={musc} title={musc} />
                    case "Dos":
                        return <img key={element.id_name + musc} src={imgBack} alt={musc} title={musc} />
                    case "Global":
                        return <img key={element.id_name + musc} src={imgBody} alt={musc} title={musc} />
                    case "Biceps":
                    case "Triceps":
                    case "Avant-Bras":
                        return <img key={element.id_name + musc} src={imgArms} alt={musc} title={musc} />
                    case "Ischios":
                    case "Quadriceps":
                        return <img key={element.id_name + musc} src={imgLegs} alt={musc} title={musc} />
                    default:
                        break;
                }
            })}
        </>

        var newTools = <>
        {
        element.tools.map(tool => {
            switch (tool) {
                case "Banc": 
                    return <img key={element.id_name + tool} src={imgBanc} alt={tool} title={tool} />;
                case "Barre":
                    return <img key={element.id_name + tool} src={imgBarre} alt={tool} title={tool} />
                case "Haltère":
                    return <img key={element.id_name + tool} src={imgHaltere} alt={tool} title={tool} />
                case "Elastic":
                    return <img key={element.id_name + tool} src={imgElastic} alt={tool} title={tool} />
                case "Aucun":
                    return <span key={element.id_name + tool} alt={tool} >Aucun</span>
                default:
                    break;
            }
        })}
        </>

        const newElem = {
            name: element.name,
            tools: newTools,
            muscle: newMuscle,
            id_name: element.id_name,
            id_name_bis: element.id_name_bis
        }
        listResult.push(newElem)
    });
    return listResult
}
