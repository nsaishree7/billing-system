import { createSlice } from "@reduxjs/toolkit";

const popupSlice=createSlice({
    name:"popup",
    initialState:{showPopup:"",inputType:"",inputValue:""},
    reducers:{
        updateShowPopup:(state,action)=>{
            console.log(action)
            state.showPopup=action.payload.showPopup
            state.inputType=action.payload.inputType
            state.inputValue=action.payload.inputValue
            
        }
    }
})

export const {updateShowPopup} = popupSlice.actions
export default popupSlice.reducer;