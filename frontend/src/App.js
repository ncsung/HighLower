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


const Product = ({link, brand, item, price}) => {
  if (item) {
    return (
      <div>
        <img src={`http://localhost:8080/api/items/${link}/image/download`} key={link} />
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

// const Products = ({images}) => {
//   return (
//     <div>

//       {Object.keys(images).map(image_key => {
//         return (
//           <>
//           <img src={`http://localhost:8080/api/items/${image_key}/image/download`}
//             key={image_key} />
//             <h1> {images[image_key]['brand']} </h1>
//             <p> {images[image_key]['item-name']} </p>
//             <p> {images[image_key]['price']} </p>
//             </>
//         )
//       })}

//     </div>
//   )
// }

function App() {
  
  const [products, setProducts] = useState([]);
  // const [productKeys, setProductKeys] = useState(null);
  let productKeys = [];

  useEffect(() => {
    backendService
      .getAll()
      .then(response => {
        // response is an object that lists ALL the s3 bucket items
        setProducts(response);
        })

  }, [])

//   const productObj = {
//     "Bode_SSENSE-Exclusive-Black-Limited-Edition-Camel-Patch-Sweater_7686611.jpg": {
//         "brand": "Bode",
//         "content-length": "85742",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Black Limited Edition Camel Patch Sweater",
//         "price": "1050",
//         "product-id": "7686611"
//     },
//     "Bode_SSENSE-Exclusive-Off-White-Limited-Edition-Sailboat-Side-Tie-Trousers_7769781.jpg": {
//         "brand": "Bode",
//         "content-length": "74988",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Off-White Limited Edition Sailboat Side-Tie Trousers",
//         "price": "775",
//         "product-id": "7769781"
//     },
//     "Bode_SSENSE-Exclusive-White-Limited-Edition-Tea-Time-Redwork-Shirt_7686301.jpg": {
//         "brand": "Bode",
//         "content-length": "60265",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive White Limited Edition Tea Time Redwork Shirt",
//         "price": "590",
//         "product-id": "7686301"
//     },
//     "Bode_SSENSE-Exclusive-Red-Shelter-Plaid-Side-Tie-Trousers_7686121.jpg": {
//         "brand": "Bode",
//         "content-length": "204156",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Red Shelter Plaid Side-Tie Trousers",
//         "price": "775",
//         "product-id": "7686121"
//     },
//     "Bode_SSENSE-Exclusive-White-Limited-Edition-Tea-Time-Rugby-Shorts_7686131.jpg": {
//         "brand": "Bode",
//         "content-length": "57345",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive White Limited Edition Tea Time Rugby Shorts",
//         "price": "570",
//         "product-id": "7686131"
//     },
//     "Bode_SSENSE-Exclusive-Off-White-Limited-Edition-Sailboat-Shirt_7686291.jpg": {
//         "brand": "Bode",
//         "content-length": "80337",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Off-White Limited Edition Sailboat Shirt",
//         "price": "590",
//         "product-id": "7686291"
//     },
//     "Bode_SSENSE-Exclusive-Brown-&amp;-Orange-Victoria-Sweater_7686621.jpg": {
//         "brand": "Bode",
//         "content-length": "72246",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Brown &amp",
//         "price": "715",
//         "product-id": "7686621"
//     },
//     "Bode_SSENSE-Exclusive-Brown-Limited-Edition-Twin-Antelope-Jacket_7686641.jpg": {
//         "brand": "Bode",
//         "content-length": "133250",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Brown Limited Edition Twin Antelope Jacket",
//         "price": "1975",
//         "product-id": "7686641"
//     },
//     "Bode_SSENSE-Exclusive-White-&amp;-Blue-Limited-Edition-Chenille-Fleur-Short-Sleeve-Shirt_7686201.jpg": {
//         "brand": "Bode",
//         "content-length": "79730",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive White &amp",
//         "price": "590",
//         "product-id": "7686201"
//     },
//     "Bode_SSENSE-Exclusive-Black-Linen-Pennant-Shirt_7686231.jpg": {
//         "brand": "Bode",
//         "content-length": "81280",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Black Linen Pennant Shirt",
//         "price": "650",
//         "product-id": "7686231"
//     },
//     "Bode_SSENSE-Exclusive-Pink-Embroidered-Canterbury-Shirt_7686151.jpg": {
//         "brand": "Bode",
//         "content-length": "80752",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Pink Embroidered Canterbury Shirt",
//         "price": "650",
//         "product-id": "7686151"
//     },
//     "Bode_SSENSE-Exclusive-Purple-Logo-Rugby-Shorts_7686651.jpg": {
//         "brand": "Bode",
//         "content-length": "70041",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive Purple Logo Rugby Shorts",
//         "price": "550",
//         "product-id": "7686651"
//     },
//     "Bode_SSENSE-Exclusive-White-Limited-Edition-London-Scene-Shirt_7686471.jpg": {
//         "brand": "Bode",
//         "content-length": "64522",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive White Limited Edition London Scene Shirt",
//         "price": "590",
//         "product-id": "7686471"
//     },
//     "Craig-Green_Black-Worker-Trousers_8893561.jpg": {
//         "brand": "Craig Green",
//         "content-length": "58693",
//         "content-type": "image/jpeg",
//         "item-name": "Black Worker Trousers",
//         "price": "825",
//         "product-id": "8893561"
//     },
//     "Bode_SSENSE-Exclusive-White-Limited-Edition-Cat-Motif-Shirt_7686491.jpg": {
//         "brand": "Bode",
//         "content-length": "58488",
//         "content-type": "image/jpeg",
//         "item-name": "SSENSE Exclusive White Limited Edition Cat Motif Shirt",
//         "price": "650",
//         "product-id": "7686491"
//     }
// };

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


  return (
    <div>
      <h1> Hello world! </h1>
      {/* <Products images={images}/> */}
      <Product link={randomProductKey} brand={brand} item={item} price={price}/>
    </div>
  );
}

export default App;
