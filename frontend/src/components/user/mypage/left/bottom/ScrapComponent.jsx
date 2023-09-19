import React from 'react';
import styled from 'styled-components';
import "components/user/mypage/css/Scrap.css";
import "components/user/mypage/css/MoveArticleBtn.css";
import ScrapButtonComponent from './ScrapButtonComponent';

export default function ScrapComponent({deleteScrap, scrapId, articleId, image, title}) {

    return (
        <ScrapContainer>
            <div class="card">
                <div class="card-inner">
                    <div class="card-front">
                        <Folder src='/images/scrap/folder.png'/>
                        <Image src={image}></Image>
                        <Title>{title}</Title>
                    </div>
                    <div class="card-back">
                        <Folder src='/images/scrap/folder_focus.png'/>
                        <ScrapButtonComponent />
                        <DismissButton onClick={() => deleteScrap(scrapId)}>x</DismissButton>
                    </div>
                </div>
            </div>
        </ScrapContainer>
    );
}

const ScrapContainer = styled.div`
    display: flex;
    margin-top: 50px;
    margin-left: 60px;
    margin-right: 45px;
    margin-bottom: 80px;
`;

const Image = styled.img`
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 120px;
    height: 100%;
`;

const Folder = styled.img`
    width: 180px;
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
`;

const DismissButton = styled.button`
    position: absolute;
    left: 95%;
    top: -30%;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0.1rem 0.8rem;
    background-color: #D0D1FF;
    color: black;
    border: none;
    font-size: 1em;
    font-weight: 600;
    width: 10px;
    height: 25px;
    border-radius: 7px;
    transition: .3s ease;

    &:hover{
        background-color: #ee0d0d;
        border: 2px solid #ee0d0d;
        color: #fff;
    }
`;