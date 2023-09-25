import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  accessToken: "",
  refreshToken: "",
  userId: 1,
  nickname: "",
  snsEmail: "",
  provider: ""
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
      state.provider = action.payload.user.provider;
    },
    initUser: (state) => {
      state.accessToken = "";
      state.refreshToken = "";
      state.userId = -1;
      state.nickname = "";
      state.snsEmail = "";
      state.provider = "";
    },
  },
});
export const { setUser, initUser } = userSlice.actions;
export default userSlice.reducer;
