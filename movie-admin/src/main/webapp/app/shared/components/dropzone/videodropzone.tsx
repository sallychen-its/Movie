import React from 'react'
import Dropzone from 'react-dropzone-uploader'
import 'react-dropzone-uploader/dist/styles.css'

const MyUploader = (props) => {

  const getUploadParams = ({ meta }) => { return { url: 'http://localhost:9000/api/media/upload' } }
  const handleChangeStatus = ({ meta, file }, status) => { console.warn(status, meta, file) }
  const handleSubmit = (files, allFiles) => {
    console.warn(files.map(f => f.meta))
    allFiles.forEach(f => f.remove())
  }

  return (
    <Dropzone
      getUploadParams={getUploadParams}
      onChangeStatus={handleChangeStatus}
      onSubmit={handleSubmit}
      accept="image/*,audio/*,video/*"
    />
  )
}

export default MyUploader;
