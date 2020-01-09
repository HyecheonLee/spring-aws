import React from 'react';
import {List, Datagrid, TextField,EditButton} from 'react-admin'

const PostList = (props) => {
  console.log(props);
  return (
      <List {...props}>
        <Datagrid>
          <TextField source="id" />
          <TextField source="title"/>
          <TextField source="author"/>
          <TextField source="content"/>
          <EditButton />
        </Datagrid>
      </List>
  );
};

export default PostList;