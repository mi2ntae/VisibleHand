import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import ScrapComponent from './ScrapComponent';
import http from 'api/commonHttp';
import NoContentComponent from './NoContentComponent';
import { useSelector } from 'react-redux';
import { useAutoAnimate } from '@formkit/auto-animate/react'
import { Pagination } from '@mui/material';

export default function MyPageScrap() {
    const [scraps, setScraps] = useState([{init: "0"}]);
    const userId = useSelector((state) => state.user.userId);
    const [load, setLoad] = useState(false);
    const [parent, enableAnimations] = useAutoAnimate(/* optional config */)
    const [maxPage, setMaxPage] = useState(1);
    const [pageNo, setPageNo] = useState(1);

    const deleteScrap = async (scrapId) => {
        await http.delete(`article/scrap/${scrapId}`)
        .then(({data}) => {
            console.log(data);
        })
        .catch((err) => {
            console.log(err);
        })

        await http.get(`article/scrap/${userId}?page=${pageNo-1}`)
        .then(({data}) => {
            setScraps(data.content);
            setMaxPage(data.totalPages)
            setLoad(true);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    useEffect(() => {
        http.get(`article/scrap/${userId}?page=${pageNo-1}`)
        .then(({data}) => {
            console.log(data.content);
            setScraps(data.content);
            setMaxPage(data.totalPages)
            setLoad(true);
        })
        .catch((err) => {
            console.log(err);
        })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [pageNo])
    
    return (
        <ScrapContainer>
            {(load && scraps.length === 0) ? <NoContentComponent text={"스크랩한 기사가 존재하지 않습니다."}></NoContentComponent> : <span></span>}

            <Scraps ref={parent}>
                {(load && scraps.length > 0) ? 
                scraps.map((scrap) => 
                    <ScrapComponent deleteScrap={deleteScrap} scrapId={scrap.scrapId} articleId={scrap.articleId} image={scrap.thumbnail} title={scrap.title}></ScrapComponent>
                )
                : <span></span>
                }
            </Scraps>
            
            {(load && scraps.length > 0) ? 
                <Pagination count={maxPage} page={pageNo} onChange={(event, value) => setPageNo(value)} style={{position: 'absolute', top: 600, left: 600, textAlign: 'center'}} />
                : <span></span>
            }
        </ScrapContainer>
    );
}

const ScrapContainer = styled.div`
    flex-wrap: wrap;
    width: 100%;
    dispaly: relative;
`;

const Scraps = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    flex: 1;
`;

