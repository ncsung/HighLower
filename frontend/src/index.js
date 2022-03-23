import ReactDOM from 'react-dom';
import App from './App';


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

const modifiedProductObj = {
  "https://img.ssensemedia.com/images/b_white,g_center,f_auto,q_auto:best/212169M201024_1/bode-ssense-exclusive-black-limited-edition-camel-patch-sweater.jpg": {
      "brand": "Bode",
      "content-length": "85742",
      "content-type": "image/jpeg",
      "item-name": "SSENSE Exclusive Black Limited Edition Camel Patch Sweater",
      "price": "1050",
      "product-id": "7686611"
  },
  "https://img.ssensemedia.com/images/b_white,g_center,f_auto,q_auto:best/212169M191002_1/bode-ssense-exclusive-off-white-limited-edition-sailboat-side-tie-trousers.jpg": {
      "brand": "Bode",
      "content-length": "74988",
      "content-type": "image/jpeg",
      "item-name": "SSENSE Exclusive Off-White Limited Edition Sailboat Side-Tie Trousers",
      "price": "775",
      "product-id": "7769781"
  },
  "https://img.ssensemedia.com/images/b_white,g_center,f_auto,q_auto:best/212169M192013_1/bode-ssense-exclusive-off-white-limited-edition-sailboat-shirt.jpg": {
      "brand": "Bode",
      "content-length": "60265",
      "content-type": "image/jpeg",
      "item-name": "SSENSE Exclusive White Limited Edition Tea Time Redwork Shirt",
      "price": "590",
      "product-id": "7686301"
  },
  "https://img.ssensemedia.com/images/b_white,g_center,f_auto,q_auto:best/212169M191001_1/bode-ssense-exclusive-red-shelter-plaid-side-tie-trousers.jpg": {
      "brand": "Bode",
      "content-length": "204156",
      "content-type": "image/jpeg",
      "item-name": "SSENSE Exclusive Red Shelter Plaid Side-Tie Trousers",
      "price": "775",
      "product-id": "7686121"
  },
  "https://img.ssensemedia.com/images/b_white,g_center,f_auto,q_auto:best/212169M192014_1/bode-ssense-exclusive-white-limited-edition-tea-time-redwork-shirt.jpg": {
      "brand": "Bode",
      "content-length": "57345",
      "content-type": "image/jpeg",
      "item-name": "SSENSE Exclusive White Limited Edition Tea Time Rugby Shorts",
      "price": "570",
      "product-id": "7686131"
  }
}

ReactDOM.render(
    <App products={modifiedProductObj}/>,
  document.getElementById('root')
);

