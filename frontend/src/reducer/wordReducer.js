import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  word : "",
};

const wordSlice = createSlice({
  name: "word",
  initialState,
  reducers: {
    setWord: (state, action) => {
      state.word = action.payload;
    },
    initWord: (state) => {
      state.word = "";
    },
  },
});
export const { setWord, initWord } = wordSlice.actions;
export default wordSlice.reducer;
