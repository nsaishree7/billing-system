import React from 'react'
import CustomSelectSearch from '../comman/customselect/CustomSelectSearch'
import { Button, Form } from 'react-bootstrap'
import { useDispatch, useSelector } from 'react-redux'
import { deleteProduct, updateProduct, updateQty } from '../../redux/slices/productSlice'

function ProductEachRow({ products, prdt }) {
  const productState = useSelector((state) => state.prdt)
  const dispatch  = useDispatch()
  console.log("pro")
  console.log(prdt)

  const filterFunction = (option) => {
    console.log(productState[option.hsn]);
    return !productState[option.hsn]
  }
  const handleDelete=()=>{
    console.log("del")
    console.log(prdt)
    dispatch(deleteProduct(prdt))
  }
  
  const handleQuantityChange=(e)=>{
    let updatedProduct={...prdt};
    let qty=e.target.value,tax=prdt?.productTax??1,price=prdt?.productPrice;
    console.log(updatedProduct)
    updatedProduct["productNos"]=qty;
    updatedProduct["productRate"] =(qty*price) +((tax/100)*(qty*price));
    dispatch(updateQty(updatedProduct))
  }
  return (
    <div className='d-flex align-items-center justify-content-evenly mt-3' style={{ width: "100%" }}>
      <div>
        <Form.Label>HSN</Form.Label>
        <CustomSelectSearch value={prdt ? prdt.productHsn : ""} options={products} field={"hsn"} filter={filterFunction} />
      </div>
      <div>
        <Form.Label>Product Name</Form.Label>
        <CustomSelectSearch value={prdt ? prdt.productName : ""} options={products} field={"productName"} filter={filterFunction} />
      </div>
      <div style={{ width: "10%" }}>
        <Form.Label>Price</Form.Label>
        <Form.Control type='text' readOnly value={prdt?.productPrice} />
      </div>
      <div style={{ width: "10%" }}>
        <Form.Label>Unit</Form.Label>
        <Form.Control type='text' readOnly value={prdt?.productQty} />
      </div>  
      <div style={{ width: "10%" }}>
        <Form.Label >Product Tax</Form.Label>
        <Form.Control type='text' value={prdt?.productTax}/>
      </div>
      <div style={{ width: "10%" }}>
        <Form.Label >Product Qty</Form.Label>
        <Form.Control type='text' onChange={(e)=>handleQuantityChange(e)}/>
      </div>
      <div style={{ width: "15%" }}>
        <Form.Label >Amount</Form.Label>
        <Form.Control type='text' readOnly value={prdt?.productRate}/>
      </div>
      <Button className='mt-3' onClick={handleDelete} disabled={!prdt}>
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
          <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z" />
          <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z" />
        </svg>
      </Button>
    </div>
  )
}

export default ProductEachRow