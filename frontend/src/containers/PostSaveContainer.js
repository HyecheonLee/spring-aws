import React, {useState} from 'react';
import PostSave from "../components/PostSave";
import {savePost} from "../utils/PostApi";

const PostSaveContainer = () => {
  let [post, setPost] = useState({
    title: "",
    author: "",
    content: ""
  });
  const onChange = (e) => {
    const {name, value} = e.target;
    setPost(prevState => ({
      ...prevState,
      [name]: value
    }));
  };
  const onClick = (e) => {
    savePost(post)
    .then(response => {
      console.log(response);
    });
  };
  return (
      <div>
        <PostSave onChange={onChange} onSaveClick={onClick} title={post.title}
                  author={post.author} content={post.content}/>
      </div>
  );
};

export default PostSaveContainer;