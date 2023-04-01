import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import HeaderLayout from "./components/HeaderLayout";
import PrepareSessionLayout from "./components/PrepareSessionLayout";
import HistorySessionLayout from "./components/HistorySessionLayout";
import DashboardLayout from "./components/DashboardLayout";
import "./App.css"

//theme
import "primereact/resources/themes/lara-light-indigo/theme.css";     
//core
import "primereact/resources/primereact.min.css";
//icons
import "primeicons/primeicons.css";                                         
         

const App = () => {
  return <Routes>
        <Route path="/" element={<HeaderLayout />}>
          <Route index element={<PrepareSessionLayout />} />
          <Route path="history" element={<HistorySessionLayout />} />
          <Route path="dashboard" element={<DashboardLayout />} />

          {/* Using path="*"" means "match anything", so this route
                acts like a catch-all for URLs that we don't have explicit
                routes for. */}
          <Route path="*" element={<Navigate to="/" />} />
        </Route>
      </Routes>;
};

export default App;
