import React, { useEffect } from 'react'
import { Toast, ToastContainer } from 'react-bootstrap'

function AlertMsg({AlertMsg,setShow}) {
  useEffect(()=>{
    setTimeout(()=>setShow(false),10000);

    return()=>{setShow(false)}
  },[])
  return (
    <ToastContainer position={"top-center"}>
    <Toast
          className="d-inline-block m-1"
          bg={(AlertMsg?.err==true)?"danger":"success"}
          
          // delay={3000} autohide
        >
          <Toast.Body className={'text-white'} >
            {AlertMsg?.alertMsg}
          </Toast.Body>
        </Toast>
        </ToastContainer>
  )
}

export default AlertMsg