import styled from 'styled-components';
import { lightest_grey, light_grey } from "lib/style/colorPalette";
import { Link } from "react-router-dom";

export default function Article({ articleId, company, kind, title, thumbnail }) {
    const types = [
        {kor : "금융", eng : "FINANCE"},
        {kor : "증권", eng : "STOCK"},
        {kor : "산업/재계", eng : "INDUSTRY"},
        {kor : "중기/벤처", eng : "VENTURE"},
        {kor : "부동산", eng : "REAL_ESTATE"},
        {kor : "글로벌 경제", eng : "GLOBAL"},
        {kor : "생활경제", eng : "LIVING"},
        {kor : "경제 일반", eng : "GENERAL"}
    ];

    const EngToKorTitle = (eng) => {
        const matchingType = types.find((type) => type.eng === eng);
        if (matchingType) return matchingType.kor;
        return eng;
    };

    return (

        <Container>
            <Link to={`/news/${articleId}`}  style={{ color:'black' }}>
                <Box >
                    <div>
                        <div style={{paddingBottom:'10px'}}>
                            <span style={{color : 'navy', fontWeight :'700', paddingRight :'10px', display:'inline'}}>{company}</span> 
                            <span style={{color : light_grey, display:'inline'}}>{EngToKorTitle(kind)}</span>
                        </div>
                        <div>{title.slice(0,22)}...</div>
                    </div>
                    <div><img src={thumbnail} alt="" style={{width:'80px', height: '60px', borderRadius: '0.5em'}} />  </div>
                </Box>
            </Link>
            <Hr></Hr>
        </Container>
    );
}

const Container = styled.div`
height : 110px;

`;

const Box = styled.div`
display: flex;
justify-content: space-between; /* 요소들을 양쪽으로 붙입니다 */
align-items: center; /* 요소들을 수직 가운데로 정렬합니다 */
padding-bottom : 10px;
`;

const Hr = styled.hr`
background: ${lightest_grey};
height: 1px;
border: 0;
`;


