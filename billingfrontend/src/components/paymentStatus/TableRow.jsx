import React from 'react'
import { Button } from 'react-bootstrap'

function TableRow({invoice}) {
  return (
    <tr>
              <td>{invoice.invoiceNo}</td>
              <td>{invoice.invoiceDate}</td>
              <td>{invoice.invoiceAmt}</td>
              <td>{invoice.companyName}</td>
              <td>{invoice.paidStatus?"Paid":"Not Paid"}</td>
              <td><Button variant={`${invoice.paidStatus?"outline-success":"outline-danger"}`}>Change</Button></td>
    </tr>
  )
}

export default TableRow