import { configureStore } from "@reduxjs/toolkit";
import menuSlice from "./slices/menuSlice"
import companySlice from "./slices/companySlice"
import popupSlice from "./slices/popupSlice"
import poSearchSlice from "./slices/poSearchSlice"
import productSlice from "./slices/productSlice"
const store=configureStore({
    reducer:{
        menus:menuSlice,
        cmpny:companySlice,
        popup:popupSlice,
        po:poSearchSlice,
        prdt:productSlice
    }
})

export default store