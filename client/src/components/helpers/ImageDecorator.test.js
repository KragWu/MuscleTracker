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
import { replaceTextByIcon } from './ImageDecorator.js'

const listExo = require("../../listExercise.json")

test("Test exercise to inject the images Pecs, Shoulders, Banc and Barre", () => {
    const elemBancBarrePecsShoulder = [listExo[0]]
    const expectedElemBancBarrePecsShoulder = [
        {
            "name": "Développé couché",
            "tools": <><img key="devcouBanc" src={imgBanc} alt="Banc" title="Banc" /><img key="devcouBarre" src={imgBarre} alt="Barre" title="Barre" /></>,
            "muscle": <><img key="devcouPectoraux" src={imgPecs} alt="Pectoraux" title="Pectoraux" /><img key="devcouEpaules" src={imgShoulders} alt="Epaules" title="Epaules" /></>,
            "id_name": "devcou",
            "id_name_bis": ""
        }
    ]
    const result = replaceTextByIcon(elemBancBarrePecsShoulder)
    expect(result[0].name).toEqual(expectedElemBancBarrePecsShoulder[0].name);
    expect(result[0].tools.toString()).toEqual(expectedElemBancBarrePecsShoulder[0].tools.toString());
    expect(result[0].muscle.toString()).toEqual(expectedElemBancBarrePecsShoulder[0].muscle.toString());
    expect(result[0].id_name).toEqual(expectedElemBancBarrePecsShoulder[0].id_name);
    expect(result[0].id_name_bis).toEqual(expectedElemBancBarrePecsShoulder[0].id_name_bis);
});

test("Test exercise to inject the images Legs, Banc", () => {
    const elemBancLegs = [listExo[18]]
    const expectedElemBancLegs = [
        {
            "name": "Leg curl",
            "tools": <><img key="legcurlBanc" src={imgBanc} alt="Banc" title="Banc" /></>,
            "muscle": <><img key="legcurlQuadriceps" src={imgLegs} alt="Quadriceps" title="Quadriceps" /><img key="legcurlIschios" src={imgLegs} alt="Ischios" title="Ischios" /></>,
            "id_name": "legcurl",
            "id_name_bis": ""
        }
    ]
    const result = replaceTextByIcon(elemBancLegs)
    expect(result[0].name).toEqual(expectedElemBancLegs[0].name);
    expect(result[0].tools.toString()).toEqual(expectedElemBancLegs[0].tools.toString());
    expect(result[0].muscle.toString()).toEqual(expectedElemBancLegs[0].muscle.toString());
    expect(result[0].id_name).toEqual(expectedElemBancLegs[0].id_name);
    expect(result[0].id_name_bis).toEqual(expectedElemBancLegs[0].id_name_bis);
});

test("Test exercise to inject the images Body, Aucun", () => {
    const elemPompes = [listExo[7]]
    const expectedElemPompes = [
        {
            "name": "Pompes",
            "tools": <>Aucun</>,
            "muscle": <><img key="pompesGlobal" src={imgBody} alt="Global" title="Global" /></>,
            "id_name": "pompes",
            "id_name_bis": ""
        }
    ]
    const result = replaceTextByIcon(elemPompes)
    expect(result[0].name).toEqual(expectedElemPompes[0].name);
    expect(result[0].tools.toString()).toEqual(expectedElemPompes[0].tools.toString());
    expect(result[0].muscle.toString()).toEqual(expectedElemPompes[0].muscle.toString());
    expect(result[0].id_name).toEqual(expectedElemPompes[0].id_name);
    expect(result[0].id_name_bis).toEqual(expectedElemPompes[0].id_name_bis);
});

test("Test exercise to inject the images Arms, Haltere", () => {
    const elemArmsHalte = [listExo[12]]
    const expectedElemArmsHalte = [
        {
            "name": "Curls aux haltères",
            "tools": <><img key="curlshalteHaltère" src={imgHaltere} alt="Haltère" title="Haltère" /></>,
            "muscle": <><img key="curlshalteBiceps" src={imgArms} alt="Biceps" title="Biceps" /></>,
            "id_name": "curlshalte",
            "id_name_bis": ""
        }
    ]
    const result = replaceTextByIcon(elemArmsHalte)
    expect(result[0].name).toEqual(expectedElemArmsHalte[0].name);
    expect(result[0].tools.toString()).toEqual(expectedElemArmsHalte[0].tools.toString());
    expect(result[0].muscle.toString()).toEqual(expectedElemArmsHalte[0].muscle.toString());
    expect(result[0].id_name).toEqual(expectedElemArmsHalte[0].id_name);
    expect(result[0].id_name_bis).toEqual(expectedElemArmsHalte[0].id_name_bis);
});

test("Test exercise to inject the images Back, Elastic", () => {
    const elemBancLegs = [listExo[8]]
    const expectedElemBancLegs = [
        {
            "name": "Traction",
            "tools": <><img key="tractionElastic" src={imgElastic} alt="Elastic" title="Elastic" /></>,
            "muscle": <><img key="tractionDos" src={imgBack} alt="Dos" title="Dos" /></>,
            "id_name": "traction",
            "id_name_bis": ""
        }
    ]
    const result = replaceTextByIcon(elemBancLegs)
    expect(result[0].name).toEqual(expectedElemBancLegs[0].name);
    expect(result[0].tools.toString()).toEqual(expectedElemBancLegs[0].tools.toString());
    expect(result[0].muscle.toString()).toEqual(expectedElemBancLegs[0].muscle.toString());
    expect(result[0].id_name).toEqual(expectedElemBancLegs[0].id_name);
    expect(result[0].id_name_bis).toEqual(expectedElemBancLegs[0].id_name_bis);
});

test("Test empty exercise", () => {
    const elemEmpty = [{
        "name": "empty",
        "tools": [""],
        "muscle": [""],
        "id_name": "empty",
        "id_name_bis": ""
    }]
    const expectedElemEmpty = [{
        "name": "empty",
        "tools": <></>,
        "muscle": <></>,
        "id_name": "empty",
        "id_name_bis": ""
    }]
    
    const result = replaceTextByIcon(elemEmpty)
    expect(result[0].name).toEqual(expectedElemEmpty[0].name);
    expect(result[0].tools.toString()).toEqual(expectedElemEmpty[0].tools.toString());
    expect(result[0].muscle.toString()).toEqual(expectedElemEmpty[0].muscle.toString());
    expect(result[0].id_name).toEqual(expectedElemEmpty[0].id_name);
    expect(result[0].id_name_bis).toEqual(expectedElemEmpty[0].id_name_bis);
});