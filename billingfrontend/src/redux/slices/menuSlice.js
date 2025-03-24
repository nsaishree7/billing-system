import { createSlice } from "@reduxjs/toolkit";

const getTite=(menu)=>{
    switch(menu){
        case "invoiceGenerate":return "Generate new Invoice";
        case "paymentStatus":return "Check Payment Status";
    }

}

const menuSlice=createSlice({
    name:"menu",
    initialState:{selectedMenu:"invoiceGenerate",title:"Generate new Invoice"},
    reducers:{
        change:(state,action)=>{
            state.selectedMenu=action.payload.menu;
            state.title=getTite(state.selectedMenu)
        }
    }
})

export const {change} =menuSlice.actions
export default menuSlice.reducer