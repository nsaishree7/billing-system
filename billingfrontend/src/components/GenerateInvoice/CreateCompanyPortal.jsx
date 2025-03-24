import React, { useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';
import { useDispatch, useSelector } from 'react-redux';
import { updateShowPopup } from '../../redux/slices/popupSlice';
import { COMPANY } from '../../util/const';
import { SAVE_COMPANY } from '../../util/url';
import axios from 'axios';
import { createPortal } from 'react-dom';
import { Alert } from 'react-bootstrap';

function CreateCompanyPortal({showAlertOnAddPopup}) {
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

    const[compnayName,setCompnayName] = useState(inputType=="companyName"?inputValue:"");
    const [gstNo,setGstNo] = useState(inputType=="gstNo"?inputValue:"");
    const [addr,setAddr] = useState("");
    const addCompany=()=>{
        axios.post(SAVE_COMPANY,{
            companyName:compnayName,
            gstNo:gstNo,
            companyAddress:addr
        })
        .then((data)=>{
            setShow(false);
            showAlertOnAddPopup("Company Created",false) 
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
          <Modal.Title style={{color:'blueviolet'}}>Add Company Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>Company Name</Form.Label>
              <Form.Control
                placeholder="ABC"
                autoFocus
                onChange={(e)=>setCompnayName(e.target.value)}
                defaultValue={inputType=="companyName"?inputValue:""}
              />
            </Form.Group>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
              <Form.Label>GST Number</Form.Label>
              <Form.Control
                placeholder=""
                autoFocus
                onChange={(e)=>setGstNo(e.target.value)}
                defaultValue={inputType=="gstNo"?inputValue:""}
              />
            </Form.Group>
            
            <Form.Group
              className="mb-3"
              controlId="exampleForm.ControlTextarea1"
            >
              <Form.Label>Company Address</Form.Label>
              <Form.Control as="textarea" rows={3} onChange={(e)=>setAddr(e.target.value)}/>
            </Form.Group>
            
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Cancel
          </Button>
          <Button variant="primary" onClick={()=>addCompany()}>
            Add company
          </Button>
        </Modal.Footer>
      </Modal>}
      </>
  )
}

export default CreateCompanyPortal