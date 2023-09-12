import React from 'react';
import "../css/Tab.css";
import { useDispatch } from 'react-redux';
import { setTabNo } from '../../../../../reducer/mypageTabReducer';

export default function MyPageTab({no, tabName, isFirst}) {
    const dispatch = useDispatch();

    const handleChange = (e) => {
        dispatch(setTabNo(parseInt(e.currentTarget.value)));
    }
    return (
        <label class="radio">
            {isFirst ? <input type="radio" name="radio" value={no} defaultChecked onChange={handleChange}/> : <input type="radio" name="radio" value={no} onChange={handleChange}/>}
            <span class="name">{tabName}</span>
        </label>
    );
}