import React, {useEffect, useState} from 'react'
import backendService from './services/backend'
import "./App.css"

const Products = ({images}) => {

  return (
    <div>

      {images.map(image => {
        return (
          <img src={`http://localhost:8080/api/test/${image.key}/image/download`}
               key = {image.key}/>
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
