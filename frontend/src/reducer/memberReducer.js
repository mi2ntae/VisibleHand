import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  token: "",
  memberNo: -1,
  name: "",
  memberKind: -1,
  snsCheck: false,
};

const memberSlice = createSlice({
  name: "member",
  initialState,
  reducers: {
    setMember: (state, action) => {
      state.token = action.payload.token;
      state.memberNo = action.payload.member.memberNo;
      state.name = action.payload.member.name;
      state.memberKind = action.payload.member.memberKind;
      state.snsCheck = action.payload.member.snsCheck;
    },
    initMember: (state) => {
      state.token = "";
      state.memberNo = -1;
      state.name = "";
      state.memberKind = -1;
      state.snsCheck = false;
    },
  },
});
export const { setMember, initMember } = memberSlice.actions;
export default memberSlice.reducer;
