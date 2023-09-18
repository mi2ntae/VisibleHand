import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import http from "api/commonHttp";
import Streak from "react-github-contribution-calendar"
import color, { dark_grey } from "lib/style/colorPalette";
import { Background, BannerTitle } from 'styled';

export default function MyPageStreak({userId}) {
    const [data, setData] = useState({});
    const colors = ["lightgray", color.streakFirst, color.streakSecond, color.streakThird, color.streakFourth, color.streakFifth]
    const monthNames = [
        'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
        'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
    ]
    const weekNames = ['S', 'M', 'T', 'W', 'T', 'F', 'S'];

    let now = new Date();
    const until = now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate();
    useEffect(() => {
        http.get(`user/streak/${userId}`)
        .then(({data}) => {
            console.log(data);
            setData(variate(data));
        })
        .catch((err) => {
            console.log(err);
        })

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    const variate = (res) => {
        let k = {};
        for(let v of res) {
            k[v.createAt] = v.weight
        }
        return k;
    }
    return (
        <Background style={{gap: "1rem"}}>
            <TitleContainer>
                <BannerTitle style={{fontWeight: 500}}>활동기록</BannerTitle>
                <Button>월간</Button>
            </TitleContainer>
            <Streak weekNames={weekNames} monthNames={monthNames} panelAttributes={{rx: 3, ry: 3}} values={data} panelColors={colors} until={until}/>
        </Background>
    );
}

const TitleContainer = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
`

const Button = styled.div`
    font-size: 0.75rem;
    color: ${dark_grey};
    font-weight: 400;
`