import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import router from "./router.jsx";
import { RouterProvider } from "react-router-dom";
import store from "./store";
import { PersistGate } from "redux-persist/integration/react";
import { Provider } from "react-redux";
import { persistStore } from "redux-persist";
import { Interceptor } from "./api/commonHttp";

export let persistor = persistStore(store);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <Interceptor>
        <RouterProvider router={router} />
      </Interceptor>
    </PersistGate>
  </Provider>
);
