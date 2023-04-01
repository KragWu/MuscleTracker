import React from "react";
import { Outlet, useNavigate } from "react-router-dom";
import "./HeaderLayout.css"
import {TabMenu} from 'primereact/tabmenu'

const HeaderLayout = () => {

  const navigate=useNavigate();

  const items = [
    {id: 0, label: 'Prepare Session', command: () => navigate("/"), path: '/', className: "onglet-menu"},
    {id: 1, label: 'History', command: () => navigate("/history"), path: '/history', className: "onglet-menu"},
    {id: 2, label: 'Dashboard', command: () => navigate("/dashboard"), path: '/dashboard', className: "onglet-menu"}
  ]

  function computeUrl() {
    var result = 0;
    var url = window.location.pathname;
    items.forEach (it => {
      if (it.path == url) {
        result = it.id;
      }
    });
    return result;
  }

  return (
    <>
      <div>
        <div className="Title-App">
          <h1 className="Title-App" >Muscle Tracker</h1>
        </div>
        <TabMenu model={items} activeIndex={computeUrl()} />
      </div>

      {/* An <Outlet> renders whatever child route is currently active,
          so you can think about this <Outlet> as a placeholder for
          the child routes we defined above. */}
      <Outlet />
    </>
  );
}

export default HeaderLayout;