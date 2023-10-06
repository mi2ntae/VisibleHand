import React from 'react';
import styled from 'styled-components';
import "components/user/mypage/css/Scrap.css";
import "components/user/mypage/css/ReviewNote.css"

export default function ReviewNoteComponent({question, answer}) {

    return (
        <ReviewNoteContainer>
            <div class="flip-card">
                <div class="flip-card-inner">
                    <div class="flip-card-front">
                        {/* <Image src='/images/reviewnote/q&a.png' alt="asdasdadss"></Image> */}
                        <Text>{question}</Text>
                    </div>
                    <div class="flip-card-back">
                        <Answer>- 정답 -</Answer>
                        <Answer>{answer}</Answer>
                        {/* <Button>본문 확인</Button> */}
                    </div>
                </div>
            </div>
        </ReviewNoteContainer>
    );
}

const ReviewNoteContainer = styled.div`
    width: 140px;
    max-height: 110px;
    margin-left: 35px;
    margin-right: 10px;
    margin-bottom: 50px;
`;

const Text = styled.div`
    padding: 10px;
    font-size: 0.8em;
    width: 120px;
    display: -webkit-box;
    -webkit-line-clamp: 9;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
    overflow: hidden;
    line-height: 14.5px;
`;

const Answer = styled.div`
    font-weight: bold;
    margin-bottom: 20px;
    justify-content: center;
    padding-left: 10px;
    font-size: 0.9em;
    width: 120px;
    display: -webkit-box;
    -webkit-line-clamp: 4;
    -webkit-box-orient: vertical;
    text-overflow: ellipsis;
    overflow: hidden;
`;

const Button = styled.button`
    justify-content: center;
    align-items: center;
    font-size: 0.8em;
    position: absolute;
    bottom: 5%;
    margin-left: 20%;
    text-align: center;
    width: 80px;
`;

const Image = styled.img`
    position: absolute;
    width: 45px;
    height: 30px;
    transform: translate(-50%, -50%);
    left: 1%;
    top: 5%;
    object-fit: fill;
`