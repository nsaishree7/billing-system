import axios from 'axios';
import React, { useState } from 'react'
import { Table } from 'react-bootstrap'
import TableRow from './TableRow';

function PaymentStatusTable({invoices}) {

    
    
  return (
    <Table striped="columns">
      <thead>
        <tr>
          <th>Invoice No.</th>
          <th>Date</th>
          <th>Amount</th>
          <th>Company</th>
          <th>Status</th>
          <th>      </th>
        </tr>
      </thead>
      <tbody>
        {
            invoices.length>0 && invoices.map((inv)=><TableRow invoice={inv}/>)
        }
        </tbody>
          </Table>
  )
}

export default PaymentStatusTable