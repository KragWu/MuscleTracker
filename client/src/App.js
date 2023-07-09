import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import HeaderLayout from "./components/HeaderLayout";
import PrepareSessionLayout from "./components/PrepareSessionLayout";
import HistorySessionLayout from "./components/HistorySessionLayout";
import DashboardLayout from "./components/DashboardLayout";
import SessionLayout from "./components/SessionLayout";
import LoginLayout from "./components/LoginLayout";
import "./Global.css"

//theme
import "primereact/resources/themes/lara-light-indigo/theme.css";     
//core
import "primereact/resources/primereact.min.css";
//icons
import "primeicons/primeicons.css";                                         
import RegisterLayout from "./components/RegisterLayout";
         

const App = () => {
  return <Routes>
        <Route path="/" element={<HeaderLayout />}>
          <Route path="login" element={<LoginLayout />} />
          <Route path="register" element={<RegisterLayout />} />
          <Route path="prepare" element={<PrepareSessionLayout />} />
          <Route path="history" element={<HistorySessionLayout />} />
          <Route path="dashboard" element={<DashboardLayout />} />
          <Route path="session" element={<SessionLayout />} />
          {/* Using path="*"" means "match anything", so this route
                acts like a catch-all for URLs that we don't have explicit
                routes for. */}
          <Route path="*" element={<Navigate to="/login" />} />
        </Route>
      </Routes>;
};

export default App;
