import React, { useEffect, useState } from 'react'
import CustomSelectSearch from '../comman/customselect/CustomSelectSearch'
import { useSelector } from 'react-redux';
import { PRODUCTS_URL } from '../../util/url';
import ProductEachRow from './ProductEachRow';
import axios from 'axios';

function ProductRows({products}) {
  const companyState = useSelector((state) => state.cmpny);
  
  const productState = useSelector((state) => state.prdt);

 

  console.log(productState)
  return (
    <>
      {(products||productState) && Object.values(productState).map((prdt) => {
        return (<ProductEachRow key={prdt.hsn} products={products} prdt={prdt} />)
      })}

      <ProductEachRow products={products} key={Object.values(productState).length}/>
    </>
  )
}

export default ProductRows