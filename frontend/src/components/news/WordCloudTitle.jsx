import React, { useEffect, useState } from 'react';
import dayjs from 'dayjs';
import styled from 'styled-components';
import Box from '@mui/material/Box';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import http from 'api/commonHttp'
import { useDispatch } from 'react-redux';
import { setDate } from 'reducer/wordCloudReducer';

export default function WordCloudTitle() {
	const [value, setValue] = useState({
		selected : "",
		recent : "",
		last : ""
	});
	const dispatch = useDispatch();
	const onChange = (newValue) => {
		setValue(old => ({...old, selected : dateFormat(newValue)}));
		dispatch(setDate(dateFormat(newValue)));
	}
	const dateFormat = (data) => {
		return dayjs(data).format("YYYY-MM-DD");
	}
	useEffect(()=>{
		http.get('/wordcloud/recent').then(({data}) => {
			setValue({selected : dateFormat(data.recent), recent : dateFormat(data.recent), last : dateFormat(data.last)});
			dispatch(setDate(dateFormat(data.recent)));
		})
	},[])
	return (
		<Container>
			<h1>&nbsp;&nbsp;{dayjs(value.selected).get("month")+1}월 {dayjs(value.selected).get("date")}일 <Span>인기 키워드</Span></h1>
			<LocalizationProvider dateAdapter={AdapterDayjs}>
        <Box width={150}>
          <DatePicker
            value={dayjs(value.selected)}
			format="YYYY-MM-DD"
            slotProps={{ textField: { size: 'small', display:'none' }} }
            minDate={dayjs(value.last)}
            maxDate={dayjs(value.recent)}
            onChange={onChange}
          />
        </Box>
      </LocalizationProvider>
		</Container>
	);
};

const Container = styled.div`
display: flex;
box-sizing: border-box;
padding : 0 auto;
line-height : .5;
justify-content : space-between;
align-items : center;
`;

const Span = styled.span`
font-size : 24px;
`;
