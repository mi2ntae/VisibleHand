import { combineReducers } from "redux";
import memberReducer from "./reducer/memberReducer";
import storageSession from "redux-persist/lib/storage/session";
import persistReducer from "redux-persist/es/persistReducer";

const persistConfig = {
  key: "root",
  storage: storageSession,
  whitelist: ["member"],
};

const rootReducer = combineReducers({
  member: memberReducer,
});
const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
