const { parseExerciseStringToList, parseExerciseListToString } = require("./ExerciseParser");

test("Test to parse and unparse empty list exercise", () => {
    const elementString = ""
    const result = parseExerciseStringToList(elementString)
    const initialElement = parseExerciseListToString(result)
    expect(elementString).toEqual(initialElement);
});

test("Test to parse and unparse one exercise in list (Développé couché)", () => {
    const elementString = "Développé couché-devcou/"
    const result = parseExerciseStringToList(elementString)
    const initialElement = parseExerciseListToString(result)
    expect(elementString).toEqual(initialElement);
});

test("Test to parse and unparse with bad pattern", () => {
    const elementString = "devcou"
    const result = parseExerciseStringToList(elementString)
    const initialElement = parseExerciseListToString(result)
    expect("").toEqual(initialElement);
});

test("Test to parse and unparse with bad list", () => {
    const elementString = "-/-"
    const result = parseExerciseStringToList(elementString)
    const initialElement = parseExerciseListToString(result)
    expect("").toEqual(initialElement);
});

test("Test to parse and unparse with bad empty list", () => {
    const elementString = "/"
    const result = parseExerciseStringToList(elementString)
    const initialElement = parseExerciseListToString(result)
    expect("").toEqual(initialElement);
});
