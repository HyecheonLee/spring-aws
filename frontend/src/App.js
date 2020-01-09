import React from 'react';
import './App.css';
import {Admin, Resource} from 'react-admin';
import PostList from "./posts/PostList";
import customDataProvider from "./utils/customDataProvider";
import PostCreate from "./posts/PostCreate";
import PostEdit from "./posts/PostEdit";

const dataProvider = customDataProvider('/api/v1');

function App() {
  return (
      <Admin dataProvider={dataProvider}>
        <Resource name={"posts"} list={PostList} create={PostCreate} edit={PostEdit}/>
      </Admin>
  );
}

export default App;
