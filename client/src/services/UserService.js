import { encrypt } from "../security/Cipher";

async function register(login, password) {
    const currentDate = new Date();
    const strCurrentDate = currentDate.toISOString().split('T')[0]
    const cryptedLogin = encrypt(login, strCurrentDate)
    const cryptedPassword = encrypt(password, strCurrentDate)
    const body = {
        "login": cryptedLogin,
        "password": cryptedPassword
    }
    console.log("call User API register")
    const response = await fetch('http://localhost:8080/register', { method: 'POST', mode: "cors", body: JSON.stringify(body), headers: {'Content-Type': 'application/json'}});
    const data = await response.text()
    if (response.status != 201) {
        throw new Error("failed register")
    } else {
        return data
    }
}

async function login(login, password) {
    const currentDate = new Date();
    const strCurrentDate = currentDate.toISOString().split('T')[0]
    const cryptedLogin = encrypt(login, strCurrentDate)
    const cryptedPassword = encrypt(password, strCurrentDate)
    const body = {
        "login": cryptedLogin,
        "password": cryptedPassword
    }
    console.log("call User API login")
    // Can add userid in headers
    const response = await fetch('http://localhost:8080/login', { method: 'POST', mode: "cors", body: JSON.stringify(body), headers: {'Content-Type': 'application/json'}});
    const data = await response.text()
    if (response.status == 400) {
        throw new Error("login Unknown")
    } else if (response.status != 200) {
        throw new Error("error login")
    } else {
        const obj = JSON.parse(data)
        return obj
    }
}

async function authorize(idSession, token) {
    const body = {
        "id": idSession,
        "token": token
    }
    console.log("call User API authorize")
    // Can add userid in headers
    const response = await fetch('http://localhost:8080/authorize', { method: 'POST', mode: "cors", body: JSON.stringify(body), headers: {'Content-Type': 'application/json'}});
    const data = await response.text()
    if (response.status != 200) {
        throw new Error("failed authorize")
    } else {
        return data
    }
}

async function logout(idSession, token) {
    if (idSession == null) {
        return ""
    } else {
        const body = {
            "id": idSession,
            "token": token
        }
        console.log("call User API logout")
        // Can add userid in headers
        const response = await fetch('http://localhost:8080/logout', { method: 'POST', mode: "cors", body: JSON.stringify(body), headers: {'Content-Type': 'application/json'}});
        const data = await response.text()
        if (response.status != 200) {
            throw new Error("failed logout")
        } else {
            return data
        }
    }
}

export {register, login, authorize, logout}
