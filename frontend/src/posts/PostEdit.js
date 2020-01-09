import React from 'react';
import {Edit, SimpleForm, TextInput} from 'react-admin';
import RichTextInput from 'ra-input-rich-text';

const PostEdit = (props) => {
  return (
      <div>
        <Edit {...props}>
          <SimpleForm>
            <TextInput source="title"/>
            <TextInput source="author"/>
            <RichTextInput multiline  source="content"/>
          </SimpleForm>
        </Edit>
      </div>
  );
};

export default PostEdit;