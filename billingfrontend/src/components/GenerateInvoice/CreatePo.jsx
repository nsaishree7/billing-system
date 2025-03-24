import React, { useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import { useDispatch, useSelector } from 'react-redux';
import { updateShowPopup } from '../../redux/slices/popupSlice';
import { COMPANY } from '../../util/const';
import { PO_ORDER, SAVE_COMPANY } from '../../util/url';
import axios from 'axios';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";

function CreatePo({showAlertOnAddPopup}) {
    const [show, setShow] = useState(true);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const dispatch = useDispatch()

    useEffect(()=>
    {
        if(!show)
            dispatch(updateShowPopup({showPopup:"",inputType:"",inputValue:""}))

    },[show])

    const {inputValue,inputType} = useSelector((state)=>state.popup)
    const companyState= useSelector((state)=>state.cmpny)

    const[poNumber,setPoNumber] = useState(inputType=="poNumber"?inputValue:"");
    const [poDate,setPoDate] = useState(new Date());

    const addCompany=()=>{
        axios.post(PO_ORDER+`?gstno=${companyState.gstNo}`,{
            poNumber,
            poDate
        })
        .then((data)=>{
            setShow(false);
            showAlertOnAddPopup("Purchase Order Created",false) 
        })
        .catch((err)=>{
            console.log(err)
            setShow(false);
            showAlertOnAddPopup(err?.response?.data?.errorMessage,true)  
        })
    }
  return (
    <>
    {show && <Modal show={true} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title style={{color:'blueviolet'}}>Add purchase Order Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>Purchase Order Number</Form.Label>
              <Form.Control
                placeholder="ABC"
                autoFocus
                onChange={(e)=>setPoNumber(e.target.value)}
                defaultValue={inputType=="poNumber"?inputValue:""}
              />
            </Form.Group>
            <Form.Label>Purchase Order Date</Form.Label>
            <Form.Group className="mb-3" controlId="">
              <DatePicker selected={poDate} onSelect={(date)=>setPoDate(date)}/>
            </Form.Group>
            </Form>

        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Cancel
          </Button>
          <Button variant="primary" onClick={()=>addCompany()}>
            Add PO
          </Button>
        </Modal.Footer>
      </Modal>}
      </>
  )
}

export default CreatePo