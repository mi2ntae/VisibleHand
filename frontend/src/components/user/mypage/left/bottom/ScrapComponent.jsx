import React from 'react';
import styled from 'styled-components';
import "components/user/mypage/css/Scrap.css";
import { useNavigate } from 'react-router-dom';

export default function ScrapComponent({deleteScrap, scrapId, articleId, image, title}) {
    const navi = useNavigate();
    return (
        <ScrapContainer  onClick={() => {navi(`/news/${articleId}`)}}>
            <div class="card">
                <div class="card-inner">
                    <div class="card-front">
                        <Folder src='/images/scrap/folder.png'/>
                        <Image src={image}></Image>
                        <Title>{title}</Title>
                        <DismissButton onClick={(e) => deleteScrap(e, scrapId)}></DismissButton>
                    </div>
                </div>
            </div>
        </ScrapContainer>
    );
}

const ScrapContainer = styled.div`
    display: flex;
    margin-top: 60px;
    margin-left: 60px;
    margin-right: 45px;
    margin-bottom: 100px;
`;

const Image = styled.img`
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 140px;
    height: 100%;
    border-radius: 8px;
`;

const Folder = styled.img`
    width: 170px;
    height: 150px;
    border-radius: 20px;
    box-shadow: -3px 3px 6px lightgrey;
`;

const Title = styled.div`
    // display: flex;
    position: absolute;
    top: 120%;
    left: 55%;
    transform: translate(-50%, -50%);
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    // white-space: normal;
    // word-wrap: break-word;
    width: 140px;
    // height: content;
    font-weight: bold;
    font-size: 0.5em;
    height: 20px;
`;

const DismissButton = styled.button`
    position: absolute;
    left: -40%;
    top: -60%;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.1rem 0.8rem;
    background-image: url(/images/scrap/scrap_icon.png);
    background-color: #Eff4FF;
    color: black;
    border: none;
    font-size: 1em;
    font-weight: 600;
    width: 10px;
    height: 25px;
    border-radius: 7px;
`;