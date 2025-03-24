import React, { useState } from 'react'
import PaymentStatusTable from './PaymentStatusTable'
import axios from 'axios';
import { INVOICES_URL, Not_PAID_URL } from '../../util/url';
import { Dropdown, Form } from 'react-bootstrap';
import './payment.css';

function PaymentStatus() {
    const [invoices, setInvoices] = useState([])
    const getAllInvoices = () => {
        axios.get(INVOICES_URL)
            .then((data) => setInvoices([...data.data]));
    }
    const getAllNotPaidInvoices = () => {
        axios.get(Not_PAID_URL)
            .then((data) => setInvoices([...data.data]));
    }
    return (
        <div>
            <div className='d-flex align-items-center justify-content-center filterContainer'>
                <Form.Label className='me-2 mb-3'>Filter</Form.Label>
                <Form.Select className='mb-3' onChange={(e)=>{
                console.log(e)
                    if(e.target.value=="all")getAllInvoices();
                    else getAllNotPaidInvoices();
                }}>

                        <option value="all">All Status</option>
                        <option value="notpaid">Not Paid</option>
                </Form.Select>
            </div>
            <div className='table_container'>
            <PaymentStatusTable invoices={invoices} />
            </div>
        </div>
    )
}

export default PaymentStatus