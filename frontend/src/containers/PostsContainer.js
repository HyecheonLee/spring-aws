import React, {useEffect, useState} from 'react';
import Posts from "../components/Posts";

const PostsContainer = () => {
  let [posts, setPosts] = useState();
  useEffect(() => {

  }, []);
  return (
      <Posts/>
  );
};

export default PostsContainer;