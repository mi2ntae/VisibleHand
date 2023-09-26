import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import ScrapComponent from './ScrapComponent';
import http from 'api/commonHttp';
import NoContentComponent from './NoContentComponent';
import { useDispatch, useSelector } from 'react-redux';
import { useAutoAnimate } from '@formkit/auto-animate/react'
import { Pagination } from '@mui/material';
import { setKeyword } from 'reducer/mypageTabReducer';

export default function MyPageScrap() {
    const [scraps, setScraps] = useState([{init: "0"}]);
    const userId = useSelector((state) => state.user.userId);
    const [load, setLoad] = useState(false);
    const [parent, enableAnimations] = useAutoAnimate(/* optional config */)
    const [maxPage, setMaxPage] = useState(1);
    const [pageNo, setPageNo] = useState(1);
    const keyword = useSelector((state) => state.mypageTab.keyword);
    const dispatch = useDispatch();
    const deleteScrap = async (e, scrapId) => {
        e.stopPropagation();
        if(!window.confirm("정말 삭제하시겠습니까?")) return;
        await http.delete(`article/scrap/${scrapId}`)
        .then(({data}) => {
            console.log(data);
        })
        .catch((err) => {
            console.log(err);
        })
        
        let uri = ``;
        if(!keyword) uri = `article/scrap/${userId}?page=${pageNo-1}`;
        else uri = `article/scrap/${userId}?keyword=${keyword}&page=${pageNo-1}`
        await http.get(uri)
        .then(({data}) => {
            setScraps(data.content);
            setMaxPage(data.totalPages)
            setLoad(true);
        })
        .catch((err) => {
            console.log(err);
        })
    }

    const getData = () => {
        let uri = ``;
        if(!keyword) uri = `article/scrap/${userId}?page=${pageNo-1}`;
        else uri = `article/scrap/${userId}?keyword=${keyword}&page=${pageNo-1}`
        http.get(uri)
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
        getData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [pageNo])

    useEffect(() => {
        setPageNo(1)
        getData();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [keyword])
    
    useEffect(() => {
        setTimeout(() => dispatch(setKeyword("")), 10)
        return () => {
            setPageNo(1);
            dispatch(setKeyword(""));
        }
    }, [])

    return (
        <ScrapContainer>
            {(load && scraps.length === 0) ? <NoContentComponent text={"스크랩한 기사가 존재하지 않습니다."}></NoContentComponent> : <span></span>}

            <Scraps>
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

