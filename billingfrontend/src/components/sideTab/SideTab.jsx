import React from 'react'
import { Button, Nav } from 'react-bootstrap'
import './sideTab.css';
import { useDispatch, useSelector } from 'react-redux';
import { change } from '../../redux/slices/menuSlice';
function SideTab() {

    const dispatch=useDispatch();
    const {selectedMenu:menu} = useSelector(state=>state.menus);
    console.log(menu)

    return (
        <div className='sidetab7'>

            <Nav className=" d-none d-md-block bg-light  border-end border-secondary shadow mb-5 bg-white rounded"
                activeKey="/home"
                

            >
                <div className="sidebar-sticky"></div>
                <Nav.Item className='head'>
                    <h3 >Billing App</h3>
                </Nav.Item>
                <Button className={`menuBtn ${menu=="invoiceGenerate"?'active':''}`} onClick={()=>dispatch(change({menu:"invoiceGenerate"}))}>
                    Invoice generate
                </Button>
                <Button className={`menuBtn ${menu=="paymentStatus"?'active':''}`} onClick={()=>dispatch(change({menu:"paymentStatus"}))}>
                    Payment status
                </Button>


            </Nav>

        </div>
    )
}

export default SideTab