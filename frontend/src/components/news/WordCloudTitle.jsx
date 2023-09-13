import React, { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import styled from 'styled-components';

import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

export default function WordCloudTitle() {
	const [value, setValue] = useState();
	const onChange = (newValue) => {
		const dateFormat = dayjs(newValue).format("YYYY-MM-DD");
		setValue(dateFormat);
	}
	useEffect(()=>{
		const now = dayjs().format("YYYY-MM-DD");
		setValue(now);
	},[])
	return (
		<Container>
			<h1>&nbsp;&nbsp;{dayjs(value).get("month")+1}월 {dayjs(value).get("date")}일 오늘의 키워드</h1>
			
			{/* <LocalizationProvider dateAdapter={AdapterDayjs}>
				<DemoContainer components={['DatePicker']}>
					<DatePicker label="Basic date picker" />
				</DemoContainer>
			</LocalizationProvider> */}
		</Container>
	);
};

const Container = styled.div`
display: flex;
box-sizing: border-box;
padding : 0 auto;
line-height : .5;
`;
