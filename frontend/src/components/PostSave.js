import React from 'react';

const PostSave = ({title, author, content, onChange, onSaveClick}) => {
  return (
      <div>
        <h1>게시글 등록</h1>
        <label>제목
          <input name={"title"} value={title} onChange={onChange}/>
        </label>
        <label>작성자
          <input name={"author"} value={author} onChange={onChange}/>
        </label>
        <label>내용
          <input name={"content"} value={content} onChange={onChange}/>
        </label>
        <button>취소</button>
        <button onClick={onSaveClick}>저장</button>
      </div>
  );
};

export default PostSave;