import React from "react";
import { Outlet, useNavigate } from "react-router-dom";
import {TabMenu} from 'primereact/tabmenu'
import "../Global.css"

const HeaderLayout = () => {

  const navigate=useNavigate();

  const itemNoLogin = [
    {id: 0, label: 'Connexion', command: () => navigate("/login"), path: '/login'}
  ]

  const items = [
    {id: 0, label: 'Prepare Session', command: () => navigate("/prepare"), path: '/prepare'},
    {id: 1, label: 'History', command: () => navigate("/history"), path: '/history'},
    {id: 2, label: 'Dashboard', command: () => navigate("/dashboard"), path: '/dashboard'}
  ]

  function computeUrl(items) {
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
        <h1 className="Title-App centrage" >Muscle Tracker</h1>
        {
          sessionStorage.getItem("userId") == undefined ? <TabMenu model={itemNoLogin} activeIndex={computeUrl(itemNoLogin)} /> :          
        <TabMenu model={items} activeIndex={computeUrl(items)} />
        }
      </div>

      {/* An <Outlet> renders whatever child route is currently active,
          so you can think about this <Outlet> as a placeholder for
          the child routes we defined above. */}
      <Outlet />
    </>
  );
}

export default HeaderLayout;
