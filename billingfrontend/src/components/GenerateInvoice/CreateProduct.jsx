import React, { useEffect, useState } from 'react'
import { PRODUCTS_URL } from '../../util/url';
import { useDispatch, useSelector } from 'react-redux';
import { Button, Form, Modal } from 'react-bootstrap';
import { updateShowPopup } from '../../redux/slices/popupSlice';
import axios from 'axios';

function CreateProduct({ showAlertOnAddPopup }) {
    const [show, setShow] = useState(true);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const dispatch = useDispatch()

    useEffect(() => {
        if (!show)
            dispatch(updateShowPopup({ showPopup: "", inputType: "", inputValue: "" }))

    }, [show])

    const { inputValue, inputType } = useSelector((state) => state.popup)
    const companyState = useSelector((state) => state.cmpny)


    const [productName, setProductName] = useState(inputType == "productName" ? inputValue : "");
    const [hsn, setHsn] = useState(inputType == "hsn" ? inputValue : "");
    const [price, setPrice] = useState(0);
    const [tax, setTax] = useState(0);
    const [unit, setUnit] = useState("");
    const [partNo, setPartNo] = useState("");

    const addProduct = () => {
        axios.post(PRODUCTS_URL + `?gstNo=${companyState.gstNo}`, {
            "productHsn":hsn,
            productName,
            productTax: tax,
            productQty: unit,
            partNo,
            rate: price
        })
            .then((data) => {
                setShow(false);
                showAlertOnAddPopup("Product Created", false)
            })
            .catch((err) => {
                console.log(err)
                setShow(false);
                showAlertOnAddPopup(err?.response?.data?.errorMessage, true)
            })
    }
    return (
        <>
            {show && <Modal show={true} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title style={{ color: 'blueviolet' }}>Add Product Details</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                            <Form.Label>Product Name</Form.Label>
                            <Form.Control
                                placeholder="ABC"
                                autoFocus
                                onChange={(e) => setProductName(e.target.value)}
                                defaultValue={inputType == "productName" ? inputValue : ""}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>HSN</Form.Label>
                            <Form.Control
                                placeholder="6116"
                                autoFocus
                                onChange={(e) => setHsn(e.target.value)}
                                defaultValue={inputType == "productHsn" ? inputValue : ""}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>Part No</Form.Label>
                            <Form.Control
                                placeholder="1223"
                                autoFocus
                                onChange={(e) => setPartNo(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>Price</Form.Label>
                            <Form.Control
                                placeholder="77.00"
                                autoFocus
                                onChange={(e) => setPrice(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>Tax</Form.Label>
                            <Form.Control
                                placeholder="23.5"
                                autoFocus
                                onChange={(e) => setTax(e.target.value)}
                            />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="">
                            <Form.Label>Unit</Form.Label>
                            <Form.Control
                                placeholder="PRS"
                                autoFocus
                                onChange={(e) => setUnit(e.target.value)}
                            />
                        </Form.Group>

                    </Form>

                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={() => addProduct()}>
                        Add Product
                    </Button>
                </Modal.Footer>
            </Modal>}
        </>
    )
}

export default CreateProduct