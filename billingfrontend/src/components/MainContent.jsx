import React from 'react'
import "./main.css"
import { Provider } from 'react-redux'
import store from "../redux/menuStore"
import Content from './Content'

function MainContent() {

    return (
        <>
            <div id="modal"></div>
            <div className='container7'>
                <Provider store={store}>
                    <Content />
                </Provider>
            </div>
        </>
    )
}

export default MainContent