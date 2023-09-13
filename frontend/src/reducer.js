import { combineReducers } from "redux";
import userReducer from "./reducer/userReducer";
import mypageTabReducer from "./reducer/mypageTabReducer";
import storageSession from "redux-persist/lib/storage/session";
import persistReducer from "redux-persist/es/persistReducer";

const persistConfig = {
  key: "root",
  storage: storageSession,
  whitelist: ["user", "mypageTab"],
};

const rootReducer = combineReducers({
  user: userReducer,
  mypageTab: mypageTabReducer,
});
const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
