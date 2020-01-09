import React from 'react';
import {Create, SimpleForm, TextInput} from 'react-admin';
import RichTextInput from 'ra-input-rich-text';

const PostCreate = (props) => {
  return (
      <Create {...props}>
        <SimpleForm>
          <TextInput source="title"/>
          <TextInput source="author"/>
          <RichTextInput multiline  source="content"/>
        </SimpleForm>
      </Create>
  );
};

export default PostCreate;