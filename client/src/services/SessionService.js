async function storeMovement(name, weight, repetition, id_name, idSession) {
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

async function startSession() {
    const body = {}
    console.log("call Session API with body = " + body)
    // Can add userid in headers
    const response = await fetch('http://localhost:8080/startsession', { method: 'POST', mode: "cors", body: body, headers: {'Content-Type': 'application/json'}});
    console.log(response)
    const data = await response.text()
    console.log(data)
    return data    
}

export {storeMovement, startSession}