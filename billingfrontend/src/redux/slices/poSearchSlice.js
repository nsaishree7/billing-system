import { createSlice } from "@reduxjs/toolkit";

const poSearchSlice=createSlice({
    name:"po-search-slice",
    initialState:{poNumber:"",poDate:""},
    reducers:{
        updatePo:(state,action)=>{
            console.log(action)
            state.poNumber=action.payload.poNumber;
            state.poDate=action.payload.poDate;
        }
    }
})

export const {updatePo} = poSearchSlice.actions
export default poSearchSlice.reducer