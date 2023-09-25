import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { useDispatch, useSelector } from 'react-redux';
import { setKeyword } from 'reducer/mypageTabReducer';
import http from 'api/commonHttp';
import NoContentComponent from './NoContentComponent';
import { Pagination } from '@mui/material';
import ReviewNoteComponent from './ReviewNoteComponent';

export default function MyPageReview() {
    const userId = useSelector((state) => state.user.userId);
    const [note, setNote] = useState([]);
    const dispatch = useDispatch();
    const [pageNo, setPageNo] = useState(1);
    const [maxPage, setMaxPage] = useState(1);
    const [load, setLoad] = useState(false);

    const getData = () => {
        http.get(`user/reviewnote/${userId}?page=${pageNo-1}`)
        .then(({data}) => {
            setNote(data.content);
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
        setTimeout(() => dispatch(setKeyword("")), 10)
        return () => {
            setPageNo(1);
        }
    }, [])

    return (
        <div>
            <ReviewContainer>
                {(load && note.length === 0) ? <NoContentComponent text={"틀린 퀴즈가 존재하지 않습니다."}></NoContentComponent> : <span></span>}
                <Notes>
                    {(load && note.length > 0) ? 
                    note.map((note) => 
                        <ReviewNoteComponent question={note.question} answer={note.answer}></ReviewNoteComponent>
                    )
                    : <span></span>
                    }
                </Notes>
                
                {(load && note.length > 0) ? 
                    <Pagination count={maxPage} page={pageNo} onChange={(event, value) => setPageNo(value)} style={{position: 'absolute', top: 620, left: 600, textAlign: 'center'}} />
                    : <span></span>
                }
            </ReviewContainer>
        </div>
    );
}

const ReviewContainer = styled.div`
    flex-wrap: wrap;
    width: 100%;
    dispaly: relative;
`;

const Notes = styled.div`
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    box-sizing: border-box;
    width: 100%;
    height: 100%;
    flex: 1;
`;

