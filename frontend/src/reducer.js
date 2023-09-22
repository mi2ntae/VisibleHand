import { combineReducers } from "redux";
import userReducer from "./reducer/userReducer";
import mypageTabReducer from "./reducer/mypageTabReducer";
import cloudReducer from './reducer/wordCloudReducer';
import wordReducer from './reducer/wordReducer';
import storageSession from "redux-persist/lib/storage/session";
import persistReducer from "redux-persist/es/persistReducer";

const persistConfig = {
  key: "root",
  storage: storageSession,
  whitelist: ["user", "mypageTab", "cloud", "word"],
};

const rootReducer = combineReducers({
  user: userReducer,
  mypageTab: mypageTabReducer,
  cloud : cloudReducer,
  word : wordReducer
});
const persistedReducer = persistReducer(persistConfig, rootReducer);

export default persistedReducer;
