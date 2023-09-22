import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  accessToken: "",
  refreshToken: "",
  userId: 1,
  nickname: "",
  snsEmail: ""
};

const userSlice = createSlice({
  name: "member",
  initialState,
  reducers: {
    setUser: (state, action) => {
      state.accessToken = action.payload.token.accessToken;
      state.refreshToken = action.payload.token.refreshToken;
      state.userId = action.payload.user.userId;
      state.nickname = action.payload.user.nickname;
      state.snsEmail = action.payload.user.snsEmail;
    },
    initUser: (state) => {
      state.accessToken = "";
      state.refreshToken = "";
      state.userId = -1;
      state.nickname = "";
      state.snsEmail = "";
    },
  },
});
export const { setUser, initUser } = userSlice.actions;
export default userSlice.reducer;
