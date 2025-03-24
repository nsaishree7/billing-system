import { createSlice } from "@reduxjs/toolkit";

const companySlice=createSlice({
    name:"company",
    initialState:{gstNo:"",companyName:"",addr:"" },
    reducers:{
        updateCompany:(state,action)=>{
            console.log(action)
            state.gstNo=action.payload.gstNo;
            state.companyName=action.payload.companyName;
            state.addr=action.payload.companyAddress;
        }
    }
})

export const {updateCompany} = companySlice.actions
export default companySlice.reducer;