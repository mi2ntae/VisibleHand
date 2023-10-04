import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  userId: 0,
  tabNo: 0,
  keyword: "",
  searchType: 0,
};

const tabSlice = createSlice({
  name: "mypageTab",
  initialState,
  reducers: {
    setTabNo: (state, action) => {
      state.tabNo = action.payload;
    },
    setKeyword: (state, action) => {
      state.keyword = action.payload;
    },
    setSearchType: (state, action) => {
      state.searchType = action.payload;
    },
    setUserId: (state, action) => {
      state.userId = action.payload;
    },
  },
});
export const { setTabNo, setKeyword, setSearchType, setUserId } =
  tabSlice.actions;
export default tabSlice.reducer;
