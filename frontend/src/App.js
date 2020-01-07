import React from 'react';
import './App.css';
import {Route} from 'react-router-dom';

import PostSaveContainer from "./containers/PostSaveContainer";

function App() {
  return (
      <div className="App">
        <Route path="/posts/save" component={PostSaveContainer}/>
      </div>
  );
}

export default App;
