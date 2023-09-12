import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  tabNo: 0,
  asd: "",
};

const tabSlice = createSlice({
  name: "mypageTab",
  initialState,
  reducers: {
    setTabNo: (state, action) => {
      state.tabNo = action.payload;
    },
  },
});
export const { setTabNo } = tabSlice.actions;
export default tabSlice.reducer;
