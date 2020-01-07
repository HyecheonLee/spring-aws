import axios from "axios";

export function savePost(post) {
  return axios.post("/api/v1/posts", post);
}
