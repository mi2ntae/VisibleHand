import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  kind : "FINANCE",
  date : ""
};

const cloudSlice = createSlice({
  name: "cloud",
  initialState,
  reducers: {
    setDate: (state, action) => {
      state.date = action.payload;
      state.kind = state.kind;
    },
    setKind: (state, action) => {
      state.kind = action.payload;
      state.date = state.date;
    },
  },
});
export const { setDate, setKind } = cloudSlice.actions;
export default cloudSlice.reducer;
