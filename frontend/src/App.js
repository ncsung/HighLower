import React, {useEffect, useState} from 'react'
import backendService from './services/backend'
import "./App.css"

// SOURCE: https://stackoverflow.com/questions/2450954/how-to-randomize-shuffle-a-javascript-array
function shuffle(array) {
  let currentIndex = array.length,  randomIndex;

  // While there remain elements to shuffle...
  while (currentIndex != 0) {

    // Pick a remaining element...
    randomIndex = Math.floor(Math.random() * currentIndex);
    currentIndex--;

    // And swap it with the current element.
    [array[currentIndex], array[randomIndex]] = [
      array[randomIndex], array[currentIndex]];
  }

  return array;
}


// Display an image from the DB with its brand, item name and price
const Product = ({link, brand, item, price}) => {
  if (item) {
    return (
      <div className='product-container'>
        {/* <img src={`http://localhost:8080/api/items/${link}/image/download`} key={link} /> */}
        <img src={link} key={link} />
        <h1> {brand} </h1>
        <p> {item} </p>
        <p> {price} </p>
      </div>
    )
  } else {
    return (
      <></>
    )
  }
}

function App({products}) {
  
  // const [products, setProducts] = useState([]);
  let productKeys = [];

  // useEffect(() => {
  //   backendService
  //     .getAll()
  //     .then(response => {
  //       // response is an object that lists ALL the s3 bucket items
  //       setProducts(response);
  //       })

  // }, [])


  if (products) {
    // Loop through Object, and get ALL its keys into a flat array
    Object.keys(products).map(image_key => {
      productKeys.push(image_key);
    })

    // Shuffle the array
    productKeys = shuffle(productKeys);
  }


  // // As the array is randomized, we can simply pop from the end - this ensures we never get the same product twice
  let randomProductKey = "";
  let brand = "";
  let item = "";
  let price = "";
  if (productKeys.length > 0) {
    // console.log(productKeys)
    randomProductKey =  productKeys.pop();
    brand = products[randomProductKey]['brand'];
    item = products[randomProductKey]['item-name'];
    price = products[randomProductKey]['price'];
  }

  let randomProductKey2 = "";
  let brand2 = "";
  let item2 = "";
  let price2 = "";
  if (productKeys.length > 0) {
    // console.log(productKeys)
    randomProductKey2 =  productKeys.pop();
    brand2 = products[randomProductKey2]['brand'];
    item2 = products[randomProductKey2]['item-name'];
    price2 = products[randomProductKey2]['price'];
  }

// TODO: Figure out how to make each image take up the half the width and the full height
//    Scale up, keep aspect ratio but crop?

  return (
    <div>
      <h1> Hello world! </h1>
      <div className = 'frame'>
        <Product link={randomProductKey} brand={brand} item={item} price={price}/>
        <Product link={randomProductKey2} brand={brand2} item={item2} price={price2}/>
      </div>
    </div>
  );
}

export default App;
