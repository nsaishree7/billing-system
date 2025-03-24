import Toast from 'react-bootstrap/Toast';
import React from 'react'
import "./toast.css"
import { MdWarning } from 'react-icons/md';
import { Button } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { updateShowPopup } from '../../../redux/slices/popupSlice';
import { COMPANY } from '../../../util/const';


function ToastHover({message,field2,search}) {

    const dispatch =useDispatch();

  return (
    <Toast  show={true}  bg={'danger'} className='text-white toast-pos' style={{zIndex:"1000"}}>
    <Toast.Body>
        <div className='warn'>
        <MdWarning/> 
        </div> 
    <div className='hover'>
    <div className='hover-msg'>{message} doesn' t exist<Button className='p-1 toastbtn' onClick={()=>dispatch(updateShowPopup({showPopup:message,inputType:message,inputValue:search}))}>Create {field2}</Button></div>
    </div>
    </Toast.Body>
  </Toast>
  )
}

export default ToastHover