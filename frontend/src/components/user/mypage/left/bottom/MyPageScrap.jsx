import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import ScrapComponent from './ScrapComponent';
import http from 'api/commonHttp';
import NoContentComponent from './NoContentComponent';
import { useSelector } from 'react-redux';
import { useAutoAnimate } from '@formkit/auto-animate/react'

export default function MyPageScrap() {
    const [scraps, setScraps] = useState([{init: "0"}]);
    const userId = useSelector((state) => state.user.userId);
    const [load, setLoad] = useState(false);
    const [parent, enableAnimations] = useAutoAnimate(/* optional config */)

    const deleteScrap = async (scrapId) => {
        await http.delete(`article/scrap/${scrapId}`)
        .then(({data}) => {
            console.log(data);
        })
        .catch((err) => {
            console.log(err);
        })

        await http.get(`article/scrap/${userId}?page=0`)
        .then(({data}) => {
            setScraps(data.content);
            setLoad(true);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    useEffect(() => {
        http.get(`article/scrap/${userId}?page=0`)
        .then(({data}) => {
            console.log(data.content);
            setScraps(data.content);
            setLoad(true);
        })
        .catch((err) => {
            console.log(err);
        })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    
    return (
        <ScrapContainer ref={parent}>
            {(load && scraps.length === 0) ? <NoContentComponent text={"스크랩한 기사가 존재하지 않습니다."}></NoContentComponent> : <span></span>}

            {(load && scraps.length > 0) ? 
                scraps.map((scrap) => 
                    <ScrapComponent deleteScrap={deleteScrap} scrapId={scrap.scrapId} articleId={scrap.articleId} image={scrap.thumbnail} title={scrap.title}></ScrapComponent>
                )
                : <span></span>
            }
        </ScrapContainer>
    );
}

const ScrapContainer = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin: auto;
    padding: 20px;
    box-sizing: border-box;
    width: 100%;
`;

