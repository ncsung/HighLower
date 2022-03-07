import React, {useEffect, useState} from 'react'
import backendService from './services/backend'
import "./App.css"

const Products = ({images}) => {


  return (
    <div>

      {Object.keys(images).map(image_key => {
        return (
          <>
          <img src={`http://localhost:8080/api/items/${image_key}/image/download`}
            key={image_key} />
            <h1> {images[image_key]['brand']} </h1>
            <p> {images[image_key]['item-name']} </p>
            <p> {images[image_key]['price']} </p>
            </>
        )
      })}

    </div>
  )

}

function App() {
  
  const [images, setImages] = useState([]);

  useEffect(() => {
    backendService
      .getAll()
      .then(response => {
        setImages(response);
      })

  }, [])


  return (
    <div>
      <h1> Hello world! </h1>
      <Products images={images}/>
    </div>
  );
}

export default App;
