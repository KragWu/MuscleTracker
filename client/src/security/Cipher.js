function encrypt(password, currentDate) {
    let cryptedPassword = ""
    for(let index = 0; index < password.length; index++) {
        let characterEncodeA = password.charCodeAt(index)
        let characterEncodeB = currentDate.charCodeAt(index % currentDate.length)
        let characterEncode = Number(characterEncodeA) + Number(characterEncodeB)
        let characterStrEncode = String.fromCharCode(characterEncode)
        cryptedPassword = cryptedPassword + characterStrEncode
    }
    return cryptedPassword;
}

function decrypt(cryptedPassword, currentDate) {
    let decryptedPassword = ""
    for(let index = 0; index < cryptedPassword.length; index++) {
        let characterDecodeA = cryptedPassword.charCodeAt(index)
        let characterDencodeB = currentDate.charCodeAt(index % currentDate.length)
        let characterDecode = Number(characterDecodeA) - Number(characterDencodeB)
        let characterStrDecode = String.fromCharCode(characterDecode)
        decryptedPassword = decryptedPassword + characterStrDecode
    }
    return decryptedPassword;
}

export {encrypt, decrypt}
