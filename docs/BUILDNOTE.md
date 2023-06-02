# Build Note : Create Muscle Tracker App

## Step 1: Create React App with Webpack

To start this projet, I use these documentations : 
- https://www.educative.io/answers/how-to-create-a-react-application-with-webpack (Tutorial)
- https://webpack.js.org/guides/getting-started/ (Official Doc)

The goal is to avoid using the `create-react-app` command to understand the tools used to create a project.

## Step 2: Prepare Config to split Frontend & Backend

To split the project, I use this documentation  : 
 - https://levelup.gitconnected.com/how-to-simultaneously-run-the-client-and-server-of-your-full-stack-app-in-one-folder-ef5a988d56d7 (Tutorial)

## Step 3: Add Linter 

To add Linter in React Project, I use these documentations: 
 - https://medium.com/@RossWhitehouse/setting-up-eslint-in-react-c20015ef35f7 (Tutorial)
 - https://stackoverflow.com/questions/72780296/warning-react-version-not-specified-in-eslint-plugin-react-settings-while-run (Fix bug ESLint no detect React version)
 - https://blog.logrocket.com/12-essential-eslint-rules-react/ (Guide)
 - https://eslint.org/docs/latest/use/getting-started (Official Doc)

## Step 4: Add StoryBook (Not Mandatory)

To add Storybook in this project that no used `create-react-app`, I used this documentation: 
- https://www.bam.tech/article/use-storybook-react-project

## Step 5: Add Material

To add most popular UI framework, I used this documentation: 
- https://mui.com/material-ui/getting-started/installation/ (Official Doc)

## Step 5(bis): Add PrimeReact

To add most popular UI framework with charts, I used this documentation: 
- https://primereact.org/ (Official Doc)

## Step 6: Add Router

To add router in this project, I used this documentation: 
- https://reactrouter.com/en/main (Official Doc)
- https://github.com/remix-run/react-router/tree/dev/examples/basic/src (Basic Example)

To fix the routing problem, when we write url in browser, I used this documentation: 
- https://ui.dev/react-router-cannot-get-url-refresh
- https://www.copycat.dev/blog/react-router-redirect/

## Step 7: Share data between Components

In your Parent Component, you define a state to share between 2 Child Component 
and a handler (no forget to bind your handler).

```
class Parent extends React.Component {
    constructor(props) {
        super(props)
        this.handler = this.handler.bind(this)
        this.state = {
            dataToShare: []
        }
    }

    handler(data) {
        this.setState({dataToShare: data})
    }

    render () {
        return <></>
    }
}
```

Then you create the Emitter Component, with a state and a handler too.
And in the handler, you update the state with the `setState` method and you send the result in the callback closure of the `setState` method.

```
export default class ChildOne extends React.Component {
    constructor(props) {
        super(props)
        this.handler = this.handler.bind(this)
        this.state = {
            dataToTransmit: this.props.dataParent
        }
    }

    handler(newdata) {
        this.setState({dataToTransmit: newdata}, () => {
            this.props.onChangeFromParent(this.state.dataToTransmit)
        })
    }

    render () {
        return <>
            <InputComponent onChange={this.handler}>
        </>
    }
}
```

In the Second Child Component, you create just a state which recieve the data.

```
export default class ChildTwo extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            dataRecieved: this.props.dataRecieved
        }
    }

    render () {
        return <>
            {this.props.dataRecieved}
        </>
    }
}
```

And now, in the Parent Component, we called the 2 Children

```
class Parent extends React.Component {
    constructor(props) {
        super(props)
        this.handler = this.handler.bind(this)
        this.state = {
            dataToShare: []
        }
    }

    handler(data) {
        this.setState({dataToShare: data})
    }

    render () {
        return <>
            <ChildOne dataParent={dataToShare} onChangeFromParent={this.handler}>
            <ChildTwo dataRecieved={dataToShare}>
        </>
    }
}
```

## Step 8: Fix the encoding showing

To show a checkmark, we do select the concerned element HTML and modify the content like that: 
```
.pi-check::before {
    content: "\2713" !important;
}
```
We use this documentation to find the right code: 
https://outils-javascript.aliasdmc.fr/encodage-caracteres-formulaire/encode-caractere-2713-html-css-js-autre.html

## Step 9: Search the icons

To find the free icons, we use these websites: 
- https://www.flaticon.com/packs/human-body-parts/2
- https://fr.freepik.com/icones-gratuites

## Step 10: Add image in table

Start to import the wanted picture to inject, like this : 
```
import nameVarContentPicture from "./assests/wantedPicture.png"
```

To add the picture in DOM Element, we create a new DOM Element that contains the same values except the label replaced by picture.

```
var newElement = <div id={oldElement.id}>
    {
        <img src={nameVarContentPicture} alt={oldElement.labelToReplaceByImage} title={oldElement.labelToReplaceByImage} />
    }
</>
```

For our case, we replaced a list of labels, we can do the same implement, in applying the method `map` on the list, like this:

```
var newElement = <div id={oldElement.id}>
    {
        oldElement.labelsToReplaceByImage.map(labelToReplaceByImage => {
            switch (labelToReplaceByImage) {
                case "toto": 
                    return <img src={nameVarContentPictureToto} alt={oldElement.labelToReplaceByImage} title={oldElement.labelToReplaceByImage} />
                case "tata":
                    return <img src={nameVarContentPictureTata} alt={oldElement.labelToReplaceByImage} title={oldElement.labelToReplaceByImage} />
                default:
                    break;
            }
        })
    }
</>
```

## Step 11: Fix found errors by ESLinter

To fix the linter error of react/prop-types, we add this in each asked class: 
```
import PropTypes from 'prop-types'

export default class Example extends React.Component {
    static propTypes = {
        variable: PropTypes.string.isRequired,
        listVariable: PropTypes.array.isRequired,
        onFunc: PropTypes.func
    }
}
```

You can read this documentation: https://github.com/jsx-eslint/eslint-plugin-react/blob/master/docs/rules/prop-types.md

To fix the linter error of the function `require` is not defined, we need to add this line in eslint config:
```
module.exports = {
    "env": {
        "node": true
    },
}
```

## Step 12: Write unit tests with Jest

I use this documentation to install and write my first test:
https://jestjs.io/docs/getting-started (Official documentation)

And I use this information to configure the linter with Jest:
https://stackoverflow.com/questions/31629389/how-to-use-eslint-with-jest (Question StackOverflow)

To fix my unit test that compare image, I use this answer read here: 
https://stackoverflow.com/questions/46898638/importing-images-breaks-jest-test (Question StackOverflow)

## Step 13: Pass data between Two Components

### Solution 1: Use Navigate or useNavigate by `react-router-dom`


https://reactrouter.com/en/main/components/navigate (Official Doc)
https://stackoverflow.com/questions/72379695/how-to-navigate-to-another-component-and-pass-data (Question StackOverflow)
https://stackoverflow.com/questions/42173786/react-router-pass-data-when-navigating-programmatically (Long Question StackOverflow)

**NOTE:** Use for serialize data

### Solution 2: Use Link by `react-router-dom`


https://stackoverflow.com/questions/31168014/pass-object-through-link-in-react-router (Question StackOverflow)

**NOTE:** Use for serialize data

### Solution 3: Use sessionStorage


https://developer.mozilla.org/en-US/docs/Web/API/Window/sessionStorage (Official Doc)
https://contactmentor.com/session-storage-react-js/ (Blog)

### Solution 4: Use architecture MVC (Model-View-Controller)


https://www.freakyjolly.com/react-how-to-create-services-and-consume-in-components-with-examples/ (Blog)

## Step 14: Use HTML Element

I use this documentation to know the specific attributes usefull : 
https://developer.mozilla.org/fr/docs/Web/HTML/Element (Official Doc)

## Step 15: Improve the usage of CSS

I use this documentation to improve my usage of CSS: 
https://speakerdeck.com/goetter/ah-tu-peux-faire-ca-en-css-maintenant
This documentation found due to a Devoxx video by Raphael Goetter.

And the speaker created this website and company: 
https://www.alsacreations.com/


