import React from 'react';
import './HumanResourcesSideBar.css';

const HumanResourcesSideBar = ({ onNewStaff }) => {
    return (
        <div className="sidebar">
            <div className="sidebar-logo">
                <img src="/logo.png" alt="Company Logo" className="logo-img" />
                <div className="sidebar-title">Human Resources Panel</div>

            </div>
            <h3>📁 Menu</h3>
            <ul>
                <li onClick={onNewStaff}>➕ New Staff</li>
                <li>📊 Reports</li>
                <li>📝 Leave Requests</li>
                <li>📥 Export Data</li>
            </ul>
        </div>
    );
};

export default HumanResourcesSideBar;
