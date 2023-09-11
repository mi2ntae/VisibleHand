import { configureStore } from "@reduxjs/toolkit";
import persistedReducer from "./reducer.js";

const store = configureStore({
  reducer: persistedReducer,
  devTools: true,
});

export default store;
