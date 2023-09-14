import styled from 'styled-components';

export default function MyPageRightBottom({userId}) {
    
    return (
        <RightBottomContainer>
            <Title>활동뱃지</Title>
        </RightBottomContainer>
    );
}

const RightBottomContainer = styled.div`
    padding: 15px;
    min-height: 220px;
    margin-top: 10%;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    background-color: white;
    border-radius: 20px;
    box-shadow: 3px 3px 3px lightgrey;
    width: 100%;
`;

const Title = styled.div`
    color: black;
    margin-bottom: 10px;
    margin-left: 10px;
    font-weight: light;
`;