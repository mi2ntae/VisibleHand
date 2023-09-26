import React from 'react';

function BtnImg({width, height, src, alt}) {
  return (
    <img
      width={width}
      height={height}
      src={src}
      alt={alt}
    />
  );
}

export default BtnImg;
