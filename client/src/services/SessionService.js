async function callSessionService(name, weight, repetition, id_name, idSession) {
    const body = {
        "name": name,
        "weight": weight,
        "repetition": repetition,
        "idSession": idSession,
        "id_base": id_name,
        "stored": false
    }
    console.log("call Session API with body = " + JSON.stringify(body))
    const response = await fetch('http://localhost:8080/movement', { method: 'POST', mode: "cors", body: JSON.stringify(body), headers: {'Content-Type': 'application/json'}});
    const data = await response.json()
    console.log(data)
}

export default callSessionService;