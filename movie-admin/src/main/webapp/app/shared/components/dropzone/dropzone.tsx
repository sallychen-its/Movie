import './dropzone.scss';

import React, {useEffect, useState} from 'react';
import {useDropzone} from 'react-dropzone';
import { Player } from 'video-react';

function Previews(props) {
  const { onDrop, files } = props;
  const {getRootProps, getInputProps} = useDropzone({
    accept: 'image/*, video/*',
    onDrop
  });

  const thumbs = files.map((file) => {
    console.warn(file);

    switch (file.type) {
      case "image": {
        return (
          <div className="thumb" key={file.name}>
          <div className="thumbInner">`
            <img
              src={file.preview}
              className="img"
            />
          </div>
        </div>);
        break;
      }
      case "video": {
        return (
          <div className="thumb" key={file.name}>
            <div className="thumbInner">`
              <img
                src="https://icon-library.com/images/icon-video-png/icon-video-png-4.jpg"
                className="img"
              />
            </div>
          </div>
        )
      }
      default: return;
    }
  });

  useEffect(() => () => {
    files.forEach(file => URL.revokeObjectURL(file.preview));
  }, [files]);

  return (
    <React.Fragment>
      <div {...getRootProps({className: 'dropzone'})}>
        <input {...getInputProps()} />
        <p>Drag drop some files here, or click to select files</p>
      </div>
      <aside className="thumbsContainer">
        {thumbs}
      </aside>
    </React.Fragment>
  );
}

export default Previews;
