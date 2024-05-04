const listExo = require("../../listExercise.json")

export function parseExerciseStringToList(strListExo) {
    let resultListExo = []
    const arrayStrListExo = strListExo.split("/")
    arrayStrListExo.forEach(element => {
        const exoSplit = element.split("-")
        if (exoSplit[0] != "") {
            listExo.forEach(
                exo => {
                    if(exo.id_name == exoSplit[1]) {
                        resultListExo.push(exo)
                    }
                }
            )
        }
    });
    return resultListExo;
}

export function parseExerciseListToString(listExo) {
    let resultString = ""
    listExo.forEach(element => {
        console.log(element.name)
        resultString = resultString + element.name + "-" + element.id_name + "/" 
    });
    console.log(resultString)
    return resultString;
}
