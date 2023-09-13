import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  token: "",
  userId: -1,
  nickname: "",
};

const userSlice = createSlice({
  name: "member",
  initialState,
  reducers: {
    setUser: (state, action) => {
      state.token = action.payload.token;
      state.userId = action.payload.user.userId;
      state.nickname = action.payload.user.nickname;
    },
    initUser: (state) => {
      state.token = "";
      state.userId = -1;
      state.nickname = "";
    },
  },
});
export const { setUser, initUser } = userSlice.actions;
export default userSlice.reducer;
