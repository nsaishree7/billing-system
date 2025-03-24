import React, { useEffect, useRef, useState } from 'react'
import "./main.css"
import { useClickOutside } from '../useClickOutside';
import { useDispatch } from 'react-redux';
import { updateCompany } from '../../../redux/slices/companySlice';
import ToastHover from '../toast/ToastHover';
import { updatePo } from '../../../redux/slices/poSearchSlice';
import { updateProduct } from '../../../redux/slices/productSlice';

function CustomSelectSearch({ value,options,field,
    filter=()=>true
 }) {
    const [show, setShow] = useState(false);
    const ref = useRef(null);
   
    const changeShow = (bool = false) => {setShow(bool);setSearch("");}
    useClickOutside(ref, changeShow)

    const [selected, setSelected] = useState(value);
    console.log(selected)
    //redux
    const dispatch = useDispatch();
    const handleSelect = (value,selectedOption) => {
        setSelected(value);
        switch (getToastTitle(field)){
            case "Company":
                dispatch(updateCompany(selectedOption));
                break;
            case "Purchase Order Details":
                dispatch(updatePo(selectedOption));
                break;
            case "Product":
            dispatch(updateProduct(selectedOption));
            break;

        }        
        changeShow(false);
        setSearch("")
    }

    const [search, setSearch] = useState("")
    const [dummyOption,setDummyOption] = useState([...options]);
    
    const searchRef=useRef();
    useEffect(()=>{
        setDummyOption(options )
    },[options])
    useEffect(() => {
        console.log(options[0]?.[field])
        //pass a filter 

        //set default filter
        setDummyOption(options.filter((option)=>{
            return (search.trim()==''|option[field]+''.toLowerCase().includes(search.toLowerCase()))&& filter(option)
    }));
    }, [search])
    useEffect(()=>{
        if(show)searchRef.current.focus()
    },[show])


    useEffect(()=>{
        setSelected(value);
    },[value])
    
    const getToastTitle=(field)=>{
        switch(field){
            case 'companyName':return 'Company';
            case 'companyGst':return 'Company';
            case 'poNumber':return 'Purchase Order Details';
            case 'hsn':return 'Product'
            case 'productName':return 'Product'
            default:
            return 'Company';
        }
    }

    return (
        <div className="select-container position-relative" ref={ref} style={{padding:"0px", marginLeft:"5px"}}>
            {dummyOption.length==0 && (options.length!=0 || search!="") && selected=="" && <ToastHover message={field} field2={getToastTitle(field)} search={search}/>}
            <div className='select-box' >
                <input type='text' value={selected} readOnly placeholder='Select' onClick={() => changeShow(true)} className='shadow-sm border border-gray-300'/>
            </div>

            {show && <div className='searchContent'>
                <div>
                    <input type='text' ref={searchRef} placeholder='Search' onChange={(e) => setSearch(e.target.value)} />
                </div>
                <ul>
                    {
                        dummyOption.map((option, index) => (option[field] && <li  value={index} onClick={() => handleSelect(option[field],option)}>{option[field]}</li>))
                    }
                </ul>
            </div>}

            

        </div>
    )
}

export default CustomSelectSearch