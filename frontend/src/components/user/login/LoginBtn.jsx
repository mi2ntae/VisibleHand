import React from 'react';
import BtnImg from "components/user/login/BtnImg";
import styled from "styled-components";

export default function LoginBtn({ background, title, onClick, imgWidth, imgHeight, imgSrc, imgAlt, imgPadding }) {

  const BtnStyle = {
    position: 'relative',
    width: '300px',
    height: '60px',
    background: background || 'white',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: '15px',
    fontWeight: 'bold',
    borderRadius: '15px',
    cursor: 'pointer',
    border: 'none',
  };

  return (
      <Container>
        <button style={BtnStyle} onClick={onClick}>
        <div style={{ paddingRight: imgPadding }}>
            <BtnImg width={imgWidth} height={imgHeight} src={imgSrc} alt={imgAlt}></BtnImg>
        </div>
            {title}
        </button>
      </Container>
  );
}

const Container = styled.div`
    transform: translate(30%, 50%);
    margin-top: 10px;
`;