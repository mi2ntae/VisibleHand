import { Background, BannerTitle } from 'styled';
import styled from 'styled-components';

export default function MyPageRightBottom({userId}) {
    
    return (
        <Background>
            <BannerTitle style={{fontWeight: 500}}>활동뱃지</BannerTitle>
        </Background>
    );
}