import React from 'react'
import { useSelector } from 'react-redux'
import Title from './comman/Title'
import SideTab from './sideTab/SideTab'
import "./main.css"
import Invoice from './GenerateInvoice/Invoice'
import PaymentStatus from './paymentStatus/PaymentStatus'

function Content() {
    const { selectedMenu, title } = useSelector((state) => state.menus)
    return (
        <>
            <SideTab />
            <Title title={title} />
            {
                selectedMenu=="invoiceGenerate" && <Invoice/>
            }
            {
                selectedMenu=="paymentStatus" && <PaymentStatus/>
            }
        </>
    )
}

export default Content