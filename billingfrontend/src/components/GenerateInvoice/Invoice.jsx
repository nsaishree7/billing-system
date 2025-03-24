import React, { useEffect, useState } from 'react'
import CustomSelectSearch from '../comman/customselect/CustomSelectSearch';
import { useDispatch, useSelector } from 'react-redux';
import axios from 'axios';
import { GET_COMPANY, GET_PO_DETAILS, PRODUCTS_URL } from '../../util/url';
import { COMPANY } from '../../util/const';
import { createPortal } from 'react-dom';
import CreateCompanyPortal from './CreateCompanyPortal';
import { Alert, Button, Form } from 'react-bootstrap';
import AlertMsg from '../comman/AlertMsg';
import CreatePo from './CreatePo';
import DatePicker from 'react-datepicker';
import { updatePo } from '../../redux/slices/poSearchSlice';
import ProductRows from './ProductRows';
import CreateProduct from './CreateProduct';
import { getProductDataGST } from '../../util/apiCalls';
import { resetProduct, updateProduct } from '../../redux/slices/productSlice';


function Invoice() {
  const [companies, setCompanies] = useState([]);
  const [pos, setPos] = useState([]);
  const companyState = useSelector((state) => state.cmpny);
  const poState = useSelector((state) => state.po);
  const productState = useSelector((state) => state.prdt);
  const showPopup = useSelector((state) => state.popup)

  const getPoDetails = () => {
    if (companyState.gstNo == "") return;
    axios
      .get(GET_PO_DETAILS + `?gstno=${companyState.gstNo}`)
      .then((data) => {
        let newData = data.data.poNumbers;
        setPos([...newData]);
      })
      .catch((err) => {
        setPos([])
      })
  }
  const getCompnayData = () => {
    axios.get(GET_COMPANY).then((data) => {
      // if(data?.data?.listOfCompanies)
      console.log(data.data.listOfCompanies)
      let newData = data.data.listOfCompanies
      setCompanies([...newData])
    })
  }
  useEffect(() => {
    getCompnayData()
  }, [])

  useEffect(() => {
    if (!(companyState?.gstNo) || companyState.gstNo == "") return
    getPoDetails()
    getProductDataGST()
    resetFromCmpny();
  }, [companyState])


  const dispatch = useDispatch()
  const resetFromCmpny = () => {
    dispatch(updatePo({ poNumber: "", poDate: "" }))
    dispatch(resetProduct())
  }


  const [showAlert, setShowAlert] = useState(false);

  const [alertMsg, setAlertMsg] = useState();
  const showAlertOnAddPopup = (alertMsg, err) => {
    setShowAlert(true);
    if (!err) {
      getCompnayData();
    }
    setAlertMsg({ alertMsg, err });
  }

  const showPopUpOnPoAddPopup = (alertMsg, err) => {
    setShowAlert(true)
    if (!err) getPoDetails()
    setAlertMsg({ alertMsg, err })
  }
  const [products, setProducts] = useState([])
  const getProductDataGST = () => {
    axios.get(PRODUCTS_URL + `?gstNo=${companyState.gstNo}`)
      .then((data) => setProducts([...data.data]))
      .catch(() => setProducts([]))
  }
  const showPopUpOnForProduct = (alertMsg, err) => {
    setShowAlert(true)
    if (!err) getProductDataGST();
    setAlertMsg({ alertMsg, err })
  }

  const handleGenerateBill=()=>{
    let prdts=[];
    Object.keys(productState).map((prdtKey)=>prdts.push(productState[prdtKey]));
    const body={
      "gstNo": companyState.gstNo,
    "poNumber": poState.poNumber,
    // Date poDate;
    "prdts": prdts,
    "invoiceDate": new Date()
    }


  
    axios.post("http://localhost:8080/generate",body,{
      responseType: 'arraybuffer'  // Ensure you're handling the binary response
    })
    .then((response)=>{
      console.log(response)
      console.log(response.data)
      console.log(response.headers)
      const blob = new Blob([response.data], { type: "application/pdf" });
    const url = window.URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;

    // Optional: Get filename from Content-Disposition header
    const contentDisposition = response.headers['content-disposition'];
    let fileName = "invoice.pdf";
    if (contentDisposition) {
      const fileNameMatch = contentDisposition.match(/filename="(.+)"/);
      if (fileNameMatch?.length > 1) {
        fileName = fileNameMatch[1];
      }
    }

    //instaed of redirecting asking anchor tag to download
    link.setAttribute("download", fileName);
    document.body.appendChild(link);
    link.click();
    link.remove();
      setShowAlert(true);
      setAlertMsg({alertMsg:"Successfull Genration of Invoice"});
    })
    .catch((err)=>{
      setShowAlert(true);

      setAlertMsg({alertMsg:"Successfull Genration of Invoice",err});
    })
  }
  return (
    <div>
      {showAlert && <AlertMsg AlertMsg={alertMsg} setShow={setShowAlert} />}
      {(showPopup.showPopup == "companyName" || showPopup.showPopup == "gstNo") && createPortal(<CreateCompanyPortal showAlertOnAddPopup={showAlertOnAddPopup} />, document.getElementById("modal"))}
      {showPopup.showPopup == 'poNumber' && createPortal(<CreatePo showAlertOnAddPopup={showPopUpOnPoAddPopup} />, document.getElementById("modal"))}
      {showPopup.showPopup == 'productName' && createPortal(<CreateProduct showAlertOnAddPopup={showPopUpOnForProduct} />, document.getElementById("modal"))}
      <div>

        <div className='d-flex align-items-start justify-content-evenly mt-3'>
          <div>
            <div>
              <h6 >Company Name</h6>
              <CustomSelectSearch value={companyState.companyName} options={companies} field={"companyName"} />
            </div>
            <div className='margin-top'>
              <h6>Gst No</h6>
              <CustomSelectSearch value={companyState.gstNo} options={companies} field={"gstNo"} />
            </div>
          </div>
          <div className=''>
            <h6>Address</h6>
            <textarea value={companyState["addr"]} readOnly className='w-96 h-32 p-3 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400' />
          </div>
        </div>

        <div className='d-flex align-items-start justify-content-evenly mt-3'>
          <div>
            <div>
              <h6>Purchase Order Number</h6>
              <CustomSelectSearch value={poState.poNumber} options={pos} field={"poNumber"} />
            </div>
          </div>
          <div>
            <div>
              <h6>Purchase Order date</h6>
              <DatePicker selected={poState.poDate} />
            </div>
          </div>
        </div>
        <div className='d-flex align-items-start justify-content-evenly mt-3 flex-column'>
          <ProductRows products={products} />
        </div>
        <Form.Label>Seperate bill on tax</Form.Label>
        <Form.Check onClick={()=>{}}/>

      <div className='d-flex align-items-start justify-content-center'>
        <Button onClick={handleGenerateBill}>Generate Bill</Button>
      </div>
      </div>
    </div>
  )
}

export default Invoice
