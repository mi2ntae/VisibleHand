import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import http from "api/commonHttp";
// import Streak from "react-github-contribution-calendar"

export default function MyPageStreak({userId}) {
    const [data, setData] = useState([]);

    useEffect(() => {
        http.get(`user/streak/${userId}`)
        .then((res) => {
            console.log(res);
        })
        .catch((err) => {
            console.log(err);
        })

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    return (
        <StreakContainer>
           {/* <Streak>

           </Streak> */}
        </StreakContainer>
    );
}


const StreakContainer = styled.div`
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
    // background-color: red;
`;