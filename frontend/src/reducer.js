import { combineReducers } from "redux";
import memberReducer from "./reducer/memberReducer";
import mypageTabReducer from "./reducer/mypageTabReducer";
import storageSession from "redux-persist/lib/storage/session";
import persistReducer from "redux-persist/es/persistReducer";

const persistConfig = {
  key: "root",
  storage: storageSession,
  whitelist: ["member", "mypageTab"],
};

const rootReducer = combineReducers({
  member: memberReducer,
  mypageTab: mypageTabReducer,
});
const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
