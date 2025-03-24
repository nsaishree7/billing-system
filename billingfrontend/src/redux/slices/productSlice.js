import { createSlice } from "@reduxjs/toolkit";

const productSlice=createSlice({
    name:"product-slice",
    //{productHsn:"",productName:"",productPrice:""}
    initialState:{},
    reducers:{
        updateProduct:(state,action)=>{
            console.log(action)
            
            let {hsn,productName,productTax,productQty,partNo,rate}=action.payload
            if(!state.hsn){
                state[hsn]={}
            }
            state[hsn]["productHsn"]=hsn;
            state[hsn]["productName"]=productName;
            state[hsn]["productPrice"]=rate;
            state[hsn]["productTax"]=productTax;
            state[hsn]["productQty"]=productQty;
            state[hsn]["partNo"]=partNo;
            state[hsn]["productNos"]=0;
            state[hsn]["productRate"]=0;
            console.log(state)
        },
        resetProduct:(state)=>{
            return {}
        },
        updateQty:(state,action)=>{
            let {productHsn:hsn,productNos,productRate}=action.payload
            if(!state[hsn])return
            state[hsn]["productNos"]=productNos;
            state[hsn]["productRate"]=productRate;
        },
        deleteProduct:(state,action)=>{
            let {productHsn:hsn}=action.payload
            if(!state[hsn])return
            delete state[hsn];
console.log(state)
        }
    }
})

export const {updateProduct,deleteProduct,updateQty,resetProduct} = productSlice.actions
export default productSlice.reducer